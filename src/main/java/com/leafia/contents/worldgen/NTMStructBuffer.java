package com.leafia.contents.worldgen;

import com.leafia.contents.gear.wands.ItemWandSaving.SavingProperty;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.llib.LeafiaLib;
import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.group.LeafiaMap;
import com.llib.technical.FifthString;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

public class NTMStructBuffer {
	public enum NTMStructVersion {
		V0_FIRST_TEST,
		V1_REPLACEMENT_MAP_ADDITION,
		V2_BETTER_TE_COMPRESSION,
		;
		public boolean isUpOrNewerThan(NTMStructVersion other) { return this.ordinal() >= other.ordinal(); }
		public boolean isNewerThan(NTMStructVersion other) { return this.ordinal() > other.ordinal(); }
		public boolean isOlderThan(NTMStructVersion other) { return this.ordinal() < other.ordinal(); }
		public boolean isUpOrOlderThan(NTMStructVersion other) { return this.ordinal() <= other.ordinal(); }
		public static final NTMStructVersion latest = V2_BETTER_TE_COMPRESSION;
	}
	public enum NTMStructAngle {
		ORIGINAL(1,1,0,0),RIGHT(0,0,-1,1),BACK(-1,-1,0,0),LEFT(0,0,1,-1);
		final int xx; final int zz; final int xz; final int zx;
		NTMStructAngle(int xx,int zz,int xz,int zx) {
			this.xx = xx;
			this.zz = zz;
			this.xz = xz;
			this.zx = zx;
		}
		public NTMStructAngle getRight() { return NTMStructAngle.values()[Math.floorMod(this.ordinal()+1,4)]; }
		public NTMStructAngle getLeft() { return NTMStructAngle.values()[Math.floorMod(this.ordinal()-1,4)]; }
		public int getX(int x,int z) { return x*xx+z*xz; }
		public int getZ(int x,int z) { return z*zz+x*zx; }
	}
	public final LeafiaBuf buf;
	public final int bitNeedle;
	public final Block[] paletteBlock;
	public final NBTTagCompound[] paletteTEs;
	public final Vec3i size;
	public final Vec3i offset;
	public final EnumFacing originalFace;
	public final NTMStructVersion version;
	public NTMStructAngle rotation = NTMStructAngle.ORIGINAL;
	public static NTMStructBuffer fromFiles(String path) {
		LeafiaBuf buffer = new LeafiaBuf(null);
		try {
			buffer.bytes = Files.readAllBytes(Paths.get(path));
			buffer.writerIndex = buffer.bytes.length*8;
		} catch (IOException exception) {
			LeafiaDevFlaw flaw = new LeafiaDevFlaw("Exception while tryina read "+path+" as .ntmstruct");
			flaw.setStackTrace(exception.getStackTrace());
			throw flaw;
		}
		return new NTMStructBuffer(buffer);
	}
	public static NTMStructBuffer fromResources(ResourceLocation res) {
		//Minecraft.getMinecraft().getResourceManager().getResource(res);

		return null;
	}
	public NTMStructBuffer(LeafiaBuf buf) {
		super();
		version = NTMStructVersion.values()[buf.readUnsignedByte()];
		offset = buf.readVec3i();
		size = buf.readVec3i();
		originalFace = EnumFacing.byHorizontalIndex(buf.readByte());
		paletteBlock = new Block[buf.readUnsignedShort()];
		for (int i = 0; i < paletteBlock.length; i++) {
			boolean hasNext = true;
			while (hasNext) {
				FifthString fifth = buf.readFifthString();
				if (paletteBlock[i] == null)
					paletteBlock[i] = Block.getBlockFromName(LeafiaLib.stringSwap(fifth.toString(), ' ', ':'));
				hasNext = buf.extract(1) > 0;
			}
		}
		if (version.isOlderThan(NTMStructVersion.V2_BETTER_TE_COMPRESSION)) {
			paletteTEs = new NBTTagCompound[buf.readUnsignedShort()];
			for (int i = 0; i < paletteTEs.length; i++) {
				byte[] bytes = new byte[buf.readInt()];
				buf.readBytes(bytes);
				ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
				try {
					paletteTEs[i] = CompressedStreamTools.readCompressed(stream);
				} catch (IOException ignored) {}
			}
		} else {
			byte[] bytes = new byte[buf.readInt()];
			buf.readBytes(bytes);
			ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			try {
				NBTTagCompound attr = CompressedStreamTools.readCompressed(stream);
				NBTTagList entities = attr.getTagList("te",10);
				paletteTEs = new NBTTagCompound[entities.tagCount()];
				for (int i = 0; i < paletteTEs.length; i++)
					paletteTEs[i] = entities.getCompoundTagAt(i);
			} catch (IOException fatal) {
				LeafiaDevFlaw flaw = new LeafiaDevFlaw("Error trying to decode NBTStruct V2+ attributes");
				flaw.setStackTrace(fatal.getStackTrace());
				throw flaw;
			}
		}
		this.buf = buf;
		bitNeedle = buf.readerIndex;
	}
	public NTMStructBuffer rotateToFace(EnumFacing face) {
		rotation = NTMStructAngle.values()[Math.floorMod(face.getHorizontalIndex()-originalFace.getHorizontalIndex(),4)];
		return this;
	}
	public void build(World world,BlockPos origin) {
		buf.readerIndex = bitNeedle;
		BlockPos start = origin.add(rotation.getX(offset.getX(),offset.getZ()),offset.getY(),rotation.getZ(offset.getX(),offset.getZ()));
		int sx = size.getX()+1;
		int sy = size.getY()+1;
		int sz = size.getZ()+1;
		Map<BlockPos,NBTTagCompound> tebuffer = new LeafiaMap<>();
		SavingProperty property = null;
		int repeats = 0;
		for (int i = 0; i < sx*sy*sz; i++) {
			int x = Math.floorMod(i,sx);
			int y = Math.floorMod(i/sx,sy);
			int z = Math.floorMod(i/sx/sy,sz);
			BlockPos pos = start.add(rotation.getX(x,z),y,rotation.getZ(x,z));
			if (repeats <= 0) {
				property = new SavingProperty();
				int value = buf.readUnsignedShort();
				int modifier = value>>>13&0b11;
				repeats = value&(1<<13)-1;
				if (modifier == 0b01)
					property.ignore = true;
				else {
					if (modifier == 0b10) property.replaceAirOnly = true;
					Block block = paletteBlock[buf.readUnsignedShort()];
					if (block == null)
						block = Blocks.AIR;
					if ((value>>>15&1) > 0)
						property.state = block.getStateFromMeta(buf.readInt());
					else
						property.state = block.getDefaultState();
					if (modifier == 0b11)
						property.entity = buf.readUnsignedShort();
					for (IProperty<?> key : property.state.getPropertyKeys()) {
						if (key instanceof PropertyDirection) {
							PropertyDirection cast = (PropertyDirection)key;
							EnumFacing facing = property.state.getValue(cast);
							if (facing.getYOffset() == 0 && rotation != NTMStructAngle.ORIGINAL) {
								switch(rotation) {
									case RIGHT: property.state = property.state.withProperty(cast,facing.rotateY()); break;
									case BACK: property.state = property.state.withProperty(cast,facing.getOpposite()); break;
									case LEFT: property.state = property.state.withProperty(cast,facing.getOpposite().rotateY()); break;
								}
							}
						}
					}
				}
			}
			if (!property.ignore) {
				world.setBlockState(pos,property.state,0b00010);
				if (property.entity != null)
					tebuffer.put(pos,paletteTEs[property.entity]);
			}
			repeats--;
		}
		for (Entry<BlockPos,NBTTagCompound> entry : tebuffer.entrySet()) {
			TileEntity te = world.getTileEntity(entry.getKey());
			if (te != null) {
				BlockPos pos = te.getPos();
				te.deserializeNBT(entry.getValue());
				te.setPos(pos);
			} else
				throw new LeafiaDevFlaw("Tile entity could not be created");
		}
	}
}