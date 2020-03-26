package com.hbm.items.special;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemDrop extends Item {

	public ItemDrop(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if(entityItem != null) {

			ItemStack stack = entityItem.getItem();

			if(stack.getItem() != null && stack.getItem() == ModItems.detonator_deadman) {
				if(!entityItem.world.isRemote) {

					if(stack.getTagCompound() != null) {

						int x = stack.getTagCompound().getInteger("x");
						int y = stack.getTagCompound().getInteger("y");
						int z = stack.getTagCompound().getInteger("z");

						if(entityItem.world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof IBomb) {
							if(!entityItem.world.isRemote) {
								((IBomb) entityItem.world.getBlockState(new BlockPos(x, y, z)).getBlock()).explode(entityItem.world, new BlockPos(x, y, z));

								if(MainRegistry.enableExtendedLogging)
									MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by dead man's switch!");
							}
						}
					}

					entityItem.world.createExplosion(entityItem, entityItem.posX, entityItem.posY, entityItem.posZ, 0.0F, true);
					entityItem.setDead();
				}
			}
			if(stack.getItem() != null && stack.getItem() == ModItems.detonator_de) {
				if(!entityItem.world.isRemote) {
					entityItem.world.createExplosion(entityItem, entityItem.posX, entityItem.posY, entityItem.posZ, 15.0F, true);

					if(MainRegistry.enableExtendedLogging)
						MainRegistry.logger.log(Level.INFO, "[DET] Detonated dead man's explosive at " + ((int) entityItem.posX) + " / " + ((int) entityItem.posY) + " / " + ((int) entityItem.posZ) + "!");
				}

				entityItem.setDead();
			}

			if(entityItem.onGround) {

				if(stack.getItem() != null && stack.getItem() == ModItems.pellet_antimatter) {
					if(!entityItem.world.isRemote) {
						ExplosionLarge.explodeFire(entityItem.world, entityItem.posX, entityItem.posY, entityItem.posZ, 100, true, true, true);
					}
				}
				if(stack.getItem() != null && stack.getItem() == ModItems.singularity) {
					if(!entityItem.world.isRemote) {

						EntityVortex bl = new EntityVortex(entityItem.world, 1.5F);
						bl.posX = entityItem.posX;
						bl.posY = entityItem.posY;
						bl.posZ = entityItem.posZ;
						entityItem.world.spawnEntity(bl);
					}
				}
				if(stack.getItem() != null && stack.getItem() == ModItems.singularity_counter_resonant) {
					if(!entityItem.world.isRemote) {

						EntityVortex bl = new EntityVortex(entityItem.world, 2.5F);
						bl.posX = entityItem.posX;
						bl.posY = entityItem.posY;
						bl.posZ = entityItem.posZ;
						entityItem.world.spawnEntity(bl);
					}
				}
				if(stack.getItem() != null && stack.getItem() == ModItems.singularity_super_heated) {
					if(!entityItem.world.isRemote) {

						EntityVortex bl = new EntityVortex(entityItem.world, 2.5F);
						bl.posX = entityItem.posX;
						bl.posY = entityItem.posY;
						bl.posZ = entityItem.posZ;
						entityItem.world.spawnEntity(bl);
					}
				}
				if(stack.getItem() != null && stack.getItem() == ModItems.black_hole) {
					if(!entityItem.world.isRemote) {
						/*entityItem.world.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ,
								"random.explode", 100.0f, entityItem.world.rand.nextFloat() * 0.1F + 0.9F);
						
						EntityNukeExplosionAdvanced entity = new EntityNukeExplosionAdvanced(entityItem.world);
						entity.posX = entityItem.posX;
						entity.posY = entityItem.posY;
						entity.posZ = entityItem.posZ;
						entity.destructionRange = MainRegistry.aSchrabRadius * 3;
						entity.speed = 25;
						entity.coefficient = 0.01F;
						entity.coefficient2 = 0.01F;
						entity.waste = false;
						
						entityItem.world.spawnEntityInWorld(entity);*/

						EntityBlackHole bl = new EntityBlackHole(entityItem.world, 1.5F);
						bl.posX = entityItem.posX;
						bl.posY = entityItem.posY;
						bl.posZ = entityItem.posZ;
						entityItem.world.spawnEntity(bl);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.singularity_spark) {
					if (!entityItem.world.isRemote) {
					EntityRagingVortex bl = new EntityRagingVortex(entityItem.world, 3.5F);
					bl.posX = entityItem.posX ;
					bl.posY = entityItem.posY ;
					bl.posZ = entityItem.posZ ;
					entityItem.world.spawnEntity(bl);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.crystal_xen) {
					if (!entityItem.world.isRemote) {
						ExplosionChaos.floater(entityItem.world, (int)entityItem.posX, (int)entityItem.posY, (int)entityItem.posZ, 25, 75);
						ExplosionChaos.move(entityItem.world, (int)entityItem.posX, (int)entityItem.posY, (int)entityItem.posZ, 25, 0, 75, 0);
					}
				}

				entityItem.setDead();
				return true;
			}
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == ModItems.pellet_antimatter) {
			tooltip.add("Very heavy antimatter cluster.");
			tooltip.add("Gets rid of black holes.");
		}
		if(this == ModItems.singularity) {
			tooltip.add("You may be asking:");
			tooltip.add("\"But HBM, a manifold with an undefined");
			tooltip.add("state of spacetime? How is this possible?\"");
			tooltip.add("Long answer short:");
			tooltip.add("\"I have no idea!\"");
		}
		if(this == ModItems.singularity_counter_resonant) {
			tooltip.add("Nullifies resonance of objects in");
			tooltip.add("non-euclidean space, creates variable");
			tooltip.add("gravity well. Spontaneously spawns");
			tooltip.add("tesseracts. If a tesseract happens to");
			tooltip.add("appear near you, do not look directly");
			tooltip.add("at it.");
		}
		if(this == ModItems.singularity_super_heated) {
			tooltip.add("Continuously heats up matter by");
			tooltip.add("resonating every planck second.");
			tooltip.add("Tends to catch fire or to create");
			tooltip.add("small plamsa arcs. Not edible.");
		}
		if(this == ModItems.black_hole) {
			tooltip.add("Contains a regular singularity");
			tooltip.add("in the center. Large enough to");
			tooltip.add("stay stable. It's not the end");
			tooltip.add("of the world as we know it,");
			tooltip.add("and I don't feel fine.");
		}
		if(this == ModItems.detonator_deadman) {
			tooltip.add("Shift right-click to set position,");
			tooltip.add("drop to detonate!");
			if(stack.getTagCompound() == null) {
				tooltip.add("No position set!");
			} else {
				tooltip.add("Set pos to " + stack.getTagCompound().getInteger("x") + ", " + stack.getTagCompound().getInteger("y") + ", " + stack.getTagCompound().getInteger("z"));
			}
		}
		if(this == ModItems.detonator_de) {
			tooltip.add("Explodes when dropped!");
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if(this != ModItems.detonator_deadman) {
			return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
		}

		if(stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}

		if(player.isSneaking()) {
			stack.getTagCompound().setInteger("x", pos.getX());
			stack.getTagCompound().setInteger("y", pos.getY());
			stack.getTagCompound().setInteger("z", pos.getZ());

			if(world.isRemote) {
				player.sendMessage(new TextComponentTranslation("Position set!"));
			}

			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBoop, SoundCategory.PLAYERS, 2.0F, 1.0F);

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.PASS;
	}
}
