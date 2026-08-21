package com.hbm.blocks.bomb;

import java.util.List;
import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NukeGadget extends BlockContainer implements IBomb {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	// ========== Added: Field for storing information about players who have been vandalized ==========
	private boolean isExploding = false;
	// ========== Added: Mark whether the destruction was caused by an explosion ==========
	private EntityPlayer lastBreaker = null;

	public NukeGadget(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNukeGadget();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		// ========== Modification: Adding drop control logic upon detonation ==========
		// If the destruction is caused by an explosion, no items will be dropped.
		if (isExploding) {
			return null;
		}

		// When NBT saving is enabled, the drop is completely controlled by breakBlock.
		if (GeneralConfig.enableBlockItemNBTSaving) {
			return null; // Returning null, the fall is controlled by breakBlock.
		}
		// ========== Modification complete ==========
		
		return Item.getItemFromBlock(ModBlocks.nuke_gadget);
	}

	// ========== Added: Override the removedByPlayer method to get the destroyed player ==========
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		// Recording and destroying player information
		this.lastBreaker = player;

		// Calling the parent class method to continue executing the disruptive logic
		boolean result = super.removedByPlayer(state, world, pos, player, willHarvest);

		// Clean up player information
		this.lastBreaker = null;

		return result;
	}

	// ========== Modification: Using player information recorded by removedByPlayer ==========
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		// ========== If the destruction is caused by an explosion, the entire block will be cleared, and no items will be dropped. ==========
		if (isExploding) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity != null) {
				world.removeTileEntity(pos);
			}
			return;
		}

		TileEntity tileentity = world.getTileEntity(pos);

		// ========== New Logic: Configuration File Controls NBT Saving ==========
		if (!GeneralConfig.enableBlockItemNBTSaving) {
			// Configure NBT saving is disabled; use the original logic.
			InventoryHelper.dropInventoryItems(world, pos, world.getTileEntity(pos));
		} else {
			if (tileentity instanceof TileEntityNukeGadget) {
				TileEntityNukeGadget nukeGadget = (TileEntityNukeGadget)tileentity;

				// Create NBT tags to store block entity data
				NBTTagCompound tileData = new NBTTagCompound();
				nukeGadget.writeToNBT(tileData);

				// ========== Check for any items inside ==========
				boolean hasItems = false;
				if (tileData.hasKey("inventory") && tileData.getCompoundTag("inventory").hasKey("Items")) {
					NBTTagList itemsList = tileData.getCompoundTag("inventory").getTagList("Items", 10);
					hasItems = itemsList.tagCount() > 0;
				}

				// ========== Player information recorded using removedByPlayer ==========
				boolean isCreativeMode = (lastBreaker != null && lastBreaker.capabilities.isCreativeMode);

				if (hasItems) {
					// ========== Internal Items Found: Drops a nuclear bomb containing NBT ==========
					ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this), 1);
					NBTTagCompound nbttagcompound = new NBTTagCompound();

					// ========== Simplify NBT data: Only keep BlockEntityTag->inventory->Items ==========
					NBTTagCompound blockEntityTag = new NBTTagCompound();
					NBTTagCompound inventoryTag = new NBTTagCompound();

					if (tileData.hasKey("inventory") && tileData.getCompoundTag("inventory").hasKey("Items")) {
						// Copy only the Items tag and all its child tags.
						NBTTagList items = tileData.getCompoundTag("inventory").getTagList("Items", 10).copy();
						inventoryTag.setTag("Items", items);
					}

					blockEntityTag.setTag("inventory", inventoryTag);

					// Write the BlockEntityTag to the item NBT
					nbttagcompound.setTag("BlockEntityTag", blockEntityTag);
					itemstack.setTagCompound(nbttagcompound);

					// Generate drops
					spawnAsEntity(world, pos, itemstack);

					// Empty the contents
					nukeGadget.clearSlots();
				} else if (!isCreativeMode) {
					// ========== Survival Mode Empty bomb: Drops a regular bomb =========
					spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this), 1));
				}
				// Creative Mode Empty Bomb: Nothing drops
			}
		}
		// Call the parent class's breakBlock but don't let it handle the falling object.
		super.breakBlock(world, pos, state);
	}
	// ========== Modification complete ==========

	// ========== Modification: onBlockPlacedBy method - Support for data recovery from simplified NBT ==========
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));

		// ========== Edit: Recovering from Simplified NBT Data ==========
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof TileEntityNukeGadget) {
				NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

				if (blockEntityTag.hasKey("inventory")) {
					NBTTagCompound savedInventory = blockEntityTag.getCompoundTag("inventory");

					// Create complete TileEntity NBT data
					NBTTagCompound tileData = new NBTTagCompound();
					tileData.setInteger("x", pos.getX());
					tileData.setInteger("y", pos.getY());
					tileData.setInteger("z", pos.getZ());

					// ========== Restore only Items data in inventory ==========
					NBTTagCompound newInventory = new NBTTagCompound();
					if (savedInventory.hasKey("Items")) {
						newInventory.setTag("Items", savedInventory.getTagList("Items", 10).copy());
					}

					tileData.setTag("inventory", newInventory);

					// Loading data from NBT to block entities
					((TileEntityNukeGadget) tileentity).readFromNBT(tileData);

					// The marker blocks need to be updated.
					world.notifyBlockUpdate(pos, state, state, 3);

					// Important: Mark block entities as dirty data to ensure data preservation.
					tileentity.markDirty();
				}
			}
		}
	}
	// ========== Modification complete ==========
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else if (!player.isSneaking()) {
			TileEntityNukeGadget entity = (TileEntityNukeGadget) world.getTileEntity(pos);
			if (entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_gadget, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		TileEntityNukeGadget entity = (TileEntityNukeGadget) worldIn.getTileEntity(pos);
		if (worldIn.getRedstonePowerFromNeighbors(pos) > 0 && !worldIn.isRemote) {
			if (entity.isReady()) {
				// ========== Modification: Set a detonation flag, then clear the blocks ==========
				this.isExploding = true;
				entity.clearSlots();
				worldIn.setBlockToAir(pos);

				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ());
				this.isExploding = false;
			}
		}
	}
	
	public boolean igniteTestBomb(World world, int x, int y, int z) {
		if (!world.isRemote) {
			
			world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.1F + 0.9F); // x,y,z,sound,volume,pitch
			
	    	world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.gadgetRadius, x + 0.5, y + 0.5, z + 0.5));
			if (BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, x + 0.5, y + 0.5, z + 0.5, BombConfig.gadgetRadius);
			}
		}

		return false;
	}

	@Override
	public void explode(World world, BlockPos pos) {
		TileEntityNukeGadget entity = (TileEntityNukeGadget) world.getTileEntity(pos);
		// if (p_149695_1_.getStrongPower(x, y, z))
		{
			if (entity.isReady()) {
				// ========== Modification: Set a detonation flag, then clear the blocks ==========
				this.isExploding = true;
				entity.clearSlots();
				world.setBlockToAir(pos);

				igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ());
				this.isExploding = false;
			}
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
	   return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§2["+ I18nUtil.resolveKey("trait.nuclearbomb")+"]"+"§r");
		tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", BombConfig.gadgetRadius)+"§r");
		if(!BombConfig.disableNuclear){
			tooltip.add("§2["+ I18nUtil.resolveKey("trait.fallout")+"]"+"§r");
			tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)BombConfig.gadgetRadius*(1+BombConfig.falloutRange/100))+"§r");
		}

		// ========== Modification: Use the condition corresponding to entity.isReady for judgment ==========
		if (isItemReady(stack)) {
			tooltip.add("§2[Is ready]§r");
		}
	}

	// ========== Modification: Correctly parsing the NBT format of ItemStackHandler ==========
	private boolean isItemReady(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");
				
				// Check if it contains the Items tag (the serialization format of ItemStackHandler).
				if (inventoryTag.hasKey("Items")) {
					NBTTagList itemsList = inventoryTag.getTagList("Items", 10);
					
					// Check if the required items are included.
					boolean hasWiring = false;
					boolean hasCore = false;
					boolean hasExplosive1 = false;
					boolean hasExplosive2 = false;
					boolean hasExplosive3 = false;
					boolean hasExplosive4 = false;
					
					for (int i = 0; i < itemsList.tagCount(); i++) {
						NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);
						int slot = itemTag.getByte("Slot");
						
						// Check the item ID (use the registered name instead of the string ID).
						String itemId = itemTag.getString("id");
						
						// Check the corresponding items according to the slot.
						if (slot == 0 && itemId.equals(ModItems.gadget_wireing.getRegistryName().toString())) {
							hasWiring = true;
						} else if (slot == 5 && itemId.equals(ModItems.gadget_core.getRegistryName().toString())) {
							hasCore = true;
						} else if (slot == 1 && itemId.equals(ModItems.gadget_explosive8.getRegistryName().toString())) {
							hasExplosive1 = true;
						} else if (slot == 2 && itemId.equals(ModItems.gadget_explosive8.getRegistryName().toString())) {
							hasExplosive2 = true;
						} else if (slot == 3 && itemId.equals(ModItems.gadget_explosive8.getRegistryName().toString())) {
							hasExplosive3 = true;
						} else if (slot == 4 && itemId.equals(ModItems.gadget_explosive8.getRegistryName().toString())) {
							hasExplosive4 = true;
						}
					}
					
					// Return whether the isReady condition is met.
					return hasWiring && hasCore && hasExplosive1 && hasExplosive2 && hasExplosive3 && hasExplosive4;
				}
			}
		}
		
		return false;
	}
	// ========== Modification complete ==========
}
