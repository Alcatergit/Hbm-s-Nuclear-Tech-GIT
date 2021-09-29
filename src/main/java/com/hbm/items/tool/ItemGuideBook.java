package com.hbm.items.tool;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGuideBook extends Item {

	public ItemGuideBook(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
		if(worldIn.isRemote)
			playerIn.openGui(MainRegistry.instance, ModItems.guiID_item_guide, worldIn, 0, 0, 0);
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
		if(tab == CreativeTabs.SEARCH || tab == this.getCreativeTab())
			for(int i = 1; i < BookType.values().length; i++)
				items.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		tooltip.add(String.join(" ", I18nUtil.resolveKeyArray(BookType.getType(stack.getItemDamage()).title)));
	}
	
	public enum BookType {

		TEST("book.test.cover", 2F, statFacTest()),
		RBMK("book.rbmk.cover", 1.5F, statFacRBMK());
		
		public List<GuidePage> pages;
		public float titleScale;
		public String title;
		
		private BookType(String title, float titleScale, List<GuidePage> pages) {
			this.title = title;
			this.titleScale = titleScale;
			this.pages = pages;
		}
		
		public static BookType getType(int i) {
			return BookType.values()[Math.abs(i) % BookType.values().length];
		}
	}
	
	public static List<GuidePage> statFacTest() {
		
		List<GuidePage> pages = new ArrayList<>();
		pages.add(new GuidePage("book.test.page1").addTitle("Title LMAO", 0x800000, 1F).setScale(2F).addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/smileman.png"), 100, 40, 40));
		pages.add(new GuidePage("book.test.page1").addTitle("LA SEXO", 0x800000, 0.5F).setScale(1.75F).addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/smileman.png"), 100, 40, 40));
		pages.add(new GuidePage("test test"));
		pages.add(new GuidePage("test test test"));
		pages.add(new GuidePage("test test"));
		pages.add(new GuidePage("test test test"));
		pages.add(new GuidePage("test test"));
		return pages;
	}
	
	public static List<GuidePage> statFacRBMK() {
		
		List<GuidePage> pages = new ArrayList<>();
		pages.add(new GuidePage("book.rbmk.page1").setScale(2F).addTitle("book.rbmk.title1", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk1.png"), 90, 80, 60));
		pages.add(new GuidePage("book.rbmk.page2").setScale(2F).addTitle("book.rbmk.title2", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk2.png"), 95, 52, 52));
		pages.add(new GuidePage("book.rbmk.page3").setScale(2F).addTitle("book.rbmk.title3", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk3.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page4").setScale(2F).addTitle("book.rbmk.title4", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk4.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page5").setScale(2F).addTitle("book.rbmk.title5", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk5.png"), 95, 80, 42));
		pages.add(new GuidePage("book.rbmk.page6").setScale(2F).addTitle("book.rbmk.title6", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk6.png"), 90, 100, 60));
		pages.add(new GuidePage("book.rbmk.page7").setScale(2F).addTitle("book.rbmk.title7", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk7.png"), 95, 52, 52));
		pages.add(new GuidePage("book.rbmk.page8").setScale(2F).addTitle("book.rbmk.title8", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk8.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page9").setScale(2F).addTitle("book.rbmk.title9", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk9.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page10").setScale(2F).addTitle("book.rbmk.title10", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk10.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page11").setScale(2F).addTitle("book.rbmk.title11", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk11.png"), 75, 85, 72));
		pages.add(new GuidePage("book.rbmk.page12").setScale(2F).addTitle("book.rbmk.title12", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk12.png"), 90, 80, 60));
		pages.add(new GuidePage("book.rbmk.page13").setScale(2F).addTitle("book.rbmk.title13", 0x800000, 1F));
		pages.add(new GuidePage("book.rbmk.page14").setScale(2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk13.png"), 70, 103, 78));
		pages.add(new GuidePage("book.rbmk.page15").setScale(2F).addTitle("book.rbmk.title15", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk15.png"), 100, 48, 48));
		pages.add(new GuidePage("book.rbmk.page16").setScale(2F).addTitle("book.rbmk.title16", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk16.png"), 50, 70, 100));
		return pages;
	}
	
	public static class GuidePage {
		
		public String title;
		public int titleColor;
		public float titleScale;
		public String text;
		public ResourceLocation image;
		public float scale = 1F;
		public int x;
		public int y;
		public int sizeX;
		public int sizeY;
		
		public GuidePage() { }
		
		public GuidePage(String text) {
			this.text = text;
		}
		
		public GuidePage setScale(float scale) {
			this.scale = scale;
			return this;
		}
		
		public GuidePage addTitle(String title, int color, float scale) {
			this.title = title;
			this.titleColor = color;
			this.titleScale = scale;
			return this;
		}
		
		public GuidePage addImage(ResourceLocation image, int x, int y, int sizeX, int sizeY) {
			
			this.image = image;
			this.x = x;
			this.y = y;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			return this;
		}
		
		//if the x-coord is -1 then it will automatically try to center the image horizontally
		public GuidePage addImage(ResourceLocation image, int y, int sizeX, int sizeY) {
			return addImage(image, -1, y, sizeX, sizeY);
		}
	}
}
