package com.hbm.items;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.config.BombConfig;
import static com.hbm.items.ItemEnums.EnumAmmo;
import com.hbm.interfaces.IHasCustomMetaModels;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;
import com.hbm.util.I18nUtil;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemEnumMulti extends Item implements IHasCustomMetaModels {
	
	//hell yes, now we're thinking with enums!
	protected Class<? extends Enum> theEnum;
	protected String separator;

	public ItemEnumMulti(Class<? extends Enum> theEnum, String s) {
		this(theEnum, s, "_");
	}

	public ItemEnumMulti(Class<? extends Enum> theEnum, String s, String separator) {
		this.setHasSubtypes(true);
		this.theEnum = theEnum;
		this.separator = separator;

		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setMaxDamage(0);
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH){
			for (Enum e: theEnum.getEnumConstants()) {
				list.add(new ItemStack(this, 1, e.ordinal()));
			}
		}
	}

	@Override
	public Set<Integer> getMetaValues(){
		Set<Integer> meta = new HashSet<Integer>();
		for (Enum e: theEnum.getEnumConstants()) {
			meta.add(e.ordinal());
		}
		return meta;
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		Enum type = theEnum.getEnumConstants()[itemStack.getItemDamage()];
		if(type instanceof ItemEnums.IEnumFurnaceFuel fuel) return fuel.getBurnTime();
		return 0;
	}

	@Override
	public ModelResourceLocation getResourceLocation(int meta) {
		Enum type = theEnum.getEnumConstants()[meta];
		String name = type.toString().toLowerCase();
		return new ModelResourceLocation(RefStrings.MODID + ":"+this.getTranslationKey().substring(5) + this.separator + name, "inventory");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		return (I18n.format(stack.getTranslationKey() + this.separator + EnumUtil.grabEnumSafely(theEnum,stack.getItemDamage()).toString().toLowerCase() + ".name")).trim();
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		//BOLTS
		if(stack.getItemDamage() == EnumAmmo.B75.ordinal()) {
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("ammo.trait.75b1"));
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("ammo.trait.75b2"));
		}
		if(stack.getItemDamage() == EnumAmmo.B75_INCENDIARY.ordinal()) {
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("ammo.trait.75b3"));
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("ammo.trait.75b4"));
		}
		if(stack.getItemDamage() == EnumAmmo.B75_HE.ordinal()) {
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("ammo.trait.75b5"));
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("ammo.trait.75b6"));
		}

		//NUKES
		if(stack.getItemDamage() == EnumAmmo.NUKE_LOW.ordinal()) {
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 20));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN +I18nUtil.resolveKey("desc.radius",(int)(20*(1+BombConfig.falloutRange/100.0))));
			}
		}
		if(stack.getItemDamage() == EnumAmmo.NUKE_STANDARD.ordinal()) {
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 35));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN +I18nUtil.resolveKey("desc.radius",(int)(35*(1+BombConfig.falloutRange/100.0))));
			}
		}
		if(stack.getItemDamage() == EnumAmmo.NUKE_HIGH.ordinal()) {
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 50));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN +I18nUtil.resolveKey("desc.radius",(int)(50*(1+BombConfig.falloutRange/100.0))));
			}
		}
		if(stack.getItemDamage() == EnumAmmo.NUKE_TOTS.ordinal()) {
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 10));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN +I18nUtil.resolveKey("desc.radius",(int)(10*(1+BombConfig.falloutRange/100.0))));
			}
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.incbomb"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.fun"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.daccuracy"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.dblast"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.noprotomirv"));
		}
		if(stack.getItemDamage() == EnumAmmo.NUKE_SAFE.ordinal()) {
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.dblast"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.noblock"));
		}
		if(stack.getItemDamage() == EnumAmmo.NUKE_PUMPKIN.ordinal()) {
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nonuke"));
		}

		//MIRV
		if(stack.getItemDamage() == EnumAmmo.MIRV_LOW.ordinal()) {
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.dblast"));
		}
		if(stack.getItemDamage() == EnumAmmo.MIRV_HIGH.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.blast"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.fallout"));
		}
		if(stack.getItemDamage() == EnumAmmo.MIRV_SAFE.ordinal()) {
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.dblast"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.noblock"));
		}
		if(stack.getItemDamage() == EnumAmmo.MIRV_SPECIAL.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.mirv1"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.mirv2"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.mirv3"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.mirv4"));
			list.add(TextFormatting.WHITE + "* " + I18nUtil.resolveKey("ammo.trait.mirv5"));
		}

		// FUEL
		if(stack.getItemDamage() == EnumAmmo.FLAME_NAPALM.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.range"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.FLAME_PHOSPHORUS.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.phosphor"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.range"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.accuracy"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.warcrime"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.single"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.FLAME_GAS.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.nograv"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.poison"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nodamage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.noinc"));
		}
		if(stack.getItemDamage() == EnumAmmo.FLAME_VAPORIZER.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.phosphorburn"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.vapor1"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.vapor2"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.daccuracy"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.vapor3"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.vapor4"));
		}
		// 12 GAUGE
		if(stack.getItemDamage() == EnumAmmo.M44_PHOSPHORUS.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.phosphorburn"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.warcrime"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.BMG50.ordinal()) {
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("ammo.trait.bmg1"));
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("ammo.trait.bmg2"));
			list.add(TextFormatting.YELLOW + I18nUtil.resolveKey("ammo.trait.bmg3"));
		}
		if(stack.getItemDamage() == EnumAmmo.BMG50_PHOSPHORUS.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.phosphorburn"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.warcrime"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.BMG50_AP.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.ROCKET_PHOSPHORUS.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.phosphor"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.warcrime"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.GRENADE_PHOSPHORUS.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.phosphor"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.warcrime"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.GRENADE_KAMPF.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.rocketprop"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.blast"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.accuracy"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G12_INCENDIARY.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.inc"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G12_SHRAPNEL.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.bounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G12_DU.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.pen"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.heavymetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G12_MARAUDER.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.12gm1"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.12gm2"));
		}
		if(stack.getItemDamage() == EnumAmmo.G12_SLEEK.ordinal()) {
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.gaugesleek"));
		}

		// 20 GAUGE
		if(stack.getItemDamage() == EnumAmmo.G20_FLECHETTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.dbounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G20_SLUG.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highaccuracy"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.single"));
		}
		if(stack.getItemDamage() == EnumAmmo.G20_INCENDIARY.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.inc"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G20_SHRAPNEL.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.bounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G20_EXPLOSIVE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.explosive"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G20_CAUSTIC.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.toxic"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.1"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.nobounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G20_SHOCK.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.2"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.emp"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.nobounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G20_WITHER.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.wither"));
		}
		if(stack.getItemDamage() == EnumAmmo.G20_SLEEK.ordinal()) {
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.gaugesleek"));
		}

		// .357 MAGNUM
		if(stack.getItemDamage() == EnumAmmo.M357_DESH.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.357d1"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.357d2"));
		}

		// .44 MAGNUM
		if(stack.getItemDamage() == EnumAmmo.M44_AP.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.M44_DU.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.heavymetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.M44_PIP.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.3"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.ddamage"));
		}
		if(stack.getItemDamage() == EnumAmmo.M44_BJ.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.4"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.ddamage"));
		}
		if(stack.getItemDamage() == EnumAmmo.M44_SILVER.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.5"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.ddamage"));
		}
		if(stack.getItemDamage() == EnumAmmo.M44_ROCKET.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.rocket"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.uhhh"));
		}
		if(stack.getItemDamage() == EnumAmmo.M44_STAR.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.starmetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}

		// 5mm
		if(stack.getItemDamage() == EnumAmmo.R5_EXPLOSIVE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.explosive"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.R5_DU.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.heavymetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.R5_STAR.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.starmetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}

		// 9mm
		if(stack.getItemDamage() == EnumAmmo.P9_AP.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.P9_DU.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.heavymetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.P9_ROCKET.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.rocket"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.uhhh"));
		}

		// .22LR
		if(stack.getItemDamage() == EnumAmmo.P22_AP.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}

		// .50 BMG
		if(stack.getItemDamage() == EnumAmmo.BMG50_INCENDIARY.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.inc"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.BMG50_EXPLOSIVE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.explosive"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.BMG50_DU.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.heavymetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.BMG50_STAR.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.starmetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.BMG50_SLEEK.ordinal()) {
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.meteorite"));
		}

		// .50 AE
		if(stack.getItemDamage() == EnumAmmo.AE50_AP.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.AE50_DU.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.heavymetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.AE50_STAR.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.starmetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}

		// 84mm ROCKETS
		if(stack.getItemDamage() == EnumAmmo.ROCKET_HE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.blast"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.ROCKET_INCENDIARY.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.incexplosive"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.ROCKET_SHRAPNEL.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.sharpnel"));
		}
		if(stack.getItemDamage() == EnumAmmo.ROCKET_EMP.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.emp"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.dblast"));
		}
		if(stack.getItemDamage() == EnumAmmo.ROCKET_GLARE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.9"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.incexplosive"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.ROCKET_TOXIC.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.8"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.noexplosive"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.dspeed"));
		}
		if(stack.getItemDamage() == EnumAmmo.ROCKET_SLEEK.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.10"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.nograv"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.jolt"));
		}
		if(stack.getItemDamage() == EnumAmmo.ROCKET_NUCLEAR.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.nuclear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.hhighwear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.dspeed"));
		}
		if(stack.getItemDamage() == EnumAmmo.ROCKET_RPC.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.6"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.pen"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.nograv"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.noexplosive"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.uhhh"));
		}

		// 40mm GRENADES
		if(stack.getItemDamage() == EnumAmmo.GRENADE_HE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.blast"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.GRENADE_INCENDIARY.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.incexplosive"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.GRENADE_TOXIC.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.8"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.noexplosive"));
		}
		if(stack.getItemDamage() == EnumAmmo.GRENADE_CONCUSSION.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.blast"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.noblock"));
		}
		if(stack.getItemDamage() == EnumAmmo.GRENADE_FINNED.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.11"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.dblast"));
		}
		if(stack.getItemDamage() == EnumAmmo.GRENADE_SLEEK.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.blast"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.jolt"));
		}
		if(stack.getItemDamage() == EnumAmmo.GRENADE_NUCLEAR.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.nuclear"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.range"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}

		// FOLLY
		if(stack.getItemDamage() == EnumAmmo.FOLLY.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.folly1"));
		}
		if(stack.getItemDamage() == EnumAmmo.FOLLY_NUCLEAR.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.folly2"));
		}
		if(stack.getItemDamage() == EnumAmmo.FOLLY_DU.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.folly3"));
		}
		// 23mm
		if(stack.getItemDamage() == EnumAmmo.G4_SLUG.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highaccuracy"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.single"));
		}
		if(stack.getItemDamage() == EnumAmmo.G4_EXPLOSIVE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.explosive"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.12"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.single"));
		}
		if(stack.getItemDamage() == EnumAmmo.G4_SEMTEX.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.explosive"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.7"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.13"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.single"));
		}
		if(stack.getItemDamage() == EnumAmmo.G4_BALEFIRE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.explosive"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.balefire"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.single"));
		}
		if(stack.getItemDamage() == EnumAmmo.G4_KAMPF.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.explosive"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.rocketprop"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.accuracy"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.single"));
		}
		if(stack.getItemDamage() == EnumAmmo.G4_SLEEK.ordinal()) {
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.gaugesleek"));
		}
		if(stack.getItemDamage() == EnumAmmo.G4_FLECHETTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.dbounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.G4_FLECHETTE_PHOSPHORUS.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.phosphorburn"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.highwarcrime"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.dbounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		// 5.56mm
		if(stack.getItemDamage() == EnumAmmo.R556_PHOSPHORUS.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.phosphorburn"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.warcrime"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_AP.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_DU.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.heavymetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_STAR.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.starmetal"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_SLEEK.ordinal()) {
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.meteorite"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_FLECHETTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.dbounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_FLECHETTE_INCENDIARY.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.inc"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.dbounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_FLECHETTE_PHOSPHORUS.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.phosphorburn"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.highwarcrime"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.dbounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.wear"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_FLECHETTE_DU.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.highdamage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.pen"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.heavymetal"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.dbounce"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.highwear"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_FLECHETTE_SLEEK.ordinal()) {
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.meteorite"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_TRACER.ordinal()) {
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.tracer"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_K.ordinal()) {
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.blank"));
		}

		//Drillgon200: New stuff (organization? Whatever)
		if(stack.getItemDamage() == EnumAmmo.M44_CHLOROPHYTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.DARK_GREEN + "* " + I18nUtil.resolveKey("ammo.trait.chlorophyte"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.homing"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.R5_CHLOROPHYTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.DARK_GREEN + "* " + I18nUtil.resolveKey("ammo.trait.chlorophyte"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.homing"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.P9_CHLOROPHYTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.DARK_GREEN + "* " + I18nUtil.resolveKey("ammo.trait.chlorophyte"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.homing"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.P22_CHLOROPHYTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.DARK_GREEN + "* " + I18nUtil.resolveKey("ammo.trait.chlorophyte"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.homing"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.BMG50_CHLOROPHYTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.DARK_GREEN + "* " + I18nUtil.resolveKey("ammo.trait.chlorophyte"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.homing"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.AE50_CHLOROPHYTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.DARK_GREEN + "* " + I18nUtil.resolveKey("ammo.trait.chlorophyte"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.homing"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_CHLOROPHYTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.DARK_GREEN + "* " + I18nUtil.resolveKey("ammo.trait.chlorophyte"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.homing"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
		if(stack.getItemDamage() == EnumAmmo.R556_FLECHETTE_CHLOROPHYTE.ordinal()) {
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.damage"));
			list.add(TextFormatting.BLUE + "+ " + I18nUtil.resolveKey("ammo.trait.dwear"));
			list.add(TextFormatting.DARK_GREEN + "* " + I18nUtil.resolveKey("ammo.trait.chlorophyte"));
			list.add(TextFormatting.YELLOW + "* " + I18nUtil.resolveKey("ammo.trait.homing"));
			list.add(TextFormatting.RED + "- " + I18nUtil.resolveKey("ammo.trait.nopen"));
		}
	}

}