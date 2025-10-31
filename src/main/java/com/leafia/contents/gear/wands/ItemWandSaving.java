package com.leafia.contents.gear.wands;

import com.hbm.items.ModItems;
import com.hbm.main.ClientProxy;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import com.hbm.util.Tuple.Pair;
import com.leafia.contents.worldgen.NTMStructBuffer.NTMStructVersion;
import com.leafia.dev.LeafiaUtil;
import com.leafia.dev.custompacket.LeafiaCustomPacket;
import com.leafia.dev.custompacket.LeafiaCustomPacketEncoder;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.passive.rendering.TopRender.Highlight;
import com.llib.LeafiaLib;
import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.exceptions.messages.TextWarningLeafia;
import com.llib.group.LeafiaMap;
import com.llib.technical.FifthString;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Consumer;

import static net.minecraft.util.EnumActionResult.PASS;
import static net.minecraft.util.EnumActionResult.SUCCESS;

public class ItemWandSaving extends Item {
	@SideOnly(Side.CLIENT) Highlight savingHighlight;
	Highlight highlight = null;
	public ItemWandSaving(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		ModItems.ALL_ITEMS.add(this);
		if (MainRegistry.proxy instanceof ClientProxy) {
			highlight = new Highlight();
			highlight.setColor(0x90FF30);
			savingHighlight = new Highlight();
			savingHighlight.color = LeafiaUtil.colorFromTextFormat(TextFormatting.GOLD);
			savingHighlight.colorTop = savingHighlight.color|0xFF000000;
			savingHighlight.label = new Object[]{TextFormatting.BOLD+"Saving Structure"};
			try {
				Files.createDirectory(Paths.get("ntmstructs"));
				List<String> list = new ArrayList<>();
				list.add("Structures saved with /hbmleaf wand save <name> will end up here");
				list.add("Can be loaded with Structure Wand!");
				Files.write(Paths.get("ntmstructs/README.txt"),list);
			} catch (IOException ignored) {}
		}
	}
	@Override
	public void addInformation(ItemStack stack,@Nullable World worldIn,List<String> tooltip,ITooltipFlag flagIn) {
		tooltip.add(I18nUtil.resolveKey("desc.creative"));
		tooltip.add(I18nUtil.resolveKey("desc.savingwand.1"));
		tooltip.add(I18nUtil.resolveKey("desc.savingwand.2"));
		tooltip.add(I18nUtil.resolveKey("desc.savingwand.3"));
		tooltip.add(I18nUtil.resolveKey("desc.savingwand.4"));
		tooltip.add(I18nUtil.resolveKey("desc.savingwand.5"));
	}
	public void resizeSelection(NBTTagCompound nbt,EnumFacing side,boolean shift) {
		int x0 = nbt.getInteger("selectionX0");
		int y0 = nbt.getInteger("selectionY0");
		int z0 = nbt.getInteger("selectionZ0");
		int x1 = nbt.getInteger("selectionX1");
		int y1 = nbt.getInteger("selectionY1");
		int z1 = nbt.getInteger("selectionZ1");
		int add = shift ? -1 : 1;
		switch(side.getOpposite()) {
			case EAST: x1 = Math.max(x1+add,x0); break;
			case WEST: x0 = Math.min(x0-add,x1); break;
			case UP: y1 = Math.max(y1+add,y0); break;
			case DOWN: y0 = Math.min(y0-add,y1); break;
			case SOUTH: z1 = Math.max(z1+add,z0); break;
			case NORTH: z0 = Math.min(z0-add,z1); break;
		}
		nbt.setInteger("selectionX0",x0);
		nbt.setInteger("selectionY0",y0);
		nbt.setInteger("selectionZ0",z0);
		nbt.setInteger("selectionX1",x1);
		nbt.setInteger("selectionY1",y1);
		nbt.setInteger("selectionZ1",z1);
		x1++; y1++; z1++;
		if (highlight != null) {
			highlight.center = new Vec3d(x0/2d+x1/2d,y0/2d+y1/2d,z0/2d+z1/2d);
			highlight.size = new Vec3d(x1-x0,y1-y0,z1-z0);
			highlight.show();
		}
	}
	public static class HighlightSavingWandRemove implements LeafiaCustomPacketEncoder {
		@Override
		public void encode(LeafiaBuf buf) {}
		@Nullable
		@Override
		public Consumer<MessageContext> decode(LeafiaBuf buf) {
			return (ctx)->{
				ModItems.wand_leaf.highlight.hide();
			};
		}
	}
	public static class HighlightSavingWandSave implements LeafiaCustomPacketEncoder {
		BlockPos min = null;
		BlockPos max = null;
		@Override
		public void encode(LeafiaBuf buf) {
			buf.writeBoolean(min != null);
			if (min != null) {
				buf.writeVec3i(min);
				buf.writeVec3i(max);
			}
		}
		@Nullable
		@Override
		public Consumer<MessageContext> decode(LeafiaBuf buf) {
			if (buf.readBoolean()) {
				min = buf.readPos();
				max = buf.readPos();
			}
			return (ctx)->{
				Highlight high = ModItems.wand_leaf.savingHighlight;
				if (min != null) {
					high.center = new Vec3d(min).scale(0.5).add(new Vec3d(max).scale(0.5)).add(0.5,0.5,0.5);
					high.size = new Vec3d(max).subtract(new Vec3d(min)).add(1,1,1);
					high.show();
				} else
					high.hide();
			};
		}
	}
	public static class HighlightSavingWandProduct implements LeafiaCustomPacketEncoder {
		String name = null;
		byte[] data = null;
		public HighlightSavingWandProduct() {}
		public HighlightSavingWandProduct(String name,byte[] bytes) {
			this.name = name;
			this.data = bytes;
		}
		@Override
		public void encode(LeafiaBuf buf) {
			buf.writeUTF8String(name);
			buf.writeInt(data.length);
			buf.writeBytes(data);
		}
		@Nullable
		@Override
		public Consumer<MessageContext> decode(LeafiaBuf buf) {
			name = buf.readUTF8String();
			data = new byte[buf.readInt()];
			buf.readBytes(data);
			return (ctx)->{
				try {
					Files.write(Paths.get("ntmstructs/"+name+".ntmstruct"),data);
				} catch (IOException exception) {
					Minecraft.getMinecraft().player.sendMessage(new TextWarningLeafia("Couldn't save structure "+name+": "+exception.getMessage()));
					return;
				}
				Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Save complete!"));
			};
		}
	}
	public boolean tryRemove(EntityPlayer player) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.getItem() != ModItems.wand_leaf)
			stack = player.getHeldItemOffhand();
		if (stack.getItem() != ModItems.wand_leaf)
			return false;
		stack.setTagCompound(null);
		LeafiaCustomPacket.__start(new HighlightSavingWandRemove()).__sendToClient(player);
		return true;
	}
	public static class SavingProperty {
		public IBlockState state;
		public Integer entity = null;
		public boolean replaceAirOnly = false;
		public boolean ignore = false;
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof SavingProperty) {
				SavingProperty property = (SavingProperty)obj;
				if (this.state != null) {
					if (property.state == null || !property.state.equals(this.state)) return false;
				} else {
					if (property.state != null) return false;
				}
				if (property.entity != null) return false;
				if (property.replaceAirOnly != this.replaceAirOnly) return false;
				return property.ignore == this.ignore;
			}
			return false;
		}
	}
	public static abstract class SavingBuffer {
		final short identifier;
		LeafiaMap<Block,List<Block>> replMap;
		public SavingBuffer(Random rand) {
			short id = (short)rand.nextInt(65536);
			while (waitingTasks.containsKey(id)) id++;
			identifier = id;
			waitingTasks.put(identifier,this);
		}
		public abstract void confirm(LeafiaBuf buf);
	}
	public static final LeafiaMap<Short,SavingBuffer> waitingTasks = new LeafiaMap<>();
	public Runnable darnit = null;
	public int trySave(EntityPlayer player,String name) {
		ItemStack stack = player.getHeldItemMainhand();
		if (stack.getItem() != ModItems.wand_leaf)
			stack = player.getHeldItemOffhand();
		if (stack.getItem() != ModItems.wand_leaf)
			return 1;
		NBTTagCompound nbt = stack.getTagCompound();
		if (nbt == null) return 2;
		if (player.getServer() == null) return 3;
		if (nbt.hasKey("selectionX0") && nbt.hasKey("selectionX1")) {
			BlockPos start = new BlockPos(nbt.getInteger("selectionX0"),nbt.getInteger("selectionY0"),nbt.getInteger("selectionZ0"));
			Vec3i size = new Vec3i(
					nbt.getInteger("selectionX1")-nbt.getInteger("selectionX0"),
					nbt.getInteger("selectionY1")-nbt.getInteger("selectionY0"),
					nbt.getInteger("selectionZ1")-nbt.getInteger("selectionZ0")
			);
			Vec3i offset = start.subtract(player.getPosition());
			EnumFacing face = player.getHorizontalFacing();
			HighlightSavingWandSave packet = new HighlightSavingWandSave();
			packet.min = start;
			packet.max = start.add(size);
			LeafiaCustomPacket.__start(packet).__sendToAll();
			Runnable callback = ()->{
				darnit = null;
				World world = player.world;
				if (world == null)
					throw new RuntimeException("Uh sir, EntityPlayer.world is null... I thought of reporting this as chat message, but that also requires World. Where do I report this error?");
				int sx = size.getX()+1;
				int sy = size.getY()+1;
				int sz = size.getZ()+1;
				LeafiaMap<Block,List<Block>> blocks = new LeafiaMap<>();
				//List<byte[]> entities = new ArrayList<>();
				List<Pair<SavingProperty,Integer>> tiles = new ArrayList<>();
				NBTTagList entities = new NBTTagList();
				for (int i = 0; i < sx*sy*sz; i++) {
					int x = Math.floorMod(i,sx);
					int y = Math.floorMod(i/sx,sy);
					int z = Math.floorMod(i/sx/sy,sz);
					BlockPos pos = start.add(x,y,z);

					SavingProperty property = new SavingProperty();
					IBlockState state = world.getBlockState(pos);
					property.state = state;

					TileEntity entity = world.getTileEntity(pos);
					if (entity != null) {
						if (!entity.isInvalid()) {
							NBTTagCompound tag = entity.serializeNBT();
							entities.appendTag(tag);
							property.entity = entities.tagCount()-1;
							/*
							ByteArrayOutputStream stream = new ByteArrayOutputStream(0);
							try {
								CompressedStreamTools.writeCompressed(tag,stream);
								entities.add(stream.toByteArray());
								property.entity = entities.size()-1;
							} catch (IOException error) {
								player.sendMessage(new TextWarningLeafia("TileEntity at "+pos.getX()+","+pos.getY()+","+pos.getZ()+" failed to save: "+error.getMessage()));
							}*/
						}
					}
					if (tiles.size() > 0) {
						Pair<SavingProperty,Integer> pair = tiles.get(tiles.size()-1);
						if (pair.getA().equals(property)) {
							if (pair.getB() <= 8191) {
								pair.setB(pair.getB()+1);
								continue;
							}
						}
					}
					tiles.add(new Pair<>(property,1));
					if (!blocks.containsKey(state.getBlock()))
						blocks.put(state.getBlock(),new ArrayList<>());
				}
				////////////////////////////////////////////////////////////////////////////////
				SavingBuffer task = new SavingBuffer(world.rand) {
					@Override
					public void confirm(LeafiaBuf buf) {
						buf.writeByte(NTMStructVersion.latest.ordinal());
						buf.writeVec3i(offset);
						buf.writeVec3i(size);
						buf.writeByte(face.getHorizontalIndex());
						buf.writeShort(blocks.size());
						for (Entry<Block,List<Block>> entry : blocks.entrySet()) {
							Block block = entry.getKey();
							for (int i = 0; i < entry.getValue().size()+1; i++) {
								if (i > 0) {
									block = entry.getValue().get(i-1);
									buf.insert(1,1);
								}
								buf.writeFifthString(new FifthString(LeafiaLib.stringSwap(block.getRegistryName().toString(),":".charAt(0)," ".charAt(0))));
							}
							buf.insert(0,1);
						}
						if (entities.tagCount() <= 0)
							buf.writeInt(0);
						else {
							NBTTagCompound tag = new NBTTagCompound();
							tag.setTag("te",entities);
							ByteArrayOutputStream stream = new ByteArrayOutputStream(0);
							try {
								CompressedStreamTools.writeCompressed(tag,stream);
							} catch (IOException error) {
								player.sendMessage(new TextWarningLeafia(error.toString()));
								error.printStackTrace();
								return;
							}
							byte[] bytes = stream.toByteArray();
							buf.writeInt(bytes.length);
							buf.writeBytes(bytes);
						}
						/*
						buf.writeShort(entities.size());
						for (byte[] entity : entities) {
							buf.writeInt(entity.length);
							buf.writeBytes(entity);
						}*/
						for (Pair<SavingProperty,Integer> pair : tiles) {
							SavingProperty property = pair.getA();
							Integer meta = null;
							buf.insert(pair.getB(),13);
							if (property.ignore)
								buf.insert(0b01,2);
							else if (property.replaceAirOnly)
								buf.insert(0b10,2);
							else if (property.entity != null)
								buf.insert(0b11,2);
							else
								buf.insert(0b00,2);
							if (!property.state.getBlock().getDefaultState().equals(property.state)) {
								meta = property.state.getBlock().getMetaFromState(property.state);
								buf.insert(1,1);
							} else
								buf.insert(0,1);
							int index = blocks.indexOf(property.state.getBlock());
							if (index == -1)
								throw new LeafiaDevFlaw("Block "+property.state.getBlock().getRegistryName().toString()+" couldn't be found!");
							if (!property.ignore) {
								buf.writeShort(index);
								if (meta != null)
									buf.writeInt(meta);
								if (property.entity != null)
									buf.writeShort(property.entity);
							}
						}
						LeafiaCustomPacket.__start(new HighlightSavingWandProduct(name,buf.bytes)).__sendToClient(player);
						////////////////////////////////////////////////////////////////////////////////
						LeafiaCustomPacket.__start(new HighlightSavingWandSave()).__sendToClient(player);
					}
				};
				////////////////////////////////////////////////////////////////////////////////
				task.confirm(new LeafiaBuf(null));
			};
			darnit = ()->{
				darnit = callback;
			};
		} else
			return 2;
		return 0;
	}
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player,World world,BlockPos pos,EnumFacing side,float hitX,float hitY,float hitZ,EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		NBTTagCompound nbt = stack.getTagCompound();
		boolean sneak = player.isSneaking();
		if (nbt != null) {
			if (nbt.hasKey("selectionX0") && nbt.hasKey("selectionX1")) {
				if (hand.equals(EnumHand.MAIN_HAND))
					resizeSelection(nbt,side,sneak);
				else
					resizeSelection(nbt,side.getOpposite(),!sneak);
				stack.setTagCompound(nbt);
				return SUCCESS;
			}
		} else nbt = new NBTTagCompound();
		if (sneak) {
			nbt.setInteger("selectionX0",pos.getX());
			nbt.setInteger("selectionY0",pos.getY());
			nbt.setInteger("selectionZ0",pos.getZ());
			nbt.setInteger("selectionX1",pos.getX());
			nbt.setInteger("selectionY1",pos.getY());
			nbt.setInteger("selectionZ1",pos.getZ());
			stack.setTagCompound(nbt);
			if (world.isRemote) {
				highlight.setBlock(pos);
				highlight.show();
			}
			return SUCCESS;
		}
		return PASS;
	}
}