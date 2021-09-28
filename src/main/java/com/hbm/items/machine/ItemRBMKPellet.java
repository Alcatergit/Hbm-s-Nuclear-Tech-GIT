package com.hbm.items.machine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import com.hbm.interfaces.IItemHazard;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRBMKPellet extends Item implements IItemHazard {
	
	public String fullName = "";
	ItemHazardModule module;

	public ItemRBMKPellet(String fullName, String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.fullName = fullName;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(MainRegistry.controlTab);
		this.module = new ItemHazardModule();
		//generateJsons(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	public static void generateJsons(String name){
		//Yeah I'm not making 10 json files per pellet manually
		String directory = "E:\\Chicken\\Workspace5\\itemmodels\\";
		String baseJson = "{\n\t\"parent\": \"item/generated\",\n\t\"textures\": {\n\t\t\"layer0\": \"hbm:items/NAME_REPLACE\",\n\t\t\"layer1\": \"hbm:items/ENRICHMENT_REPLACE\"\n\t}\n}";
		String baseXenon = "{\n\t\"parent\": \"item/generated\",\n\t\"textures\": {\n\t\t\"layer0\": \"hbm:items/NAME_REPLACE\",\n\t\t\"layer1\": \"hbm:items/ENRICHMENT_REPLACE\",\n\t\t\"layer2\": \"hbm:items/rbmk_pellet_overlay_xenon\"\n\t}\n}";
		baseJson = baseJson.replace("NAME_REPLACE", name);
		baseXenon = baseXenon.replace("NAME_REPLACE", name);
		for(int xenon = 0; xenon < 2; xenon ++){
			String base = xenon == 0 ? baseJson : baseXenon;
			for(int enrichment = 0; enrichment < 5; enrichment ++){
				File file = new File(directory + name + "_e" + enrichment + (xenon > 0 ? "_xe" : "") + ".json");
				try {
					file.createNewFile();
					BufferedWriter writer = new BufferedWriter(new FileWriter(file));
					writer.write(base.replace("ENRICHMENT_REPLACE", "rbmk_pellet_overlay_e" + enrichment));
					writer.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
		if(tab == CreativeTabs.SEARCH || tab == this.getCreativeTab()){
			for(int i = 0; i < 10; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	/*@SideOnly(Side.CLIENT)
	private IIcon[] enrichmentOverlays;
	
	@SideOnly(Side.CLIENT)
	private IIcon xenonOverlay;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		super.registerIcons(p_94581_1_);
		
		this.enrichmentOverlays = new IIcon[5];
		
		for(int i = 0; i < enrichmentOverlays.length; i++) {
			enrichmentOverlays[i] = p_94581_1_.registerIcon("hbm:rbmk_pellet_overlay_e" + i);
		}
		
		xenonOverlay = p_94581_1_.registerIcon("hbm:rbmk_pellet_overlay_xenon");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int meta) {
		return hasXenon(meta) ? 3 : 2;
	}*/
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(TextFormatting.ITALIC + this.fullName);
		tooltip.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + "Pellet for recycling");
		
		int meta = rectify(stack.getItemDamage());
		
		switch(meta % 5) {
		case 0: tooltip.add(TextFormatting.GOLD + "Brand New"); break;
		case 1: tooltip.add(TextFormatting.YELLOW + "Barely Depleted"); break;
		case 2: tooltip.add(TextFormatting.GREEN + "Moderately Depleted"); break;
		case 3: tooltip.add(TextFormatting.DARK_GREEN + "Highly Depleted"); break;
		case 4: tooltip.add(TextFormatting.DARK_GRAY + "Fully Depleted"); break;
		}
		
		if(hasXenon(meta))
			tooltip.add(TextFormatting.DARK_PURPLE + "High Xenon Poison");
		
		updateModule(stack);
		this.module.addInformation(stack, tooltip, flagIn);
	}

	/*@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
		
		if(pass == 0)
			return this.itemIcon;
		
		if(pass == 2)
			return this.xenonOverlay;
		
		return this.enrichmentOverlays[rectify(meta) % 5];
	}*/
	
	private boolean hasXenon(int meta) {
		return rectify(meta) >= 5;
	}
	
	private int rectify(int meta) {
		return Math.abs(meta) % 10;
	}

	@Override
	public ItemHazardModule getModule() {
		return this.module;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
		
		if(entity instanceof EntityLivingBase) {
			updateModule(stack);
			this.module.applyEffects((EntityLivingBase) entity, stack.getCount(), i, b, ((EntityLivingBase)entity).getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem item) {
		
		super.onEntityItemUpdate(item);
		updateModule(item.getItem());
		return this.module.onEntityItemUpdate(item);
	}
	
	private void updateModule(ItemStack stack) {
		
		int index = stack.getItemDamage() % 5;
		float mod = (index * index) / 5F;
		
		if(stack.getItemDamage() >= 5) {
			mod *= 10F;
			mod += 1F;
		}
		
		this.module.setMod(1F + mod);
	}
}