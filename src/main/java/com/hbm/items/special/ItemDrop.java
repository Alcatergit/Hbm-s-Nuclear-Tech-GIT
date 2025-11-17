package com.hbm.items.special;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.config.WeaponConfig;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityRagingVortex;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemDrop extends Item {

	public ItemDrop(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if(entityItem != null) {
			if(this == ModItems.beta) {
				entityItem.setDead();
				return true;
			}

			ItemStack stack = entityItem.getItem();

			if(entityItem.onGround || entityItem.isBurning()) {
                if(!stack.isEmpty()) {

                    if (stack.getItem() == ModItems.pellet_antimatter && WeaponConfig.dropCell) {
                        if (!entityItem.world.isRemote) {
                            ExplosionLarge.explodeFire(entityItem.world, entityItem.posX, entityItem.posY, entityItem.posZ, 100, true, true, true);
                        }
                    }
                    if (stack.getItem() == ModItems.tiny_singularity && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {

                            EntityVortex bl = new EntityVortex(entityItem.world, 0.15F, 2);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.singularity && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {

                            EntityVortex bl = new EntityVortex(entityItem.world, 1.5F, 2);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.tiny_singularity_counter_resonant && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {

                            EntityVortex bl = new EntityVortex(entityItem.world, 0.25F, 1);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.singularity_counter_resonant && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {

                            EntityVortex bl = new EntityVortex(entityItem.world, 2.5F, 1);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.tiny_singularity_super_heated && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {

                            EntityVortex bl = new EntityVortex(entityItem.world, 0.25F, 3);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.singularity_super_heated && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {

                            EntityVortex bl = new EntityVortex(entityItem.world, 2.5F, 3);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.tiny_black_hole && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {

                            EntityBlackHole bl = new EntityBlackHole(entityItem.world, 0.15F);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.black_hole && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {

                            EntityBlackHole bl = new EntityBlackHole(entityItem.world, 1.5F);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.tiny_singularity_spark && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {
                            EntityRagingVortex bl = new EntityRagingVortex(entityItem.world, 0.35F);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.singularity_spark && WeaponConfig.dropSing) {
                        if (!entityItem.world.isRemote) {
                            EntityRagingVortex bl = new EntityRagingVortex(entityItem.world, 3.5F);
                            bl.posX = entityItem.posX;
                            bl.posY = entityItem.posY;
                            bl.posZ = entityItem.posZ;
                            entityItem.world.spawnEntity(bl);
                        }
                    }
                    if (stack.getItem() == ModItems.capsule_xen && WeaponConfig.dropCrys) {
                        if (!entityItem.world.isRemote) {
                            ExplosionChaos.floater(entityItem.world, (int) entityItem.posX, (int) entityItem.posY, (int) entityItem.posZ, 3, 8);
                            ExplosionChaos.move(entityItem.world, (int) entityItem.posX, (int) entityItem.posY, (int) entityItem.posZ, 3, 0, 8, 0);
                        }
                    }
                    if (stack.getItem() == ModItems.crystal_xen && WeaponConfig.dropCrys) {
                        if (!entityItem.world.isRemote) {
                            ExplosionChaos.floater(entityItem.world, (int) entityItem.posX, (int) entityItem.posY, (int) entityItem.posZ, 25, 75);
                            ExplosionChaos.move(entityItem.world, (int) entityItem.posX, (int) entityItem.posY, (int) entityItem.posZ, 25, 0, 75, 0);
                        }
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
		tooltip.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]");
	}
}
