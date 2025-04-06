package com.hbm.items.machine;

import com.google.common.collect.ImmutableMap;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemAutogen;
import com.hbm.lib.RefStrings;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;
import com.hbm.util.I18nUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.Objects;

public class ItemScraps extends ItemAutogen {

    public ItemScraps(String s) {
        super(null, s);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (NTMMaterial mat : Mats.orderedList) {
                if (mat.smeltable == NTMMaterial.SmeltingBehavior.SMELTABLE || mat.smeltable == NTMMaterial.SmeltingBehavior.ADDITIVE) {
                    list.add(new ItemStack(this, 1, mat.id));
                }
            }
        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerSprite(TextureMap map) {
        super.registerSprite(map);
        map.registerSprite(new ResourceLocation(RefStrings.MODID, "items/scraps_liquid"));
        map.registerSprite(new ResourceLocation(RefStrings.MODID, "items/scraps_additive"));
    }

    @Override
    public void bakeModel(ModelBakeEvent event) {
        try {
            IModel baseModel = ModelLoaderRegistry.getModel(new ResourceLocation("minecraft", "item/generated"));
            for (NTMMaterial mat : Mats.orderedList) {
                if (mat.smeltable == NTMMaterial.SmeltingBehavior.SMELTABLE || mat.smeltable == NTMMaterial.SmeltingBehavior.ADDITIVE) {
                    String pathIn = getTexturePath(mat);
                    ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, pathIn);
                    IModel retexturedModel = baseModel.retexture(
                            ImmutableMap.of(
                                    "layer0", spriteLoc.toString()
                            )

                    );
                    IBakedModel bakedModel = retexturedModel.bake(ModelRotation.X0_Y0, DefaultVertexFormats.ITEM, ModelLoader.defaultTextureGetter());
                    ModelResourceLocation bakedModelLocation = new ModelResourceLocation(new ResourceLocation(RefStrings.MODID, pathIn), "inventory");
                    event.getModelRegistry().putObject(bakedModelLocation, bakedModel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModels() {
        for (NTMMaterial mat : Mats.orderedList) {
            if (mat.smeltable == NTMMaterial.SmeltingBehavior.SMELTABLE || mat.smeltable == NTMMaterial.SmeltingBehavior.ADDITIVE) {
                String texturePath = getTexturePath(mat);
                ModelResourceLocation location = new ModelResourceLocation(
                        RefStrings.MODID + ":" + texturePath, "inventory"
                );
                ModelLoader.setCustomModelResourceLocation(this, mat.id, location);
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {

        if(stack.hasTagCompound() && stack.getTagCompound().getBoolean("liquid")) {
            Mats.MaterialStack contents = getMats(stack);
            if(contents != null) {
                return I18nUtil.resolveKey(contents.material.getTranslationKey());
            }
        }

        return ("" + I18n.format(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
        Mats.MaterialStack contents = getMats(stack);

        if(contents != null) {

            if(stack.hasTagCompound() && stack.getTagCompound().getBoolean("liquid")) {
                list.add(Mats.formatAmount(contents.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
                if(contents.material.smeltable == contents.material.smeltable.ADDITIVE) list.add(ChatFormatting.DARK_RED + "Additive, not castable!");
            } else {
                list.add(I18nUtil.resolveKey(contents.material.getTranslationKey()) + ", " + Mats.formatAmount(contents.amount, Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)));
            }
        }
    }

    public static Mats.MaterialStack getMats(ItemStack stack) {

        if(stack.getItem() != ModItems.scraps) return null;

        NTMMaterial mat = Mats.matById.get(stack.getItemDamage());
        if(mat == null) return null;

        int amount = MaterialShapes.INGOT.q(1);

        if(stack.hasTagCompound()) {
            amount = stack.getTagCompound().getInteger("amount");
        }

        return new Mats.MaterialStack(mat, amount);
    }

    public static ItemStack create(Mats.MaterialStack stack) {
        return create(stack, false);
    }

    public static ItemStack create(Mats.MaterialStack stack, boolean liquid) {
        if(stack.material == null)
            return new ItemStack(ModItems.nothing); //why do i bother adding checks for fucking everything when they don't work
        ItemStack scrap = new ItemStack(ModItems.scraps, 1, stack.material.id);
        scrap.setTagCompound(new NBTTagCompound());
        scrap.getTagCompound().setInteger("amount", stack.amount);
        if(liquid) scrap.getTagCompound().setBoolean("liquid", true);
        return scrap;
    }
}
