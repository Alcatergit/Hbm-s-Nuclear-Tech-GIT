package com.leafia.contents.machines.reactors.pwr.blocks;

import com.hbm.blocks.BlockBase;
import com.hbm.blocks.ITooltipProvider;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PWRSourceBlock extends BlockBase implements ITooltipProvider {
    public PWRSourceBlock() {
        super(Material.IRON,"pwr_source");
        setSoundType(SoundType.METAL);
    }
    @Override
    public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
        MachineTooltip.addShit(tooltip);
        addStandardInfo(tooltip);
        super.addInformation(stack,player,tooltip,advanced);
    }
}
