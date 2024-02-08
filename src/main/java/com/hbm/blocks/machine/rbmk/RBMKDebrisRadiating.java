package com.hbm.blocks.machine.rbmk;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.lib.ForgeDirection;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.ContaminationUtil;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class RBMKDebrisRadiating extends RBMKDebrisBurning {

	public static PropertyInteger META = BlockDummyable.META;
	
	public RBMKDebrisRadiating(String s){
		super(s);
	}
	
	@Override
	public int tickRate(World world) {

		return 20 + world.rand.nextInt(20);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		
		if(!world.isRemote) {

			ForgeDirection dir = ForgeDirection.getOrientation(world.rand.nextInt(6));

			//Boron sand helps stop the fission reaction; 0.66% chance every 20-40 ticks for one side
			int chance = world.getBlockState(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ)).getBlock() == ModBlocks.sand_boron ? 25 : 1000;
			
			ContaminationUtil.radiate(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 32, 100F * chance, 0, 40F * chance);
			
			if(rand.nextInt(5) == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "rbmkflame");
				data.setInteger("maxAge", 300);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.getX() + rand.nextDouble(), pos.getY() + 1.75, pos.getZ() + rand.nextDouble()), new TargetPoint(world.provider.getDimension(), pos.getX() + 0.5, pos.getY() + 1.75, pos.getZ() + 0.5, 75));
				MainRegistry.proxy.effectNT(data);
				world.playSound(null, pos.getX() + 0.5F, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F);

			}

			if(rand.nextInt(chance) == 0) {
				
				int meta = state.getValue(META);
				
				if(meta < 15) {
					world.setBlockState(pos, state.withProperty(META, meta+1), 2);
					world.scheduleUpdate(pos, this, this.tickRate(world));
				} else {
					world.setBlockState(pos, ModBlocks.pribris_burning.getDefaultState());
				}
				
			} else {
				world.scheduleUpdate(pos, this, this.tickRate(world));
			}
		}
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