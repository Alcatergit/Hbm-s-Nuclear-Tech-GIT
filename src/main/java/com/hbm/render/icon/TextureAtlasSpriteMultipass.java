package com.hbm.render.icon;

import com.google.common.collect.Lists;
import com.hbm.lib.RefStrings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.apache.commons.compress.utils.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

/**
 * Allows for layered block sprites with support for overlay transparency. Much more performant than using tesselator
 * or adding quads with baked models. It iterates over every singe pixel within the overlay, checks whenever it is
 * transparent and applies the data on top of the same pixel coordinate of the base texture. Now with alpha blending
 * too!
 * //
 *
 * @parm spriteName path for the generated texture
 * @parm baseTexurePath Full resource path to the base texture, it will make up background of the sprite
 * @parm overlayPath Full resource path to the overlay texture, it will be applied on top of base texture
 * @author MrNorwood
 */
public class TextureAtlasSpriteMultipass  extends TextureAtlasSprite {

    private String overlayPath;
    private String baseTexturePath;
    private int mipmap = 0;
    private final String basePath = "textures";

    public TextureAtlasSpriteMultipass(String spriteName, String baseTexturePath, String overlayPath) {
        super(spriteName);
        this.baseTexturePath = baseTexturePath;
        this.overlayPath = overlayPath;
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    private ResourceLocation completeResourceLocation(ResourceLocation loc) {
        return new ResourceLocation(loc.getNamespace(), String.format("%s/%s%s", new Object[] { this.basePath, loc.getPath(), ".png" }));
    }

    @Override
    public boolean load(IResourceManager man, ResourceLocation resourcelocation, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {


        ResourceLocation baseSpriteResourceLocationFull = this.completeResourceLocation(new ResourceLocation(resourcelocation.getNamespace(), this.baseTexturePath));
        ResourceLocation overlaySpriteResourceLocationFull = this.completeResourceLocation(new ResourceLocation(resourcelocation.getNamespace(), this.overlayPath));

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

        } catch (RuntimeException | IOException e) {
            net.minecraftforge.fml.client.FMLClientHandler.instance().trackBrokenTexture(baseSpriteResourceLocationFull, e.getMessage());
            return true;
        } finally {
            IOUtils.closeQuietly(iresource);
        }

        return false;
    }

    public void loadSpriteFrames(IResource resource, IResource overlayResource, int mipmapLevels) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(resource.getInputStream());

        AnimationMetadataSection animationMetadataSection = resource.getMetadata("animation");

        int[][] baseFrameData = new int[mipmapLevels][];
        baseFrameData[0] = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), baseFrameData[0], 0, bufferedImage.getWidth());

        if(overlayResource != null){
            BufferedImage overlayImage = ImageIO.read(overlayResource.getInputStream());
            int[][] overlayData = new int[mipmapLevels][];
            overlayData[0] = new int[overlayImage.getWidth() * overlayImage.getHeight()];

            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    int basePixel = bufferedImage.getRGB(x, y);
                    int overlayPixel = overlayImage.getRGB(x, y);

                    int overlayAlpha = (overlayPixel >> 24) & 0xFF; // Extract alpha channel of overlay
                    if (overlayAlpha > 0) { // If the overlay pixel is not fully transparent
                        int baseRed = (basePixel >> 16) & 0xFF;
                        int baseGreen = (basePixel >> 8) & 0xFF;
                        int baseBlue = basePixel & 0xFF;

                        int overlayRed = (overlayPixel >> 16) & 0xFF;
                        int overlayGreen = (overlayPixel >> 8) & 0xFF;
                        int overlayBlue = overlayPixel & 0xFF;

                        // Perform alpha blending
                        int red = (int) ((overlayAlpha / 255.0) * overlayRed + ((255 - overlayAlpha) / 255.0) * baseRed);
                        int green = (int) ((overlayAlpha / 255.0) * overlayGreen + ((255 - overlayAlpha) / 255.0) * baseGreen);
                        int blue = (int) ((overlayAlpha / 255.0) * overlayBlue + ((255 - overlayAlpha) / 255.0) * baseBlue);

                        baseFrameData[0][y * bufferedImage.getWidth() + x] = (basePixel & 0xFF000000) | (red << 16) | (green << 8) | blue;
                    }
                }
            }
        }

        if (animationMetadataSection == null) {
            this.framesTextureData.add(baseFrameData);
        } else {
            int frameCount = bufferedImage.getHeight() / this.width;

            if (animationMetadataSection.getFrameCount() > 0) {
                for (int frameIndex : animationMetadataSection.getFrameIndexSet()) {
                    if (frameIndex >= frameCount) {
                        throw new RuntimeException("Invalid frame index " + frameIndex);
                    }

                    this.allocateFrameTextureData(frameIndex);
                    this.framesTextureData.set(frameIndex, getFrameTextureData(baseFrameData, this.width, this.width, frameIndex));
                }

                this.setAnimationMetadata(animationMetadataSection);
            } else {
                List<AnimationFrame> frames = Lists.newArrayList();

                for (int i = 0; i < frameCount; ++i) {
                    this.framesTextureData.add(getFrameTextureData(baseFrameData, this.width, this.width, i));
                    frames.add(new AnimationFrame(i, -1));
                }

                this.setAnimationMetadata(new AnimationMetadataSection(frames, this.width, this.height, animationMetadataSection.getFrameTime(), animationMetadataSection.isInterpolate()));;
            }
        }
    }

    private void setAnimationMetadata(AnimationMetadataSection metadata) {
        try {
            Field field = ReflectionHelper.findField(TextureAtlasSprite.class, "animationMetadata", "field_110982_k");
            field.setAccessible(true);
            field.set(this, metadata);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to set animation metadata", e);
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
