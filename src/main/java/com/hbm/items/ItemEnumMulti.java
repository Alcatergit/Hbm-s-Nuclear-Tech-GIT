package com.hbm.items;

import com.google.common.collect.ImmutableMap;
import com.hbm.lib.RefStrings;
import com.hbm.util.EnumUtil;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

public class ItemEnumMulti extends ItemBase implements  IDynamicModels {

    public static final String ROOT_PATH = "items/";
    public static List<ItemEnumMulti> INSTANCES = new ArrayList<>();
    protected final String[] textures;
    //hell yes, now we're thinking with enums!
    protected Class<? extends Enum> theEnum;
    protected boolean multiName;
    protected boolean multiTexture;

    public ItemEnumMulti(String registryName, Class<? extends Enum> theEnum, boolean multiName, boolean multiTexture) {
        super(registryName);
        this.setHasSubtypes(true);
        this.theEnum = theEnum;
        this.multiName = multiName;
        this.multiTexture = multiTexture;
        INSTANCES.add(this);
        this.textures = Arrays.stream(theEnum.getEnumConstants())
                .sorted(Comparator.comparing(Enum::ordinal))
                .map(Enum::name)
                .map(name -> registryName + "." + name.toLowerCase(Locale.US))
                .toArray(String[]::new);
    }


    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        INSTANCES.forEach(ItemEnumMulti::registerModel);
    }

    @SideOnly(Side.CLIENT)
    public static void registerSprites(TextureMap map) {
        INSTANCES.forEach(item -> item.registerSprite(map));
    }

    @SideOnly(Side.CLIENT)
    public static void bakeModels(ModelBakeEvent event) {
        INSTANCES.forEach(item -> item.bakeModel(event));
    }

    @SideOnly(Side.CLIENT)
    public void registerSprite(TextureMap map) {
        for (String texture : textures) {
            map.registerSprite(new ResourceLocation(RefStrings.MODID, ROOT_PATH + texture));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        for (int i = 0; i < theEnum.getEnumConstants().length; i++) {
            ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(new ResourceLocation(RefStrings.MODID, ROOT_PATH + textures[i]), "inventory"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH) {
            for (int i = 0; i < theEnum.getEnumConstants().length; i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }


    @Override
    public Item setTranslationKey(String unlocalizedName) {
        super.setTranslationKey(unlocalizedName);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void bakeModel(ModelBakeEvent event) {
        try {
            IModel baseModel = ModelLoaderRegistry.getModel(new ResourceLocation("minecraft", "item/generated"));
            for (int i = 0; i < theEnum.getEnumConstants().length; i++) {
                ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, ROOT_PATH + textures[i]);
                IModel retexturedModel = baseModel.retexture(
                        ImmutableMap.of(
                                "layer0", spriteLoc.toString()
                        )

                );
                IBakedModel bakedModel = retexturedModel.bake(ModelRotation.X0_Y0, DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter());
                ModelResourceLocation bakedModelLocation = new ModelResourceLocation(spriteLoc, "inventory");
                event.getModelRegistry().putObject(bakedModelLocation, bakedModel);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns null when the wrong enum is passed. Only really used for recipes anyway so it's good.
     */
    public ItemStack stackFromEnum(int count, Enum num) {

        if (num.getClass() != this.theEnum)
            return ItemStack.EMPTY;

        return new ItemStack(this, count, num.ordinal());
    }

    public ItemStack stackFromEnum(Enum num) {
        return stackFromEnum(1, num);
    }

    public boolean isMultiTexture() {
        return multiTexture;
    }

    public Class<? extends Enum> getTheEnum() {
        return theEnum;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {

        if (multiName) {
            Enum num = EnumUtil.grabEnumSafely(theEnum, stack.getItemDamage());
            return super.getTranslationKey() + "." + num.name().toLowerCase(Locale.US);
        } else {
            return super.getTranslationKey(stack);
        }
    }
}
