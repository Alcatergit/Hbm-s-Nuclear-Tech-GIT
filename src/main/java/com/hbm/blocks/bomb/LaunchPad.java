package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.missile.*;
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
import org.apache.logging.log4j.Level;

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
		if (worldIn.getStrongPower(pos) > 0 && !worldIn.isRemote) {
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
	public BombReturnCode explode(World world, BlockPos pos) {
		TileEntityLaunchPad entity = (TileEntityLaunchPad) world.getTileEntity(pos);
		if(entity.clearingTimer > 0) return BombReturnCode.UNDEFINED;

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
					EntityMissileTier1.EntityMissileGeneric missile = new EntityMissileTier1.EntityMissileGeneric(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 1.5D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_incendiary && entity.power >= 75000) {
					EntityMissileTier1.EntityMissileIncendiary missile = new EntityMissileTier1.EntityMissileIncendiary(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 1.5D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_cluster && entity.power >= 75000) {
					EntityMissileTier1.EntityMissileCluster missile = new EntityMissileTier1.EntityMissileCluster(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 1.5D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_buster && entity.power >= 75000) {
					EntityMissileTier1.EntityMissileBunkerBuster missile = new EntityMissileTier1.EntityMissileBunkerBuster(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 1.5D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_strong && entity.power >= 75000) {
					EntityMissileTier2.EntityMissileStrong missile = new EntityMissileTier2.EntityMissileStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 1.25D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_incendiary_strong && entity.power >= 75000) {
					EntityMissileTier2.EntityMissileIncendiaryStrong missile = new EntityMissileTier2.EntityMissileIncendiaryStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 1.25D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_cluster_strong && entity.power >= 75000) {
					EntityMissileTier2.EntityMissileClusterStrong missile = new EntityMissileTier2.EntityMissileClusterStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 1.25D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_buster_strong && entity.power >= 75000) {
					EntityMissileTier2.EntityMissileBusterStrong missile = new EntityMissileTier2.EntityMissileBusterStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 1.25D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_burst && entity.power >= 75000) {
					EntityMissileTier3.EntityMissileBurst missile = new EntityMissileTier3.EntityMissileBurst(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_inferno && entity.power >= 75000) {
					EntityMissileTier3.EntityMissileInferno missile = new EntityMissileTier3.EntityMissileInferno(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_rain && entity.power >= 75000) {
					EntityMissileTier3.EntityMissileRain missile = new EntityMissileTier3.EntityMissileRain(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_drill && entity.power >= 75000) {
					EntityMissileTier3.EntityMissileDrill missile = new EntityMissileTier3.EntityMissileDrill(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_nuclear && entity.power >= 75000) {
					EntityMissileTier4.EntityMissileNuclear missile = new EntityMissileTier4.EntityMissileNuclear(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 0.8D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_n2 && entity.power >= 75000) {
					EntityMissileTier4.EntityMissileN2 missile = new EntityMissileTier4.EntityMissileN2(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 0.8D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_endo && entity.power >= 75000) {
					EntityMissileTier3.EntityMissileEndo missile = new EntityMissileTier3.EntityMissileEndo(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 0.8D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_exo && entity.power >= 75000) {
					EntityMissileTier3.EntityMissileExo missile = new EntityMissileTier3.EntityMissileExo(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 0.8D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_nuclear_cluster && entity.power >= 75000) {
					EntityMissileTier4.EntityMissileMirv missile = new EntityMissileTier4.EntityMissileMirv(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 0.8D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_doomsday && entity.power >= 75000) {
					EntityMissileTier4.EntityMissileDoomsday missile = new EntityMissileTier4.EntityMissileDoomsday(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 0.5D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_taint && entity.power >= 75000) {
					EntityMissileTier0.EntityMissileTaint missile = new EntityMissileTier0.EntityMissileTaint(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 2.0D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_micro && entity.power >= 75000) {
					EntityMissileTier0.EntityMissileMicro missile = new EntityMissileTier0.EntityMissileMicro(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 2.0D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_bhole && entity.power >= 75000) {
					EntityMissileTier0.EntityMissileBHole missile = new EntityMissileTier0.EntityMissileBHole(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 2.0D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_schrabidium && entity.power >= 75000) {
					EntityMissileTier0.EntityMissileSchrabidium missile = new EntityMissileTier0.EntityMissileSchrabidium(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 2.0D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_emp && entity.power >= 75000) {
					EntityMissileTier0.EntityMissileEMP missile = new EntityMissileTier0.EntityMissileEMP(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 2.0D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if (entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_emp_strong && entity.power >= 75000) {
					EntityMissileTier2.EntityMissileEMPStrong missile = new EntityMissileTier2.EntityMissileEMPStrong(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 1.25D;
					if (!world.isRemote)
						world.spawnEntity(missile);
					entity.power -= 75000;

					entity.inventory.setStackInSlot(0, ItemStack.EMPTY);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.missileTakeoff, SoundCategory.BLOCKS, 2.0F, 1.0F);
					entity.clearingTimer = TileEntityLaunchPad.clearingDuraction;
				}
				if(entity.inventory.getStackInSlot(0).getItem() == ModItems.missile_volcano) {
					EntityMissileTier4.EntityMissileVolcano missile = new EntityMissileTier4.EntityMissileVolcano(world, x + 0.5F, y + 1.5F, z + 0.5F, xCoord, zCoord);
					missile.accelXZ = 0.8D;
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

					if (entity.inventory.getStackInSlot(1) != ItemStack.EMPTY)
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
		return BombReturnCode.LAUNCHED;
	}

}
