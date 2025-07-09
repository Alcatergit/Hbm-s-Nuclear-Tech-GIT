package com.hbm.blocks.bomb;

import org.apache.logging.log4j.Level;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.missile.EntityCarrier;
import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBHole;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileBurst;
import com.hbm.entity.missile.EntityMissileBusterStrong;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileClusterStrong;
import com.hbm.entity.missile.EntityMissileDoomsday;
import com.hbm.entity.missile.EntityMissileDrill;
import com.hbm.entity.missile.EntityMissileEMP;
import com.hbm.entity.missile.EntityMissileEMPStrong;
import com.hbm.entity.missile.EntityMissileEndo;
import com.hbm.entity.missile.EntityMissileExo;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.entity.missile.EntityMissileIncendiaryStrong;
import com.hbm.entity.missile.EntityMissileInferno;
import com.hbm.entity.missile.EntityMissileMicro;
import com.hbm.entity.missile.EntityMissileMirv;
import com.hbm.entity.missile.EntityMissileNuclear;
import com.hbm.entity.missile.EntityMissileN2;
import com.hbm.entity.missile.EntityMissileRain;
import com.hbm.entity.missile.EntityMissileSchrabidium;
import com.hbm.entity.missile.EntityMissileStrong;
import com.hbm.entity.missile.EntityMissileTaint;
import com.hbm.entity.missile.EntityMissileVolcano;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LaunchPad extends BlockContainer implements IBomb {

	public LaunchPad(Material materialIn, String s) {
		super(materialIn);
		this.setRegistryName(s);
		this.setTranslationKey(s);
		this.setCreativeTab(MainRegistry.missileTab);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLaunchPad();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else if (!playerIn.isSneaking()) {
			TileEntityLaunchPad entity = (TileEntityLaunchPad) worldIn.getTileEntity(pos);
			if (entity != null) {
				playerIn.openGui(MainRegistry.instance, ModBlocks.guiID_launch_pad, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn.getRedstonePowerFromNeighbors(pos) > 0 && !worldIn.isRemote) {
			this.explode(worldIn, pos);
		}
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public void explode(World world, BlockPos pos) {
		TileEntityLaunchPad entity = (TileEntityLaunchPad) world.getTileEntity(pos);
		if(entity.clearingTimer > 0) return;

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		{
			if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_anti_ballistic || entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_carrier || ((entity.inventory.getStackInSlot(1).getItem() == ModItems.designator || entity.inventory.getStackInSlot(1).getItem() == ModItems.designator_range || entity.inventory.getStackInSlot(1).getItem() == ModItems.designator_manual) && entity.inventory.getStackInSlot(1).getTagCompound() != null)) {
				int xCoord = entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_anti_ballistic || entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_carrier ? 0 : entity.inventory.getStackInSlot(1).getTagCompound().getInteger("xCoord");
				int zCoord = entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_anti_ballistic || entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_carrier ? 0 : entity.inventory.getStackInSlot(1).getTagCompound().getInteger("zCoord");

				if (xCoord == entity.getPos().getX() && zCoord == entity.getPos().getZ()) {
					xCoord += 1;
				}

				if (GeneralConfig.enableExtendedLogging)
					MainRegistry.logger.log(Level.INFO, "[MISSILE] Tried to launch missile at " + x + " / " + y + " / " + z + " to " + xCoord + " / " + zCoord + "!");

				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_generic && entity.power >= 75000) {
					// EntityMissileGeneric missile = new
					// EntityMissileGeneric(world, xCoord, zCoord, x + 0.5F, y +
					// 2F, z + 0.5F);
					EntityMissileGeneric missile = new EntityMissileGeneric(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(1.5D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_incendiary && entity.power >= 75000) {
					EntityMissileIncendiary missile = new EntityMissileIncendiary(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(1.5D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_cluster && entity.power >= 75000) {
					EntityMissileCluster missile = new EntityMissileCluster(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(1.5D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_buster && entity.power >= 75000) {
					EntityMissileBunkerBuster missile = new EntityMissileBunkerBuster(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(1.5D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_strong && entity.power >= 75000) {
					EntityMissileStrong missile = new EntityMissileStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(1.25D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_incendiary_strong && entity.power >= 75000) {
					EntityMissileIncendiaryStrong missile = new EntityMissileIncendiaryStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(1.25D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_cluster_strong && entity.power >= 75000) {
					EntityMissileClusterStrong missile = new EntityMissileClusterStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(1.25D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_buster_strong && entity.power >= 75000) {
					EntityMissileBusterStrong missile = new EntityMissileBusterStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(1.25D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_burst && entity.power >= 75000) {
					EntityMissileBurst missile = new EntityMissileBurst(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_inferno && entity.power >= 75000) {
					EntityMissileInferno missile = new EntityMissileInferno(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_rain && entity.power >= 75000) {
					EntityMissileRain missile = new EntityMissileRain(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_drill && entity.power >= 75000) {
					EntityMissileDrill missile = new EntityMissileDrill(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_nuclear && entity.power >= 75000) {
					EntityMissileNuclear missile = new EntityMissileNuclear(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(0.8D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_n2 && entity.power >= 75000) {
					EntityMissileN2 missile = new EntityMissileN2(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(0.8D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_endo && entity.power >= 75000) {
					EntityMissileEndo missile = new EntityMissileEndo(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(0.8D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_exo && entity.power >= 75000) {
					EntityMissileExo missile = new EntityMissileExo(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(0.8D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_nuclear_cluster && entity.power >= 75000) {
					EntityMissileMirv missile = new EntityMissileMirv(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(0.8D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_doomsday && entity.power >= 75000) {
					EntityMissileDoomsday missile = new EntityMissileDoomsday(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(0.5D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_taint && entity.power >= 75000) {
					EntityMissileTaint missile = new EntityMissileTaint(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(2.0D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_micro && entity.power >= 75000) {
					EntityMissileMicro missile = new EntityMissileMicro(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(2.0D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_bhole && entity.power >= 75000) {
					EntityMissileBHole missile = new EntityMissileBHole(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(2.0D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_schrabidium && entity.power >= 75000) {
					EntityMissileSchrabidium missile = new EntityMissileSchrabidium(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(2.0D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_emp && entity.power >= 75000) {
					EntityMissileEMP missile = new EntityMissileEMP(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(2.0D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_emp_strong && entity.power >= 75000) {
					EntityMissileEMPStrong missile = new EntityMissileEMPStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(1.25D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if(entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_volcano) {
					EntityMissileVolcano missile = new EntityMissileVolcano(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.setAcceleration(0.8D);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}

				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_carrier && entity.power >= 75000) {
					EntityCarrier missile = new EntityCarrier(world);
					missile.posX = x + 0.5F;
					missile.posY = y + 1.5F;
					missile.posZ = z + 0.5F;

					if (entity.inventory.getStackInSlot(1) != null)
						missile.setPayload(entity.inventory.getStackInSlot(1));

					entity.inventory.setStackInSlot(1, ItemStack.EMPTY);

					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.rocketTakeoff, SoundCategory.BLOCKS, 100.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}

				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_anti_ballistic && entity.power >= 75000) {
					EntityMissileAntiBallistic missile = new EntityMissileAntiBallistic(world);
					missile.posX = x + 0.5F;
					missile.posY = y + 1.5F;
					missile.posZ = z + 0.5F;

					if (!world.isRemote)
						world.spawnEntity(missile);

					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
			}
		}
	}

}
