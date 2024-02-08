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
		RBMK("book.rbmk.cover", 1.5F, statFacRBMK()),
		MSWORD("book.msword.cover", 1.5F, statFacMSword()),
		HADRON("book.error.cover", 1.5F, statFacHadron());
		
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

	public static List<GuidePage> statFacHadron() {
		
		List<GuidePage> pages = new ArrayList();
		
		for(int i = 1; i <= 9; i++) {
			pages.add(new GuidePage("book.error.page" + i).setScale(2F).addTitle("book.error.title" + i, 0x800000, 1F));
		}
		
		return pages;
	}
	
	public static List<GuidePage> statFacRBMK() {
		
		List<GuidePage> pages = new ArrayList<>();
		pages.add(new GuidePage("book.rbmk.page1").setScale(2F).addTitle("book.rbmk.title1", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk1.png"), 90, 80, 60));

		pages.add(new GuidePage("book.rbmk.page_schematic").setScale(2F).addTitle("book.rbmk.title_schematic", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/schematic.png"), 95, 56, 52));

		pages.add(new GuidePage("book.rbmk.page3").setScale(2F).addTitle("book.rbmk.title3", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk3.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page10").setScale(2F).addTitle("book.rbmk.title10", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk10.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page8").setScale(2F).addTitle("book.rbmk.title8", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk8.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page9").setScale(2F).addTitle("book.rbmk.title9", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk9.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page4").setScale(2F).addTitle("book.rbmk.title4", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk4.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page5").setScale(2F).addTitle("book.rbmk.title5", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk5.png"), 95, 80, 42));
		pages.add(new GuidePage("book.rbmk.page6").setScale(2F).addTitle("book.rbmk.title6", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk6.png"), 90, 100, 60));
		
		pages.add(new GuidePage("book.rbmk.page_fluxgraph").setScale(2F).addTitle("book.rbmk.title_fluxgraph", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/fluxgraph.png"), 95, 95, 47));
		pages.add(new GuidePage("book.rbmk.page_flux").setScale(2F).addTitle("book.rbmk.title_flux", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk_flux.png"), 95, 88, 52));
		pages.add(new GuidePage("book.rbmk.page_flux_explain").setScale(2F).addTitle("book.rbmk.title_flux_explain", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/func_explain.png"), 95, 95, 48));

		pages.add(new GuidePage("book.rbmk.page_func_passive").setScale(2F).addTitle("book.rbmk.title_func_passive", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/func_passive.png"), 85, 95, 48));
		pages.add(new GuidePage("book.rbmk.page_func_euler").setScale(2F).addTitle("book.rbmk.title_func_euler", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/func_euler.png"), 85, 95, 48));
		pages.add(new GuidePage("book.rbmk.page_func_sigmoid").setScale(2F).addTitle("book.rbmk.title_func_sigmoid", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/func_sigmoid.png"), 85, 95, 48));
		pages.add(new GuidePage("book.rbmk.page_func_logarithmic").setScale(2F).addTitle("book.rbmk.title_func_logarithmic", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/func_logarithmic.png"), 85, 95, 48));
		pages.add(new GuidePage("book.rbmk.page_func_square_root").setScale(2F).addTitle("book.rbmk.title_func_square_root", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/func_square_root.png"), 85, 95, 48));
		pages.add(new GuidePage("book.rbmk.page_func_neg_quad").setScale(2F).addTitle("book.rbmk.title_func_neg_quad", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/func_neg_quad.png"), 85, 95, 48));
		pages.add(new GuidePage("book.rbmk.page_func_linear").setScale(2F).addTitle("book.rbmk.title_func_linear", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/func_linear.png"), 85, 95, 48));
		pages.add(new GuidePage("book.rbmk.page_func_quadratic").setScale(2F).addTitle("book.rbmk.title_func_quadratic", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/func_quadratic.png"), 85, 95, 48));

		pages.add(new GuidePage("book.rbmk.page2").setScale(2F).addTitle("book.rbmk.title2", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk2.png"), 95, 52, 52));
		pages.add(new GuidePage("book.rbmk.page7").setScale(2F).addTitle("book.rbmk.title7", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk7.png"), 95, 52, 52));
		pages.add(new GuidePage("book.rbmk.page_tips").setScale(2F).addTitle("book.rbmk.title_tips", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/steamconnector.png"), 90, 18, 60));

		pages.add(new GuidePage("book.rbmk.page11").setScale(2F).addTitle("book.rbmk.title11", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk11.png"), 75, 85, 72));
		pages.add(new GuidePage("book.rbmk.page12").setScale(2F).addTitle("book.rbmk.title12", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk12.png"), 90, 95, 48));
		pages.add(new GuidePage("book.rbmk.page13").setScale(2F).addTitle("book.rbmk.title13", 0x800000, 1F));
		pages.add(new GuidePage("book.rbmk.page14").setScale(2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk13.png"), 70, 103, 72));
		pages.add(new GuidePage("book.rbmk.page15").setScale(2F).addTitle("book.rbmk.title15", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/starter_designs.png"), 95, 95, 48));

		
		pages.add(new GuidePage("book.rbmk.page_mistakes").setScale(2F).addTitle("book.rbmk.title_mistakes", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/fluxinteractions.png"), 105, 95, 43));

		pages.add(new GuidePage("book.rbmk.page16").setScale(2F).addTitle("book.rbmk.title16", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/rbmk16.png"), 50, 70, 100));
		pages.add(new GuidePage("book.rbmk.page_post_meltdown").setScale(2F).addTitle("book.rbmk.title_post_meltdown", 0x800000, 1F));
		pages.add(new GuidePage("").setScale(2F).addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/meltdown.png"), 45, 75, 80));
		return pages;
	}


	public static List<GuidePage> statFacMSword() {
		int widthX = 100;
		List<GuidePage> pages = new ArrayList<>();
		pages.add(new GuidePage("book.msword.page0").setScale(2F).addTitle("book.msword.title0", 0x800000, 1F));
		pages.add(new GuidePage("book.msword.page1").setScale(2F).addTitle("book.msword.title1", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/01.png"), 95, widthX, (int)(widthX * (64F/164F))));
		pages.add(new GuidePage("book.msword.page2").setScale(2F).addTitle("book.msword.title2", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/02.png"), 90, widthX, (int)(widthX * (64F/158F))));
		pages.add(new GuidePage("book.msword.page3").setScale(2F).addTitle("book.msword.title3", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/03.png"), 75, widthX, (int)(widthX * (62F/90F))));
		pages.add(new GuidePage("book.msword.page4").setScale(2F).addTitle("book.msword.title4", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/04.png"), 100, widthX, (int)(widthX * (26F/98F))));
		pages.add(new GuidePage("book.msword.page5").setScale(2F).addTitle("book.msword.title5", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/05.png"), 75, widthX, (int)(widthX * (62F/90F))));
		pages.add(new GuidePage("book.msword.page6").setScale(2F).addTitle("book.msword.title6", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/06.png"), 100, widthX, (int)(widthX * (26F/98F))));
		pages.add(new GuidePage("book.msword.page7").setScale(2F).addTitle("book.msword.title7", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/07.png"), 80, widthX, (int)(widthX * (62F/124F))));
		pages.add(new GuidePage("book.msword.page8").setScale(2F).addTitle("book.msword.title8", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/08.png"), 75, widthX, (int)(widthX * (62F/90F))));
		pages.add(new GuidePage("book.msword.page9").setScale(2F).addTitle("book.msword.title9", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/09.png"), 100, widthX, (int)(widthX * (26F/98F))));
		pages.add(new GuidePage("book.msword.page10").setScale(2F).addTitle("book.msword.title10", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/10.png"), 75, widthX, (int)(widthX * (64F/92F))));
		pages.add(new GuidePage("book.msword.page11").setScale(2F).addTitle("book.msword.title11", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/11.png"), 90, widthX, (int)(widthX * (64F/158F))));
		pages.add(new GuidePage("book.msword.page12").setScale(2F).addTitle("book.msword.title12", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/12.png"), 70, widthX, (int)(widthX * (118F/172F))));
		pages.add(new GuidePage("book.msword.page13").setScale(2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/13.png"), 70, widthX, (int)(widthX * (118F/172F))));
		pages.add(new GuidePage("book.msword.page14").setScale(2F).addTitle("book.msword.title14", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/14.png"), 70, widthX, (int)(widthX * (118F/172F))));
		pages.add(new GuidePage("book.msword.page15").setScale(2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/15.png"), 70, widthX, (int)(widthX * (118F/172F))));
		pages.add(new GuidePage("book.msword.page16").setScale(2F).addTitle("book.msword.title16", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/16.png"), 90, widthX, (int)(widthX * (63F/163F))));
		pages.add(new GuidePage("book.msword.page17").setScale(2F).addTitle("book.msword.title17", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/17.png"), 75, widthX, (int)(widthX * (63F/98F))));
		pages.add(new GuidePage("book.msword.page18").setScale(2F).addTitle("book.msword.title18", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/18.png"), 70, widthX, (int)(widthX * (80F/160F))));
		pages.add(new GuidePage("book.msword.page19").setScale(2F));
		pages.add(new GuidePage("book.msword.page20").setScale(2F).addTitle("book.msword.title20", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/20.png"), 60, widthX, (int)(widthX * (118F/136F))));
		pages.add(new GuidePage("book.msword.page21").setScale(2F).addTitle("book.msword.title21", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/21.png"), 60, widthX, (int)(widthX * (118F/136F))));
		pages.add(new GuidePage("book.msword.page22").setScale(2F).addTitle("book.msword.title22", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/22.png"), 70, widthX, (int)(widthX * (122F/176F))));
		pages.add(new GuidePage("book.msword.page23").setScale(2F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/23.png"), 70, widthX, (int)(widthX * (122F/176F))));
		pages.add(new GuidePage("book.msword.page24").setScale(2F).addTitle("book.msword.title24", 0x800000, 1F)
				.addImage(new ResourceLocation(RefStrings.MODID + ":textures/gui/book/guide_meteor_sword/24.png"), 80, widthX, (int)(widthX * (60F/142F))));
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
