package com.hbm.explosion;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.projectile.EntityExplosiveBeam;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ExplosionNukeGeneric {

	private final static Random random = new Random();

	/*public static void detonateTestBomb(World world, int x, int y, int z, int bombStartStrength) {
		int r = bombStartStrength;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (r22 >= 25) {
						if (ZZ < r22 + world.rand.nextInt(r22 / 25)) {
							if (Y >= y)
								destruction(world, X, Y, Z);
						}
					} else {
						if (ZZ < r22) {
							if (Y >= y)
								destruction(world, X, Y, Z);
						}
					}
				}
			}
		}

		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy * 50;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						if (Y < y)
							destruction(world, X, Y, Z);
					}
				}
			}
		}
	}

	public static void empBlast(World world, int x, int y, int z, int bombStartStrength) {
		int r = bombStartStrength;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22) {
						emp(world, X, Y, Z);
					}
				}
			}
		}
	}
	*/
	public static void dealDamage(World world, int x, int y, int z, int bombStartStrength) {
		float f = bombStartStrength;
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = bombStartStrength
		;

		// bombStartStrength *= 2.0F;
		i = MathHelper.floor(x - wat - 1.0D);
		j = MathHelper.floor(x + wat + 1.0D);
		k = MathHelper.floor(y - wat - 1.0D);
		int i2 = MathHelper.floor(y + wat + 1.0D);
		int l = MathHelper.floor(z - wat - 1.0D);
		int j2 = MathHelper.floor(z + wat + 1.0D);
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(i, k, l, j, i2, j2));

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / bombStartStrength;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
				if(!Library.isObstructed(world, x, y, z, entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ))
				if (d9 < wat && !(entity instanceof EntityOcelot) &&
					//TODO All this random nuke stuff
						!(entity instanceof EntityNukeCloudSmall)
					//	&& !(entity instanceof EntityMIRV) && !(entity instanceof EntityMiniNuke)
					//	&& !(entity instanceof EntityMiniMIRV) && !(entity instanceof EntityGrenadeASchrab)
					//	&& !(entity instanceof EntityGrenadeNuclear) 
						&& !(entity instanceof EntityExplosiveBeam)
						&& !(entity instanceof EntityPlayer && Library.checkArmor((EntityPlayer) entity, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots)))
						{
					d5 /= d9;
					d6 /= d9;
					d7 /= d9;
					// double d10 = (double)world.getBlockDensity(vec3,
					// entity.boundingBox);
					// if(d10 > 0) isOccupied = true;
					double d11 = (1.0D - d4);// * d10;
					if (!(entity instanceof EntityPlayerMP) || !(entity instanceof EntityPlayerMP
							&& ((EntityPlayerMP) entity).capabilities.isCreativeMode)) {
						// entity.attackEntityFrom(DamageSource.generic,
						// ((int)((d11 * d11 + d11) / 2.0D * 8.0D *
						// bombStartStrength + 1.0D)));
						double damage = entity.getDistance(x, y, z) / bombStartStrength * 250;
						entity.attackEntityFrom(ModDamageSource.nuclearBlast, (float)damage);
						entity.setFire(5);
						double d8 = 1.0;
						if(entity instanceof EntityLivingBase)
							d8 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, d11);
						entity.motionX += d5 * d8 * 0.2D;
						entity.motionY += d6 * d8 * 0.2D;
						entity.motionZ += d7 * d8 * 0.2D;
					}
				}
			}
		}

		bombStartStrength = (int) f;
	}
