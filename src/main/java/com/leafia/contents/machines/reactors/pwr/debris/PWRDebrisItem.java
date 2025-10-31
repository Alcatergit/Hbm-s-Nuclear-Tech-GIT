package com.leafia.contents.machines.reactors.pwr.debris;

import com.hbm.interfaces.IItemHazard;
import com.hbm.items.ModItems;
import com.hbm.modules.ItemHazardModule;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.pwr.debris.PWRDebrisEntity.DebrisType;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class PWRDebrisItem extends Item implements IItemHazard {
	public final ItemHazardModule hazard;
	public final DebrisType type;
	final String typeNameKey;
	public boolean canBeCraftedBack = true;
	public PWRDebrisItem(String s,DebrisType type) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.hazard = new ItemHazardModule();
		this.type = type;
		typeNameKey = s.replace("pwr_","item.pwrdebris.");
		ModItems.ALL_ITEMS.add(this);
	}
	public PWRDebrisItem disableCrafting() { canBeCraftedBack = false; return this; }
	@Override
	public void addInformation(ItemStack stack,@Nullable World worldIn,List<String> tooltip,ITooltipFlag flagIn) {
		hazard.addInformation(stack,tooltip,flagIn);
		super.addInformation(stack,worldIn,tooltip,flagIn);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String typename = I18nUtil.resolveKey(typeNameKey);
		String itemNameKey = super.getItemStackDisplayName(stack);
		if (stack.getTagCompound() != null)
			itemNameKey = stack.getTagCompound().getString("block");
		Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(itemNameKey));
		String itemName = itemNameKey;
		if (block != null)
			itemName = block.getLocalizedName();
		String mainKey = "item.pwrdebris.conjunctive";
		if (itemName.toLowerCase().contains(I18nUtil.resolveKey("item.pwrdebris.conjunctive.switch").toLowerCase()))
			mainKey = mainKey+".alt";
		return I18nUtil.resolveKey(mainKey).replace("{type}",typename).replace("{block}",itemName);
	}
	@Override
	public ItemHazardModule getModule() {
		return hazard;
	}
}