package com.leafia.contents.machines.reactors.pwr.blocks.components.channel;

import com.hbm.blocks.BlockBase;
import com.hbm.blocks.ITooltipProvider;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentBlock;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PWRExchangerBlock extends BlockBase implements ITooltipProvider, PWRComponentBlock {
    public PWRExchangerBlock() {
        super(Material.IRON,"pwr_exchanger");
        setSoundType(SoundType.METAL);
    }
    @Override
    public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
        MachineTooltip.addMultiblock(tooltip);
        MachineTooltip.addModular(tooltip);
        addStandardInfo(tooltip);
        super.addInformation(stack,player,tooltip,advanced);
    }

    @Override
    public boolean shouldRenderOnGUI() {
        return true;
    }

    @Override
    public boolean tileEntityShouldCreate(World world,BlockPos pos) {
        return false;
    }
}
