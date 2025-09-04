package com.hbm.blocks.fluid;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.ArmorUtil;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import org.jetbrains.annotations.NotNull;

public class MudBlock extends BlockFluidClassic {

	public static DamageSource damageSource;
	public Random rand = new Random();
	public int color;
	
	public MudBlock(Fluid fluid, DamageSource d, String s, int color) {
		super(fluid, Material.LAVA);
		damageSource = d;
		this.color = color;
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setQuantaPerBlock(4);
		this.setCreativeTab(null);
		displacements.put(this, false);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public Vec3d getFogColor(World world, BlockPos pos, IBlockState state, Entity entity, Vec3d originalColor, float partialTicks) {
		if (!isWithinFluid(world, pos, ActiveRenderInfo.projectViewFromEntity(entity, partialTicks)))
		{
			BlockPos otherPos = pos.down(densityDir);
			IBlockState otherState = world.getBlockState(otherPos);
			return otherState.getBlock().getFogColor(world, otherPos, otherState, entity, originalColor, partialTicks);
		}
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		return new Vec3d(red, green, blue);
	}

	private boolean isWithinFluid(IBlockAccess world, BlockPos pos, Vec3d vec) {
		float filled = getFilledPercentage(world, pos);
		return filled < 0 ? vec.y > pos.getY() + filled + 1
				: vec.y < pos.getY() + filled;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getMaterial().isLiquid()) {
			return false;
		}
		return super.canDisplace(world, pos);
	}
	
	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {
		if (world.getBlockState(pos).getMaterial().isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, pos);
	}
	
	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity) {
		entity.setInWeb();

		if (!(entity instanceof EntityPlayer && ArmorUtil.checkForHazmat((EntityPlayer) entity))) {
			entity.attackEntityFrom(ModDamageSource.mudPoisoning, 8);
		}
		if(entity instanceof EntityLivingBase)
			ContaminationUtil.contaminate((EntityLivingBase)entity, HazardType.RADIATION, ContaminationType.CREATIVE, 20F);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		reactToBlocks2(world, pos.east());
		reactToBlocks2(world, pos.west());
		reactToBlocks2(world, pos.up());
		reactToBlocks2(world, pos.down());
		reactToBlocks2(world, pos.south());
		reactToBlocks2(world, pos.north());
		super.updateTick(world, pos, state, rand);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighbourPos) {
		reactToBlocks(world, pos.east());
		reactToBlocks(world, pos.west());
		reactToBlocks(world, pos.up());
		reactToBlocks(world, pos.down());
		reactToBlocks(world, pos.south());
		reactToBlocks(world, pos.north());
		super.neighborChanged(state, world, pos, neighborBlock, neighbourPos);
	}
	
	public void reactToBlocks(World world, BlockPos pos) {
		if(world.getBlockState(pos).getMaterial() != Material.LAVA) {
			IBlockState block = world.getBlockState(pos);
			
			if(block.getMaterial().isLiquid()) {
				world.setBlockToAir(pos);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void reactToBlocks2(World world, BlockPos pos) {
		if(world.getBlockState(pos).getMaterial() != Material.LAVA) {
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			if (block == Blocks.STONE || 
					block == Blocks.STONE_BRICK_STAIRS || 
					block == Blocks.STONEBRICK || 
					block == Blocks.STONE_SLAB) {
				if(rand.nextInt(20) == 0)
					world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
			} else if (block == Blocks.COBBLESTONE) {
				if(rand.nextInt(15) == 0)
					world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
			} else if (block == Blocks.SANDSTONE) {
				if(rand.nextInt(5) == 0)
					world.setBlockState(pos, Blocks.SAND.getDefaultState());
			} else if (block == Blocks.HARDENED_CLAY || 
					block == Blocks.STAINED_HARDENED_CLAY) {
				if(rand.nextInt(10) == 0)
					world.setBlockState(pos, Blocks.CLAY.getDefaultState());
			} else if (state.getMaterial() == Material.WOOD) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.CACTUS) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.CAKE) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.CIRCUITS) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.CLOTH) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.CORAL) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.CRAFTED_SNOW) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.GLASS) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.GOURD) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.ICE) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.LEAVES) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.PACKED_ICE) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.PISTON) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.PLANTS) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.PORTAL) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.REDSTONE_LIGHT) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.SNOW) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.SPONGE) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.VINE) {
				world.setBlockToAir(pos);
			} else if (state.getMaterial() == Material.WEB) {
				world.setBlockToAir(pos);
			} else if (block.getExplosionResistance(null) < 1.2F) {
				world.setBlockToAir(pos);
			}
		}
	}
	
	@Override
	public int tickRate(World world) {
		return 15;
	}
	
}
