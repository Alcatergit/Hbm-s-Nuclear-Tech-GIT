package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.capability.HbmLivingProps;
import com.hbm.capability.HbmLivingProps.ContaminationEffect;
import com.hbm.interfaces.IItemHazard;
import com.hbm.items.ModItems;
import com.hbm.modules.ItemHazardModule;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFallout extends Block implements IItemHazard {
	
	public static final PropertyInteger META = BlockDummyable.META;
	
	ItemHazardModule module;

	public BlockFallout(Material mat, SoundType soundType, String s) {
		super(mat);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setSoundType(soundType);
		this.module = new ItemHazardModule();
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		return new AxisAlignedBB(0, 0, 0, 1, 0.125, 1);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state){
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state){
		return false;
	}
	

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return ModItems.fallout;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos){
		IBlockState state = world.getBlockState(pos.down());
		Block block = state.getBlock();
		return block != Blocks.ICE && block != Blocks.PACKED_ICE ? (block.isLeaves(state, world, pos.down()) ? true : (block == this && (state.getValue(META) & 7) == 7 ? true : state.isOpaqueCube() && state.getMaterial().blocksMovement())) : false;
	}
	
	@Override
	public void onEntityWalk(World world, BlockPos pos, Entity entity){
		if(!world.isRemote && entity instanceof EntityLivingBase) {
			PotionEffect effect = new PotionEffect(HbmPotion.radiation, 10 * 60 * 20, 0);
			effect.setCurativeItems(new ArrayList<>());
			((EntityLivingBase) entity).addPotionEffect(effect);
		}
	}
	
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {

		if(!world.isRemote) {
			//player.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 15 * 20, 1));
			HbmLivingProps.addCont(player, new ContaminationEffect(1F, 200, false));
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
		this.func_150155_m(worldIn, pos.getX(), pos.getY(), pos.getZ());
	}
	
	private boolean func_150155_m(World world, int x, int y, int z) {
		if(!this.canPlaceBlockAt(world, new BlockPos(x, y, z))) {
			world.setBlockToAir(new BlockPos(x, y, z));
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos){
		return true;
	}
	
	@Override
	public ItemHazardModule getModule() {
		return module;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{META});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(META);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(META, meta);
	}
}