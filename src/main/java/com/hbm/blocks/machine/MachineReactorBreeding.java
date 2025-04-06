package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.TileEntityMachineReactorBreeding;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class MachineReactorBreeding extends BlockDummyable {

    public MachineReactorBreeding(Material mat, String s) {
        super(mat, s);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        if(meta >= 12)
            return new TileEntityMachineReactorBreeding();

        return new TileEntityProxyInventory();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(world.isRemote)
        {
            return true;
        } else if(!player.isSneaking())
        {
            int[] posC = this.findCore(world, pos.getX(), pos.getY(), pos.getZ());

            if(posC == null)
                return false;

            TileEntityMachineReactorBreeding entity = (TileEntityMachineReactorBreeding) world.getTileEntity(new BlockPos(posC[0], posC[1], posC[2]));
            if(entity != null)
            {
                FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, posC[0], posC[1], posC[2]);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int[] getDimensions() {
        return new int[] { 2, 0, 0, 0, 0, 0 };
    }

    @Override
    public int getOffset() {
        return 0;
    }
}
