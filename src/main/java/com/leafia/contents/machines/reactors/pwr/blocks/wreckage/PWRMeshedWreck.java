package com.leafia.contents.machines.reactors.pwr.blocks.wreckage;

import com.hbm.blocks.BlockBase;
import com.hbm.hfr.render.loader.HFRWavefrontObject;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.ClientProxy;
import com.hbm.main.MainRegistry;
import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.group.LeafiaMap;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;

public abstract class PWRMeshedWreck extends BlockBase implements ITileEntityProvider {
	public PWRMeshedWreck(Material m,String s) {
		super(m,s);
		this.setHardness(5);
	}
	public PWRMeshedWreck(Material m,SoundType sound,String s) {
		super(m,sound,s);
		this.setHardness(5);
	}

	public static final Map<BlockPos,TileEntity> rmCache = new LeafiaMap<>();
	@Override
	public void getDrops(NonNullList<ItemStack> drops,IBlockAccess world,BlockPos pos,IBlockState state,int fortune) {
		Random rand = world instanceof World ? ((World)world).rand : RANDOM;
		TileEntity entity = rmCache.getOrDefault(pos,world.getTileEntity(pos));
		if (entity instanceof PWRMeshedWreckEntity) {
			PWRMeshedWreckEntity wreck = (PWRMeshedWreckEntity)entity;
			String resource = wreck.resourceLocation;
			Item item;
			if (resource.contains("glass"))
				item = ModItems.pwr_shard;
			else
				item = getDebrisItem();
			ItemStack stack = new ItemStack(item);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("block",resource);
			nbt.setInteger("meta",wreck.meta);
			//nbt.setBoolean("ntmPyrophoric",wreck.scorch > 4);
			stack.setTagCompound(nbt);
			for (int i = rand.nextInt(3); i < 5+fortune; i++)
				drops.add(stack);
		}
	}
	@Override
	public void onBlockHarvested(World world,BlockPos pos,IBlockState state,EntityPlayer player) {
		if (!world.isRemote) {
			TileEntity entity = world.getTileEntity(pos);
			if (entity != null)
				rmCache.put(pos,entity);
		}
		super.onBlockHarvested(world,pos,state,player);
	}

