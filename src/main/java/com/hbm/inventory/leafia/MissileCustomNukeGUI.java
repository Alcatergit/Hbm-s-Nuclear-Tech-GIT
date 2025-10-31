package com.hbm.inventory.leafia;

import com.hbm.config.BombConfig;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

public class MissileCustomNukeGUI extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gunBombSchematic.png");

	NBTTagCompound nbt;

	public MissileCustomNukeGUI(InventoryPlayer invPlayer, ItemStack stack) {
		super(new MissileCustomNukeContainer(invPlayer, stack));
		this.nbt = stack.getTagCompound();
		
		this.xSize = 176;
		this.ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer( int i, int j) {
		String name = I18nUtil.resolveKey("item.missile_customnuke.name");
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	public float getNukeAdj() {
		float nuke = nbt.getFloat("nuke");
		float tnt = nbt.getFloat("tnt");
		if(nuke == 0)
			return 0;

		return Math.min(nuke + tnt / 2, BombConfig.maxCustomNukeRadius);
	}

	public float getHydroAdj() {
		float hydro = nbt.getFloat("hydro");
		float nuke = nbt.getFloat("nuke");
		float tnt = nbt.getFloat("tnt");
		if(hydro == 0)
			return 0;

		return Math.min(hydro + nuke / 2 + tnt / 4, BombConfig.maxCustomHydroRadius);
	}

	public float getBaleAdj() {
		float bale = nbt.getFloat("bale");
		float hydro = nbt.getFloat("hydro");
		float nuke = nbt.getFloat("nuke");
		float tnt = nbt.getFloat("tnt");
		if(bale == 0)
			return 0;

		return Math.min(bale + hydro / 2 + nuke / 4 + tnt / 8, BombConfig.maxCustomBaleRadius);
	}

	public float getSchrabAdj() {
		float schrab = nbt.getFloat("schrab");
		float bale = nbt.getFloat("bale");
		float hydro = nbt.getFloat("hydro");
		float nuke = nbt.getFloat("nuke");
		float tnt = nbt.getFloat("tnt");
		if(schrab == 0)
			return 0;

		return Math.min(schrab + bale / 2 + hydro / 4 + nuke / 8 + tnt / 16, BombConfig.maxCustomSchrabRadius);
	}

	public float getSolAdj() {
		float sol = nbt.getFloat("sol");
		float schrab = nbt.getFloat("schrab");
		float bale = nbt.getFloat("bale");
		float hydro = nbt.getFloat("hydro");
		float nuke = nbt.getFloat("nuke");
		float tnt = nbt.getFloat("tnt");
		if(sol == 0)
			return 0;

		return Math.min(sol + schrab / 2 + bale / 4 + hydro / 8 + nuke / 16 + tnt / 32, BombConfig.maxCustomSolRadius);
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		String[] text;

		text = new String[] { TextFormatting.YELLOW + "Conventional Explosives (Radius " + this.nbt.getFloat("tnt") + "/" + BombConfig.maxCustomTNTRadius + ")",
				"Caps at " + BombConfig.maxCustomTNTRadius,
				"N²-like above level 75",
				TextFormatting.ITALIC + "\"Goes boom\"" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 16, guiTop + 88, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { TextFormatting.YELLOW + "Nuclear (Radius " + this.nbt.getFloat("nuke") + "(" + getNukeAdj() + ")/"+ BombConfig.maxCustomNukeRadius + ")",
				"Requires TNT level 16",
				"Caps at " + BombConfig.maxCustomNukeRadius,
				"Has fallout",
				TextFormatting.ITALIC + "\"Now I am become death, destroyer of worlds.\"" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 34, guiTop + 88, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { TextFormatting.YELLOW + "Thermonuclear (Radius " + this.nbt.getFloat("hydro") + "(" + getHydroAdj() + ")/" + BombConfig.maxCustomHydroRadius + ")",
				"Requires nuclear level 100",
				"Caps at " + BombConfig.maxCustomHydroRadius,
				"Reduces added fallout by salted stage by 75%",
				TextFormatting.ITALIC + "\"And for my next trick, I'll make",
				TextFormatting.ITALIC + "the island of Elugelab disappear!\"" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 52, guiTop + 88, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { TextFormatting.YELLOW + "Balefire (Radius " + this.nbt.getFloat("bale") + "/(" + getBaleAdj() + ")/" + BombConfig.maxCustomBaleRadius + ")",
				"Requires nuclear level 50",
				"Caps at " + BombConfig.maxCustomBaleRadius,
				TextFormatting.ITALIC + "\"Antimatter, Balefire, whatever.\"" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 70, guiTop + 88, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { TextFormatting.YELLOW + "Salted (Radius " + this.nbt.getFloat("dirty") + "/" + BombConfig.maxCustomDirtyRadius + ")",
				"Extends fallout of nuclear and",
				"thermonuclear stages",
				"Caps at " + BombConfig.maxCustomDirtyRadius,
				TextFormatting.ITALIC + "\"Not to be confused with tablesalt.\"" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 53, guiTop + 83, 25, 5, mouseX, mouseY, text);
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 53, guiTop + 106, 25, 5, mouseX, mouseY, text);
		
		text = new String[] { TextFormatting.YELLOW + "Schrabidium (Radius " + this.nbt.getFloat("schrab") + "(" + getSchrabAdj() + ")/" + BombConfig.maxCustomSchrabRadius + ")",
				"Requires nuclear level 50",
				"Caps at " + BombConfig.maxCustomSchrabRadius,
				TextFormatting.ITALIC + "\"For the hundredth time,",
				TextFormatting.ITALIC + "you can't bypass these caps!\"" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 88, guiTop + 88, 18, 18, mouseX, mouseY, text);

		text = new String[] { TextFormatting.YELLOW + "Solinium (Radius " + this.nbt.getFloat("sol") + "(" + getSolAdj() + ")/" + BombConfig.maxCustomSolRadius + ")",
				"Requires nuclear level 25",
				"Caps at " + BombConfig.maxCustomSolRadius,
				TextFormatting.ITALIC + "\"For the hundredth time,",
				TextFormatting.ITALIC + "you can't bypass these caps!\"" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 106, guiTop + 88, 18, 18, mouseX, mouseY, text);
		
		text = new String[] { TextFormatting.YELLOW + "Ice cream (Level " + this.nbt.getFloat("euph") + "/" + BombConfig.maxCustomEuphLvl + ")",
				"Requires schrabidium and solinium level 1",
				"Caps at " + BombConfig.maxCustomEuphLvl,
				TextFormatting.ITALIC + "\"Probably not ice cream but the label came off.\"" };
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 142, guiTop + 88, 18, 18, mouseX, mouseY, text);
		
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		byte best = 10;

		if(this.nbt.getFloat("euph") > 0){
			drawTexturedModalRect(guiLeft + 142, guiTop + 89, 176, 108, 18, 18); //Euph strongest
			best = 9;
		}

		if(this.nbt.getFloat("sol") > 0){
			if(best == 10){
				drawTexturedModalRect(guiLeft + 106, guiTop + 89, 194, 90, 18, 18); //Sol strongest
				best = 8;
			}
			else{
				drawTexturedModalRect(guiLeft + 106, guiTop + 89, 176, 90, 18, 18);
			}
		}

		if(this.nbt.getFloat("schrab") > 0){
			if(best == 10){
				drawTexturedModalRect(guiLeft + 88, guiTop + 89, 194, 72, 18, 18); //Schrab strongest
				best = 7;
			}
			else{
				drawTexturedModalRect(guiLeft + 88, guiTop + 89, 176, 72, 18, 18);
			}
		}

		if(this.nbt.getFloat("bale") > 0){
			if(best == 10){
				drawTexturedModalRect(guiLeft + 70, guiTop + 89, 194, 54, 18, 18); //Bale strongest
				best = 6;
			}
			else{
				drawTexturedModalRect(guiLeft + 70, guiTop + 89, 176, 54, 18, 18);
			}
		}
			
		if(this.nbt.getFloat("hydro") > 0){
			if(best == 10){
				drawTexturedModalRect(guiLeft + 52, guiTop + 89, 194, 36, 18, 18); //Hydro strongest
				best = 5;
			}
			else{
				drawTexturedModalRect(guiLeft + 52, guiTop + 89, 176, 36, 18, 18);
			}
		}
			
		if(this.nbt.getFloat("nuke") > 0){
			if(best == 10){
				drawTexturedModalRect(guiLeft + 34, guiTop + 89, 194, 18, 18, 18); //Nuke strongest
				best = 4;
			}
			else{
				drawTexturedModalRect(guiLeft + 34, guiTop + 89, 176, 18, 18, 18);
			}
		}
			
		if(this.nbt.getFloat("tnt") > 0){
			if(best == 10){
				drawTexturedModalRect(guiLeft + 16, guiTop + 89, 194, 0, 18, 18); //TNT strongest
				best = 3;
			}
			else{
				drawTexturedModalRect(guiLeft + 16, guiTop + 89, 176, 0, 18, 18);
			}
		}
			
		
		if(this.nbt.getFloat("dirty") > 0){
			if(best < 6 && best > 3){
				drawTexturedModalRect(guiLeft + 53, guiTop + 83, 201, 125, 25, 29);
			}
			else{
				drawTexturedModalRect(guiLeft + 53, guiTop + 83, 176, 125, 25, 29);
			}
		}
		
	}
}