/*
	public static void succ(World world, int x, int y, int z, int radius) {
		float f = radius;
		HashSet hashset = new HashSet();
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = radius
		;
		boolean isOccupied = false;

		// bombStartStrength *= 2.0F;
		i = MathHelper.floor(x - wat - 1.0D);
		j = MathHelper.floor(x + wat + 1.0D);
		k = MathHelper.floor(y - wat - 1.0D);
		int i2 = MathHelper.floor(y + wat + 1.0D);
		int l = MathHelper.floor(z - wat - 1.0D);
		int j2 = MathHelper.floor(z + wat + 1.0D);
		List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
		Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			
			if(entity instanceof EntityBlackHole)
				continue;
			
			double d4 = entity.getDistance(x, y, z) / radius;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat && !(entity instanceof EntityPlayer
								&& Library.checkArmor((EntityPlayer) entity, ModItems.euphemium_helmet,
										ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))) {
					d5 /= d9;
					d6 /= d9;
					d7 /= d9;
					
					if (!(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {
						double d8 = 0.125 + (random.nextDouble() * 0.25);
						entity.motionX -= d5 * d8;
						entity.motionY -= d6 * d8;
						entity.motionZ -= d7 * d8;
					}
				}
			}
		}
	}

	public static boolean dedify(World world, int x, int y, int z, int radius) {
		float f = radius;
		HashSet hashset = new HashSet();
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;
		double wat = radius
		;
		boolean isOccupied = false;

		// bombStartStrength *= 2.0F;
		i = MathHelper.floor(x - wat - 1.0D);
		j = MathHelper.floor(x + wat + 1.0D);
		k = MathHelper.floor(y - wat - 1.0D);
		int i2 = MathHelper.floor(y + wat + 1.0D);
		int l = MathHelper.floor(z - wat - 1.0D);
		int j2 = MathHelper.floor(z + wat + 1.0D);
		List list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
		Vec3 vec3 = Vec3.createVectorHelper(x, y, z);

		for (int i1 = 0; i1 < list.size(); ++i1) {
			Entity entity = (Entity) list.get(i1);
			double d4 = entity.getDistance(x, y, z) / radius;

			if (d4 <= 1.0D) {
				d5 = entity.posX - x;
				d6 = entity.posY + entity.getEyeHeight() - y;
				d7 = entity.posZ - z;
				double d9 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
				if (d9 < wat && !(entity instanceof EntityPlayer
								&& Library.checkArmor((EntityPlayer) entity, ModItems.euphemium_helmet,
										ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))) {
					d5 /= d9;
					d6 /= d9;
					d7 /= d9;
					// double d10 = (double)world.getBlockDensity(vec3,
					// entity.boundingBox);
					// if(d10 > 0) isOccupied = true;
					double d11 = (1.0D - d4);// * d10;

					if(entity instanceof EntityItem && ((EntityItem)entity).getEntityItem().getItem() == ModItems.flame_pony) {
						entity.setDead();
						return true;
					}
					if(entity instanceof EntityItem && ((EntityItem)entity).getEntityItem().getItem() == ModItems.pellet_antimatter) {
						entity.setDead();
						return true;
					}
						
					if (!(entity instanceof EntityPlayerMP
							&& ((EntityPlayerMP) entity).theItemInWorldManager.getGameType() == GameType.CREATIVE)) {
						entity.attackEntityFrom(ModDamageSource.blackhole, 1000F);
					}
					
					if(!(entity instanceof EntityLivingBase) && !(entity instanceof EntityPlayerMP) && !(entity instanceof EntityBlackHole)) {
						if(random.nextInt(8) == 0)
							entity.setDead();
					}
				}
			}
		}
		
		return false;
	}

	public static void vapor(World world, int x, int y, int z, int bombStartStrength) {
		int r = bombStartStrength * 2;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22)
						vaporDest(world, X, Y, Z);
				}
			}
		}
	}
*/
	public static int destruction(World world, BlockPos pos) {
		int rand;
		if (!world.isRemote) {
			IBlockState b = world.getBlockState(pos);
			if (b.getBlock().getExplosionResistance(world, pos, null, null)>=200f) {	//500 is the resistance of liquids
				//blocks to be spared
				int protection = (int)(b.getBlock().getExplosionResistance(world, pos, null, null)/300f);
				if (b.getBlock() == ModBlocks.brick_concrete) {
					rand = random.nextInt(8);
					if (rand == 0) {
						world.setBlockState(pos, Blocks.GRAVEL.getDefaultState(), 3);
						return 0;
					}
				} else if (b.getBlock() == ModBlocks.brick_light) {
					rand = random.nextInt(3);
					if (rand == 0) {
						world.setBlockState(pos, ModBlocks.waste_planks.getDefaultState(), 3);
						return 0;
					}else if (rand == 1){
						world.setBlockState(pos,ModBlocks.block_scrap.getDefaultState());
						return 0;
					}
				} else if (b.getBlock() == ModBlocks.brick_obsidian) {
					rand = random.nextInt(20);
					if (rand == 0) {
						world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
					}
				} else if (b.getBlock() == Blocks.OBSIDIAN) {
					world.setBlockState(pos, ModBlocks.gravel_obsidian.getDefaultState(), 3);
					return 0;
				} else if(random.nextInt(protection+3)==0){
					world.setBlockState(pos, ModBlocks.block_scrap.getDefaultState());
				}
				return protection;
			}else{//otherwise, kill the block!
				world.setBlockToAir(pos);
			}
		}
		return 0;
	}
	
	public static int vaporDest(World world, BlockPos pos) {
		if (!world.isRemote) {
			IBlockState b = world.getBlockState(pos);
			if (b.getBlock().getExplosionResistance(world, pos, null, null)<0.5f //most light things
					|| b.getBlock() == Blocks.WEB || b.getBlock() == ModBlocks.red_cable
					|| b.getBlock() instanceof BlockLiquid) {
				world.setBlockToAir(pos);
				return 0;
			} else if (b.getBlock().getExplosionResistance(world, pos, null, null)<=3.0f && !b.isOpaqueCube()){
				if(b.getBlock() != Blocks.CHEST && b.getBlock() != Blocks.FARMLAND){
					//destroy all medium resistance blocks that aren't chests or farmland
					world.setBlockToAir(pos);
					return 0;
				}
			}
			
			if (b.getBlock().isFlammable(world, pos, EnumFacing.UP)
					&& world.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
				world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState(),2);
			}
			return (int)( b.getBlock().getExplosionResistance(world, pos, null, null)/300f);
		}
		return 0;
	}

	public static void waste(World world, int x, int y, int z, int radius) {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		int r = radius;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22 + world.rand.nextInt(r22 / 5)) {
						if (world.getBlockState(pos.setPos(X, Y, Z)).getBlock() != Blocks.AIR)
							wasteDest(world, pos);
					}
				}
			}
		}
	}

	public static void wasteDest(World world, BlockPos pos) {
		if (!world.isRemote) {
			int rand;
			IBlockState bs = world.getBlockState(pos);
			Block b = bs.getBlock();
			if (b == Blocks.ACACIA_DOOR || b == Blocks.BIRCH_DOOR || b == Blocks.DARK_OAK_DOOR || b == Blocks.JUNGLE_DOOR || b == Blocks.OAK_DOOR || b == Blocks.SPRUCE_DOOR || b == Blocks.IRON_DOOR) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
			}

			else if (b == Blocks.GRASS) {
				world.setBlockState(pos, ModBlocks.waste_earth.getDefaultState());
			}

			else if (b == Blocks.MYCELIUM) {
				world.setBlockState(pos, ModBlocks.waste_mycelium.getDefaultState());
			}

			else if (b == Blocks.SAND) {
				rand = random.nextInt(20);
				if (rand == 1 && bs.getValue(BlockSand.VARIANT) == BlockSand.EnumType.SAND) {
					world.setBlockState(pos, ModBlocks.waste_trinitite.getDefaultState());
				}
				if (rand == 1 && bs.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) {
					world.setBlockState(pos, ModBlocks.waste_trinitite_red.getDefaultState());
				}
			}

			else if (b == Blocks.CLAY) {
				world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
			}

			else if (b == Blocks.MOSSY_COBBLESTONE) {
				world.setBlockState(pos, Blocks.COAL_ORE.getDefaultState());
			}

			else if (b == Blocks.COAL_ORE) {
				rand = random.nextInt(10);
				if (rand == 1 || rand == 2 || rand == 3) {
					world.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
				}
				if (rand == 9) {
					world.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState());
				}
			}

			else if (b == Blocks.LOG || b == Blocks.LOG2) {
				world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
			}

			else if (b == Blocks.BROWN_MUSHROOM_BLOCK) {
				if (bs.getValue(BlockHugeMushroom.VARIANT) == BlockHugeMushroom.EnumType.STEM) {
					world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
				} else {
					world.setBlockState(pos, Blocks.AIR.getDefaultState(),2);
				}
			}

			else if (b == Blocks.RED_MUSHROOM_BLOCK) {
				if (bs.getValue(BlockHugeMushroom.VARIANT) == BlockHugeMushroom.EnumType.STEM) {
					world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
				} else {
					world.setBlockState(pos, Blocks.AIR.getDefaultState(),2);
				}
			}
			
			else if (bs.getMaterial() == Material.WOOD && bs.isOpaqueCube() && b != ModBlocks.waste_log) {
				world.setBlockState(pos, ModBlocks.waste_planks.getDefaultState());
			}

			else if (b == ModBlocks.ore_uranium) {
				rand = random.nextInt(30);
				if (rand == 1) {
					world.setBlockState(pos, ModBlocks.ore_schrabidium.getDefaultState());
				}
			}

			else if (b == ModBlocks.ore_nether_uranium) {
				rand = random.nextInt(30);
				if (rand == 1) {
					world.setBlockState(pos, ModBlocks.ore_nether_schrabidium.getDefaultState());
				}
			}

		}
	}

	public static void wasteNoSchrab(World world, BlockPos pos, int radius) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		MutableBlockPos mpos = new BlockPos.MutableBlockPos(pos);
		int r = radius;
		int r2 = r * r;
		int r22 = r2 / 2;
		for (int xx = -r; xx < r; xx++) {
			int X = xx + x;
			int XX = xx * xx;
			for (int yy = -r; yy < r; yy++) {
				int Y = yy + y;
				int YY = XX + yy * yy;
				for (int zz = -r; zz < r; zz++) {
					int Z = zz + z;
					int ZZ = YY + zz * zz;
					if (ZZ < r22 + world.rand.nextInt(r22 / 5)) {
						mpos.setPos(X, Y, Z);
						if (world.getBlockState(mpos).getBlock() != Blocks.AIR)
							wasteDestNoSchrab(world, mpos);
					}
				}
			}
		}
	}

	public static void wasteDestNoSchrab(World world, BlockPos pos) {
		if (!world.isRemote) {
			int rand;
			Block b = world.getBlockState(pos).getBlock();
			if (b == Blocks.GLASS || b == Blocks.STAINED_GLASS
					|| b == Blocks.ACACIA_DOOR || b == Blocks.BIRCH_DOOR || b == Blocks.DARK_OAK_DOOR || b == Blocks.JUNGLE_DOOR || b == Blocks.OAK_DOOR || b == Blocks.SPRUCE_DOOR || b == Blocks.IRON_DOOR
					|| b == Blocks.LEAVES || b == Blocks.LEAVES2) {
				world.setBlockToAir(pos);
			}

			else if (b == Blocks.GRASS) {
				world.setBlockState(pos, ModBlocks.waste_earth.getDefaultState());
			}

			else if (b == Blocks.MYCELIUM) {
				world.setBlockState(pos, ModBlocks.waste_mycelium.getDefaultState());
			}

			else if (b == Blocks.SAND) {
				rand = random.nextInt(20);
				if (rand == 1 && world.getBlockState(pos).getValue(BlockSand.VARIANT) == BlockSand.EnumType.SAND) {
					world.setBlockState(pos, ModBlocks.waste_trinitite.getDefaultState());
				}
				if (rand == 1 && world.getBlockState(pos).getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) {
					world.setBlockState(pos, ModBlocks.waste_trinitite_red.getDefaultState());
				}
			}

			else if (b == Blocks.CLAY) {
				world.setBlockState(pos, Blocks.HARDENED_CLAY.getDefaultState());
			}

			else if (b == Blocks.MOSSY_COBBLESTONE) {
				world.setBlockState(pos, Blocks.COAL_ORE.getDefaultState());
			}

			else if (b == Blocks.COAL_ORE) {
				rand = random.nextInt(30);
				if (rand == 1 || rand == 2 || rand == 3) {
					world.setBlockState(pos, Blocks.DIAMOND_ORE.getDefaultState());
				}
				if (rand == 29) {
					world.setBlockState(pos, Blocks.EMERALD_ORE.getDefaultState());
				}
			}

			else if (b == Blocks.LOG || b == Blocks.LOG2) {
				world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
			}

			else if (b == Blocks.PLANKS) {
				world.setBlockState(pos, ModBlocks.waste_planks.getDefaultState());
			}

			else if (b == Blocks.BROWN_MUSHROOM_BLOCK) {
				if (world.getBlockState(pos).getValue(BlockHugeMushroom.VARIANT) == BlockHugeMushroom.EnumType.STEM) {
					world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
				} else {
					world.setBlockToAir(pos);
				}
			}

			else if (b == Blocks.RED_MUSHROOM_BLOCK) {
				if (world.getBlockState(pos).getValue(BlockHugeMushroom.VARIANT) == BlockHugeMushroom.EnumType.STEM) {
					world.setBlockState(pos, ModBlocks.waste_log.getDefaultState());
				} else {
					world.setBlockToAir(pos);
				}
			}
		}
	}
