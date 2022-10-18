package com.hbm.items.machine;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFELCrystal extends Item {

	public EnumWavelengths wavelength = EnumWavelengths.NULL;

	public ItemFELCrystal(EnumWavelengths wavelength, String s) {
		this.wavelength = wavelength;
		this.setMaxStackSize(1);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		String desc = (stack.getItem() == ModItems.laser_crystal_digamma) ? (TextFormatting.OBFUSCATED + "THERADIANCEOFATHOUSANDSUNS") : (this.getUnlocalizedNameInefficiently(stack) + ".desc");
		list.add(I18nUtil.resolveKey(desc));
		list.add(wavelength.textColor + I18nUtil.resolveKey(wavelength.name) + " - " + wavelength.textColor + I18nUtil.resolveKey(this.wavelength.wavelengthRange));
	}

	public static enum EnumWavelengths{
		NULL("la creatura", "6 dollar", 0x010101, 0x010101, TextFormatting.WHITE), //why do you exist?

		RADIO("wavelengths.name.radio", "wavelengths.waveRange.radio", 0x80FF00, 0x80FF00, TextFormatting.YELLOW),
		MICRO("wavelengths.name.micro", "wavelengths.waveRange.micro", 0xFF8000, 0xFF8000, TextFormatting.GOLD),
		IR("wavelengths.name.ir", "wavelengths.waveRange.ir", 0xFF0000, 0xFF0000, TextFormatting.RED),
		VISIBLE("wavelengths.name.visible", "wavelengths.waveRange.visible", 0, 0, TextFormatting.GREEN),
		UV("wavelengths.name.uv", "wavelengths.waveRange.uv", 0xFF00FF, 0xFF00FF, TextFormatting.LIGHT_PURPLE),
		XRAY("wavelengths.name.xray", "wavelengths.waveRange.xray", 0x00FFFF, 0x00FFFF, TextFormatting.DARK_AQUA),
		GAMMA("wavelengths.name.gamma", "wavelengths.waveRange.gamma", 0x00FF00, 0x00FF00, TextFormatting.DARK_GREEN),
		DRX("wavelengths.name.drx", "wavelengths.waveRange.drx", 0xFF0000, 0xFF0000, TextFormatting.DARK_RED);

		public String name = "";
		public String wavelengthRange = "";
		public int renderedBeamColor;
		public int guiColor;
		public TextFormatting textColor;

		private EnumWavelengths(String name, String wavelength, int color, int guiColor, TextFormatting textColor) {
			this.name = name;
			this.wavelengthRange = wavelength;
			this.renderedBeamColor = color;
			this.guiColor = guiColor;
			this.textColor = textColor;
		}
	}
}