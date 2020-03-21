package com.hbm.items.special;

import java.util.List;

import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.items.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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

			/*if (stack.getItem() != null && stack.getItem() == ModItems.detonator_deadman) {
				if (!entityItem.world.isRemote) {
					
					if(stack.stackTagCompound != null) {
						
						 int x = stack.stackTagCompound.getInteger("x");
						 int y = stack.stackTagCompound.getInteger("y");
						 int z = stack.stackTagCompound.getInteger("z");
						 
						 if(entityItem.world.getBlock(x, y, z) instanceof IBomb)
						 {
							if(!entityItem.world.isRemote)
							{
								((IBomb)entityItem.world.getBlock(x, y, z)).explode(entityItem.world, x, y, z);
			
					    		if(MainRegistry.enableExtendedLogging)
					    			MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by dead man's switch!");
							}
						 }
					}
			
					entityItem.world.createExplosion(entityItem, entityItem.posX, entityItem.posY,
							entityItem.posZ, 0.0F, true);
					entityItem.setDead();
				}
			}
			if (stack.getItem() != null && stack.getItem() == ModItems.detonator_de) {
				if (!entityItem.world.isRemote) {
					entityItem.world.createExplosion(entityItem, entityItem.posX, entityItem.posY,
							entityItem.posZ, 15.0F, true);
			
					if(MainRegistry.enableExtendedLogging)
						MainRegistry.logger.log(Level.INFO, "[DET] Detonated dead man's explosive at " + ((int)entityItem.posX) + " / " + ((int)entityItem.posY) + " / " + ((int)entityItem.posZ) + "!");
				}
				
				entityItem.setDead();
			}
			
			*/if(entityItem.onGround) {/*
										
										if (stack.getItem() != null && stack.getItem() == ModItems.cell_antimatter) {
										if (!entityItem.world.isRemote) {
										entityItem.world.createExplosion(entityItem, entityItem.posX, entityItem.posY,
										entityItem.posZ, 10.0F, true);
										}
										}
										if (stack.getItem() != null && stack.getItem() == ModItems.pellet_antimatter) {
										if (!entityItem.world.isRemote) {
										ExplosionLarge.explodeFire(entityItem.world, entityItem.posX, entityItem.posY,
										entityItem.posZ, 100, true, true, true);
										}
										}
										if (stack.getItem() != null && stack.getItem() == ModItems.cell_anti_schrabidium) {
										if (!entityItem.world.isRemote) {
										entityItem.world.playSoundEffect(entityItem.posX, entityItem.posY, entityItem.posZ,
										"random.explode", 100.0f, entityItem.world.rand.nextFloat() * 0.1F + 0.9F);
										
										EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(entityItem.world);
										entity.posX = entityItem.posX;
										entity.posY = entityItem.posY;
										entity.posZ = entityItem.posZ;
										entity.destructionRange = MainRegistry.aSchrabRadius;
										entity.speed = 25;
										entity.coefficient = 1.0F;
										entity.waste = false;
										
										entityItem.world.spawnEntity(entity);
										
										EntityCloudFleija cloud = new EntityCloudFleija(entityItem.world, MainRegistry.aSchrabRadius);
										cloud.posX = entityItem.posX;
										cloud.posY = entityItem.posY;
										cloud.posZ = entityItem.posZ;
										entityItem.world.spawnEntity(cloud);
										}
										}*/
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
				/*if (stack.getItem() != null && stack.getItem() == ModItems.singularity_spark) {
					if (!entityItem.world.isRemote) {
					EntityRagingVortex bl = new EntityRagingVortex(entityItem.world, 3.5F);
					bl.posX = entityItem.posX ;
					bl.posY = entityItem.posY ;
					bl.posZ = entityItem.posZ ;
					entityItem.world.spawnEntityInWorld(bl);
					}
				}
				if (stack.getItem() != null && stack.getItem() == ModItems.crystal_xen) {
					if (!entityItem.world.isRemote) {
						ExplosionChaos.floater(entityItem.world, (int)entityItem.posX, (int)entityItem.posY, (int)entityItem.posZ, 25, 75);
						ExplosionChaos.move(entityItem.world, (int)entityItem.posX, (int)entityItem.posY, (int)entityItem.posZ, 25, 0, 75, 0);
					}
				}*/

				entityItem.setDead();
				return true;
			}
		}

		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		/*if (this == ModItems.cell_antimatter) {
			tooltip.add("Warning: Exposure to matter will");
			tooltip.add("lead to violent annihilation!");
		}
		if (this == ModItems.pellet_antimatter) {
			tooltip.add("Very heavy antimatter cluster.");
			tooltip.add("Gets rid of black holes.");
		}
		if (this == ModItems.cell_anti_schrabidium) {
			tooltip.add("Warning: Exposure to matter will");
			tooltip.add("create a fólkvangr field!");
		}*/
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
		/*if (this == ModItems.detonator_deadman) {
			tooltip.add("Shift right-click to set position,");
			tooltip.add("drop to detonate!");
			if(stack.getTagCompound() == null)
			{
				tooltip.add("No position set!");
			} else {
				tooltip.add("Set pos to " + stack.getTagCompound().getInteger("x") + ", " + stack.getTagCompound().getInteger("y") + ", " + stack.getTagCompound().getInteger("z"));
			}
		}
		if (this == ModItems.detonator_de) {
			tooltip.add("Explodes when dropped!");
		}*/
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		/*if(this != ModItems.detonator_deadman) {
			return super.onItemUse(stack, player, world, x, y, z, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
		}
		
		if(stack.stackTagCompound == null)
		{
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		if(player.isSneaking())
		{
			stack.stackTagCompound.setInteger("x", x);
			stack.stackTagCompound.setInteger("y", y);
			stack.stackTagCompound.setInteger("z", z);
			
			if(world.isRemote)
			{
				player.addChatMessage(new ChatComponentText("Position set!"));
			}
			
		    world.playSoundAtEntity(player, "hbm:item.techBoop", 2.0F, 1.0F);
			
			return true;
		}
		
		return false;*/
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
