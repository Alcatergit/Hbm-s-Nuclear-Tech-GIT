package com.leafia.contents.building.mixed;

import com.google.common.collect.Lists;
import com.llib.exceptions.LeafiaDevFlaw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.compress.utils.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public class TextureAtlasSpriteHalf extends TextureAtlasSprite {
	/// thanks community edition
	public final String a;
	public final String b;
	private int mipmap = 0;
	private final String basePath = "textures";
	public final HalfDirection dir;

	public enum HalfDirection { A_UP_B_DOWN,A_RIGHT_B_LEFT,A_DOWN_B_UP,A_LEFT_B_RIGHT }

	public TextureAtlasSpriteHalf(String spriteName,String a,String b,HalfDirection dir) {
		super(spriteName);
		this.a = a;
		this.b = b;
		this.dir = dir;
	}

	@Override
	public boolean hasCustomLoader(IResourceManager manager,ResourceLocation location) {
		return true;
	}

	private ResourceLocation completeResourceLocation(ResourceLocation loc) {
		return new ResourceLocation(loc.getNamespace(), String.format("%s/%s%s", new Object[] { this.basePath, loc.getPath(), ".png" }));
	}

	@Override
	public boolean load(IResourceManager man, ResourceLocation resourcelocation, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {


		ResourceLocation baseSpriteResourceLocationFull = this.completeResourceLocation(new ResourceLocation(resourcelocation.getNamespace(), this.a));
		ResourceLocation overlaySpriteResourceLocationFull = this.completeResourceLocation(new ResourceLocation(resourcelocation.getNamespace(), this.b));

		IResource iresource = null;
		IResource overlayResource = null;
		try {

			//Base texture
			iresource = man.getResource(baseSpriteResourceLocationFull);
			PngSizeInfo pngSizeInfo = PngSizeInfo.makeFromResource(iresource);
			boolean hasAnimation = iresource.getMetadata("animation") != null;
			this.loadSprite(pngSizeInfo, hasAnimation);

			//Overlay
			overlayResource = man.getResource(overlaySpriteResourceLocationFull);
			PngSizeInfo overlayPngSizeInfo = PngSizeInfo.makeFromResource(overlayResource);
			boolean overlayHasAnimation = iresource.getMetadata("animation") != null;
			this.loadSprite(overlayPngSizeInfo, overlayHasAnimation);


			iresource = man.getResource(baseSpriteResourceLocationFull);
			overlayResource = man.getResource(overlaySpriteResourceLocationFull);
			this.mipmap = Minecraft.getMinecraft().getTextureMapBlocks().getMipmapLevels()+1;
			this.loadSpriteFrames(iresource, overlayResource, this.mipmap);

		} catch (RuntimeException|IOException e) {
			net.minecraftforge.fml.client.FMLClientHandler.instance().trackBrokenTexture(baseSpriteResourceLocationFull, e.getMessage());
			return true;
		} finally {
			IOUtils.closeQuietly(iresource);
		}

		return false;
	}

	public void loadSpriteFrames(IResource aResource, IResource bResource, int mipmapLevels) throws IOException {
		BufferedImage aImage = ImageIO.read(aResource.getInputStream());

		AnimationMetadataSection animationMetadataSection = aResource.getMetadata("animation");

		int[][] baseFrameData = new int[mipmapLevels][];
		baseFrameData[0] = new int[aImage.getWidth() * aImage.getHeight()];
		aImage.getRGB(0, 0, aImage.getWidth(), aImage.getHeight(), baseFrameData[0], 0, aImage.getWidth());

		if(bResource != null){
			BufferedImage bImage = ImageIO.read(bResource.getInputStream());
			int[][] overlayData = new int[mipmapLevels][];
			overlayData[0] = new int[bImage.getWidth() * bImage.getHeight()];

			int height = aImage.getHeight();
			int width = aImage.getWidth();
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int aPixel = aImage.getRGB(x, y);
					int bPixel = bImage.getRGB(x, y);
					boolean isB = false;
					if (dir == HalfDirection.A_UP_B_DOWN)
						isB = y >= height/2;
					else if (dir == HalfDirection.A_DOWN_B_UP)
						isB = y < height/2;
					else if (dir == HalfDirection.A_LEFT_B_RIGHT)
						isB = x >= width/2;
					else if (dir == HalfDirection.A_RIGHT_B_LEFT)
						isB = x < width/2;
					baseFrameData[0][y*width+x] = isB ? bPixel : aPixel;
				}
			}
		}

		if (animationMetadataSection == null) {
			this.framesTextureData.add(baseFrameData);
		} else {
			int frameCount = aImage.getHeight() / this.width;

			if (animationMetadataSection.getFrameCount() > 0) {
				for (int frameIndex : animationMetadataSection.getFrameIndexSet()) {
					if (frameIndex >= frameCount) {
						throw new RuntimeException("Invalid frame index " + frameIndex);
					}

					this.allocateFrameTextureData(frameIndex);
					this.framesTextureData.set(frameIndex, getFrameTextureData(baseFrameData, this.width, this.width, frameIndex));
				}

				//this.animationMetadata = animationMetadataSection;
				throw new LeafiaDevFlaw("fuck off animationMetadata");
			} else {
				List<AnimationFrame> frames = Lists.newArrayList();

				for (int i = 0; i < frameCount; ++i) {
					this.framesTextureData.add(getFrameTextureData(baseFrameData, this.width, this.width, i));
					frames.add(new AnimationFrame(i, -1));
				}

				//this.animationMetadata = new AnimationMetadataSection(frames, this.width, this.height, animationMetadataSection.getFrameTime(), animationMetadataSection.isInterpolate());
				throw new LeafiaDevFlaw("fuck off animationMetadata");
			}
		}
	}

	private void allocateFrameTextureData(int index)
	{
		if (this.framesTextureData.size() <= index)
		{
			for (int i = this.framesTextureData.size(); i <= index; ++i)
			{
				this.framesTextureData.add(null);
			}
		}
	}

	private static int[][] getFrameTextureData(int[][] data, int width, int height, int frame) {
		int[][] result = new int[data.length][];
		for (int i = 0; i < data.length; ++i) {
			int[] pixels = data[i];
			if (pixels != null) {
				result[i] = new int[(width >> i) * (height >> i)];
				System.arraycopy(pixels, frame * result[i].length, result[i], 0, result[i].length);
			}
		}
		return result;
	}
}
