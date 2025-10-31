package com.hbm.inventory.gui;

import java.util.Arrays;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.config.WeaponConfig;
import com.hbm.inventory.container.ContainerMachineRadar;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineRadar;
import com.hbm.packet.AuxButtonPacket;
import com.hbm.packet.PacketDispatcher;

import api.hbm.entity.IRadarDetectable.RadarTargetType;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GUIMachineRadar extends GuiInfoContainer {
	
	private static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/gui_radar.png");
	private TileEntityMachineRadar diFurnace;

	public GUIMachineRadar(InventoryPlayer invPlayer, TileEntityMachineRadar tedf) {
		super(new ContainerMachineRadar(invPlayer, tedf));
		diFurnace = tedf;
		
		this.xSize = 216;
		this.ySize = 234;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);

		this.drawElectricityInfo(this, mouseX, mouseY, guiLeft + 8, guiTop + 221, 200, 7, diFurnace.power, TileEntityMachineRadar.maxPower);

		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 10, guiTop + 98, 8, 8, mouseX, mouseY, new String[] {"Detect Missiles"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 10, guiTop + 108, 8, 8, mouseX, mouseY, new String[] {"Detect Players"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 10, guiTop + 118, 8, 8, mouseX, mouseY, new String[] {"Smart Mode", "Redstone output only triggers on approaching descending missiles"});
		this.drawCustomInfoStat(mouseX, mouseY, guiLeft - 10, guiTop + 128, 8, 8, mouseX, mouseY, new String[] {"Redstone Mode", "On: Redstone output based on range", "Off: Redstone output based on tier"});


		if(!diFurnace.nearbyMissiles.isEmpty()) {
			for(int[] m : diFurnace.nearbyMissiles) {
				int x = guiLeft + (int)((m[0] - diFurnace.getPos().getX()) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) + 108;
				int z = guiTop + (int)((m[1] - diFurnace.getPos().getZ()) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) + 117;
				
				if(mouseX + 4 > x && mouseX - 4 < x && mouseY + 4 > z && mouseY - 4 < z) {
					double relX = m[0] - diFurnace.getPos().getX();
					double relY = m[3] - diFurnace.getPos().getY();
					double relZ = m[1] - diFurnace.getPos().getZ();
					int distanceToMissile = (int)Math.sqrt(relX*relX+relY*relY+relZ*relZ);
					String[] text = new String[] { RadarTargetType.values()[m[2]].name, "Dist.: "+distanceToMissile+"m", "Alt.: " + m[3] };
					
					this.drawHoveringText(Arrays.asList(text), x, z);
					
					return;
				}
			}
		}
	}

	@Override
	protected void mouseClicked(int x, int y, int i) throws IOException {
		super.mouseClicked(x, y, i);

		if(guiLeft -10 <= x && guiLeft + -10 + 8 > x && guiTop + 98 < y && guiTop + 98 + 8 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos().getX(), diFurnace.getPos().getY(), diFurnace.getPos().getZ(), 0, 0));
		}

		if(guiLeft -10 <= x && guiLeft + -10 + 8 > x && guiTop + 108 < y && guiTop + 108 + 8 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos().getX(), diFurnace.getPos().getY(), diFurnace.getPos().getZ(), 0, 1));
		}

		if(guiLeft -10 <= x && guiLeft + -10 + 8 > x && guiTop + 118 < y && guiTop + 118 + 8 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos().getX(), diFurnace.getPos().getY(), diFurnace.getPos().getZ(), 0, 2));
		}

		if(guiLeft -10 <= x && guiLeft + -10 + 8 > x && guiTop + 128 < y && guiTop + 128 + 8 >= y) {
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
			PacketDispatcher.wrapper.sendToServer(new AuxButtonPacket(diFurnace.getPos().getX(), diFurnace.getPos().getY(), diFurnace.getPos().getZ(), 0, 3));
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		String name = I18n.format("container.radar");
		
		this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawTexturedModalRect(guiLeft - 14, guiTop + 94, 216, 198, 14, 46);

		if(diFurnace.scanMissiles || (diFurnace.jammed && diFurnace.getWorld().rand.nextBoolean()))
			drawTexturedModalRect(guiLeft - 10, guiTop + 98, 230, 202, 8, 8);
		
		if(diFurnace.scanPlayers || (diFurnace.jammed && diFurnace.getWorld().rand.nextBoolean()))
			drawTexturedModalRect(guiLeft - 10, guiTop + 108, 230, 212, 8, 8);
		
		if(diFurnace.smartMode || (diFurnace.jammed && diFurnace.getWorld().rand.nextBoolean()))
			drawTexturedModalRect(guiLeft - 10, guiTop + 118, 230, 222, 8, 8);
		
		if(diFurnace.redMode || (diFurnace.jammed && diFurnace.getWorld().rand.nextBoolean()))
			drawTexturedModalRect(guiLeft - 10, guiTop + 128, 230, 232, 8, 8);
		
		if(diFurnace.power > 0) {
			int i = (int)diFurnace.getPowerScaled(200);
			drawTexturedModalRect(guiLeft + 8, guiTop + 221, 0, 234, i, 16);
		}

		if(diFurnace.jammed) {
			
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 5; j++) {
					drawTexturedModalRect(guiLeft + 8 + i * 40, guiTop + 17 + j * 40, 216, 118 + diFurnace.getWorld().rand.nextInt(41), 40, 40);
				}
			}
			
			return;
		}
		
		if(!diFurnace.nearbyMissiles.isEmpty()) {
			for(int[] m : diFurnace.nearbyMissiles) {
				int x = (int)((m[0] - diFurnace.getPos().getX()) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int z = (int)((m[1] - diFurnace.getPos().getZ()) / ((double)WeaponConfig.radarRange * 2 + 1) * (200D - 8D)) - 4;
				int t = m[2];

				drawTexturedModalRect(guiLeft + 108 + x, guiTop + 117 + z, 216, 8 * t, 8, 8);
			}
		}
	}
}
