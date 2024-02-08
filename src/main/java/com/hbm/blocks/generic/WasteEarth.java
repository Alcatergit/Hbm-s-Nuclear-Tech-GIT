package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.items.ModItems;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ContaminationUtil;
import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WasteEarth extends Block implements IItemHazard {

	public static final PropertyInteger META = PropertyInteger.create("meta", 0, 15);
	ItemHazardModule module;
	
	public WasteEarth(Material materialIn, boolean tick, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		this.setTickRandomly(tick);
		this.setHarvestLevel("shovel", 0);
		this.module = new ItemHazardModule();
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	public WasteEarth(Material materialIn, SoundType type, boolean tick, String s) {
		this(materialIn, tick, s);
		setSoundType(type);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{META});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(META);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(META, meta);
	}

	@Override
	public ItemHazardModule getModule() {
		return module;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(this == ModBlocks.frozen_grass){
			return Items.SNOWBALL;
		}
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 1;
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entity) {
		if (entity instanceof EntityLivingBase && this == ModBlocks.waste_earth) {

    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 15 * 20, 4));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.waste_dirt) {

    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 20 * 20, 9));
    	}
    	
    	if (entity instanceof EntityLivingBase && this == ModBlocks.frozen_grass) {
    	
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2 * 60 * 20, 2));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.waste_mycelium) {
    	
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 29));
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 0));
    	}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		
		if(this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium){
			worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + rand.nextFloat(), pos.getY() + 1.1F, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public boolean canEntitySpawn(IBlockState state, Entity entityIn){
		return ContaminationUtil.isRadImmune(entityIn);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos1, IBlockState state, Random rand) {
		int x = pos1.getX();
		int y = pos1.getY();
		int z = pos1.getZ();
		if(this == ModBlocks.waste_mycelium && GeneralConfig.enableMycelium) {
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2; j++) {
					for(int k = -1; k < 2; k++) {
						Block b0 = world.getBlockState(new BlockPos(x + i, y + j, z + k)).getBlock();
						IBlockState b1 = world.getBlockState(new BlockPos(x + i, y + j + 1, z + k));
						if(!b1.isOpaqueCube() && (b0 == Blocks.DIRT || b0 == Blocks.GRASS || b0 == Blocks.MYCELIUM || b0 == ModBlocks.waste_earth)) {
							world.setBlockState(new BlockPos(x + i, y + j, z + k), ModBlocks.waste_mycelium.getDefaultState());
						}
					}
				}
			}
		}

		if(this == ModBlocks.waste_earth || this == ModBlocks.waste_dirt || this == ModBlocks.waste_mycelium) {
			
			if(GeneralConfig.enableAutoCleanup) {
				world.setBlockState(new BlockPos(x, y, z), Blocks.DIRT.getDefaultState());
			}
			
			if(world.getBlockState(new BlockPos(x, y + 1, z)).getBlock() instanceof BlockMushroom) {
				world.setBlockState(new BlockPos(x, y + 1, z), ModBlocks.mush.getDefaultState());
			}
		}
	}
}
