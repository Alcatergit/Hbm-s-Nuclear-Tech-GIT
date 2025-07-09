package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityCondenserPowered;
import com.hbm.util.BobMathUtil;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MachineCondenserPowered extends BlockDummyable implements ILookOverlay {
    public MachineCondenserPowered(Material mat, String s) {
        super(mat, s);
    }

    @Override
    public TileEntity createNewTileEntity(@NotNull World world, int meta) {
        if(meta >= 12) return new TileEntityCondenserPowered();
        if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
        return null;
    }

    @Override
    public int[] getDimensions() {
        return new int[] {2, 0, 1, 1, 3, 3};
    }

    @Override
    public int getOffset() {
        return 1;
    }

    @Override
    public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
        super.fillSpace(world, x, y, z, dir, o);

        x = x + dir.offsetX * o;
        z = z + dir.offsetZ * o;

        ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

        this.makeExtra(world, x + rot.offsetX * 3, y + 1, z + rot.offsetZ * 3);
        this.makeExtra(world, x - rot.offsetX * 3, y + 1, z - rot.offsetZ * 3);
        this.makeExtra(world, x + dir.offsetX + rot.offsetX, y + 1, z + dir.offsetZ + rot.offsetZ);
        this.makeExtra(world, x + dir.offsetX - rot.offsetX, y + 1, z + dir.offsetZ - rot.offsetZ);
        this.makeExtra(world, x - dir.offsetX + rot.offsetX, y + 1, z - dir.offsetZ + rot.offsetZ);
        this.makeExtra(world, x - dir.offsetX - rot.offsetX, y + 1, z - dir.offsetZ - rot.offsetZ);
    }

    @Override
    public void printHook(RenderGameOverlayEvent.Pre event, World world, int x, int y, int z) {
        int[] pos = this.findCore(world, x, y, z);

        if(pos == null)
            return;

        TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));

        if(!(te instanceof TileEntityCondenserPowered)) return;

        TileEntityCondenserPowered tower = (TileEntityCondenserPowered) te;
        List<String> text = new ArrayList<>();

        text.add(BobMathUtil.getShortNumber(tower.power) + "HE / " + BobMathUtil.getShortNumberNew(TileEntityCondenserPowered.maxPower) + "HE");

        for(int i = 0; i < tower.tanks.length; i++)
            text.add((i < 1 ? (TextFormatting.GREEN + "-> ") : (TextFormatting.RED + "<- ")) + TextFormatting.RESET + (tower.tanks[i].getFluid() != null ? tower.tanks[i].getFluid().getLocalizedName() : "None") + ": " + String.format(Locale.US, "%,d", tower.tanks[i].getFluidAmount()) + "/" + String.format(Locale.US, "%,d", tower.tanks[i].getCapacity()) + "mB");

        ILookOverlay.printGeneric(event, getLocalizedName(), 0xffff00, 0x404000, text);
    }
}
