package com.leafia.contents.machines.reactors.pwr.blocks;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.generic.BlockRadResistant;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PWRReflectorBlock extends BlockRadResistant implements ITooltipProvider {
    public PWRReflectorBlock() {
        super(Material.IRON,"pwr_reflector");
        setSoundType(SoundType.METAL);
    }
    @Override
    public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
        MachineTooltip.addMultiblock(tooltip);
        MachineTooltip.addModular(tooltip);
        addStandardInfo(tooltip);
        super.addInformation(stack,player,tooltip,advanced);
    }
}
