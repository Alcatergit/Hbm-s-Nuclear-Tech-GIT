package com.leafia.contents.machines.reactors.pwr.blocks.components.port;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.generic.BlockRadResistant;
import com.hbm.main.MainRegistry;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentBlock;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PWRPortBlock extends BlockRadResistant implements ITooltipProvider, ITileEntityProvider, PWRComponentBlock {
    public PWRPortBlock() {
        super(Material.IRON,"pwr_port");
        this.setCreativeTab(MainRegistry.machineTab);
    }
    @Override
    public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
        MachineTooltip.addMultiblock(tooltip);
        MachineTooltip.addModular(tooltip);
        addStandardInfo(tooltip);
        super.addInformation(stack,player,tooltip,advanced);
    }

    @Override
    public boolean tileEntityShouldCreate(World world,BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn,int meta) {
        return new PWRPortTE();
    }
}
