package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.ModBlocks;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockPinkLog extends BlockLog {

	public BlockPinkLog(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(LOG_AXIS, EnumAxis.values()[meta]);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(LOG_AXIS).ordinal();
	}
	
	@Override
	protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
    	tooltip.add(I18n.format(this.getTranslationKey()+".desc"));
        super.addInformation(stack, player, tooltip, advanced);
    }
}
