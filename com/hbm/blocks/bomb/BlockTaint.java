package com.hbm.blocks.bomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityTaintedCreeper;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.generic.TileEntityTaint;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTaint extends BlockContainer {
	public static final PropertyInteger TEXTURE = PropertyInteger.create("tex", 0, 15);
	
	public BlockTaint(Material m, String s) {
		super(m);
		this.setTickRandomly(true);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TEXTURE, 0));
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos1, IBlockState state, Random rand) {

		int meta = state.getValue(TEXTURE);
    	if(!world.isRemote && meta < 15) {
    		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
	    	for(int i = 0; i < 15; i++) {
	    		int a = rand.nextInt(11) + pos1.getX() - 5;
	    		int b = rand.nextInt(11) + pos1.getY() - 5;
	    		int c = rand.nextInt(11) + pos1.getZ() - 5;
	    		pos.setPos(a, b, c);
	            if(world.getBlockState(pos).getBlock().isReplaceable(world, pos) && hasPosNeightbour(world, pos))
	            	world.setBlockState(pos, ModBlocks.taint.getBlockState().getBaseState().withProperty(TEXTURE, meta + 1), 2);
	    	}
	            
		    for(int i = 0; i < 85; i++) {
		    	int a = rand.nextInt(7) + pos1.getX() - 3;
		    	int b = rand.nextInt(7) + pos1.getY() - 3;
		    	int c = rand.nextInt(7) + pos1.getZ() - 3;
		    	pos.setPos(a, b, c);
		           if(world.getBlockState(pos).getBlock().isReplaceable(world, pos) && BlockTaint.hasPosNeightbour(world, pos))
		           	world.setBlockState(pos, ModBlocks.taint.getBlockState().getBaseState().withProperty(TEXTURE, meta + 1), 2);
		    }
    	}
	}
	//Drillgon200: Ah yes, spelling.
	 public static boolean hasPosNeightbour(World world, BlockPos pos) {
	    	Block b0 = world.getBlockState(pos.add(1, 0, 0)).getBlock();
	    	Block b1 = world.getBlockState(pos.add(0, 1, 0)).getBlock();
	    	Block b2 = world.getBlockState(pos.add(0, 0, 1)).getBlock();
	    	Block b3 = world.getBlockState(pos.add(-1, 0, 0)).getBlock();
	    	Block b4 = world.getBlockState(pos.add(0, -1, 0)).getBlock();
	    	Block b5 = world.getBlockState(pos.add(0, 0, -1)).getBlock();
	    	boolean b = b0.isNormalCube(world.getBlockState(pos.add(1, 0, 0)), world, pos) ||
	    			b1.isNormalCube(world.getBlockState(pos.add(0, 1, 0)), world, pos) ||
	    			b2.isNormalCube(world.getBlockState(pos.add(0, 0, 1)), world, pos) ||
	    			b3.isNormalCube(world.getBlockState(pos.add(-1, 0, 0)), world, pos) ||
	    			b4.isNormalCube(world.getBlockState(pos.add(0, -1, 0)), world, pos) ||
	    			b5.isNormalCube(world.getBlockState(pos.add(0, 0, -1)), world, pos);
	    	return b;
	    }
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return new AxisAlignedBB(pos, pos);
	}
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(state);
		int level = 15 - meta;
		
    	List<ItemStack> list = new ArrayList<ItemStack>();
    	PotionEffect effect = new PotionEffect(HbmPotion.taint, 15 * 20, level);
    	effect.setCurativeItems(list);
    	
    	if(entity instanceof EntityLivingBase) {
    		if(world.rand.nextInt(50) == 0) {
    			((EntityLivingBase)entity).addPotionEffect(effect);
    		}
    	}
    	
    	if(entity instanceof EntityCreeper) {
    		EntityTaintedCreeper creep = new EntityTaintedCreeper(world);
    		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);

    		if(!world.isRemote) {
    			entity.setDead();
    			world.spawnEntity(creep);
    		}
    	}
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TEXTURE);
	}
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TEXTURE, meta);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TEXTURE);
	}
	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isCollidable() {
		return true;
	}
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
	@Override
	public boolean isTranslucent(IBlockState state) {
		return true;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(!BlockTaint.hasPosNeightbour(world, pos) && !world.isRemote)
			world.setBlockToAir(pos);
	}
	
	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTaint();
	}
}
