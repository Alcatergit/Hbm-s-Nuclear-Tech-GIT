package com.hbm.blocks.generic;

import com.google.common.collect.ImmutableMap;
import com.hbm.blocks.BlockBase;
import com.hbm.blocks.ICustomBlockItem;
import com.hbm.items.IDynamicModels;
import com.hbm.items.IDynamicSprites;
import com.hbm.items.IModelRegister;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * This is a 1:1 Sellafield block ported from 1.7.10, but also allows for retrieval of any variant of the block in game, as it's not based on renderer
 * Only limitation is that I was not able to cram all the meta blocks into single block due to 4 bit restriction (it would have been 5 bits to do so),
 * hence a compromise of splitting all of them between different blocks, but allowing each block to have states with textures independent off of the
 * coordinates.
 *
 * @author MrNorwood
 */
public class BlockSellafieldSlaked extends BlockBase implements ICustomBlockItem,  IDynamicModels {
    public static final String[] sellafieldTextures = new String[]{"sellafield_slaked", "sellafield_slaked_1", "sellafield_slaked_2", "sellafield_slaked_3"};
    public static final int TEXTURE_VARIANTS = sellafieldTextures.length;
    public static final int META_COUNT = TEXTURE_VARIANTS;
    public static final String basePath = "blocks/";
    public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, sellafieldTextures.length - 1);
    public static final PropertyBool NATURAL = PropertyBool.create("natural");
    //public static List<BlockSellafieldSlaked> INSTANCES = new ArrayList<>();
    public static final boolean showMetaInCreative = true;
    protected boolean isNatural = true;

    public BlockSellafieldSlaked(Material mat, SoundType type, String s) {
        super(mat, type, s);
        INSTANCES.add(this);
    }


//    public static void registerModels(){
//        INSTANCES.forEach(BlockSellafieldSlaked::registerModel);
//    }

    @SideOnly(Side.CLIENT)
    public void registerModel() {
        // Register the model for natural
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getRegistryName(), "natural=true,variant=0")); // Special handling for natural

        // Register the models for meta=1 to meta=4
        for (int meta = 1; meta <= META_COUNT; meta++) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), meta,
                    new ModelResourceLocation(getRegistryName(), "natural=false,variant=" + (meta - 1)));
        }
    }

    public static void bakeModels(ModelBakeEvent event){
        INSTANCES.forEach(blockSellafieldSlaked -> blockSellafieldSlaked.bakeModel(event));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        if (!state.getValue(NATURAL))  return state;
        int variant = getVariantForPos(pos);
        return state.withProperty(NATURAL, true).withProperty(VARIANT, variant);
    }


    @SideOnly(Side.CLIENT)
    public void bakeModel(ModelBakeEvent event) {
        try {
            IModel baseModel = ModelLoaderRegistry.getModel(new ResourceLocation("minecraft:block/cube_all"));

            for (int textureIndex = 0; textureIndex <= sellafieldTextures.length - 1; textureIndex++) {
                ImmutableMap.Builder<String, String> textureMap = ImmutableMap.builder();

                ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, basePath + sellafieldTextures[textureIndex]);

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

    public static int getVariantForPos(BlockPos pos){
        /*
            For any autist exploiter: YES, this is deterministic, YES you can theoretically derive coordinates from
            a patch of those, assuming people use meta 0 blocks. Now go stroke your ego elsewhere on something
            more productive
         */
        long l = (pos.getX() * 3129871L) ^ (long) pos.getY() * 116129781L ^ (long) pos.getZ();
        l = l * l * 42317861L + l * 11L;
        int i = (int) (l >> 16 & 3L);
        return Math.abs(i) % TEXTURE_VARIANTS;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack
            stack) {
        int meta = stack.getMetadata();
        IBlockState newState;
        if (meta == 0) {
            newState = this.getStateFromMeta(meta).withProperty(VARIANT, getVariantForPos(pos));
            this.isNatural = true;
        } else {
            newState = this.getStateFromMeta(meta).withProperty(VARIANT, (meta - 1));
            this.isNatural = false;
        }
        world.setBlockState(pos, newState, 3);
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NATURAL, VARIANT);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        boolean natural = state.getValue(NATURAL); // Get the natural property (boolean)
        int variant = state.getValue(VARIANT); // Get the variant property (int)

        // 1 bit for NATURAL (true = 0, false = 1)
        // 1 bit for padding
        // 2 bits for variant

        // 7 variants possible atm, can be 8 if player obtainable gem  block was meta 16 or higher

        return (variant & 0b111) | ((natural ? 0 : 1) << 3);

    }
    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean natural;
        int variant =  (meta & 0b111);
        //0 is reserved for natural block accessable from ingame
        //Anything >= 8 is not neutralized as blockItems only go up to 4 meta
        if(meta == 0 || meta >= 8) {
            natural = true;
        }
        else {
            //Enforce staggered ID
            variant--;
            natural = ((meta >> 3) & 1) == 1;
        }
        return this.getDefaultState()
                .withProperty(NATURAL, natural)
                .withProperty(VARIANT, variant);
    }



    @Override

    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        boolean natural = state.getValue(NATURAL);
        int variant = state.getValue(VARIANT);
        int meta = variant + 1;
        if (natural)
            meta = 0;
        return Collections.singletonList(new ItemStack(Item.getItemFromBlock(this), 1, meta));
    }

//    public static void registerSprites(TextureMap map){
//        INSTANCES.forEach(blockSellafield -> blockSellafield.registerSprite(map));
//    }

    @SideOnly(Side.CLIENT)
    public void registerSprite(TextureMap map) {
        for (String texture : sellafieldTextures) {
            ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, basePath + texture);
            map.registerSprite(spriteLoc);
        }
    }

    public void registerItem() {
        ItemBlock itemBlock = new SellafieldSlackedItemBlock(this);
        itemBlock.setRegistryName(Objects.requireNonNull(this.getRegistryName()));
        if(showMetaInCreative) itemBlock.setCreativeTab(this.getCreativeTab());
        ForgeRegistries.ITEMS.register(itemBlock);
    }


    public class SellafieldSlackedItemBlock extends ItemBlock implements IModelRegister {
        public SellafieldSlackedItemBlock(Block block) {
            super(block);
            this.hasSubtypes = true;
            this.canRepair = false;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
            if (this.isInCreativeTab(tab)) {
                for (int i = 0; i <= META_COUNT; i++) {
                    list.add(new ItemStack(this, 1, i));
                }
            }
        }

        @Override
        public String getTranslationKey(ItemStack stack) {
            return super.getTranslationKey() + "_name";

        }

        public String getItemStackDisplayName(ItemStack stack)
        {
            int meta = stack.getMetadata();
            String name = I18nUtil.resolveKey( this.getTranslationKey() + ".name");
            String neutralizedKey = I18nUtil.resolveKey("adjective.neutralized");
            if(meta == 0)
                return name;
            else
                return neutralizedKey + " " + name;
        }


        @Override
        public void registerModels() {
            ModelLoader.setCustomModelResourceLocation(this, 0,
                    new ModelResourceLocation(this.getRegistryName(), "variant=1"));

            for (int meta = 1; meta <= META_COUNT; meta++) {
                ModelLoader.setCustomModelResourceLocation(this, meta,
                        new ModelResourceLocation(this.getRegistryName(), "variant=" + meta));
            }
        }

    }
}
