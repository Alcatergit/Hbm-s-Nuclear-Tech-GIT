package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WasteEarth extends Block {
	
	private float radIn = 0.0F;
	private float radMax = 0.0F;
	
	public WasteEarth(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	public WasteEarth(Material mat, float rad, float max, String s){
		this(mat, s);
		this.radIn = rad;
		this.radMax = max;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium){
			return Item.getItemFromBlock(Blocks.DIRT);
		}
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

    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 15 * 20, 0));
    	}
    	
    	if (entity instanceof EntityLivingBase && this == ModBlocks.frozen_grass) {
    	
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2 * 60 * 20, 2));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.waste_mycelium) {
    	
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 3));
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
	public void updateTick(World world, BlockPos pos1, IBlockState state, Random rand) {
		if(this.radIn > 0){
			RadiationSavedData.incrementRad(world, pos1.getX(), pos1.getZ(), this.radIn, this.radMax);
			world.scheduleBlockUpdate(pos1, state.getBlock(), this.tickRate(world), 40);
		}
		MutableBlockPos pos = new BlockPos.MutableBlockPos().setPos(pos1.getX(), pos1.getY(), pos1.getZ());
		if((this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium) && world.getBlockState(pos.add(0, 1, 0)).getBlock() == Blocks.AIR && rand.nextInt(10) == 0)
    	{
    		Block b0;
    		int count = 0;
    		for(int i = -5; i < 5; i++) {
    			for(int j = -5; j < 6; j++) {
    				for(int k = -5; k < 5; k++) {
    					b0 = world.getBlockState(pos.add(i, j, k)).getBlock();
    					if((b0 instanceof BlockMushroom) || b0 == ModBlocks.mush)
    					{
    						count++;
    					}
    				}
    			}
    		}
    		if(count > 0 && count < 5)
    			world.setBlockState(pos.add(0, 1, 0), ModBlocks.mush.getDefaultState());
    	}
    	
    	if(this == ModBlocks.waste_mycelium && MainRegistry.enableMycelium)
    	{
    		for(int i = -1; i < 2; i++) {
    			for(int j = -1; j < 2; j++) {
    				for(int k = -1; k < 2; k++) {
    					IBlockState bs = world.getBlockState(pos.add(0, 1, 0));
    					Block b0 = world.getBlockState(pos.add(i, j, k)).getBlock();
    					Block b1 = world.getBlockState(pos.add(0, 1, 0)).getBlock();
    					if(!bs.isOpaqueCube() && (b0 == Blocks.DIRT || b0 == Blocks.GRASS || b0 == Blocks.MYCELIUM || b0 == ModBlocks.waste_earth))
    					{
    						world.setBlockState(pos.add(i, j, k), ModBlocks.waste_mycelium.getDefaultState());
    					}
    				}
    			}
    		}
    		
    		if(rand.nextInt(10) == 0) {
        		Block b0;
        		int count = 0;
        		for(int i = -5; i < 5; i++) {
        			for(int j = -5; j < 6; j++) {
        				for(int k = -5; k < 5; k++) {
        					b0 = world.getBlockState(pos.add(i, j, k)).getBlock();
        					if(b0 == ModBlocks.mush)
        					{
        						count++;
        					}
        				}
        			}
        		}
        		if(count < 5)
        			world.setBlockState(pos.add(0, 1, 0), ModBlocks.mush.getDefaultState());
    		}
    	}
    	
    	if(this == ModBlocks.waste_earth || this == ModBlocks.waste_mycelium)
    	{
            if (!world.isRemote)
            {
                if (world.getLight(pos.add(0, 1, 0)) < 4 && world.getLight(pos.add(0, 1, 0)) > 2)
                {
                	
                	world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                }
            }
    	}
    	
    	if(MainRegistry.enableAutoCleanup && (this == ModBlocks.waste_earth | this == ModBlocks.waste_mycelium))
    		if(!world.isRemote)
    			world.setBlockState(pos, Blocks.DIRT.getDefaultState());
	}

	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
}
