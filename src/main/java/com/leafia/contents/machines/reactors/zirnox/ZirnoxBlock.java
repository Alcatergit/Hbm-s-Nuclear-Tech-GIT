package com.leafia.contents.machines.reactors.zirnox;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.leafia.contents.machines.reactors.zirnox.container.ZirnoxTE;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ZirnoxBlock extends BlockDummyable {

    public ZirnoxBlock(Material mat,String s) {
        super(mat, s);
    }

    @Override
    public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
        MachineTooltip.addBoiler(tooltip);
        MachineTooltip.addNuclear(tooltip);
        super.addInformation(stack,player,tooltip,advanced);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {

        if(meta >= 12)
            return new ZirnoxTE();
        if(meta >= 6)
            return new TileEntityProxyCombo(true, true, true);

        return null;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos1, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(world.isRemote) {
            return true;
        } else if(!player.isSneaking()) {
            BossSpawnHandler.markFBI(player);

            int[] pos = this.findCore(world, pos1.getX(), pos1.getY(), pos1.getZ());

            if(pos == null)
                return false;

            player.openGui(MainRegistry.instance, ModBlocks.guiID_zirnox, world, pos[0], pos[1], pos[2]);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int[] getDimensions() {
        return new int[] {1, 0, 2, 2, 2, 2,};
    }

    @Override
    public int getOffset() {
        return 2;
    }

    @Override
    protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
        return super.checkRequirement(world, x, y, z, dir, o) &&
                MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 1, 1, 1, 1}, x, y, z, dir) &&
                MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 0, 0, 2, -2}, x, y, z, dir) &&
                MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 0, 0, -2, 2}, x, y, z, dir);
    }

    @Override
    protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
        super.fillSpace(world, x, y, z, dir, o);

        MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 1, 1, 1, 1}, this, dir);
        MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 0, 0, 2, -2}, this, dir);
        MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o, y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {4, -2, 0, 0, -2, 2}, this, dir);

        ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
        this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * 2, y + 1, z + dir.offsetZ * o + rot.offsetZ * 2);
        this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * 2, y + 3, z + dir.offsetZ * o + rot.offsetZ * 2);
        this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * -2, y + 1, z + dir.offsetZ * o + rot.offsetZ * -2);
        this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * -2, y + 3, z + dir.offsetZ * o + rot.offsetZ * -2);
        //i still don't know why the ports were such an issue all those months ago
        this.makeExtra(world, x + dir.offsetX * o, y + 4, z + dir.offsetZ * o);
    }

}