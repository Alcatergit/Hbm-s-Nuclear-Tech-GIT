package com.leafia.contents.machines.reactors.pwr.debris;

import com.hbm.render.item.TEISRBase;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PWRDebrisItemRender extends TEISRBase {
	RenderPWRDebris associate = null;

	@Override
	public void renderByItem(ItemStack itemStackIn) {
		if (associate == null)
			associate = new RenderPWRDebris(Minecraft.getMinecraft().getRenderManager());
		LeafiaGls._push();
		LeafiaGls.pushMatrix();
		LeafiaGls.enableCull();

		LeafiaGls.translate(0.5,0.5,0.5);
		LeafiaGls.rotate(225,0,1,0);
		LeafiaGls.rotate(30,1,0,1);
		if (type == TransformType.GUI || type == TransformType.FIXED)
			LeafiaGls.scale(1.5);
		else
			LeafiaGls.scale(0.75);

		NBTTagCompound nbt = itemStackIn.getTagCompound();
		if (nbt != null) {
			associate.drawModel(((PWRDebrisItem)itemStackIn.getItem()).type,nbt.getString("block"),nbt.getInteger("meta"));
		}

		LeafiaGls.popMatrix();
		LeafiaGls._pop();
	}
}
