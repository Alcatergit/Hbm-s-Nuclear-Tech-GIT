package com.leafia.dev.blockitems;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.item.ItemRenderBase;
import com.leafia.dev.items.LeafiaGripOffsetHelper;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

import static com.hbm.render.RenderHelper.bindTexture;

public interface LeafiaQuickModel {
	boolean debug = false;
	// automate this shit already
	@SideOnly(Side.CLIENT)
	Map<String,ResourceLocation> texMap = new HashMap<>();
	@SideOnly(Side.CLIENT)
	Map<String,IModelCustom> mdlMap = new HashMap<>();


	/**
	 * @return Should return the path for the TE to be registered.
	 */
	String _resourcePath();

	/**
	 * @return Should return the path for assets, both textures and model assuming they're in the same location.
	 */
	@SideOnly(Side.CLIENT)
	String _assetPath();

	/**
	 * @return Just return a new instance of corresponding renderer.
	 */
	@SideOnly(Side.CLIENT)
	TileEntitySpecialRenderer<TileEntity> _renderer();

	/**
	 * @return Should return the block under ModBlocks.
	 */
	Block _block();

	@SideOnly(Side.CLIENT)
	default ResourceLocation __getTexture() {
		if (!LeafiaQuickModel.texMap.containsKey(this._resourcePath()))
			throw new RuntimeException("Leafia: Texture "+this._resourcePath()+" not registered in QuickModel");
		return LeafiaQuickModel.texMap.get(this._resourcePath());
	}
	@SideOnly(Side.CLIENT)
	default IModelCustom __getModel() {
		if (!LeafiaQuickModel.mdlMap.containsKey(this._resourcePath()))
			throw new RuntimeException("Leafia: Model "+this._resourcePath()+" not registered in QuickModel");
		return LeafiaQuickModel.mdlMap.get(this._resourcePath());
	}
	@SideOnly(Side.CLIENT)
	static void _loadResources(LeafiaQuickModel subject, String asset) {
		String path = subject._resourcePath();
		if (!texMap.containsKey(path)) {
			texMap.put(path,new ResourceLocation(RefStrings.MODID, "textures/models/"+asset+".png"));
		}
		if (!mdlMap.containsKey(path)) {
			mdlMap.put(path, AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/"+asset+".obj")));
		}
	}
	@SideOnly(Side.CLIENT)
	double _sizeReference();
	@SideOnly(Side.CLIENT)
	double _itemYoffset();
	LeafiaGripOffsetHelper genericGrip = new LeafiaGripOffsetHelper()
			.get(TransformType.GUI)
			.setPosition(-2.05,0,-1.25).setRotation(-39,65,-54).getHelper()

			.get(TransformType.FIRST_PERSON_RIGHT_HAND)
			.setPosition(-4.25,4.5,0).setRotation(-115,0,0).getHelper()

			.get(TransformType.FIRST_PERSON_LEFT_HAND)
			.setPosition(5,0,0).getHelper()

			.get(TransformType.THIRD_PERSON_RIGHT_HAND)
			.setPosition(-1.25,0.85,-2).getHelper()

			.get(TransformType.FIXED)
			.setScale(0.25).setPosition(-2,2,-1.25).setRotation(-90,0,0).getHelper();
	@SideOnly(Side.CLIENT)
	default ItemRenderBase _itemRenderer() {
		return new ItemRenderBase() {
			double scale = _sizeReference();
			double offset = _itemYoffset();
			boolean buttonPressed = false;
			@Override
			public void renderByItem(ItemStack itemStackIn) {
				if (debug) {
					if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
						if (!buttonPressed)
							offset += Keyboard.isKeyDown(Keyboard.KEY_TAB) ? 0.005 : 0.05;
						buttonPressed = true;
					} else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
						if (!buttonPressed)
							offset -= Keyboard.isKeyDown(Keyboard.KEY_TAB) ? 0.005 : 0.05;
						buttonPressed = true;
					} else if (Keyboard.isKeyDown(Keyboard.KEY_ADD)) {
						if (!buttonPressed)
							scale -= Keyboard.isKeyDown(Keyboard.KEY_TAB) ? 0.01 : 0.1;
						buttonPressed = true;
					} else if (Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)) {
						if (!buttonPressed)
							scale += Keyboard.isKeyDown(Keyboard.KEY_TAB) ? 0.01 : 0.1;
						buttonPressed = true;
					} else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
						if (!buttonPressed) {
							EntityPlayer player = Minecraft.getMinecraft().player;
							Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages(false);
							player.sendMessage(new TextComponentString("-- Current Configuration --").setStyle(new Style().setColor(TextFormatting.AQUA)));
							player.sendMessage(new TextComponentString(String.format(" Scale: %01.2f",scale)));
							player.sendMessage(new TextComponentString(String.format(" Offset: %01.2f",offset)));
						}
						buttonPressed = true;
					} else
						buttonPressed = false;
				}
				LeafiaGls.pushMatrix();
				if (type.equals(TransformType.GUI)) {
					LeafiaGls.enableLighting();
					LeafiaGls.translate(offset,0,-0.01);
				}
				genericGrip.apply(type);
				GL11.glScaled(10/scale, 10/scale, 10/scale);
				renderCommon(itemStackIn);
				if (type.equals(TransformType.GUI))
					LeafiaGls.disableLighting();
				LeafiaGls.popMatrix();
			}
//			public void renderInventory() {
//                /*GL11.glRotated(-65,1,0,0);
//                GL11.glRotated(25,0,0,1);
//                GL11.glTranslated(0,-_itemYoffset(),0);*/
//			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GlStateManager.shadeModel(GL11.GL_SMOOTH);
				bindTexture(__getTexture());
				__getModel().renderAll();
				GlStateManager.shadeModel(GL11.GL_FLAT);
			}
		};
	}
}
