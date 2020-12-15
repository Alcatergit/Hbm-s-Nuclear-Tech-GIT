package com.hbm.core;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions({"com.hbm.core"})
public class HbmCorePlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		/*File file = new File("./config/hbm/hbm.cfg");
		if(file.exists()){
			Configuration config = new Configuration(file);
			if(config.get("01_general", "1.21_enableShaders", false).getBoolean()){
				
				return new String[]{"com.hbm.core.ProfilerClassTransformer", 
						"com.hbm.core.ChunkRenderContainerClassTransformer", 
						"com.hbm.core.RenderManagerClassTransformer", 
						"com.hbm.core.TileEntityRendererDispatcherClassTransformer", 
						"com.hbm.core.GlStateManagerClassTransformer"};
			}
		}
		System.out.println("Shaders are disabled! Not applying transformers!");*/
		return new String[]{/*"com.hbm.core.EntityRendererTransformer"*/};
	}

	@Override
	public String getModContainerClass() {
		return "com.hbm.core.HbmCoreModContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
