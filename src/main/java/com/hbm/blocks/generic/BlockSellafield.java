package com.hbm.blocks.generic;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.hbm.blocks.ICustomBlockItem;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.items.IDynamicModels;
import com.hbm.items.IDynamicSprites;
import com.hbm.items.IModelRegister;
import com.hbm.lib.RefStrings;
import com.hbm.potion.HbmPotion;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * See parent class for more detailed info. Since I could not cram all the data into single item due to 4bit block data
 * restriction, check out getSellafiteFromLvl and getLvlfromSellafite, they should make it much closer in behavior to
 * what 1.7 can get away with.
  * @author MrNorwood
 */
public class BlockSellafield extends BlockSellafieldSlaked implements ICustomBlockItem, IDynamicModels {

    public static final int LEVELS = 7;
    public static final float rad = 0.5f;
    public final int level;
    public static final int[][] colors = new int[][]{
            {0x4C7939, 0x41463F},
            {0x418223, 0x3E443B},
            {0x338C0E, 0x3B5431},
            {0x1C9E00, 0x394733},
            {0x02B200, 0x37492F},
            {0x00D300, 0x324C26}
    };
    public static BiMap<Integer, Block> SELLAFIETE_LEVELS = HashBiMap.create(LEVELS);

    public static void registerSellafieldLevels() {
        SELLAFIETE_LEVELS.put(-1, ModBlocks.sellafield_slaked);
        SELLAFIETE_LEVELS.put(0, ModBlocks.sellafield_0);
        SELLAFIETE_LEVELS.put(1, ModBlocks.sellafield_1);
        SELLAFIETE_LEVELS.put(2, ModBlocks.sellafield_2);
        SELLAFIETE_LEVELS.put(3, ModBlocks.sellafield_3);
        SELLAFIETE_LEVELS.put(4, ModBlocks.sellafield_4);
        SELLAFIETE_LEVELS.put(5, ModBlocks.sellafield_core);
    }


    public BlockSellafield(Material mat, SoundType type, String s, int level) {
        super(mat, type, s);
        this.level = level;
        this.needsRandomTick = true;
    }

    public static Block getSellafiteFromLvl(int level) {
        if (SELLAFIETE_LEVELS.containsKey(level))
            return SELLAFIETE_LEVELS.get(level);
        else
            return SELLAFIETE_LEVELS.get(-1); //Slaked
    }

    public static int getLvlFromSellafite(Block block) {
        return SELLAFIETE_LEVELS.inverse().getOrDefault(block, -1);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if(!isNatural)
            return;
        int level = this.level;

        if(entityIn instanceof EntityLivingBase) {
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, level < 5 ? level : level * 2));
            if (level >= 3)
                entityIn.setFire(level);

        }
    }

    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!state.getValue(NATURAL)) return;
        IBlockState currentState = world.getBlockState(pos);
        int level = getLvlFromSellafite(currentState.getBlock());
        int variant = currentState.getValue(VARIANT);


        float netRad = rad * (level + 1);
        RadiationSystemNT.incrementRad(world, pos, netRad, netRad);


        if (rand.nextInt(level == 0 ? 25 : 15) == 0) {
            if (level > 0)
                world.setBlockState(pos, getSellafiteFromLvl(level - 1).getDefaultState().withProperty(VARIANT, variant), 3);
            else
                world.setBlockState(pos, ModBlocks.sellafield_slaked.getDefaultState().withProperty(VARIANT, variant));
        }
    }


    @SideOnly(Side.CLIENT)
    public void registerSprite(TextureMap map) {
        int[] tint = colors[level];
        for (String texture : sellafieldTextures) {
            ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, basePath + texture + "-" + level);
            TextureAtlasSpriteMutatable mutatedTexture = new TextureAtlasSpriteMutatable(spriteLoc.toString(), new RGBMutatorInterpolatedComponentRemap(0x858384, 0x434343, tint[0], tint[1]));
            map.setTextureEntry(mutatedTexture);
        }
    }


    public void registerItem() {
        ItemBlock itemBlock = new SellafieldItemBlock(this);
        itemBlock.setRegistryName(Objects.requireNonNull(this.getRegistryName()));
        itemBlock.setCreativeTab(this.getCreativeTab());
        ForgeRegistries.ITEMS.register(itemBlock);
    }


    @SideOnly(Side.CLIENT)
    public void bakeModel(ModelBakeEvent event) {
        try {
            IModel baseModel = ModelLoaderRegistry.getModel(new ResourceLocation("minecraft:block/cube_all"));

            for (int textureIndex = 0; textureIndex <= sellafieldTextures.length - 1; textureIndex++) {
                ImmutableMap.Builder<String, String> textureMap = ImmutableMap.builder();

                ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, basePath + sellafieldTextures[textureIndex] + "-" + level);

                // Base texture
                textureMap.put("all", spriteLoc.toString());


                IModel retexturedModel = baseModel.retexture(textureMap.build());
                IBakedModel bakedModel = retexturedModel.bake(
                        ModelRotation.X0_Y0, DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter()
                );

                List<ModelResourceLocation> modelLocations = new ArrayList<>();
                modelLocations.add(new ModelResourceLocation(getRegistryName(), "natural=false,variant=" + textureIndex));
                modelLocations.add(new ModelResourceLocation(getRegistryName(), "natural=true,variant=" + textureIndex));
                modelLocations.forEach(model -> event.getModelRegistry().putObject(model, bakedModel));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SellafieldItemBlock extends SellafieldSlackedItemBlock implements IModelRegister {
        public SellafieldItemBlock(Block block) {
            super(block);
            this.hasSubtypes = true;
            this.canRepair = false;
        }


    }
}