	@Override
	public void onEntityWalk(World world,BlockPos pos,Entity entityIn) {
		TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof PWRMeshedWreckEntity) {
			PWRMeshedWreckEntity wreck = (PWRMeshedWreckEntity) entity;
			if (wreck.scorch > 4)
				entityIn.setFire(4);
		}
	}

	public abstract Item getDebrisItem();

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state,World worldIn,BlockPos pos,AxisAlignedBB entityBox,List<AxisAlignedBB> collidingBoxes,@Nullable Entity entityIn,boolean isActualState) {
		//if (worldIn.isBlockLoaded(pos)) {
			TileEntity entity = worldIn.getTileEntity(pos);
			if (entity instanceof PWRMeshedWreckEntity) {
				PWRMeshedWreckEntity wreck = (PWRMeshedWreckEntity)entity;
				Variation variation = this.getVariations().get(wreck.erosion,wreck.variation);
				EnumFacing face = state.getValue(FACING);
				for (WreckBound hitbox : variation.hitboxes) {
					WreckBound bound = hitbox.reflect();
					// rotation algorithm parity to RenderPWRMeshedWreck
					if (face.getYOffset() != 0) {
						if (face.equals(EnumFacing.DOWN))
							bound = bound.rotateX(-180);
					} else {
						bound = bound.rotateY(180+face.getHorizontalAngle()).rotateX(90);
					}
					addCollisionBoxToList(pos,entityBox,collidingBoxes,bound.compile());
					//collidingBoxes.add(bound.compile());
				}
			}
		//} else
		//	super.addCollisionBoxToList(state,worldIn,pos,entityBox,collidingBoxes,entityIn,isActualState);
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn,World world,BlockPos pos,Random rand) {
		super.randomDisplayTick(stateIn,world,pos,rand);
		if (world.isBlockLoaded(pos)) {
			Chunk chunk = world.getChunk(pos);
			TileEntity entity = chunk.getTileEntity(pos,EnumCreateEntityType.CHECK);
			if (entity != null) {
				if (entity instanceof PWRMeshedWreckEntity) {
					int heat = ((PWRMeshedWreckEntity) entity).scorch;
					if (heat >= 4) {
						if (rand.nextInt(45+40*(7-heat)) == 0) {
							NBTTagCompound data = new NBTTagCompound();
							data.setString("type","rbmkflame");
							data.setInteger("maxAge",300);
							data.setDouble("posX",pos.getX()+0.5);
							data.setDouble("posY",pos.getY()+1.75+0.75*(heat-4)/3);
							data.setDouble("posZ",pos.getZ()+0.5);
							MainRegistry.proxy.effectNT(data);
							world.playSound(Minecraft.getMinecraft().player,pos.getX() + 0.5F,pos.getY() + 0.5,pos.getZ() + 0.5,SoundEvents.BLOCK_FIRE_AMBIENT,SoundCategory.BLOCKS,1.0F + rand.nextFloat(),rand.nextFloat() * 0.7F + 0.3F);
						}
					}
				}
			}
		}
	}

	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	public boolean isNormalCube(IBlockState state,IBlockAccess world,BlockPos pos) {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new PWRMeshedWreckEntity(this);
	}

	abstract public VariationGroup getVariations();
	public static HFRWavefrontObject meshFromString(String name) {
		if (MainRegistry.proxy instanceof ClientProxy)
			return new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/leafia/pwrwreck/"+name+".obj"));
		else
			return null;
	}


	public static final PropertyDirection FACING = BlockDirectional.FACING;
	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	@Override
	public int getMetaFromState(IBlockState state){
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	@Override
	public IBlockState getStateFromMeta(int meta) { EnumFacing enumfacing = EnumFacing.byIndex(meta); return this.getDefaultState().withProperty(FACING, enumfacing); }
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){ return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING))); }
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn){ return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING))); }
	@Override
	public void onBlockPlacedBy(World worldIn,BlockPos pos,IBlockState state,EntityLivingBase placer,ItemStack stack) { worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer))); }


	public void create(World world,BlockPos pos,EnumFacing face,IBlockState state,Erosion erosion,int heat) {
		world.setBlockState(pos,this.getDefaultState().withProperty(FACING,face));
		PWRMeshedWreckEntity entity = ((PWRMeshedWreckEntity)world.getTileEntity(pos));
		entity.resourceLocation = state.getBlock().getRegistryName().toString();
		entity.meta = state.getBlock().getMetaFromState(state);
		entity.erosion = erosion;
		entity.scorch = heat;
		entity.variation = world.rand.nextInt(this.getVariations().list(erosion).length);
		entity.markDirty();
	}
	public enum Erosion {
		NONE,SLIGHT,NORMAL,RUBBLE;
		public int getBits() {
			return this.ordinal()<<3;
		}
	}
	public static class VariationGroupConstructor {
		Erosion writeMode = null;
		Map<Erosion,List<Variation>> map = new HashMap<>();
		public VariationGroupConstructor() {
			for (Erosion value : Erosion.values())
				map.put(value,new ArrayList<>());
		}
		public VariationGroupConstructor setErosion(Erosion erosion) { writeMode = erosion; return this; }
		public VariationGroupConstructor addVariation(HFRWavefrontObject mesh,WreckBound... hitboxes) {
			if (writeMode == null)
				throw new LeafiaDevFlaw("setErosion must be called before using addVariation!");
			List<Variation> list = map.get(writeMode);
			if (list.size() >= 8)
				throw new LeafiaDevFlaw("Variations can only be added nax 8 for each Erosion level!");
			list.add(new Variation((list.size()<<5)+writeMode.getBits(),mesh,hitboxes));
			return this;
		}
		public VariationGroupConstructor addVariation(String meshName,WreckBound... hitboxes) {
			return addVariation(meshFromString(meshName),hitboxes);
		}
		public VariationGroup compile() {
			Map<Erosion,Variation[]> arrayMap = new HashMap<>();
			for (Map.Entry<Erosion,List<Variation>> entry : map.entrySet()) {
				Variation[] array = new Variation[entry.getValue().size()];
				for (int i = 0; i < entry.getValue().size(); i++)
					array[i] = entry.getValue().get(i);
				arrayMap.put(entry.getKey(),array);
			}
			return new VariationGroup(
					arrayMap.get(Erosion.NONE),
					arrayMap.get(Erosion.SLIGHT),
					arrayMap.get(Erosion.NORMAL),
					arrayMap.get(Erosion.RUBBLE)
			);
		}
	}
	public static class VariationGroup {
		public final Variation[] NONE;
		public final Variation[] SLIGHT;
		public final Variation[] NORMAL;
		public final Variation[] RUBBLE;
		private VariationGroup(@Nullable Variation[] NONE,@Nullable Variation[] SLIGHT,@Nullable Variation[] NORMAL,@Nullable Variation[] RUBBLE) {
			if (NONE == null) NONE = new Variation[0];
			if (SLIGHT == null) SLIGHT = new Variation[0];
			if (NORMAL == null) NORMAL = new Variation[0];
			if (RUBBLE == null) RUBBLE = new Variation[0];
			this.NONE = NONE;
			this.SLIGHT = SLIGHT;
			this.NORMAL = NORMAL;
			this.RUBBLE = RUBBLE;
		}
		public Variation[] list(Erosion erosion) {
			switch(erosion) {
				case NONE: return NONE;
				case SLIGHT: return SLIGHT;
				case NORMAL: return NORMAL;
				case RUBBLE: return RUBBLE;
				default: throw new LeafiaDevFlaw("Unexpected item for Erosion enum");
			}
		}
		public Variation fromBits(int bits) { return list(Erosion.values()[(bits>>3)&0b11])[(bits>>5)&0b111]; }
		public Variation get(Erosion erosion,int variation) { return list(erosion)[variation]; }
	}
	public enum RuntimeRenderType {
		PARTICLE,
		ALL_SIX,
		SIDE_TOP,
		SIDE_TOP_BOTTOM
	}
	public static class Variation {;
		public final HFRWavefrontObject mesh;
		public final WreckBound[] hitboxes;
		public final boolean collision;
		public final int bits;
		public RuntimeRenderType renderType = null; // set runtime
		Variation(int bits,HFRWavefrontObject mesh,@Nullable WreckBound[] hitboxes) {
			this.bits = bits;
			this.mesh = mesh;
			if (hitboxes == null) {
				this.collision = true;
				this.hitboxes = new WreckBound[] { new WreckBound(0,0,2,2,0,2) };
			} else {
				this.collision = (hitboxes.length > 0);
				this.hitboxes = hitboxes;
			}
		}
	}
	public static class WreckBound {
		// Utility class for making bounding boxes
		// Set like in the way shown on ASCII art diagram below
		double front0; double front1;
		double up0; double up1;
		double right0; double right1;
		Vec3d lookVector = new Vec3d(0,0,1);
		Vec3d upVector = new Vec3d(0,1,0);
		Vec3d rightVector = new Vec3d(1,0,0);
		// But internally it's converted to -0.5 ~ 0.5 range with the center as its pivot, which makes rotations easy
		private WreckBound() {};
		public WreckBound(double x,double f,double width,double thickness,double y,double height) {
			// x,f: -1 ~ 0 ~ 1
			// y,width,thickness,height: 0 ~ 2
			//      Front          _________.
			//   .    1    .      |\____\____\Y
			//     +  |  +        |\    |##   2
			// -1 --- 0 --- 1  X  | ----|##-- 1
			//   . +  |  + .       \____|##__ 0
			//       -1
			//
			front0 = (f-thickness/2)/2; right0 = (x-width/2)/2; up0 = (y-1       )/2;
			front1 = (f+thickness/2)/2; right1 = (x+width/2)/2; up1 = (y-1+height)/2;
		}
		public static WreckBound blend(double x0,double f0,double x1,double f1,double y,double height) {
			// x0,x1,f0,f1: -5 ~ 0 ~ 5
			// y,height: 0 ~ 10
			//      Front          _________.
			//   .   -5    .      |\____\____\Y
			//     +  |  +        |\    |##   10
			// -5 --- 0 --- 5  X  | ----|##-- 5
			//   . +  |  + .       \____|##__ 0
			//        5
			//
			return new WreckBound((x0+x1)/10,-(f0+f1)/10,Math.abs(x1-x0)/5,Math.abs(f1-f0)/5,y/5,height/5);
		}
		public WreckBound reflect() {
			WreckBound bound = new WreckBound();
			bound.front0 = this.front0; bound.front1 = this.front1;
			bound.up0    = this.up0;    bound.up1    = this.up1;
			bound.right0 = this.right0; bound.right1 = this.right1;
			bound.lookVector = this.lookVector; bound.upVector = this.upVector; bound.rightVector = this.rightVector;
			return bound;
		}
		public WreckBound rotateY(double angle) {
			WreckBound bound = reflect();
			double cos = Math.cos(angle/180*Math.PI);
			double sin = Math.sin(angle/180*Math.PI);
			bound.rightVector = rightVector.scale(cos).add(lookVector.scale(sin));
			bound.lookVector = lookVector.scale(cos).subtract(rightVector.scale(sin));
			return bound;
		}
		public WreckBound rotateZ(double angle) {
			WreckBound bound = reflect();
			double cos = Math.cos(angle/180*Math.PI);
			double sin = Math.sin(angle/180*Math.PI);
			bound.rightVector = rightVector.scale(cos).add(upVector.scale(sin));
			bound.upVector = upVector.scale(cos).subtract(rightVector.scale(sin));
			return bound;
		}
		public WreckBound rotateX(double angle) {
			WreckBound bound = reflect();
			double cos = Math.cos(angle/180*Math.PI);
			double sin = Math.sin(angle/180*Math.PI);
			bound.lookVector = lookVector.scale(cos).add(upVector.scale(sin));
			bound.upVector = upVector.scale(cos).subtract(lookVector.scale(sin));
			return bound;
		}
		public AxisAlignedBB compile() {
			Vec3d vec0 = new Vec3d(0.5,0.5,0.5)
					.add(rightVector.scale( right0  ))
					.add(upVector   .scale(    up0  ))
					.add(lookVector .scale(-front1  ));
			Vec3d vec1 = new Vec3d(0.5,0.5,0.5)
					.add(rightVector.scale( right1  ))
					.add(upVector   .scale(    up1  ))
					.add(lookVector .scale(-front0  ));
			return new AxisAlignedBB(
					Math.min(vec0.x,vec1.x),Math.min(vec0.y,vec1.y),Math.min(vec0.z,vec1.z),
					Math.max(vec0.x,vec1.x),Math.max(vec0.y,vec1.y),Math.max(vec0.z,vec1.z)
			);
		}
	}
}
