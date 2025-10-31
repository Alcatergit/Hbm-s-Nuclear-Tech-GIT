package com.leafia.contents.machines.reactors.pwr.container;

import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import com.hbm.util.Tuple.Pair;
import com.leafia.contents.machines.reactors.pwr.PWRData;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentEntity;
import com.leafia.contents.machines.reactors.pwr.blocks.components.control.PWRControlTE;
import com.leafia.contents.machines.reactors.pwr.container.PWRTerminalContainer.RemoteSlot;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.transformer.LeafiaGls;
import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.group.LeafiaMap;
import com.llib.group.LeafiaSet;
import com.llib.math.range.RangeInt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class PWRTerminalUI extends GuiInfoContainer {
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/reactors/leafia_pwr.png");

	final PWRData core;
	//final ItemStackHandler remote;
	final PWRComponentEntity entity;

	static final int UI_EDGE = 10;
	static final int UI_CORNER = 28;
	static final int UI_SLOT = 18;

	RangeInt rangeX = new RangeInt(Integer.MAX_VALUE,Integer.MIN_VALUE);
	RangeInt rangeZ = new RangeInt(Integer.MAX_VALUE,Integer.MIN_VALUE);
	static final int centerX = 45+176/2;
	static final int inventoryStart = 18;
	static final int inventoryHeight = 100;
	static final int preferredWidth = 176;
	int preferredHeight = 0;
	double calculatedScale = 1;

	final int pwrWidth;
	final int pwrHeight;
	int rawWidth = 0;
	int rawHeight = 0;
	static class TileData {
		final int width;
		final int height;
		final int xTex;
		final int yTex;
		final int xOffset;
		final int yOffset;
		TileData(int width,int height,int xTex,int yTex,int xOffset,int yOffset) {
			this.width = width;
			this.height = height;
			this.xTex = xTex;
			this.yTex = yTex;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
		}
	}
	void drawEdgeTile(int x,int y,TileData LU,TileData RU,TileData LD,TileData RD) {
		drawTexturedModalRect(x+LU.xOffset,y+LU.yOffset,LU.xTex,LU.yTex,LU.width,LU.height);
		drawTexturedModalRect(x+RU.xOffset+9,y+RU.yOffset,RU.xTex,RU.yTex,RU.width,RU.height);
		drawTexturedModalRect(x+LD.xOffset,y+LD.yOffset+9,LD.xTex,LD.yTex,LD.width,LD.height);
		drawTexturedModalRect(x+RD.xOffset+9,y+RD.yOffset+9,RD.xTex,RD.yTex,RD.width,RD.height);
	}
	static class EdgeTile {
		final TileData LU; final TileData RU;
		final TileData LD; final TileData RD;
		@Spaghetti("i'm stupid ok?")
		EdgeTile(ColumnVector vector,TileData lu,TileData ru,TileData ld,TileData rd) {
			// i'm stupid ok?
			if (vector.y == 1) {
				LU = lu;
				RU = ru;
				LD = ld;
				RD = rd;
			} else if (vector.x == 1) {
				LU = ld;
				RU = lu;
				LD = rd;
				RD = ru;
			} else if (vector.y == -1) {
				LU = rd;
				RU = ld;
				LD = ru;
				RD = lu;
			} else if (vector.x == -1) {
				LU = ru;
				RU = rd;
				LD = lu;
				RD = ld;
			} else
				throw new LeafiaDevFlaw("I'm stupid ok?");
		}
		void draw(PWRTerminalUI self,int x,int y) { self.drawEdgeTile(x,y,LU,RU,LD,RD); }
	}
	final Map<ColumnPos,EdgeTile> edgeTileMap = new LeafiaMap<>();
	TileData tileA(ColumnVector vector,int fromCenterX,int fromCenterY) {
		ColumnPos texPos = new ColumnPos(2,2).add(vector,fromCenterY).add(vector.right(),fromCenterX);
		return tileA(texPos.x,texPos.y);
	}
	TileData tileB(ColumnVector vector,int fromCenterX,int fromCenterY) {
		ColumnPos texPos = new ColumnPos(1,1).add(vector,fromCenterY).add(vector.right(),fromCenterX);
		return tileB(texPos.x,texPos.y);
	}
	TileData tileA(int tx,int ty) {
		int width = 10; int height = 10;
		int texX = 0; int texY = 0;
		int offX = 0; int offY = 0;
		switch (tx*10+ty) {
			case 21:
				texX = 140; texY = 130;
				offX = -1; offY = -1;
				break;
			case 12:
				texX = 130; texY = 141;
				offX = -1;
				break;
			case 32:
				texX = 151; texY = 140;
				offY = -1;
				break;
			case 23:
				texX = 141; texY = 151;
				break;
			default:
				width = 9; height = 9;
				switch(tx) {
					case 0:
						texX = 120;
						width = 10;
						offX = -1;
						break;
					case 4:
						texX = 161;
						width = 10;
						break;
					default:
						texX = 131+(tx-1)*10;
						break;
				}
				switch(ty) {
					case 0:
						texY = 120;
						height = 10;
						offY = -1;
						break;
					case 4:
						texY = 161;
						height = 10;
						break;
					default:
						texY = 131+(ty-1)*10;
						break;
				}
				break;
		}
		return new TileData(width,height,texX,texY,offX,offY);
	}
	TileData tileB(int tx,int ty) {
		switch (tx*10+ty) {
			case  0: return new TileData(9,10,121,172,0,-1);
			case 10: return new TileData(9,10,131,172,0,-1);
			case 20: return new TileData(10,9,141,173,0,0);

			case  1: return new TileData(10,9,120,183,-1,0);
			case 11: return new TileData(9,9,131,183,0,0);
			case 21: return new TileData(10,9,141,183,0,0);

			case  2: return new TileData(10,9,120,193,-1,0);
			case 12: return new TileData(9,10,131,193,0,0);
			case 22: return new TileData(9,10,141,193,0,0);
		}
		throw new LeafiaDevFlaw("PWR texture "+tx+","+ty+" does not exist on Type B Texture");
	}
	static class ColumnPos {
		final int x;
		final int y;
		ColumnPos(int x,int y) {
			this.x = x;
			this.y = y;
		}
		ColumnPos(ColumnVector vector) { this.x = vector.x; this.y = -vector.y; }
		ColumnPos(BlockPos pos) { this.x = pos.getX(); this.y = pos.getZ(); }
		ColumnPos add(int x,int y) { return new ColumnPos(this.x+x,this.y+y); }
		ColumnPos add(ColumnVector vector,int distance) { return add(vector.x*distance,-vector.y*distance); }
		ColumnPos subtract(int x,int y) { return new ColumnPos(this.x-x,this.y-y); }
		ColumnPos subtract(ColumnVector vector,int distance) { return subtract(vector.x*distance,-vector.y*distance); }
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof ColumnPos) {
				ColumnPos pos = (ColumnPos)obj;
				return pos.x == this.x && pos.y == this.y;
			}
			return super.equals(obj);
		}
	}
	static class ColumnVector {
		final int x;
		final int y;
		ColumnVector(EnumFacing facing) { x = facing.getXOffset(); y = -facing.getZOffset(); }
		ColumnVector(ColumnPos pos) { x = pos.x; y = -pos.y; }
		ColumnVector(int x,int y) { this.x = x; this.y = y; }
		ColumnVector right() { return new ColumnVector(y,-x); }
		ColumnVector left() { return new ColumnVector(-y,x); }
		ColumnVector back() { return new ColumnVector(-x,-y); }
	}
	final IBlockState terminalState;
	final BlockPos terminalPos;
	final LeafiaSet<BlockPos> fuels;
	final LeafiaSet<BlockPos> controls;
	final HashMap<String,LeafiaSet<BlockPos>> controlsByName;
	public PWRTerminalUI(InventoryPlayer invPlayer,TileEntity terminal,PWRData core) {
		super(new PWRTerminalContainer(invPlayer,terminal,core));
		terminalPos = terminal.getPos();
		terminalState = terminal.getWorld().getBlockState(terminalPos);
		this.core = core;
		Pair<LeafiaSet<BlockPos>,LeafiaSet<BlockPos>> keyElements = core.getProjectionFuelAndControlPositions();
		fuels = keyElements.getA();
		controls = keyElements.getB();
		controlsByName = new HashMap<>();
		for (BlockPos pos : core.controls) {
			TileEntity entity1 = core.getWorld().getTileEntity(pos);
			if (entity1 instanceof PWRControlTE) {
				PWRControlTE control = (PWRControlTE)entity1;
				if (!controlsByName.containsKey(control.name))
					controlsByName.put(control.name,new LeafiaSet<>());
				controlsByName.get(control.name).add(pos);
			}
		}
		rodPositions = new double[controlsByName.keySet().size()];
		rodPositionsD = new double[rodPositions.length];
		//this.remote = core.remoteContainer;
		this.entity = (PWRComponentEntity)core.companion;
		//final Map<ColumnPos,AdjacentType> typeMap = new LeafiaMap<>();
		Set<ColumnPos> set2d = new LeafiaSet<>();
		for (BlockPos pos : core.projection) {
			pos = core.terminal_toLocal(terminalState,terminalPos,pos);
			rangeX.min = Math.min(rangeX.min,pos.getX());
			rangeX.max = Math.max(rangeX.max,pos.getX());
			rangeZ.min = Math.min(rangeZ.min,pos.getZ());
			rangeZ.max = Math.max(rangeZ.max,pos.getZ());
			set2d.add(new ColumnPos(pos.getX(),pos.getZ()));
			//typeMap.put(new ColumnPos(pos),REACTOR);
			set2d.add(new ColumnPos(pos.getX(),pos.getZ()));
		}
		//Set<ColumnPos> setEdge = new LeafiaSet<>();
		for (ColumnPos pos : set2d) {
			for (EnumFacing face : EnumFacing.HORIZONTALS) {
				ColumnVector vector = new ColumnVector(face);
				ColumnPos forward = pos.add(vector,1);
				if (!set2d.contains(forward)) {
					boolean spaceRight = !set2d.contains(pos.add(vector.right(),1));
					boolean spaceLeft = !set2d.contains(pos.add(vector.left(),1));

					// 90 DEGREES CURVE CHECKS
					boolean spaceRightDn = !set2d.contains(pos.subtract(vector,1).add(vector.right(),1));
					boolean spaceLeftDn = !set2d.contains(pos.subtract(vector,1).add(vector.left(),1));

					boolean spaceRightUp = !set2d.contains(pos.add(vector,1).add(vector.right(),1));
					boolean spaceLeftUp = !set2d.contains(pos.add(vector,1).add(vector.left(),1));
					boolean spaceRight2Up = !set2d.contains(pos.add(vector,1).add(vector.right(),2));
					boolean spaceLeft2Up = !set2d.contains(pos.add(vector,1).add(vector.left(),2));
					if (!spaceLeftUp && spaceRightUp) {
						//if (setReactor.contains(pos.add(vector,2).add(vector.right(),1)) || setReactor.contains(pos.add(vector,2).add(vector.left(),1)))
						//	continue;
						edgeTileMap.put(forward,new EdgeTile(vector,
								tileA(vector,0,0),tileA(vector,1,1),
								tileA(vector,0,0),tileA(vector,0,0)
						));
						if (spaceRight && !spaceRightDn) {
							edgeTileMap.put(forward.add(vector.right(),1),new EdgeTile(vector,
									tileA(vector,2,2),tileB(vector,0,0),
									tileA(vector,1,1),tileA(vector,2,2)
							));
						}
					} else if (spaceLeftUp && !spaceRightUp) {
						edgeTileMap.put(forward,new EdgeTile(vector,
								tileA(vector,-1,1),tileA(vector,0,0),
								tileA(vector,0,0),tileA(vector,0,0)
						));
						if (spaceLeft && !spaceLeftDn) {
							edgeTileMap.put(forward.add(vector.left(),1),new EdgeTile(vector,
									tileB(vector,0,0),tileA(vector,-2,2),
									tileA(vector,-2,2),tileA(vector,-1,1)
							));
						}
					} else {
						edgeTileMap.put(forward,new EdgeTile(vector,
								!spaceLeft2Up ? tileA(vector,2,2) : tileB(vector,0,0),
								!spaceRight2Up ? tileA(vector,-2,2) : tileB(vector,0,0),
								!spaceLeft2Up ? tileB(vector,0,1) : tileA(vector,0,2),
								!spaceRight2Up ? tileB(vector,-1,1) : tileA(vector,0,2)
						));
						if (spaceRight) {
							edgeTileMap.put(forward.add(vector.right(),1),new EdgeTile(vector,
									tileB(vector,0,0),tileB(vector,0,0),
									tileA(vector,1,spaceRightDn ? 0 : 2),
									spaceRightDn ? tileB(vector,0,0) : tileA(vector,2,2)
							));
						}
						if (spaceLeft) {
							edgeTileMap.put(forward.add(vector.left(),1),new EdgeTile(vector,
									tileB(vector,0,0),tileB(vector,0,0),
									spaceLeftDn ? tileB(vector,0,0) : tileA(vector,-2,2),
									tileA(vector,spaceLeftDn ? 0 : -1,spaceLeftDn ? 1 : 2)
							));
						}
					}
					//set2d.add(forward);
				}
			}
		}

		pwrWidth = rangeX.max-rangeX.min+1;
		pwrHeight = rangeZ.max-rangeZ.min+1;

		rawWidth = pwrWidth*18+UI_EDGE*2;
		rawHeight = pwrHeight*18+UI_EDGE*2;
	}
	int inventorySpacing = 5;
	int containerOffset = 0;
	@Override
	public void initGui() {
		preferredHeight = height-inventoryHeight-inventorySpacing;
		this.calculatedScale = Math.min(Math.min(preferredWidth/(double)rawWidth,preferredHeight/(double)rawHeight),1);
		this.containerOffset = (int)Math.round(rawHeight*calculatedScale)+inventorySpacing-inventoryStart-UI_EDGE;
		this.xSize = 215;
		this.ySize = containerOffset +inventoryHeight;
		super.initGui();
		guiTop -= UI_EDGE/2;
		guiLeft = width/2-45-playerInventoryWidth/2;
		for (Slot slot : inventorySlots.inventorySlots) {
			if (slot instanceof RemoteSlot) {
				RemoteSlot remote = (RemoteSlot)slot;
				ColumnPos cpos = new ColumnPos(remote.localPos).subtract(rangeX.min,rangeZ.min);
				slot.xPos = getSlotX(cpos)+1;
				slot.yPos = getSlotY(cpos)+1;
			}
		}
	}
	@Override
	public boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
		int x = slot.xPos;
		int y = slot.yPos;
		if (slot instanceof RemoteSlot) {
			x = (int)(x*calculatedScale)-guiLeft;
			y = (int)(y*calculatedScale)-guiTop;
			return this.isPointInRegion(x,y, (int)(16*calculatedScale), (int)(16*calculatedScale), mouseX, mouseY);
		}
		return this.isPointInRegion(x,y+containerOffset, 16, 16, mouseX, mouseY);
	}
	@Override
	protected void drawSlotHighlight(Slot slot) {
		int slotX = slot.xPos;
		int slotY = slot.yPos;
		LeafiaGls._push();
		LeafiaGls.disableLighting();
		LeafiaGls.disableDepth();
		LeafiaGls.colorMask(true, true, true, false);
		if (slot instanceof RemoteSlot) {
			LeafiaGls.translate(-guiLeft,-guiTop,0);
			LeafiaGls.pushMatrix();
			LeafiaGls.scale(calculatedScale,calculatedScale,1);
			this.drawGradientRect(slotX, slotY, slotX + 16, slotY + 16, -2130706433, -2130706433);
			LeafiaGls.popMatrix();
			LeafiaGls.translate(guiLeft,guiTop,0);
		} else
			this.drawGradientRect(slotX, slotY + containerOffset, slotX + 16, slotY + 16 + containerOffset, -2130706433, -2130706433);
		LeafiaGls._pop();
	}
	@Override
	protected void drawSlots(int mouseX,int mouseY) {
		this.hoveredSlot = null;
		for (int index = 0; index < this.inventorySlots.inventorySlots.size(); ++index)
		{
			Slot slot = preDrawSlot(index);
			if (slot.isEnabled()) {
				if (slot instanceof RemoteSlot) {
					LeafiaGls.translate(-guiLeft,-guiTop,0);
					LeafiaGls.pushMatrix();
					LeafiaGls.scale(calculatedScale,calculatedScale,1);
					this.drawSlot(slot,slot.xPos,slot.yPos);
					LeafiaGls.popMatrix();
					LeafiaGls.translate(guiLeft,guiTop,0);
				} else {
					this.drawSlot(slot,slot.xPos,slot.yPos+containerOffset);
				}
				detectSlotHover(slot,mouseX,mouseY);
			}

			postDrawSlot(index,slot);
		}
	}
	String[] tip = null;
	@Override
	public void drawScreen(int mouseX,int mouseY,float partialTicks) {
		if (((TileEntity)entity).isInvalid() || entity.getCore() != core) {
			this.mc.player.closeScreen();
			return;
		}
		LeafiaGls._push();
		super.drawScreen(mouseX,mouseY,partialTicks);
		super.renderHoveredToolTip(mouseX,mouseY);
		if (tip != null) {
			super.drawHoveringText(Arrays.asList(tip),mouseX,mouseY);
		}
		FFUtils.renderTankInfo(this,mouseX,mouseY,guiLeft+5,guiTop+containerOffset+61,16,52,core.tanks[0]);
		FFUtils.renderTankInfo(this,mouseX,mouseY,guiLeft+23,guiTop+containerOffset+61,16,52,core.tanks[1]);
		FFUtils.renderTankInfo(this,mouseX,mouseY,guiLeft+23,guiTop+containerOffset+32,16,26,core.tanks[2]);
		FFUtils.renderTankInfo(this,mouseX,mouseY,guiLeft-22,guiTop+containerOffset+25,16,52,core.tanks[3]);
		FFUtils.renderTankInfo(this,mouseX,mouseY,guiLeft-5,guiTop+containerOffset+-3,10,16,core.tanks[4]);
		LeafiaGls._pop();

		TextureManager tex = Minecraft.getMinecraft().getTextureManager();
		tex.bindTexture(texture);
		LeafiaGls._push();
		LeafiaGls.resetEffects();
		LeafiaGls.blendFunc(SourceFactor.SRC_ALPHA,DestFactor.ONE);
		for (int i = -1; i < rodPositions.length; i++) {
			if (((i < 0) ? core.masterControl : rodPositions[i]) > 0)
				drawTexturedModalRect(guiLeft+244+19*i+9-16,guiTop+containerOffset+7-15,153,190,38,38);
		}
		LeafiaGls._pop();
	}
	int getContainerX() {
		return (int)((guiLeft+45+playerInventoryWidth/2)/calculatedScale)-rawWidth/2;
	}
	public int getSlotX(ColumnPos pos) {
		int containerX = getContainerX();
		return containerX+pos.x*18+UI_EDGE;
	}
	public int getSlotY(ColumnPos pos) {
		return (int)(guiTop/calculatedScale)+pos.y*18;//+UI_EDGE;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX,int mouseY) {
		this.fontRenderer.drawString(I18n.format("container.inventory"), 53, containerOffset+24, 4210752);
	}
	final double[] rodPositions;
	final double[] rodPositionsD;
	static final int playerInventoryWidth = 176;

	double avoidNull(Double in) {
		if (in == null)
			return 0;
		else
			return in;
	}
	Integer rodGrab = null;
	Integer rodHover = null;
	String hoveringRodName = null;
	double rodHoverPosition = 0;
	boolean compressionHover = false;

	void mouse(int mouseX,int mouseY,int button) {
		if (rodGrab != null) {
			NBTTagCompound data = new NBTTagCompound();
			if (hoveringRodName != null)
				data.setString("name",hoveringRodName);
			data.setDouble("level",rodHoverPosition);
			LeafiaPacket._start(core.companion).__write(30, data).__sendToServer();
		}
	}
	@Override
	protected void mouseClicked(int mouseX,int mouseY,int mouseButton) throws IOException {
		super.mouseClicked(mouseX,mouseY,mouseButton);
		if (rodHover != null) {
			rodGrab = rodHover;
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		} else if (compressionHover) {
			LeafiaPacket._start(core.companion).__write(29,core.compression+((mouseButton == 0) ? 1 : -1)).__sendToServer();
			mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		}
		mouse(mouseX,mouseY,mouseButton);
	}
	@Override
	protected void mouseClickMove(int mouseX,int mouseY,int clickedMouseButton,long timeSinceLastClick) {
		super.mouseClickMove(mouseX,mouseY,clickedMouseButton,timeSinceLastClick);
		mouse(mouseX,mouseY,clickedMouseButton);
	}
	@Override
	protected void mouseReleased(int mouseX,int mouseY,int state) {
		super.mouseReleased(mouseX,mouseY,state);
		rodGrab = null;
	}

	boolean hoverNoOffset(int rectX,int rectY,int rectWidth,int rectHeight,int pointX,int pointY) {
		return pointX >= rectX && pointX <= rectX+rectWidth && pointY >= rectY && pointY <= rectY+rectHeight;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mouseX,int mouseY) {
		tip = null;
		rodHover = null;
		compressionHover = false;
		LeafiaGls._push();
		super.drawDefaultBackground();
		LeafiaGls.pushMatrix();
		TextureManager tex = Minecraft.getMinecraft().getTextureManager();
		tex.bindTexture(texture);
		RangeInt rodRange;
		drawTexturedModalRect(guiLeft,guiTop+containerOffset,0,0,244,118);
		{
			int x = guiLeft+244-19;
			int y = guiTop+containerOffset+7;
			rodRange = new RangeInt(y+18+88,y+18);

			int pix = (int) Math.floor((1 - core.masterControl) * 88);
			drawTexturedModalRect(x + 8,y + 18,42,235 - pix,8,pix);

			boolean isHovering = hoverNoOffset(x + 6,y + 17,12,92,mouseX,mouseY);
			if ((rodGrab == null) ? isHovering : (rodGrab == -1)) {
				rodHover = -1;
				rodHoverPosition = MathHelper.clamp(rodRange.ratio(mouseY),0,1);
				hoveringRodName = null;
				tip = I18nUtil.leafia.statusDecimals("desc.leafia._repeated.reactors.controlrods",core.masterControl * 100,0);
			}
			LeafiaGls.pushMatrix();
			LeafiaGls.translate(x,y+106,0);
			LeafiaGls.pushMatrix();
			LeafiaGls.rotate(-90,0,0,1);
			LeafiaGls.scale(7/13d,7/13d,1);
			this.fontRenderer.drawString(TextFormatting.BOLD+"Master Control",0,3,0xFFFFFF);
			tex.bindTexture(texture);
			LeafiaGls.popMatrix();
			LeafiaGls.popMatrix();
			if (core.masterControl > 0)
				drawTexturedModalRect(x+9,y,192,190,6,8);
		}
		List<String> names = new ArrayList<>(controlsByName.keySet());
		names.sort(Comparator.naturalOrder());
		Iterator<String> iter = names.iterator();
		for (int i = 0; iter.hasNext(); i++) {
			String s = iter.next();
			int x = guiLeft+244+19*i;
			int y = guiTop+containerOffset+7;
			drawTexturedModalRect(x,y,iter.hasNext() ? 0 : 19,120,iter.hasNext() ? 19 : 22,111);
			{
				LeafiaGls.pushMatrix();
				LeafiaGls.translate(x,y+106,0);
				LeafiaGls.pushMatrix();
				LeafiaGls.rotate(-90,0,0,1);
				LeafiaGls.scale(7/13d,7/13d,1);
				this.fontRenderer.drawString(TextFormatting.BOLD+s,0,3,4210752);
				tex.bindTexture(texture);
				LeafiaGls.popMatrix();
				LeafiaGls.popMatrix();
			}
			boolean isAllEqual = true;
			int number = 0;
			double position = 0;
			Double lastPosition = null;
			for (BlockPos pos : controlsByName.get(s)) {
				TileEntity entity = core.getWorld().getTileEntity(pos);
				if (entity instanceof PWRControlTE) {
					PWRControlTE control = (PWRControlTE)entity;
					number++;
					position += control.position;
					if (lastPosition != null) {
						if (position != lastPosition)
							isAllEqual = false;
					} else lastPosition = control.position;
				}
			}
			rodPositions[i] = position/number;
			rodPositionsD[i] = avoidNull(core.controlDemand.get(s));

			LeafiaGls._push(); {
				boolean isHovering = hoverNoOffset(x + 6,y + 17,12,92,mouseX,mouseY);
				if ((rodGrab == null) ? isHovering : (rodGrab == i)) {
					rodHover = i;
					rodHoverPosition = MathHelper.clamp(rodRange.ratio(mouseY),0,1);
					hoveringRodName = s;
					tip = I18nUtil.leafia.statusDecimals("desc.leafia._repeated.reactors.controlrods",rodPositions[i] * 100,0);
				}
				float col = isHovering ? 0.85F : 1F;

				int pix = (int) Math.floor((1 - rodPositions[i]) * 88);
				LeafiaGls.color(col,col,col,isAllEqual ? 1 : 0.4f);
				drawTexturedModalRect(x + 8,y + 18,42,235 - pix,8,pix);

				pix = (int) Math.floor((1 - rodPositionsD[i]) * 88);
				LeafiaGls.color(col,col,col,0.25f);
				drawTexturedModalRect(x + 8,y + 18,42,235 - pix,8,pix);

				LeafiaGls.color(1,1,1,1);
			}
			LeafiaGls._pop();
			if (position > 0)
				drawTexturedModalRect(x+9,y,192,190,6,8);
		}

		LeafiaGls.scale(calculatedScale,calculatedScale,1);
		for (Entry<ColumnPos,EdgeTile> entry : edgeTileMap.entrySet()) {
			ColumnPos pos = entry.getKey().subtract(rangeX.min,rangeZ.min);
			entry.getValue().draw(this,getSlotX(pos),getSlotY(pos));
		}
		for (BlockPos worldPos : core.projection) {
			BlockPos pos = core.terminal_toLocal(terminalState,terminalPos,worldPos);
			if (!rangeX.isInRange(pos.getX()) || !rangeZ.isInRange(pos.getZ())) {
				this.mc.player.closeScreen();
				break;
			}
			ColumnPos cpos = new ColumnPos(pos).subtract(rangeX.min,rangeZ.min);
			if (fuels.contains(worldPos)) {
				// Slot
				drawTexturedModalRect(getSlotX(cpos),getSlotY(cpos),191,120,18,18);
			} else if (controls.contains(worldPos)) {
				// Control
				drawTexturedModalRect(getSlotX(cpos),getSlotY(cpos),172,120,18,18);
				TileEntity entity = core.getWorld().getTileEntity(worldPos);
				if (entity instanceof PWRControlTE) {
					PWRControlTE control = (PWRControlTE)entity;
					int level = (int)Math.min(Math.floor(control.position*5),4);
					drawTexturedModalRect(getSlotX(cpos)+5,getSlotY(cpos)+5,153+level*8,181,8,8);
				}
			} else {
				// None
				drawTexturedModalRect(getSlotX(cpos),getSlotY(cpos),172,120,18,18);
			}
		}
		LeafiaGls.popMatrix();
		FFUtils.drawLiquid(core.tanks[0],guiLeft,guiTop+containerOffset+80,zLevel,16,52,5,61);
		FFUtils.drawLiquid(core.tanks[1],guiLeft,guiTop+containerOffset+80,zLevel,16,52,23,61);
		FFUtils.drawLiquid(core.tanks[2],guiLeft,guiTop+containerOffset+54,zLevel,16,26,23,32);

		LeafiaGls.color(1,1,1,1); // for some reason without this the entire thing turns red lol
		tex.bindTexture(texture);
		if (core.tanks[2].getFluidAmount() > 0) {
			if (core.warnTicks >= 3)
				drawTexturedModalRect(guiLeft+18,guiTop+containerOffset,42,120,26,26);
		}

		if (core.tanks[3].getCapacity() > 0) {
			drawTexturedModalRect(guiLeft - 27,guiTop + containerOffset - 9,69,120,38,127);
			drawTexturedModalRect(guiLeft - 22,guiTop + containerOffset - 4,14*core.compression,238,14,18);
			if (isPointInRegion(-22,containerOffset-4,14,18,mouseX,mouseY)) {
				tip = I18nUtil.leafia.statusDecimals("desc.leafia._repeated.reactors.compression",Math.pow(10,core.compression),0);
				compressionHover = true;
			}
			FFUtils.drawLiquid(core.tanks[3],guiLeft,guiTop + containerOffset + 80,zLevel,16,52,-22,25);
			FFUtils.drawLiquid(core.tanks[4],guiLeft,guiTop + containerOffset + 80,zLevel,10,16,-5,-39);
		}
		LeafiaGls._pop();
	}
}
