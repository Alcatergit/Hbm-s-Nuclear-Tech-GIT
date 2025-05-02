package com.hbm.blocks;

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.hbm.util.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public interface ITooltipProvider {

	public default void addStandardInfo(List<String> list) {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			for(String s : I18nUtil.resolveKeyArray(((Block)this).getTranslationKey() + ".desc")) list.add(TextFormatting.YELLOW + s);
		} else {
			list.add(I18nUtil.resolveKey("desc.tooltip.hold", "LSHIFT"));
		}
	}
}
