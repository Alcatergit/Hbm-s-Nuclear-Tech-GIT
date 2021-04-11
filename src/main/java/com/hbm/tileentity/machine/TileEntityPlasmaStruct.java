package com.hbm.tileentity.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePlasmaHeater;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.lib.ForgeDirection;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityPlasmaStruct extends TileEntity implements ITickable {

	int age;
	
	@Override
	public void update() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata());

		if(world.isRemote) {
			world.spawnParticle(EnumParticleTypes.REDSTONE,
					pos.getX() + 0.5 + dir.offsetX * -11 + world.rand.nextGaussian() * 0.1,
					pos.getY() + 2.5 + world.rand.nextGaussian() * 0.1,
					pos.getZ() + 0.5 + dir.offsetZ * -11 + world.rand.nextGaussian() * 0.1,
					0.9, 0.3, 0.7);
			return;
		}

		age++;

		if(age < 20)
			return;

		age = 0;

		MachinePlasmaHeater plas = (MachinePlasmaHeater)ModBlocks.plasma_heater;

		int[] rot = MultiblockHandlerXR.rotate(plas.getDimensions(), dir.toEnumFacing());

		for(int a = pos.getX() - rot[4]; a <= pos.getX() + rot[5]; a++) {
			for(int b = pos.getY() - rot[1]; b <= pos.getY() + rot[0]; b++) {
				for(int c = pos.getZ() - rot[2]; c <= pos.getZ() + rot[3]; c++) {

					if(a == pos.getX() && b == pos.getY() && c == pos.getZ())
						continue;

					if(world.getBlockState(new BlockPos(a, b, c)).getBlock() != ModBlocks.fusion_heater){
						return;
					}
				}
			}
		}

		rot = MultiblockHandlerXR.rotate(new int[] {4, -3, 2, 1, 1, 1}, dir.toEnumFacing());

		for(int a = pos.getX() - rot[4]; a <= pos.getX() + rot[5]; a++) {
			for(int b = pos.getY() - rot[1]; b <= pos.getY() + rot[0]; b++) {
				for(int c = pos.getZ() - rot[2]; c <= pos.getZ() + rot[3]; c++) {

					if(a == pos.getX() && b == pos.getY() && c == pos.getZ())
						continue;

					if(world.getBlockState(new BlockPos(a, b, c)).getBlock() != ModBlocks.fusion_heater)
						return;
				}
			}
		}


        for(int i = 9; i <= 10; i++)
            for(int j = 1; j <= 2; j++)
			if(world.getBlockState(new BlockPos(pos.getX() - dir.offsetX * i, pos.getY() + j, pos.getZ() - dir.offsetZ * i)).getBlock() != ModBlocks.fusion_heater)
				return;

		BlockDummyable.safeRem = true;
		world.setBlockState(pos, ModBlocks.plasma_heater.getDefaultState().withProperty(BlockDummyable.META, this.getBlockMetadata() + BlockDummyable.offset), 3);
		plas.fillSpace(world, pos.getX() + dir.offsetX, pos.getY(), pos.getZ() + dir.offsetZ, dir, -plas.getOffset());
		BlockDummyable.safeRem = false;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
