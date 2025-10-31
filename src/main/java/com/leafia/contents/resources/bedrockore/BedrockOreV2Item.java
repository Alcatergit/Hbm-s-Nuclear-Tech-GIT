package com.leafia.contents.resources.bedrockore;

import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.util.I18nUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.hbm.inventory.material.Mats.*;
import static com.leafia.contents.resources.bedrockore.BedrockOreV2Item.V2Overlay.*;

public class BedrockOreV2Item extends ItemCustomLore {
	public final V2Type type;
	@Override
	public void getSubItems(CreativeTabs tab,NonNullList<ItemStack> list) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH){
			for (V2Grade grade : V2Grade.values())
				list.add(new ItemStack(this, 1, grade.ordinal()));
		}
	}

	public enum V2Overlay {
		ARC,CENTRIFUGED,RAD,ROASTED,SOLVENT,SULFURIC,WASHED;
		public String toPath() {
			return "bedrock_ore_overlay_"+name().toLowerCase();
		}
	}
	public enum V2Shape {
		BASE,CRUMBS,PRIMARY,RAD,SOLVENT,SULFURIC;
		public String toPath() {
			return "bedrock_ore_"+name().toLowerCase();
		}
	}
	public static class BedrockOreOutput {
		public NTMMaterial mat;
		public int amount;
		public BedrockOreOutput(NTMMaterial mat, int amount) {
			this.mat = mat;
			this.amount = amount;
		}
	}

	public static BedrockOreOutput o(NTMMaterial mat, int amount) {
		return new BedrockOreOutput(mat, amount);
	}
	public enum V2Type {
		//												                primary									                        sulfuric															                                    solvent																		                                    radsolvent
		LIGHT_METAL(	0xFFFFFF, 0x353535, "light",	o(MAT_IRON, 9),		o(MAT_COPPER, 9),	o(MAT_TITANIUM, 6),	o(MAT_BAUXITE, 9),	o(MAT_CRYOLITE, 3),	o(MAT_CHLOROCALCITE, 5),	o(MAT_LITHIUM, 5),		o(MAT_SODIUM, 3),		o(MAT_CHLOROCALCITE, 6),	o(MAT_LITHIUM, 6),		o(MAT_SODIUM, 6)),
		HEAVY_METAL(	0x868686, 0x000000, "heavy",	o(MAT_TUNGSTEN, 9),	o(MAT_LEAD, 9),		o(MAT_GOLD, 2),		o(MAT_GOLD, 2),			o(MAT_BERYLLIUM, 3),	o(MAT_TUNGSTEN, 9),			o(MAT_LEAD, 9),			o(MAT_GOLD, 5),			o(MAT_BISMUTH, 1),			o(MAT_BISMUTH, 1),		o(MAT_GOLD, 6)),
		RARE_EARTH(		0xE6E6B6, 0x1C1C00, "rare",		o(MAT_COBALT, 5),	o(MAT_RAREEARTH, 5),o(MAT_BORON, 5),	o(MAT_LANTHANIUM, 3),	o(MAT_NIOBIUM, 4),		o(MAT_NEODYMIUM, 3),		o(MAT_STRONTIUM, 3),	o(MAT_ZIRCONIUM, 3),	o(MAT_NIOBIUM, 5),			o(MAT_NEODYMIUM, 5),	o(MAT_STRONTIUM, 3)),
		ACTINIDE(		0xC1C7BD, 0x2B3227, "actinide",	o(MAT_URANIUM, 4),	o(MAT_THORIUM, 4),	o(MAT_RADIUM, 2),	o(MAT_RADIUM, 2),		o(MAT_POLONIUM, 2),		o(MAT_RADIUM, 2),			o(MAT_RADIUM, 2),		o(MAT_POLONIUM, 2),		o(MAT_TECHNETIUM, 1),		o(MAT_TECHNETIUM, 1),	o(MAT_U238, 1)),
		NON_METAL(		0xAFAFAF, 0x0F0F0F, "nonmetal",	o(MAT_COAL, 9),		o(MAT_SULFUR, 9),	o(MAT_LIGNITE, 9),	o(MAT_KNO, 6),			o(MAT_FLUORITE, 6),		o(MAT_PHOSPHORUS, 5),		o(MAT_FLUORITE, 6),		o(MAT_SULFUR, 6),		o(MAT_CHLOROCALCITE, 6),	o(MAT_SILICON, 2),		o(MAT_SILICON, 2)),
		CRYSTALLINE(	0xE2FFFA, 0x1E8A77, "crystal",	o(MAT_REDSTONE, 9),	o(MAT_CINNABAR, 4),	o(MAT_SODALITE, 9),	o(MAT_ASBESTOS, 6),		o(MAT_DIAMOND, 3),		o(MAT_CINNABAR, 3),			o(MAT_ASBESTOS, 5),		o(MAT_EMERALD, 3),		o(MAT_BORAX, 3),			o(MAT_MOLYSITE, 3),		o(MAT_SODALITE, 9));
		final int light;
		final int dark;
		final public String suffix;
		final public BedrockOreOutput primary1, primary2;
		final public BedrockOreOutput byproductAcid1, byproductAcid2, byproductAcid3;
		final public BedrockOreOutput byproductSolvent1, byproductSolvent2, byproductSolvent3;
		final public BedrockOreOutput byproductRad1, byproductRad2, byproductRad3;

		V2Type(int light, int dark, String suffix, BedrockOreOutput p1, BedrockOreOutput p2, BedrockOreOutput bA1, BedrockOreOutput bA2, BedrockOreOutput bA3, BedrockOreOutput bS1, BedrockOreOutput bS2, BedrockOreOutput bS3, BedrockOreOutput bR1, BedrockOreOutput bR2, BedrockOreOutput bR3) {
			this.light = light;
			this.dark = dark;
			this.suffix = suffix;
			this.primary1 = p1; this.primary2 = p2;
			this.byproductAcid1 = bA1; this.byproductAcid2 = bA2; this.byproductAcid3 = bA3;
			this.byproductSolvent1 = bS1; this.byproductSolvent2 = bS2; this.byproductSolvent3 = bS3;
			this.byproductRad1 = bR1; this.byproductRad2 = bR2; this.byproductRad3 = bR3;
		}
	}
	public static final int none = 0xFFFFFF;
	public static final int roasted = 0xCFCFCF;
	public static final int arc = 0xC3A2A2;
	public static final int washed = 0xDBE2CB;

	public enum V2Grade {
		BASE(none, V2Shape.BASE),												//from the slopper
		BASE_ROASTED(roasted, V2Shape.BASE, ROASTED),							//optional combination oven step, yields vitriol
		BASE_WASHED(washed, V2Shape.BASE, WASHED),							//primitive-ass acidizer with water

		PRIMARY(none, V2Shape.PRIMARY, CENTRIFUGED),							//centrifuging for more primary
		PRIMARY_ROASTED(roasted, V2Shape.PRIMARY, ROASTED),					//optional comboven
		PRIMARY_SULFURIC(0xFFFFD3, V2Shape.PRIMARY, SULFURIC),				//sulfuric acid
		PRIMARY_NOSULFURIC(0xD3D4FF, V2Shape.PRIMARY, CENTRIFUGED, SULFURIC),	//from centrifuging, sulfuric byproduct removed
		PRIMARY_SOLVENT(0xD3F0FF, V2Shape.PRIMARY, SOLVENT),					//solvent
		PRIMARY_NOSOLVENT(0xFFDED3, V2Shape.PRIMARY, CENTRIFUGED, SOLVENT),	//solvent byproduct removed
		PRIMARY_RAD(0xECFFD3, V2Shape.PRIMARY, RAD),							//radsolvent
		PRIMARY_NORAD(0xEBD3FF, V2Shape.PRIMARY, CENTRIFUGED, RAD),			//radsolvent byproduct removed
		PRIMARY_FIRST(0xFFD3D4, V2Shape.PRIMARY, CENTRIFUGED),				//higher first material yield
		PRIMARY_SECOND(0xD3FFEB, V2Shape.PRIMARY, CENTRIFUGED),				//higher second material yield
		CRUMBS(none, V2Shape.CRUMBS, CENTRIFUGED),							//endpoint for primary, recycling

		SULFURIC_BYPRODUCT(none, V2Shape.SULFURIC, CENTRIFUGED, SULFURIC),	//from centrifuging
		SULFURIC_ROASTED(roasted, V2Shape.SULFURIC, ROASTED, SULFURIC),		//comboven again
		SULFURIC_ARC(arc, V2Shape.SULFURIC, ARC, SULFURIC),					//alternate step
		SULFURIC_WASHED(washed, V2Shape.SULFURIC, WASHED, SULFURIC),			//sulfuric endpoint

		SOLVENT_BYPRODUCT(none, V2Shape.SOLVENT, CENTRIFUGED, SOLVENT),		//from centrifuging
		SOLVENT_ROASTED(roasted, V2Shape.SOLVENT, ROASTED, SOLVENT),			//comboven again
		SOLVENT_ARC(arc, V2Shape.SOLVENT, ARC, SOLVENT),						//alternate step
		SOLVENT_WASHED(washed, V2Shape.SOLVENT, WASHED, SOLVENT),				//solvent endpoint

		RAD_BYPRODUCT(none, V2Shape.RAD, CENTRIFUGED, RAD),					//from centrifuging
		RAD_ROASTED(roasted, V2Shape.RAD, ROASTED, RAD),						//comboven again
		RAD_ARC(arc, V2Shape.RAD, ARC, RAD),									//alternate step
		RAD_WASHED(washed, V2Shape.RAD, WASHED, RAD);							//rad endpoint

		public int tint;
		public V2Shape type;
		public V2Overlay[] traits;

		V2Grade(int tint,V2Shape type,V2Overlay... traits) {
			this.tint = tint;
			this.type = type;
			this.traits = traits;
		}
	}
	
	public BedrockOreV2Item(String s,V2Type type) {
		super(s);
		this.type = type;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String name = I18nUtil.resolveKey(this.getUnlocalizedNameInefficiently(stack)+".name");
		return I18n.format("item.bedrockorev2.grade." + V2Grade.values()[stack.getMetadata()].name().toLowerCase(), name);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack,World worldIn,List<String> list,ITooltipFlag flagIn) {
		for(V2Overlay trait : V2Grade.values()[stack.getMetadata()].traits) {
			list.add(I18nUtil.resolveKey("item.bedrockorev2.trait." + trait.name().toLowerCase()));
		}
	}
}