/*
	public static void emp(World world, int x, int y, int z) {
		if (!world.isRemote) {
			
			Block b = world.getBlock(x,y,z);
			TileEntity te = world.getTileEntity(x, y, z);
			
			if (te != null && te instanceof ISource) {
				
				((ISource)te).setSPower(0);
				
				if(random.nextInt(5) < 1)
					world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			}
			if (te != null && te instanceof IConsumer) {
				
				((IConsumer)te).setPower(0);
				
				if(random.nextInt(5) < 1)
					world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			}
			if (te != null && te instanceof IEnergyProvider) {

				((IEnergyProvider)te).extractEnergy(ForgeDirection.UP, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.UP), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.DOWN, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.DOWN), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.NORTH, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.NORTH), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.SOUTH, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.SOUTH), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.EAST, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.EAST), false);
				((IEnergyProvider)te).extractEnergy(ForgeDirection.WEST, ((IEnergyProvider)te).getEnergyStored(ForgeDirection.WEST), false);
				
				if(random.nextInt(5) <= 1)
					world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
			}
			if (te != null && te instanceof TileEntityTurretBase) {

				((TileEntityTurretBase)te).ammo = 0;
			}
			if((b == ModBlocks.fusion_conductor || b == ModBlocks.fwatz_conductor || b == ModBlocks.fusion_motor || b == ModBlocks.fusion_heater || b == ModBlocks.fwatz_computer) && random.nextInt(10) == 0)
				world.setBlock(x, y, z, ModBlocks.block_electrical_scrap);
		}
	}
	*/
	public static void solinium(World world, BlockPos pos) {
		if (!world.isRemote) {
			IBlockState b = world.getBlockState(pos);
			Material m = b.getMaterial();
			
			if(b.getBlock() == Blocks.GRASS || b.getBlock() == Blocks.MYCELIUM || b.getBlock() == ModBlocks.waste_earth || b.getBlock() == ModBlocks.waste_mycelium) {
				world.setBlockState(pos, Blocks.DIRT.getDefaultState());
				return;
			}
			
			if(m == Material.CACTUS || m == Material.CORAL || m == Material.LEAVES || m == Material.PLANTS || m == Material.SPONGE || m == Material.VINE || m == Material.GOURD || m == Material.WOOD) {
				world.setBlockToAir(pos);
			}
		}
	}
}
