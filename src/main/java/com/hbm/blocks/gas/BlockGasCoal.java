package com.hbm.blocks.gas;

import com.hbm.config.GeneralConfig;
import com.hbm.items.ModItems;
import com.hbm.lib.ForgeDirection;
import com.hbm.util.ContaminationUtil;
import com.leafia.unsorted.recipe_book.system.LeafiaRecipeBookServer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockGasCoal extends BlockGasBase {

	public BlockGasCoal(String s) {
		super(0.2F, 0.2F, 0.2F, s);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand){
		super.randomDisplayTick(stateIn, world, pos, rand);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + rand.nextFloat(), pos.getY() + rand.nextFloat(), pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity){
		ContaminationUtil.applyCoal(entity, 5, 1, 5);
		if (entity instanceof EntityPlayer) {
			LeafiaRecipeBookServer.unlockRecipe((EntityPlayer)entity,ModItems.rag);
			LeafiaRecipeBookServer.unlockRecipe((EntityPlayer)entity,ModItems.mask_rag);
			LeafiaRecipeBookServer.unlockRecipe((EntityPlayer)entity,ModItems.mask_damp);
		}
	}

	@Override
	public ForgeDirection getFirstDirection(World world, int x, int y, int z) {
		
		if(world.rand.nextInt(5) == 0)
			return ForgeDirection.DOWN;
		
		return ForgeDirection.getOrientation(world.rand.nextInt(6));
	}

	@Override
	public ForgeDirection getSecondDirection(World world, int x, int y, int z) {
		return this.randomHorizontal(world);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		if(!world.isRemote && (!GeneralConfig.enableCoal || rand.nextInt(100) == 0)) {
			world.setBlockToAir(pos);
			return;
		}
		
		super.updateTick(world, pos, state, rand);
		world.scheduleUpdate(pos, this, this.tickRate(world) + rand.nextInt(5));
	}
}