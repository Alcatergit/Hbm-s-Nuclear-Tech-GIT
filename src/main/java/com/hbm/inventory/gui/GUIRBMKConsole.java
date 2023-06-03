package com.hbm.inventory.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.RBMKColumn;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class GUIRBMKConsole extends GuiScreen {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/gui_rbmk_console.png");
	private TileEntityRBMKConsole console;
	protected int guiLeft;
	protected int guiTop;
	protected int xSize;
	protected int ySize;
	
	private boolean[] selection = new boolean[15 * 15];
	private boolean az5Lid = true;
	private long lastPress = 0;
	
	private GuiTextField field;

	public GUIRBMKConsole(InventoryPlayer invPlayer, TileEntityRBMKConsole tedf) {
		super();
		this.console = tedf;
		
		this.xSize = 244;
		this.ySize = 172;
	}
	
	public void initGui() {
		super.initGui();
		
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		
		Keyboard.enableRepeatEvents(true);
		
		this.field = new GuiTextField(0, this.fontRenderer, guiLeft + 9, guiTop + 84, 35, 9);
		this.field.setTextColor(0x00ff00);
		this.field.setDisabledTextColour(0x008000);
		this.field.setEnableBackgroundDrawing(false);
		this.field.setMaxStringLength(3);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		this.drawDefaultBackground();
		this.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
		
		int bX = 86;
		int bY = 11;
		int size = 10;

		if(guiLeft + 86 <= mouseX && guiLeft + 86 + 150 > mouseX && guiTop + 11 < mouseY && guiTop + 11 + 10150 >= mouseY) {
			int index = ((mouseX - bX - guiLeft) / size + (mouseY - bY - guiTop) / size * 15);
			
			if(index > 0 && index < console.columns.length) {
				RBMKColumn col = console.columns[index];
				
				if(col != null) {
					
					List<String> list = new ArrayList<>();
					list.add(col.type.toString());
					list.addAll(col.getFancyStats());
					this.drawHoveringText(list, mouseX, mouseY);
				}
			}
		}

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 61, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select all control rods" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 72, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Deselect all" } );

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 2; j++) {
				int id = i * 2 + j + 1;
				this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 6 + 40 * j, guiTop + 8 + 21 * i, 18, 18, mouseX, mouseY, new String[]{ "§e" + I18nUtil.resolveKey("rbmk.console." + console.screens[id - 1].type.name().toLowerCase(), id) } );
				this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 24 + 40 * j, guiTop + 8 + 21 * i, 18, 18, mouseX, mouseY, new String[]{ I18nUtil.resolveKey("rbmk.console.assign", id) } );
			}
		}

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 62, guiTop + 83, 10, 10, mouseX, mouseY, new String[]{ "§e" + I18nUtil.resolveKey("rbmk.console." + console.graph.type.name().toLowerCase()) } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 72, guiTop + 83, 10, 10, mouseX, mouseY, new String[]{ I18nUtil.resolveKey("rbmk.console.assignG") } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 6, guiTop + 96, 76, 38, mouseX, mouseY, new String[]{ I18nUtil.resolveKey("rbmk.graph." + console.graph.type.name().toLowerCase(), console.graph.dataBuffer[console.graph.dataBuffer.length-1]) } );
			
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 6, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select red group" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 17, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select yellow group" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 28, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select green group" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 39, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select blue group" } );
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft + 50, guiTop + 70, 10, 10, mouseX, mouseY, new String[]{ "Select purple group" } );
	}
	
	public void drawCustomInfoStat(int mouseX, int mouseY, int x, int y, int width, int height, int tPosX, int tPosY, String[] text) {
		
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY)
			this.drawHoveringText(Arrays.asList(text), tPosX, tPosY);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int i) throws IOException {
		super.mouseClicked(mouseX, mouseY, i);
		this.field.mouseClicked(mouseX, mouseY, i);
		
		int bX = 86;
		int bY = 11;
		int size = 10;

		//toggle column selection
		if(guiLeft + 86 <= mouseX && guiLeft + 86 + 150 > mouseX && guiTop + 11 < mouseY && guiTop + 11 + 150 >= mouseY) {
			
			int index = ((mouseX - bX - guiLeft) / size + (mouseY - bY - guiTop) / size * 15);
			
			if(index > 0 && index < selection.length && console.columns[index] != null) {
				this.selection[index] = !this.selection[index];
				
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 0.75F + (this.selection[index] ? 0.25F : 0.0F)));
				return;
			}
		}

		//clear selection
		if(guiLeft + 72 <= mouseX && guiLeft + 72 + 10 > mouseX && guiTop + 70 < mouseY && guiTop + 70 + 10 >= mouseY) {
			this.selection = new boolean[15 * 15];
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 0.5F));
			return;
		}

		//select all control rods
		if(guiLeft + 61 <= mouseX && guiLeft + 61 + 10 > mouseX && guiTop + 70 < mouseY && guiTop + 70 + 10 >= mouseY) {
			this.selection = new boolean[15 * 15];

			for(int j = 0; j < console.columns.length; j++) {
				
				if(console.columns[j] != null && console.columns[j].type == ColumnType.CONTROL) {
					this.selection[j] = true;
				}
			}
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.5F));
			return;
		}

		//select color groups
		for(int k = 0; k < 5; k++) {
			
			if(guiLeft + 6 + k * 11 <= mouseX && guiLeft + 6 + k * 11 + 10 > mouseX && guiTop + 70 < mouseY && guiTop + 70 + 10 >= mouseY) {
				this.selection = new boolean[15 * 15];

				for(int j = 0; j < console.columns.length; j++) {
					
					if(console.columns[j] != null && console.columns[j].type == ColumnType.CONTROL && console.columns[j].data.getShort("color") == k) {
						this.selection[j] = true;
					}
				}
				
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 0.8F + k * 0.1F));
				return;
			}
		}

		//AZ-5
		if(guiLeft + 30 <= mouseX && guiLeft + 30 + 28 > mouseX && guiTop + 138 < mouseY && guiTop + 138 + 28 >= mouseY) {
			
			if(az5Lid) {
				az5Lid = false;
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(HBMSoundHandler.rbmk_az5_cover, 0.5F));
			} else if(lastPress + 3000 < System.currentTimeMillis()) {
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(HBMSoundHandler.shutdown, 1));
				lastPress = System.currentTimeMillis();
				
				NBTTagCompound control = new NBTTagCompound();
				control.setDouble("level", 0);

				for(int j = 0; j < console.columns.length; j++) {
					if(console.columns[j] != null && console.columns[j].type == ColumnType.CONTROL)
						control.setInteger("sel_" + j, j);
				}
				
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.getPos()));
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1F));
			}
			return;
		}

		//save control rod setting
		if(guiLeft + 48 <= mouseX && guiLeft + 48 + 12 > mouseX && guiTop + 82 < mouseY && guiTop + 82 + 12 >= mouseY) {
			
			double level;
			
			if(NumberUtils.isCreatable(field.getText())) {
				int j = (int)MathHelper.clamp(Double.parseDouble(field.getText()), 0, 100);
				field.setText(j + "");
				level = j * 0.01D;
			} else {
				return;
			}
			
			NBTTagCompound control = new NBTTagCompound();
			control.setDouble("level", level);

			for(int j = 0; j < selection.length; j++) {
				if(selection[j])
					control.setInteger("sel_" + j, j);
			}
			
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.getPos()));
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1F));
		}

		//submit selection for status screen
		for(int j = 0; j < 3; j++) {
			for(int k = 0; k < 2; k++) {
				
				int id = j * 2 + k;
				
				if(guiLeft + 6 + 40 * k <= mouseX && guiLeft + 6 + 40 * k + 18 > mouseX && guiTop + 8 + 21 * j < mouseY && guiTop + 8 + 21 * j + 18 >= mouseY) {
					NBTTagCompound control = new NBTTagCompound();
					control.setByte("toggle", (byte) id);
					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.getPos()));
					mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1F));
					return;
				}
				
				if(guiLeft + 24 + 40 * k <= mouseX && guiLeft + 24 + 40 * k + 18 > mouseX && guiTop + 8 + 21 * j < mouseY && guiTop + 8 + 21 * j + 18 >= mouseY) {

					NBTTagCompound control = new NBTTagCompound();
					control.setByte("id", (byte) id);

					for(int s = 0; s < selection.length; s++) {
						if(selection[s]) {
							control.setBoolean("s" + s, true);
						}
					}

					PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.getPos()));
					mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1F));
					return;
				}
			}
		}

		//Graph Selections
		if(guiLeft + 62 <= mouseX && guiLeft + 72 > mouseX && guiTop + 83 < mouseY && guiTop + 93 >= mouseY) {
			NBTTagCompound control = new NBTTagCompound();
			control.setByte("toggle", (byte) 99);
			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.getPos()));
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1F));
			return;
		}
		
		if(guiLeft + 72 <= mouseX && guiLeft + 82 > mouseX && guiTop + 83 < mouseY && guiTop + 93 >= mouseY) {

			NBTTagCompound control = new NBTTagCompound();
			control.setByte("id", (byte) 99);

			for(int s = 0; s < selection.length; s++) {
				if(selection[s]) {
					control.setBoolean("s" + s, true);
				}
			}

			PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, console.getPos()));
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1F));
			return;
		}
	}

	protected void drawGuiContainerBackgroundLayer(float interp, int mX, int mY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(az5Lid) {
			drawTexturedModalRect(guiLeft + 30, guiTop + 138, 228, 172, 28, 28);
		} else if(lastPress + 3000 >= System.currentTimeMillis()){
			drawTexturedModalRect(guiLeft + 30, guiTop + 136, 228, 228, 28, 28);
		}

		for(int j = 0; j < 3; j++) {
			for(int k = 0; k < 2; k++) {
				int id = j * 2 + k;
				drawTexturedModalRect(guiLeft + 6 + 40 * k, guiTop + 8 + 21 * j, this.console.screens[id].type.offset, 238, 18, 18);
			}
		}
		drawTexturedModalRect(guiLeft + 62, guiTop + 83, (int)(this.console.graph.type.offset*10D/18D), 228, 10, 10);
		int bX = 86;
		int bY = 11;
		int size = 10;
		
		for(int i = 0; i < console.columns.length; i++) {
			
			RBMKColumn col = console.columns[i];
			
			if(col == null)
				continue;

			int x = bX + size * (i % 15);
			int y = bY + size * (i / 15);
			
			int tX = col.type.offset;
			int tY = 172;
			
			drawTexturedModalRect(guiLeft + x, guiTop + y, tX, tY, size, size);
			
			int h = (int)Math.ceil((col.data.getDouble("heat") - 20) * 10 / col.data.getDouble("maxHeat"));
			drawTexturedModalRect(guiLeft + x, guiTop + y + size - h, 0, 192 - h, 10, h);
			
			switch(col.type) {
			case ABSORBER: break;
			case BLANK: break;
			case MODERATOR: break;
			case REFLECTOR: break;
			case OUTGASSER: break;
			case BREEDER: break;
			
			case COOLER:
				int cryo = (int)Math.ceil(col.data.getShort("cryo") * 8 / 16000);
				if(cryo > 0)
					drawTexturedModalRect(guiLeft + x + 3, guiTop + y + size - cryo - 1, 123, 191 - cryo, 4, cryo);
				break;
			case CONTROL:
				int color = col.data.getShort("color");
				if(color > -1)
					drawTexturedModalRect(guiLeft + x, guiTop + y, color * size, 202, 10, 10);
				
			case CONTROL_AUTO:
				int fr = 8 - (int)Math.ceil((col.data.getDouble("level") * 8));
				drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 1, 24, 183, 2, fr);
				break;

			case FUEL:
			case FUEL_SIM:
				if(col.data.hasKey("c_heat")) {
					int fh = (int)Math.ceil((col.data.getDouble("c_heat") - 20) * 8 / col.data.getDouble("c_maxHeat"));
					drawTexturedModalRect(guiLeft + x + 1, guiTop + y + size - fh - 1, 11, 191 - fh, 2, fh);
					
					int fe = (int)Math.ceil((col.data.getDouble("enrichment")) * 8);
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + size - fe - 1, 14, 191 - fe, 2, fe);
				}
				break;
			case HEATEX:
				int fk = (int)Math.ceil((col.data.getInteger("inputFluidAmount")) * 8 / col.data.getDouble("inputFluidMax"));
				drawTexturedModalRect(guiLeft + x + 1, guiTop + y + size - fk - 1, 131, 191 - fk, 3, fk);
				int fz = (int)Math.ceil((col.data.getInteger("outputFluidAmount")) * 8 / col.data.getDouble("outputFluidMax"));
				drawTexturedModalRect(guiLeft + x + 6, guiTop + y + size - fz - 1, 136, 191 - fz, 3, fz);
				break;
			case BOILER:
				int fw = (int)Math.ceil((col.data.getInteger("water")) * 8 / col.data.getDouble("maxWater"));
				drawTexturedModalRect(guiLeft + x + 1, guiTop + y + size - fw - 1, 41, 191 - fw, 3, fw);
				int fs = (int)Math.ceil((col.data.getInteger("steam")) * 8 / col.data.getDouble("maxSteam"));
				drawTexturedModalRect(guiLeft + x + 6, guiTop + y + size - fs - 1, 46, 191 - fs, 3, fs);

				Fluid fluid = FluidRegistry.getFluid(col.data.getString("type"));
				
				if(fluid == ModForgeFluids.steam)
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 1, 44, 183, 2, 2);
				if(fluid == ModForgeFluids.hotsteam)
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 3, 44, 185, 2, 2);
				if(fluid == ModForgeFluids.superhotsteam)
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 5, 44, 187, 2, 2);
				if(fluid == ModForgeFluids.ultrahotsteam)
					drawTexturedModalRect(guiLeft + x + 4, guiTop + y + 7, 44, 189, 2, 2);
				
				break;
			}
			
			if(this.selection[i])
				drawTexturedModalRect(guiLeft + x, guiTop + y, 0, 192, 10, 10);
		}
		
		this.field.drawTextBox();
		drawGraph(8, 98, 72, 34, scaleData(this.console.graph.dataBuffer), 0x4CFF00, 3F);
	}
	
	@Override
	protected void keyTyped(char c, int i) throws IOException {

		if(this.field.textboxKeyTyped(c, i))
			return;
		
		if(i == 1 || i == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
			this.mc.player.closeScreen();
			return;
		}
		
		super.keyTyped(c, i);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	private double[] scaleData(int[] arrayData){
		int max = 0;
		for(int n : arrayData){
			if(n > max){
				max = n;
			}
		}
		int median = 2*arrayData[(int)(arrayData.length/2)];
		if(max < median){
			max = median;
		}
		double[] scaledData = new double[arrayData.length];
		if(max == 0){
			Arrays.fill(scaledData, 0);
		} else {
			for(int i = 0; i < scaledData.length; i++){
				scaledData[i] = ((double)arrayData[i]) / ((double)max);
			}
		}
		return scaledData;
	}

	private void drawGraph(int x, int y, int width, int height, double[] data, int color, float thickness) {
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.color(((color >> 16) & 0xFF) / 255.0F, ((color >> 8) & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
        GlStateManager.glLineWidth(thickness);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
        
        float stepsize = ((float)width)/(data.length-1);
        double currentX = x;
        double currentY = y + height - data[0]*height;
        buf.pos(guiLeft + currentX, guiTop + currentY, this.zLevel).endVertex();
        for(int i = 1; i < data.length; i++) {
            currentX = x + i*stepsize;
            currentY = y + height - data[i]*height;
            buf.pos(guiLeft + currentX, guiTop + currentY, this.zLevel).endVertex();
        }
        
        Tessellator.getInstance().draw();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableTexture2D();
    }
}