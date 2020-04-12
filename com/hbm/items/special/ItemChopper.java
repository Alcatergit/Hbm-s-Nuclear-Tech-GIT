package com.hbm.items.special;

import java.util.List;

import javax.annotation.Nullable;

import com.hbm.entity.mob.EntityHunterChopper;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemChopper extends Item {

	public ItemChopper(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModItems.ALL_ITEMS.add(this);
	}

	//Drillgon200: Code from ItemMonsterPlacer, because that looks like what was done in the one from 1.7.10
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemstack = player.getHeldItem(hand);

		if(worldIn.isRemote) {
			return EnumActionResult.SUCCESS;
		} else if(!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
			return EnumActionResult.FAIL;
		} else {
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if(block == Blocks.MOB_SPAWNER) {
				TileEntity tileentity = worldIn.getTileEntity(pos);

				if(tileentity instanceof TileEntityMobSpawner) {
					MobSpawnerBaseLogic mobspawnerbaselogic = ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic();
					mobspawnerbaselogic.setEntityId(new ResourceLocation(RefStrings.MODID, "entity_hunter_chopper"));
					tileentity.markDirty();
					worldIn.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);

					if(!player.capabilities.isCreativeMode) {
						itemstack.shrink(1);
					}

					return EnumActionResult.SUCCESS;
				}
			}

			BlockPos blockpos = pos.offset(facing);
			double d0 = this.getYOffset(worldIn, blockpos);
			Entity entity = spawnCreature(worldIn, null, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + d0, (double) blockpos.getZ() + 0.5D);

			if(entity != null) {
				if(entity instanceof EntityLivingBase && itemstack.hasDisplayName()) {
					entity.setCustomNameTag(itemstack.getDisplayName());
				}


				if(!player.capabilities.isCreativeMode) {
					itemstack.shrink(1);
				}
			}

			return EnumActionResult.SUCCESS;
		}
	}
	
	protected double getYOffset(World p_190909_1_, BlockPos p_190909_2_)
    {
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(p_190909_2_)).expand(0.0D, -1.0D, 0.0D);
        List<AxisAlignedBB> list = p_190909_1_.getCollisionBoxes((Entity)null, axisalignedbb);

        if (list.isEmpty())
        {
            return 0.0D;
        }
        else
        {
            double d0 = axisalignedbb.minY;

            for (AxisAlignedBB axisalignedbb1 : list)
            {
                d0 = Math.max(axisalignedbb1.maxY, d0);
            }

            return d0 - (double)p_190909_2_.getY();
        }
    }

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if(worldIn.isRemote) {
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		} else {
			RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

			if(raytraceresult != null && raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos blockpos = raytraceresult.getBlockPos();

				if(!(worldIn.getBlockState(blockpos).getBlock() instanceof BlockLiquid)) {
					return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
				} else if(worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, raytraceresult.sideHit, itemstack)) {
					Entity entity = spawnCreature(worldIn, null, (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D, (double) blockpos.getZ() + 0.5D);

					if(entity == null) {
						return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
					} else {
						if(entity instanceof EntityLivingBase && itemstack.hasDisplayName()) {
							entity.setCustomNameTag(itemstack.getDisplayName());
						}


						if(!playerIn.capabilities.isCreativeMode) {
							itemstack.shrink(1);
						}

						playerIn.addStat(StatList.getObjectUseStats(this));
						return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
					}
				} else {
					return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
				}
			} else {
				return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
			}
		}
	}

	public static Entity spawnCreature(World worldIn, @Nullable ResourceLocation entityID, double x, double y, double z) {

		Entity entity = null;

		for(int i = 0; i < 1; ++i) {
			entity = new EntityHunterChopper(worldIn);

			if(entity instanceof EntityLiving) {
				EntityLiving entityliving = (EntityLiving) entity;
				entity.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
				entityliving.rotationYawHead = entityliving.rotationYaw;
				entityliving.renderYawOffset = entityliving.rotationYaw;
				entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData) null);
				worldIn.spawnEntity(entity);
				entityliving.playLivingSound();
			}
		}

		return entity;

	}
}
