package com.leafia.contents.building.mixed;

import com.google.common.collect.ImmutableMap;
import com.hbm.blocks.BlockBase;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.leafia.contents.building.mixed.TextureAtlasSpriteHalf.HalfDirection;
import com.leafia.dev.customblock.ICustomBlock;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.hbm.blocks.ModBlocks.GenericBlockResistance.CONCRETE;

public class BlockMixedConcrete extends BlockBase implements ICustomBlock {
	String a;
	String b;
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	public BlockMixedConcrete(String s,String a,String b) {
		super(Material.ROCK,s);
		INSTANCES.add(this);
		setCreativeTab(MainRegistry.blockTab);
		setHardness(15.0F);
		setResistance(CONCRETE.v);
		this.a = a;
		this.b = b;
	}

	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public void onBlockPlacedBy(World worldIn,BlockPos pos,IBlockState state,EntityLivingBase placer,ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)));
	}


	@SideOnly(Side.CLIENT)
	@Override
	public void bakeModel(ModelBakeEvent event) {
		try {
			IModel baseModel = ModelLoaderRegistry.getModel(new ResourceLocation("minecraft:block/cube"));
			ImmutableMap.Builder<String, String> textureMap = ImmutableMap.builder();
			ResourceLocation front = new ResourceLocation(RefStrings.MODID, "blocks/"+b);
			ResourceLocation back = new ResourceLocation(RefStrings.MODID, "blocks/"+a);
			ResourceLocation top = new ResourceLocation(RefStrings.MODID, "blocks/"+this.getRegistryName()+"_top");
			ResourceLocation right = new ResourceLocation(RefStrings.MODID, "blocks/"+this.getRegistryName()+"_right");
			ResourceLocation bottom = new ResourceLocation(RefStrings.MODID, "blocks/"+this.getRegistryName()+"_bottom");
			ResourceLocation left = new ResourceLocation(RefStrings.MODID, "blocks/"+this.getRegistryName()+"_left");
			// Base texture
			textureMap.put("north", back.toString());
			textureMap.put("south", front.toString());
			textureMap.put("up", bottom.toString());
			textureMap.put("down", top.toString());
			textureMap.put("west", right.toString());
			textureMap.put("east", left.toString());

			IModel retexturedModel = baseModel.retexture(textureMap.build());

			IBakedModel bakedModel = retexturedModel.bake(
					ModelRotation.X0_Y0, DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter()
			);
			event.getModelRegistry().putObject(new ModelResourceLocation(getRegistryName(),"facing=north"), bakedModel);
			{
				IBakedModel rotatedModel = retexturedModel.bake(
						ModelRotation.X0_Y180, DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter()
				);
				event.getModelRegistry().putObject(new ModelResourceLocation(getRegistryName(),"facing=south"), rotatedModel);
			}
			{
				IBakedModel rotatedModel = retexturedModel.bake(
						ModelRotation.X0_Y270, DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter()
				);
				event.getModelRegistry().putObject(new ModelResourceLocation(getRegistryName(),"facing=west"), rotatedModel);
			}
			{
				IBakedModel rotatedModel = retexturedModel.bake(
						ModelRotation.X0_Y90, DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter()
				);
				event.getModelRegistry().putObject(new ModelResourceLocation(getRegistryName(),"facing=east"), rotatedModel);
			}
			{
				IBakedModel rotatedModel = retexturedModel.bake(
						ModelRotation.X270_Y0, DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter()
				);
				event.getModelRegistry().putObject(new ModelResourceLocation(getRegistryName(),"facing=up"), rotatedModel);
			}
			{
				IBakedModel rotatedModel = retexturedModel.bake(
						ModelRotation.X90_Y0, DefaultVertexFormats.BLOCK, ModelLoader.defaultTextureGetter()
				);
				event.getModelRegistry().putObject(new ModelResourceLocation(getRegistryName(),"facing=down"), rotatedModel);
			}

			event.getModelRegistry().putObject(new ModelResourceLocation(getRegistryName(),"inventory"), bakedModel);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this),
				0,
				new ModelResourceLocation(getRegistryName(),"facing=north")
		);
		// pain pain PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN PAIN
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerSprite(TextureMap map) {
		ResourceLocation aLoc = new ResourceLocation(RefStrings.MODID, "blocks/"+a);
		map.registerSprite(aLoc);
		ResourceLocation bLoc = new ResourceLocation(RefStrings.MODID, "blocks/"+b);
		map.registerSprite(bLoc);
		{
			ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, "blocks/"+this.getRegistryName()+"_top");
			TextureAtlasSpriteHalf halfSpr = new TextureAtlasSpriteHalf(
					spriteLoc.toString(),
					"blocks/"+a,"blocks/"+b,HalfDirection.A_DOWN_B_UP
			);
			map.setTextureEntry(halfSpr);
		}
		{
			ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, "blocks/"+this.getRegistryName()+"_bottom");
			TextureAtlasSpriteHalf halfSpr = new TextureAtlasSpriteHalf(
					spriteLoc.toString(),
					"blocks/"+a,"blocks/"+b,HalfDirection.A_UP_B_DOWN
			);
			map.setTextureEntry(halfSpr);
		}
		{
			ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, "blocks/"+this.getRegistryName()+"_left");
			TextureAtlasSpriteHalf halfSpr = new TextureAtlasSpriteHalf(
					spriteLoc.toString(),
					"blocks/"+a,"blocks/"+b,HalfDirection.A_RIGHT_B_LEFT
			);
			map.setTextureEntry(halfSpr);
		}
		{
			ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, "blocks/"+this.getRegistryName()+"_right");
			TextureAtlasSpriteHalf halfSpr = new TextureAtlasSpriteHalf(
					spriteLoc.toString(),
					"blocks/"+a,"blocks/"+b,HalfDirection.A_LEFT_B_RIGHT
			);
			map.setTextureEntry(halfSpr);
		}
	}
}
