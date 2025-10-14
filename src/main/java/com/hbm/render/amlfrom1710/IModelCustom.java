package com.hbm.render.amlfrom1710;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IModelCustom
{
	String getType();
    void renderAll();
    void renderOnly(String... groupNames);
    void renderPart(String partName);
    void renderAllExcept(String... excludedGroupNames);
    void tessellateAll(Tessellator tes);
    void tessellatePart(Tessellator tes, String name);
    void tessellateOnly(Tessellator tes, String... names);
    void tessellateAllExcept(Tessellator tes, String... excluded);
}