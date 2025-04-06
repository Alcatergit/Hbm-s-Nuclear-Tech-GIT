package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityReactorResearch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ReactorResearch extends BlockDummyable {

    public ReactorResearch(Material mat, String s) {
        super(mat, s);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        if(meta >= 12)
            return new TileEntityReactorResearch();
        if(meta >= 6)
            return new TileEntityProxyCombo(false, true, true);

        return null;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(world.isRemote) {
            return true;
        } else if(!player.isSneaking()) {
            BossSpawnHandler.markFBI(player);

            int[] posC = this.findCore(world, pos.getX(), pos.getY(), pos.getZ());

            if(posC == null)
                return false;

            FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, posC[0], posC[1], posC[2]);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, world, pos, rand);

        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

            if(dir == ForgeDirection.DOWN || dir == ForgeDirection.UP)
                continue;

            if(world.getBlockState(pos.add(dir.offsetX, dir.offsetY, dir.offsetZ)).getMaterial() == Material.WATER) {

                double ix = pos.getX() + 0.5F + dir.offsetX + rand.nextDouble() - 0.5D;
                double iy = pos.getY() + 0.5F + dir.offsetY + rand.nextDouble() - 0.5D;
                double iz = pos.getZ() + 0.5F + dir.offsetZ + rand.nextDouble() - 0.5D;

                if(dir.offsetX != 0)
                    ix = pos.getX() + 0.5F + dir.offsetX * 0.5 + rand.nextDouble() * 0.125 * dir.offsetX;
                if(dir.offsetZ != 0)
                    iz = pos.getZ() + 0.5F + dir.offsetZ * 0.5 + rand.nextDouble() * 0.125 * dir.offsetZ;

                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, ix, iy, iz, 0.0, 0.2, 0.0);
            }
        }
    }

    @Override
    public int[] getDimensions() {
        return new int[] {2, 0, 0, 0, 0, 0,};
    }

    @Override
    public int getOffset() {
        return 0;
    }
}
