package com.leafia.contents.machines.elevators.car.styles;

import com.hbm.render.item.TEISRBase;
import com.leafia.contents.machines.elevators.car.ElevatorRender;
import com.leafia.contents.machines.elevators.car.ElevatorRender.S6;
import com.leafia.contents.machines.elevators.car.ElevatorRender.Skylift;
import com.leafia.dev.items.LeafiaGripOffsetHelper;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EvStyleItemRender extends TEISRBase {
	static LeafiaGripOffsetHelper offsetWall = new LeafiaGripOffsetHelper()
			.get(TransformType.FIRST_PERSON_RIGHT_HAND)
			.setScale(0.25).setPosition(-4.25,4.75,0.75).setRotation(-105,-65,65).getHelper()

			.get(TransformType.FIRST_PERSON_LEFT_HAND)
			.setPosition(4.25,0,0.75).setRotation(0,-50,0).getHelper()

			.get(TransformType.THIRD_PERSON_RIGHT_HAND)
			.setScale(0.15).setPosition(-2.25,1.5,-3.25).setRotation(0,90,0).getHelper()

			.get(TransformType.FIXED)
			.setScale(0.4).setPosition(-1.25,2.25,-0.1).setRotation(-90,0,0).getHelper()

			.get(TransformType.GUI)
			.setScale(0.35).setPosition(-1.85,0,0).setRotation(-60,-40,35).getHelper()

			.get(TransformType.GROUND)
			.setScale(0.19).setPosition(-2.6,2.25,-1.5).getHelper();
	static LeafiaGripOffsetHelper offsetFloor = new LeafiaGripOffsetHelper()
			.get(TransformType.FIRST_PERSON_RIGHT_HAND)
			.setScale(0.25).setPosition(-4,4.25,-0.75).setRotation(-15,0,-15).getHelper()

			.get(TransformType.FIRST_PERSON_LEFT_HAND)
			.setPosition(3.75,1.25,0).setRotation(0,0,50).getHelper()

			.get(TransformType.THIRD_PERSON_RIGHT_HAND)
			.setScale(0.15).setPosition(-3.25,2.5,-3.35).setRotation(0,0,90).getHelper()

			.get(TransformType.FIXED)
			.setScale(0.4).setPosition(-1.25,1.25,-1.25).getHelper()

			.get(TransformType.GUI)
			.copySettings(TransformType.FIXED).getHelper()

			.get(TransformType.GROUND)
			.setScale(0.2).setPosition(-2.5,3,-2.5).setRotation(-90,0,0).getHelper();
	static LeafiaGripOffsetHelper offsetCeiling = new LeafiaGripOffsetHelper()
			.get(TransformType.FIRST_PERSON_RIGHT_HAND)
			.setScale(0.25).setPosition(-3.5,6.25,-1).setRotation(-190,0,15).getHelper()

			.get(TransformType.FIRST_PERSON_LEFT_HAND)
			.setPosition(2.75,0,0).setRotation(0,0,-55).getHelper()

			.get(TransformType.THIRD_PERSON_RIGHT_HAND)
			.setScale(0.15).setPosition(-5.5,2.5,-3.35).setRotation(0,0,-90).getHelper()

			.get(TransformType.FIXED)
			.setScale(0.4).setPosition(-1.25,3.5,-1.25).setRotation(0,0,-180).getHelper()

			.get(TransformType.GUI)
			.copySettings(TransformType.FIXED).getHelper()

			.get(TransformType.GROUND)
			.setScale(0.2).setPosition(-2,3,-4.25).setRotation(-270,0,0).getHelper();

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		LeafiaGls.pushMatrix();
		Item item0 = itemStackIn.getItem();
		if (item0 instanceof EvStyleItem) {
			EvStyleItem item = (EvStyleItem)item0;
			String style = item.getStyleId();
			if (style.contains("floor"))
				offsetFloor.apply(type);
			else if (style.contains("ceiling"))
				offsetCeiling.apply(type);
			else
				offsetWall.apply(type);
			switch(style) {
				case "s6wall":
					bindTexture(ElevatorRender.support);
					S6.mdl.renderPart("WallFrames");
					bindTexture(S6.wall);
					S6.mdl.renderPart("WallIn");
					S6.mdl.renderPart("WallOut");
					break;
				case "s6window":
					bindTexture(ElevatorRender.support);
					S6.mdl.renderPart("WallFrames");
					bindTexture(S6.window);
					S6.mdl.renderPart("WallIn");
					S6.mdl.renderPart("WallOut");
					break;
				case "s6floor":
					bindTexture(ElevatorRender.support);
					S6.mdl.renderPart("FloorSide");
					bindTexture(S6.floor);
					S6.mdl.renderPart("Floor");
					break;
				case "s6ceiling":
					bindTexture(ElevatorRender.support);
					S6.mdl.renderPart("CeilingSide");
					bindTexture(S6.ceiling);
					S6.mdl.renderPart("Ceiling");
					break;
				case "s6door":
					bindTexture(S6.door);
					S6.mdl.renderPart("DoorFrame");
					S6.mdl.renderPart("DoorL");
					S6.mdl.renderPart("DoorR");
					bindTexture(S6.arrowOff);
					S6.mdl.renderPart("ArrowUp");
					S6.mdl.renderPart("ArrowDn");
					bindTexture(S6.floor);
					S6.mdl.renderPart("DoorFloor");
					bindTexture(S6.logo);
					S6.mdl.renderPart("Logo");
					LeafiaGls.disableLighting();
					bindTexture(S6.ind.get(""));
					S6.mdl.renderPart("Digit10");
					bindTexture(S6.ind.get("1"));
					S6.mdl.renderPart("Digit0");
					LeafiaGls.enableLighting();
					break;
				case "skyliftdoor":
					bindTexture(Skylift.frame);
					Skylift.mdl.renderPart("DoorFrame");
					bindTexture(Skylift.door);
					Skylift.mdl.renderPart("DoorL");
					Skylift.mdl.renderPart("DoorR");
					bindTexture(Skylift.arrowOff);
					Skylift.mdl.renderPart("ArrowUp");
					Skylift.mdl.renderPart("ArrowDn");
					bindTexture(S6.floor);
					Skylift.mdl.renderPart("DoorFloor");
					bindTexture(Skylift.logo);
					Skylift.mdl.renderPart("Logo");
					bindTexture(Skylift.leafia);
					Skylift.mdl.renderPart("Name");
					bindTexture(Skylift.skylift);
					Skylift.mdl.renderPart("Skylift");
					LeafiaGls.disableLighting();
					bindTexture(Skylift.ind.get(""));
					Skylift.mdl.renderPart("Digit10");
					bindTexture(Skylift.ind.get("1"));
					Skylift.mdl.renderPart("Digit0");
					LeafiaGls.enableLighting();
					break;
			}
		}
		LeafiaGls.popMatrix();
	}
	void bindTexture(ResourceLocation loc) {
		Minecraft.getMinecraft().renderEngine.bindTexture(loc);
	}
}
