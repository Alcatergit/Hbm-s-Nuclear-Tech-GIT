package com.hbm.items.machine;

import java.util.List;

import com.hbm.util.I18nUtil;
import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDrillbit extends Item {

	public EnumDrillType drillType;

	public ItemDrillbit(EnumDrillType drillType, String s) {
		this.drillType = drillType;
		this.setMaxStackSize(1);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		EnumDrillType type = ((ItemDrillbit)stack.getItem()).drillType;
		if(type == null) return;
		list.add("§e"+I18nUtil.resolveKey("desc.speed")+" " + ((int) (type.speed * 100)) + "%");
		list.add("§e"+I18nUtil.resolveKey("desc.tier", type.tier));
		if(type.fortune > 0) {
            list.add("§d" + I18nUtil.resolveKey("desc.fortune") + " " + type.fortune + " "+ I18nUtil.resolveKey("desc.fortuneBedrock", (int)(100F * type.fortune/20F)));
        }
		if(type.vein) {
            list.add("§a" + I18nUtil.resolveKey("desc.veinminer"));
        }
		if(type.silk) {
            list.add("§a" + I18nUtil.resolveKey("desc.silktouch"));
        }
	}
	
	public enum EnumDrillType {
		STEEL			(1.0D, 1, 0, false, false),
		STEEL_DIAMOND	(0.8D, 1, 1, false, true),
		HSS				(1.2D, 2, 0, true, false),
		HSS_DIAMOND		(1.0D, 2, 2, true, true),
		DESH			(1.5D, 3, 0, true, false),
		DESH_DIAMOND	(1.2D, 3, 3, true, true),
		TCALLOY			(2.0D, 4, 0, true, true),
		TCALLOY_DIAMOND	(1.6D, 4, 4, true, true),
		FERRO			(2.5D, 5, 0, true, true),
		FERRO_DIAMOND	(2.0D, 5, 5, true, true),
		DNT				(5.0D, 8, 0, true, true),
		DNT_DIAMOND		(4.0D, 8, 6, true, true);
		
		public final double speed;
		public final int tier;
		public final int fortune;
		public final boolean vein;
		public final boolean silk;
		
		EnumDrillType(double speed, int tier, int fortune, boolean vein, boolean silk) {
			this.speed = speed;
			this.tier = tier;
			this.fortune = fortune;
			this.vein = vein;
			this.silk = silk;
		}
	}
}
