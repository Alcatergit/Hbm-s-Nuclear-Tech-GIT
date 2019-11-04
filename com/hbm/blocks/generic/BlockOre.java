package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockOre extends Block {
	
	private float radIn = 0.0F;
	private float radMax = 0.0F;

	public BlockOre(Material materialIn, String name) {
		super(materialIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(MainRegistry.controlTab);
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	public BlockOre(Material mat, boolean tick, String name){
		this(mat, name);
		this.setTickRandomly(tick);
	}
	
	public BlockOre(Material mat, float rad, float max, String name){
		this(mat, name);
		this.setTickRandomly(true);
		this.radIn = rad;
		this.radMax = max;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red) {
			return ModItems.trinitite;
		}
		if(this == ModBlocks.waste_planks) {
			return Items.COAL;
		}
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur || this == ModBlocks.ore_meteor_sulfur){
			return ModItems.sulfur;
		}
		if(this == ModBlocks.ore_niter){
			return ModItems.niter;
		}
		if(this == ModBlocks.ore_fluorite){
			return ModItems.fluorite;
		}
		if(this == ModBlocks.ore_lignite){
			return ModItems.lignite;
		}
		if(this == ModBlocks.ore_rare)
		{
			switch(rand.nextInt(6)) {
			case 0: return ModItems.fragment_actinium;
			case 1: return ModItems.fragment_cerium;
			case 2: return ModItems.fragment_cobalt;
			case 3: return ModItems.fragment_lanthanium;
			case 4: return ModItems.fragment_neodymium;
			case 5: return ModItems.fragment_niobium;
			}
		}
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random rand) {
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur || this == ModBlocks.ore_meteor_sulfur){
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.block_niter){
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_fluorite){
			return 2 + rand.nextInt(3);
		}
		if(this == ModBlocks.ore_rare){
			return 4 + rand.nextInt(8);
		}
		return 1;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return this == ModBlocks.waste_planks ? 1 : 0;
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entity) {
		if (entity instanceof EntityLivingBase && (this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red))
    	{
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 0));
    	}
		if (entity instanceof EntityLivingBase && this == ModBlocks.sellafield_0)
    	{
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 0));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.sellafield_1)
    	{
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 1));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.sellafield_2)
    	{
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 2));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.sellafield_3)
    	{
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 3));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.sellafield_4)
    	{
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 4));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.sellafield_core)
    	{
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(HbmPotion.radiation, 30 * 20, 5));
    	}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		if (this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red)
        {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + rand.nextFloat(), pos.getY() + 1.1F, pos.getZ() + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(this.radIn > 0){
			RadiationSavedData.incrementRad(worldIn, pos.getX(), pos.getZ(), radIn, radMax);
			//I don't know what the priority is, but I saw someone online made it 40, so I guess I'll do that
			worldIn.scheduleBlockUpdate(pos, state.getBlock(), this.tickRate(worldIn), 40);
		}
	}

	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
}
