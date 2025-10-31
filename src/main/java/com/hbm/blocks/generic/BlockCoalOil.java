package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.tool.ItemToolAbility;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.leafia.dev.optimization.LeafiaParticlePacket.FiaSpark;
import com.leafia.dev.optimization.LeafiaParticlePacket.VanillaExt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCoalOil extends BlockOre {

	public BlockCoalOil(String s) {
		super();
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		for(EnumFacing dir : EnumFacing.VALUES) {

        	IBlockState nS = world.getBlockState(pos.offset(dir));
        	Block n = nS.getBlock();

        	if(n == ModBlocks.ore_coal_oil_burning || n == ModBlocks.balefire || n == Blocks.FIRE || nS.getMaterial() == Material.LAVA) {
        		world.scheduleUpdate(pos, this, world.rand.nextInt(20) + 2);
        	}
        }
	}
	/*
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {

	}*/

	public void onBreakBlockProgress(World world,BlockPos pos,EntityPlayer player) {
		if(player.getHeldItemMainhand().isEmpty())
			return;

		if(!(player.getHeldItemMainhand().getItem() instanceof ItemTool || player.getHeldItemMainhand().getItem() instanceof ItemToolAbility))
			return;

		ItemTool tool = (ItemTool)player.getHeldItemMainhand().getItem();

		if(!tool.getToolMaterialName().equals(ToolMaterial.WOOD.toString())) {
			world.playSound(null,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,HBMSoundEvents.sbPickaxeOre,SoundCategory.BLOCKS,0.4f+world.rand.nextFloat()*0.2f,1);
			if (world.rand.nextBoolean()) {
				RayTraceResult res = Library.rayTrace(player,20,0);
				FiaSpark spark = new FiaSpark();
				spark.color = 0xFFEE80;
				spark.count = world.rand.nextInt(3)+1;
				spark.thickness = 0.014f;
				//spark.speed = 0.15+world.rand.nextDouble()*0.2;

				spark.emit(res.hitVec,new Vec3d(res.sideHit.getDirectionVec()),world.provider.getDimension());
				int rand = world.rand.nextInt(4);
				for (int i = 0; i <= rand; i++)
					VanillaExt.Smoke().emit(res.hitVec.add(world.rand.nextDouble()*0.2-0.1,world.rand.nextDouble()*0.2-0.1,world.rand.nextDouble()*0.2-0.1),new Vec3d(0,0,0),world.provider.getDimension());
				if (rand == 3) {
					VanillaExt.Lava().emit(res.hitVec,new Vec3d(0,0,0),world.provider.getDimension());
					if (world.rand.nextInt(3) == 0) {
						world.playSound(null,pos,SoundEvents.ITEM_FIRECHARGE_USE,SoundCategory.BLOCKS,0.65F,0.9F+world.rand.nextFloat()*0.2F);
						world.setBlockState(pos,ModBlocks.ore_coal_oil_burning.getDefaultState());
					}
				}
			}
//			TauSpark spark = new TauSpark();
//			spark.color = 0xFFEE80;
//			spark.life = 2;
//			spark.width = 0.03F;
//			spark.emit(res.hitVec,new Vec3d(res.sideHit.getDirectionVec()).scale(3F+world.rand.nextFloat()),world.provider.getDimension());
		}
	}
	
	@Override
	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
		worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		world.setBlockState(pos, ModBlocks.ore_coal_oil_burning.getDefaultState());
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.COAL;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		return 2 + random.nextInt(2);
	}
}
