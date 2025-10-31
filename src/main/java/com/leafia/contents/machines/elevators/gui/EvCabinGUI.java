package com.leafia.contents.machines.elevators.gui;

import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.elevators.car.ElevatorEntity;
import com.leafia.contents.machines.elevators.car.ElevatorEntity.*;
import com.leafia.dev.custompacket.LeafiaCustomPacket;
import com.leafia.dev.gui.FiaUIRect;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvCabinGUI extends GuiInfoContainer {
	static final ResourceLocation tex = new ResourceLocation(RefStrings.MODID+":textures/gui/elevators/cab_002.png");
	final ElevatorEntity entity;

	final List<FiaUIRect> addMenuRects = new ArrayList<>();
	static final String[] addMenuButtons = new String[]{"Floor","Fire","Open","Close","Bell"};
	FiaUIRect addBtn;
	FiaUIRect rmBtn;
	int addBtnTimer = 0;
	int rmBtnTimer = 0;
	boolean renderAddMenu = false;
	int addMenuTimer = 0;
	int addMenuSelection = -1;
	int buttonSelection = -1;
	boolean dragging = false;
	public EvCabinGUI(ElevatorEntity entity) {
		super(entity.tempContainer);
		this.entity = entity;
		xSize = 176;
		ySize = 173;
	}
	private GuiTextField[] fields = new GuiTextField[3];
	private FiaUIRect[] fieldButtons = new FiaUIRect[3];
	private int[] fieldButtonTicks = new int[3];

	@Override
	public void initGui() {
		super.initGui();
		int spacing = 15;
		int yOffset = addMenuButtons.length*spacing/2;
		for (int i = 0; i < addMenuButtons.length; i++)
			addMenuRects.add(new FiaUIRect(this,xSize/2-40,ySize/2-yOffset+spacing*i,80,13));
		Keyboard.enableRepeatEvents(true);
		for (int z = 0; z < 3; z++) {
			fields[z] = new GuiTextField(0, this.fontRenderer, guiLeft + 120, guiTop + 11+19*z, 29, 9);
			fields[z].setTextColor(0x5BBC00);
			fields[z].setDisabledTextColour(0x499500);
			fields[z].setEnableBackgroundDrawing(false);
			fieldButtons[z] = new FiaUIRect(this,152,5+19*z,18,18);
			//fields[z].setMaxStringLength(5);
		}
		addBtn = new FiaUIRect(this,114,62,27,13);
		rmBtn = new FiaUIRect(this,143,62,27,13);
	}
	int lastLength = -1;
	int startX = 0;
	int startY = 0;
	ElevatorButton buttonToReset = null;

	@Override
	public void drawScreen(int mouseX,int mouseY,float partialTicks) {
		if (!renderAddMenu) {
			super.drawScreen(mouseX,mouseY,partialTicks);
			if (entity.buttons.size() < lastLength) {
				if (buttonToReset != null) {
					buttonToReset.x = startX;
					buttonToReset.y = startY;
					buttonToReset = null;
				}
				buttonSelection = -1;
				dragging = false;
				lastLength = entity.buttons.size();
			}
			if (dragging) {
				ElevatorButton button = entity.buttons.get(buttonSelection);
				int x = (mouseX-92-guiLeft)/3;
				int y = Math.round((78.5f-(mouseY-guiTop))/3);
				button.x = x;
				button.y = y;
			} else {
				super.renderHoveredToolTip(mouseX,mouseY);
				if (fieldButtons[0].isMouseIn(mouseX,mouseY))
					drawHoveringText(I18nUtil.resolveKey("gui.evcab.floor"),mouseX,mouseY);
				if (fieldButtons[1].isMouseIn(mouseX,mouseY))
					drawHoveringText(I18nUtil.resolveKey("gui.evcab.floordisplay"),mouseX,mouseY);
				if (fieldButtons[2].isMouseIn(mouseX,mouseY))
					drawHoveringText(I18nUtil.resolveKey("gui.evcab.label"),mouseX,mouseY);
				if (addBtn.isMouseIn(mouseX,mouseY))
					drawHoveringText(I18nUtil.resolveKey("gui.evcab.add"),mouseX,mouseY);
				if (rmBtn.isMouseIn(mouseX,mouseY))
					drawHoveringText(I18nUtil.resolveKey("gui.evcab.rm"),mouseX,mouseY);

				for (int i = 0; i < entity.buttons.size(); i++) {
					ElevatorButton button = entity.buttons.get(i);
					FiaUIRect rect = getButtonRect(button.x,button.y);
					if (rect.isMouseIn(mouseX,mouseY)) {
						String s = "Error!";
						if (button instanceof FloorButton) s = ((FloorButton) button).label;
						else if (button instanceof OpenButton) s = "Open";
						else if (button instanceof CloseButton) s = "Close";
						else if (button instanceof BellButton) s = "Bell";
						else if (button instanceof FireButton) s = "Fire Service";
						drawHoveringText(s,mouseX,mouseY);
					}
				}
			}
		} else {
			drawGuiContainerBackgroundLayer(partialTicks,mouseX,mouseY);
			drawDefaultBackground();
			Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
			for (int i = 0; i < addMenuButtons.length; i++) {
				FiaUIRect rect = addMenuRects.get(i);
				String s = addMenuButtons[i];
				if (s.equals("Fire")) s = s+" Service";
				else s = s+" Button";
				drawTexturedModalRect(rect.absX(),rect.absY(),176,addMenuSelection == i ? 13 : 0,rect.w,rect.h);
				this.fontRenderer.drawString(s,rect.absX()+rect.w/2-this.fontRenderer.getStringWidth(s)/2,rect.absY()+3,4210752);
				LeafiaGls.color(1,1,1);
				Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
			}
			if (addMenuSelection >= 0) {
				addMenuTimer++;
				if (addMenuTimer >= 10) {
					addMenuTimer = 0;
					addMenuSelection = -1;
					renderAddMenu = false;
				}
			}
		}
	}
	@Override
	protected void mouseClicked(int mouseX,int mouseY,int mouseButton) throws IOException {
		if (!renderAddMenu) {
			super.mouseClicked(mouseX,mouseY,mouseButton);
			if (mouseButton == 0) {
				if (!dragging) {
					if (addBtn.isMouseIn(mouseX,mouseY) && addBtnTimer <= 0) {
						addBtnTimer = 20;
						renderAddMenu = true;
						playClick(1);
					}
					if (rmBtn.isMouseIn(mouseX,mouseY)) {
						if (buttonSelection != -1) {
							rmBtnTimer = 20;
							playClick(1);
							EvButtonModifyPacket packet = new EvButtonModifyPacket();
							packet.localEntity = entity;
							packet.localMode = 2;
							packet.localTarget = buttonSelection;
							LeafiaCustomPacket.__start(packet).__sendToServer();
							buttonSelection = -1;
						} else playDenied();
					}
					for (int i = 0; i < entity.buttons.size(); i++) {
						ElevatorButton button = entity.buttons.get(i);
						FiaUIRect rect = new FiaUIRect(this,getButtonUIX(button.x),getButtonUIY(button.y),6,3);
						if (rect.isMouseIn(mouseX,mouseY)) {
							if (buttonSelection != i) {
								buttonSelection = i;
								if (button instanceof FloorButton) {
									FloorButton floor = (FloorButton)button;
									fields[0].setText(Integer.toString(floor.floor));
									fields[1].setText(entity.specialDisplayFloors.getOrDefault(floor.floor,Integer.toString(floor.floor)));
									fields[2].setText(floor.label);
								} else {
									fields[0].setText("");
									fields[1].setText("");
									fields[2].setText("");
								}
								playClick(1.5f);
							} else {
								dragging = true;
								buttonToReset = button;
								startX = button.x;
								startY = button.y;
							}
							return;
						}
					}
					boolean savePressed = false;
					for (int i = 0; i < 3; i++) {
						if (fieldButtons[i].isMouseIn(mouseX,mouseY)) {
							if (buttonSelection == -1 || entity.buttons.size() < buttonSelection || !(entity.buttons.get(buttonSelection) instanceof FloorButton)) {
								playDenied();
								break;
							}
							savePressed = true;
							EvButtonModifyPacket packet = new EvButtonModifyPacket();
							packet.localEntity = entity;
							packet.localMode = 3;
							packet.localTarget = buttonSelection;
							packet.localProperty = i;
							boolean doSend = true;
							if (i == 0) {
								try {
									packet.localFloor = Integer.parseInt(fields[0].getText());
								} catch (NumberFormatException ignored) {
									doSend = false;
								}
							} else
								packet.localLabel = fields[i].getText();
							if (doSend) {
								playClick(1);
								fieldButtonTicks[i] = 20;
								LeafiaCustomPacket.__start(packet).__sendToServer();
							} else
								playDenied();
						}
					}
					boolean fieldPressed = false;
					for (GuiTextField field : fields)
						fieldPressed = field.mouseClicked(mouseX,mouseY,mouseButton) || fieldPressed;
					if (!savePressed && !fieldPressed) {
						buttonSelection = -1;
						fields[0].setText("");
						fields[1].setText("");
						fields[2].setText("");
					}
				}
			}
		} else if (mouseButton == 0 && addMenuSelection < 0) {
			for (int i = 0; i < addMenuButtons.length; i++) {
				if (addMenuRects.get(i).isMouseIn(mouseX,mouseY)) {
					addMenuSelection = i;
					playClick(1);
					EvButtonModifyPacket packet = new EvButtonModifyPacket();
					packet.localEntity = entity;
					packet.localMode = 1;
					packet.localButtonType = i;
					LeafiaCustomPacket.__start(packet).__sendToServer();
					return;
				}
			}
			if (addMenuSelection < 0)
				renderAddMenu = false;
		}
	}
	@Override
	protected void mouseReleased(int mouseX,int mouseY,int mouseButton) {
		super.mouseReleased(mouseX,mouseY,mouseButton);
		if (dragging) {
			dragging = false;
			buttonToReset = null;
			ElevatorButton button = entity.buttons.get(buttonSelection);
			EvButtonModifyPacket packet = new EvButtonModifyPacket();
			packet.localEntity = entity;
			packet.localMode = 0;
			packet.localTarget = buttonSelection;
			packet.localX = button.x;
			packet.localY = button.y;
			LeafiaCustomPacket.__start(packet).__sendToServer();
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX,int mouseY) {
	}
	int getButtonUIX(int btnX) {
		return 92+btnX*3 -3;
	}
	int getButtonUIY(int btnY) {
		return 80-btnY*3 -3;
	}
	FiaUIRect getButtonRect(int x,int y) {
		return new FiaUIRect(this,getButtonUIX(x),getButtonUIY(y),6,3);
	}
	void renderButtons(int ox,int oy) {
		for (int i = 0; i < entity.buttons.size(); i++) {
			ElevatorButton button = entity.buttons.get(i);
			drawTexturedModalRect(ox+getButtonUIX(button.x),oy+getButtonUIY(button.y),176+(i==buttonSelection ? 6 : 0),39,6,3);
		}
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mouseX,int mouseY) {
		drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
		drawTexturedModalRect(guiLeft, guiTop,0,0,xSize,ySize);
		for (int z = 0; z < 3; z++) {
			if (fields[z].isFocused())
				drawTexturedModalRect(guiLeft + 116,guiTop + 7 + 19 * z,188,39,32,14);
		}
		for (GuiTextField field : fields)
			field.drawTextBox();
		Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
		LeafiaGls.color(1,1,1);
		for (int i = 0; i < 3; i++) {
			if (fieldButtonTicks[i] > 0) {
				fieldButtonTicks[i]--;
				drawTexturedModalRect(fieldButtons[i].absX(),fieldButtons[i].absY(),230,26,18,18);
			}
		}
		if (addBtnTimer > 0) {
			addBtnTimer--;
			drawTexturedModalRect(addBtn.absX(),addBtn.absY(),176,26,addBtn.w,addBtn.h);
		}
		if (rmBtnTimer > 0) {
			rmBtnTimer--;
			drawTexturedModalRect(rmBtn.absX(),rmBtn.absY(),203,26,rmBtn.w,rmBtn.h);
		}
		renderButtons(guiLeft,guiTop);
	}
	protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException
	{
		for (int z = 0; z < 3; z++)
			if (fields[z].textboxKeyTyped(p_73869_1_, p_73869_2_)) return;
		super.keyTyped(p_73869_1_, p_73869_2_);
	}
}
