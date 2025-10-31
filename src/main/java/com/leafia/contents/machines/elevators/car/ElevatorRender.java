package com.leafia.contents.machines.elevators.car;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.CompositeBrush;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.leafia.contents.machines.elevators.car.ElevatorEntity.*;
import com.leafia.contents.machines.elevators.car.styles.panels.ElevatorPanelBase;
import com.leafia.transformer.LeafiaGls;
import com.llib.group.LeafiaMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import java.util.ConcurrentModificationException;

public class ElevatorRender extends Render<ElevatorEntity> {
	public static final IRenderFactory<ElevatorEntity> FACTORY = manager->new ElevatorRender(manager);
	public static final ResourceLocation buttonLabelOpen = resource("buttons/open");
	public static final ResourceLocation buttonLabelClose = resource("buttons/close");
	public static final ResourceLocation buttonLabelBell = resource("buttons/bell");

	protected ElevatorRender(RenderManager renderManager) {
		super(renderManager);
	}
	public static ResourceLocation resource(String shortPath) { return new ResourceLocation(RefStrings.MODID+":textures/models/leafia/elevator/"+shortPath+".png"); }
	public static IModelCustom model(String shortPath) { return AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID+":models/leafia/elevators/"+shortPath+".obj")); }
	public static LeafiaMap<String,ResourceLocation> indicator(String shortPath) {
		LeafiaMap<String,ResourceLocation> map = new LeafiaMap<>();
		map.put("",resource(shortPath+"off"));
		map.put(" ",resource(shortPath+"off"));
		for (String allowedDigit : ElevatorEntity.ALLOWED_DIGITS) {
			map.put(allowedDigit,resource(shortPath+allowedDigit));
		}
		return map;
	}
	public static ResourceLocation support = resource("support");
	public static class S6 {
		public static final IModelCustom mdl = model("otis_s6");
		public static final ResourceLocation floor = resource("s6/floor");
		public static final ResourceLocation ceiling = resource("s6/ceiling");
		public static final ResourceLocation wall = resource("s6/wall");
		public static final ResourceLocation window = resource("s6/window");
		public static final ResourceLocation door = resource("s6/door");
		public static final ResourceLocation logo = new ResourceLocation(RefStrings.MODID+":textures/models/leafia/exaprism_txt_lowres.png");
		public static final ResourceLocation arrowOff = resource("s6/arrow_off");
		public static final ResourceLocation arrowOn = resource("s6/arrow_on");
		public static final ResourceLocation buttonOff = resource("s6/buttons/button_off");
		public static final ResourceLocation buttonOn = resource("s6/buttons/button_on");
		public static final ResourceLocation buttonLabel = resource("s6/buttons/button_label");
		public static final ResourceLocation buttonFireOff = resource("s6/buttons/28275428");
		public static final ResourceLocation buttonFireOn = resource("s6/buttons/28275551");
		public static final LeafiaMap<String,ResourceLocation> ind = indicator("s6/indicator/");
	}
	public static class Skylift {
		public static final IModelCustom mdl = model("skylift");
		public static final ResourceLocation frame = resource("skylift/door");
		public static final ResourceLocation door = resource("skylift/door_color");
		public static final ResourceLocation logo = resource("skylift/logo");
		public static final ResourceLocation leafia = resource("skylift/leafia");
		public static final ResourceLocation skylift = resource("skylift/skylift");
		public static final ResourceLocation arrowOff = resource("skylift/arrow_off");
		public static final ResourceLocation arrowOn = resource("skylift/arrow_on");
		public static final ResourceLocation buttonOff = resource("skylift/buttons/button_off");
		public static final ResourceLocation buttonOn = resource("skylift/buttons/button_on");
		public static final ResourceLocation buttonLabel = resource("skylift/buttons/button_label");
		public static final LeafiaMap<String,ResourceLocation> ind = indicator("skylift/indicator/");
	}

	@Override
	public void doRender(ElevatorEntity entity,double x,double y,double z,float entityYaw,float partialTicks) {
		LeafiaGls.pushMatrix();
		LeafiaGls.translate(x,y,z);
		LeafiaGls.rotate(entityYaw,0,-1,0);

		CompositeBrush brush = CompositeBrush.instance;
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		bindTexture(support);
		S6.mdl.renderPart("Frames");
		LeafiaGls.color(1,1,1);

		ResourceLocation floorTexture = S6.floor;
		for (int i = 0; i < 6; i++) {
			DataParameter<String> param = ElevatorEntity.styleParams[i];
			String style = entity.getDataString(param);
			LeafiaGls.pushMatrix();
			int r = i-2;
			if (i >= 2) {
				LeafiaGls.rotate(90*r,0,1,0);
			}
			switch(style) {
				case "s6floor": {
					floorTexture = S6.floor;
					bindTexture(floorTexture);
					S6.mdl.renderPart("Floor");
					bindTexture(support);
					S6.mdl.renderPart("FloorSide");
					break;
				}
				case "s6ceiling": {
					bindTexture(S6.ceiling);
					S6.mdl.renderPart("Ceiling");
					bindTexture(support);
					S6.mdl.renderPart("CeilingSide");
					break;
				}
				case "s6wall": {
					bindTexture(support);
					S6.mdl.renderPart("WallFrames");
					S6.mdl.renderPart("WallOut");
					bindTexture(S6.wall);
					S6.mdl.renderPart("WallIn");
					break;
				}
				case "s6window": {
					bindTexture(support);
					S6.mdl.renderPart("WallFrames");
					bindTexture(S6.window);
					S6.mdl.renderPart("WallOut");
					S6.mdl.renderPart("WallIn");
					break;
				}
				case "s6door": {
					bindTexture(S6.door);
					S6.mdl.renderPart("DoorFrame");
					float door = entity.getDataFloat(ElevatorEntity.DOOR_IN);
					if (!entity.hasExteriorDoor(r)) door = 0;
					LeafiaGls.pushMatrix();
					LeafiaGls.translate(-0.440625f*door,0,0);
					S6.mdl.renderPart("DoorL");
					LeafiaGls.popMatrix();
					LeafiaGls.pushMatrix();
					LeafiaGls.translate(0.440625f*door,0,0);
					S6.mdl.renderPart("DoorR");
					LeafiaGls.popMatrix();

					bindTexture(floorTexture);
					S6.mdl.renderPart("DoorFloor");
					bindTexture(S6.logo);
					S6.mdl.renderPart("Logo");

					int dir = entity.getDataInteger(ElevatorEntity.ARROW);
					bindTexture(dir == 1 ? S6.arrowOn : S6.arrowOff);
					if (dir == 1) LeafiaGls.disableLighting();
					S6.mdl.renderPart("ArrowUp");
					LeafiaGls.enableLighting();
					bindTexture(dir == -1 ? S6.arrowOn : S6.arrowOff);
					if (dir == -1) LeafiaGls.disableLighting();
					S6.mdl.renderPart("ArrowDn");
					LeafiaGls.enableLighting();

					LeafiaGls.disableLighting();
					String display = entity.getDataString(ElevatorEntity.FLOOR_DISPLAY);
					if (display.length() < 2) display = " "+display;
					if (display.length() < 2) display = " "+display;
					bindTexture(S6.ind.getOrDefault(display.substring(1,2),S6.ind.get(" ")));
					S6.mdl.renderPart("Digit0");
					bindTexture(S6.ind.getOrDefault(display.substring(0,1),S6.ind.get(" ")));
					S6.mdl.renderPart("Digit10");
					LeafiaGls.enableLighting();

					ElevatorPanelBase panel = entity.getPanel(r);
					if (panel != null) {
						int staticX = panel.getStaticX();
						double staticZ = panel.getStaticZ();
						try {
							for (ElevatorButton button : entity.buttons) {
								if (button instanceof FireButton) {
									bindTexture(S6.buttonFireOff);
									brush.startDrawingQuads();
									brush.addVertexWithUV(staticX/16d+button.x/16d-1/16d,button.y/16d-0.2/16d,-staticZ,0,1);
									brush.addVertexWithUV(staticX/16d+button.x/16d+1/16d,button.y/16d-0.2/16d,-staticZ,1,1);
									brush.addVertexWithUV(staticX/16d+button.x/16d+1/16d,button.y/16d+1.2/16d,-staticZ,1,0);
									brush.addVertexWithUV(staticX/16d+button.x/16d-1/16d,button.y/16d+1.2/16d,-staticZ,0,0);
									brush.draw();
									continue;
								}
								if (entity.enabledButtons.contains(button.id) || entity.clickedButtons.containsKey(button.id))
									bindTexture(S6.buttonOn);
								else
									bindTexture(S6.buttonOff);
								brush.startDrawingQuads();
								brush.addVertexWithUV(staticX/16d+button.x/16d+0.15/16d,button.y/16d+0.15/16d,-staticZ,0,1);
								brush.addVertexWithUV(staticX/16d+button.x/16d+0.85/16d,button.y/16d+0.15/16d,-staticZ,1,1);
								brush.addVertexWithUV(staticX/16d+button.x/16d+0.85/16d,button.y/16d+0.85/16d,-staticZ,1,0);
								brush.addVertexWithUV(staticX/16d+button.x/16d+0.15/16d,button.y/16d+0.85/16d,-staticZ,0,0);
								brush.draw();

								bindTexture(S6.buttonLabel);
								brush.startDrawingQuads();
								brush.addVertexWithUV(staticX/16d+button.x/16d-1/16d,button.y/16d+0/16d,-staticZ,0,1);
								brush.addVertexWithUV(staticX/16d+button.x/16d-0/16d,button.y/16d+0/16d,-staticZ,1,1);
								brush.addVertexWithUV(staticX/16d+button.x/16d-0/16d,button.y/16d+1/16d,-staticZ,1,0);
								brush.addVertexWithUV(staticX/16d+button.x/16d-1/16d,button.y/16d+1/16d,-staticZ,0,0);
								brush.draw();
								String text = null;
								if (button instanceof FloorButton) text = ((FloorButton)button).label;
								if (text != null) {
									LeafiaGls.pushMatrix();
									LeafiaGls.translate(staticX/16d+button.x/16d-1/16d,button.y/16d+1/16d,-staticZ+0.001);
									LeafiaGls.scale(1/16d/(9+4));
									LeafiaGls.translate((9+4)/2d-font.getStringWidth(text)/2d,0,0);
									LeafiaGls.rotate(180,1,0,0);
									font.drawString(text,0,3,0xFFFFFF);
									LeafiaGls.popMatrix();
								} else {
									ResourceLocation res = null;
									if (button instanceof OpenButton) res = buttonLabelOpen;
									if (button instanceof CloseButton) res = buttonLabelClose;
									if (button instanceof BellButton) res = buttonLabelBell;
									if (res != null) {
										LeafiaGls.pushMatrix();
										LeafiaGls.translate(staticX/16d+button.x/16d-1/16d,button.y/16d+1/16d,-staticZ+0.001);
										LeafiaGls.scale(1/16d/(9+4));
										LeafiaGls.translate(1,-1,0);
										bindTexture(res);
										brush.startDrawingQuads();
										brush.addVertexWithUV(0,-11,0,0,1);
										brush.addVertexWithUV(11,-11,0,1,1);
										brush.addVertexWithUV(11,0,0,1,0);
										brush.addVertexWithUV(0,0,0,0,0);
										brush.draw();
										LeafiaGls.popMatrix();
									}
								}
							}
						} catch (ConcurrentModificationException ignored) {} // fuck you
					}
					break;
				}
				case "skyliftdoor": {
					bindTexture(Skylift.frame);
					Skylift.mdl.renderPart("DoorFrame");
					bindTexture(Skylift.door);
					float door = entity.getDataFloat(ElevatorEntity.DOOR_IN);
					if (!entity.hasExteriorDoor(r)) door = 0;
					LeafiaGls.pushMatrix();
					LeafiaGls.translate(-0.440625f*door,0,0);
					Skylift.mdl.renderPart("DoorL");
					LeafiaGls.popMatrix();
					LeafiaGls.pushMatrix();
					LeafiaGls.translate(0.440625f*door,0,0);
					Skylift.mdl.renderPart("DoorR");
					LeafiaGls.popMatrix();

					bindTexture(floorTexture);
					Skylift.mdl.renderPart("DoorFloor");
					bindTexture(Skylift.logo);
					Skylift.mdl.renderPart("Logo");
					bindTexture(Skylift.leafia);
					Skylift.mdl.renderPart("Name");
					bindTexture(Skylift.skylift);
					Skylift.mdl.renderPart("Skylift");

					int dir = entity.getDataInteger(ElevatorEntity.ARROW);
					bindTexture(dir == 1 ? Skylift.arrowOn : Skylift.arrowOff);
					if (dir == 1) LeafiaGls.disableLighting();
					Skylift.mdl.renderPart("ArrowUp");
					LeafiaGls.enableLighting();
					bindTexture(dir == -1 ? Skylift.arrowOn : Skylift.arrowOff);
					if (dir == -1) LeafiaGls.disableLighting();
					Skylift.mdl.renderPart("ArrowDn");
					LeafiaGls.enableLighting();

					LeafiaGls.disableLighting();
					String display = entity.getDataString(ElevatorEntity.FLOOR_DISPLAY);
					if (display.length() < 2) display = " "+display;
					if (display.length() < 2) display = " "+display;
					bindTexture(Skylift.ind.getOrDefault(display.substring(1,2),S6.ind.get(" ")));
					Skylift.mdl.renderPart("Digit0");
					bindTexture(Skylift.ind.getOrDefault(display.substring(0,1),S6.ind.get(" ")));
					Skylift.mdl.renderPart("Digit10");
					LeafiaGls.enableLighting();

					ElevatorPanelBase panel = entity.getPanel(r);
					if (panel != null) {
						int staticX = panel.getStaticX();
						double staticZ = panel.getStaticZ();
						try {
							for (ElevatorButton button : entity.buttons) {
								if (button instanceof FireButton) {
									bindTexture(S6.buttonFireOff);
									brush.startDrawingQuads();
									brush.addVertexWithUV(staticX/16d+button.x/16d-1/16d,button.y/16d-0.2/16d,-staticZ,0,1);
									brush.addVertexWithUV(staticX/16d+button.x/16d+1/16d,button.y/16d-0.2/16d,-staticZ,1,1);
									brush.addVertexWithUV(staticX/16d+button.x/16d+1/16d,button.y/16d+1.2/16d,-staticZ,1,0);
									brush.addVertexWithUV(staticX/16d+button.x/16d-1/16d,button.y/16d+1.2/16d,-staticZ,0,0);
									brush.draw();
									continue;
								}
								if (entity.enabledButtons.contains(button.id) || entity.clickedButtons.containsKey(button.id))
									bindTexture(Skylift.buttonOn);
								else
									bindTexture(Skylift.buttonOff);
								brush.startDrawingQuads();
								brush.addVertexWithUV(staticX/16d+button.x/16d-0.25/16d,button.y/16d-0.25/16d,-staticZ,0,1);
								brush.addVertexWithUV(staticX/16d+button.x/16d+1.25/16d,button.y/16d-0.25/16d,-staticZ,1,1);
								brush.addVertexWithUV(staticX/16d+button.x/16d+1.25/16d,button.y/16d+1.25/16d,-staticZ,1,0);
								brush.addVertexWithUV(staticX/16d+button.x/16d-0.25/16d,button.y/16d+1.25/16d,-staticZ,0,0);
								brush.draw();

								bindTexture(Skylift.buttonLabel);
								brush.startDrawingQuads();
								brush.addVertexWithUV(staticX/16d+button.x/16d-1/16d,button.y/16d+0/16d,-staticZ,0,1);
								brush.addVertexWithUV(staticX/16d+button.x/16d-0.25/16d,button.y/16d+0/16d,-staticZ,1,1);
								brush.addVertexWithUV(staticX/16d+button.x/16d-0.25/16d,button.y/16d+1.25/16d,-staticZ,1,0);
								brush.addVertexWithUV(staticX/16d+button.x/16d-1/16d,button.y/16d+1.25/16d,-staticZ,0,0);
								brush.draw();
								String text = null;
								if (button instanceof FloorButton) text = ((FloorButton)button).label;
								if (text != null) {
									LeafiaGls.pushMatrix();
									LeafiaGls.translate(staticX/16d+button.x/16d-1/16d,button.y/16d+1/16d,-staticZ+0.001);
									LeafiaGls.scale(1/16d/(9+4));
									LeafiaGls.translate((9+4)/2d-font.getStringWidth(text)/2d,0,0);
									LeafiaGls.rotate(180,1,0,0);
									font.drawString(text,0,3,0xFFFFFF);
									LeafiaGls.popMatrix();
								} else {
									ResourceLocation res = null;
									if (button instanceof OpenButton) res = buttonLabelOpen;
									if (button instanceof CloseButton) res = buttonLabelClose;
									if (button instanceof BellButton) res = buttonLabelBell;
									if (res != null) {
										LeafiaGls.pushMatrix();
										LeafiaGls.translate(staticX/16d+button.x/16d-1/16d,button.y/16d+1/16d,-staticZ+0.001);
										LeafiaGls.scale(1/16d/(9+4));
										LeafiaGls.translate(1,-1,0);
										bindTexture(res);
										brush.startDrawingQuads();
										brush.addVertexWithUV(0,-11,0,0,1);
										brush.addVertexWithUV(11,-11,0,1,1);
										brush.addVertexWithUV(11,0,0,1,0);
										brush.addVertexWithUV(0,0,0,0,0);
										brush.draw();
										LeafiaGls.popMatrix();
									}
								}
							}
						} catch (ConcurrentModificationException ignored) {} // fuck you
					}
					break;
				}
			}
			LeafiaGls.popMatrix();
		}

		LeafiaGls.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(ElevatorEntity entity) {
		return null;
	}
}
