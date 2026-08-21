package com.hbm.blocks.bomb;

import java.util.List;
import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeTsar;

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

public class NukeTsar extends BlockContainer implements IBomb {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	// ========== Added: Field for storing information about the player who last broke the block ==========
	private EntityPlayer lastBreaker = null;
	// ========== Added: Flag to mark if the destruction was caused by an explosion ==========
	private boolean isExploding = false;

	public NukeTsar(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.nukeTab);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNukeTsar();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		// ========== Modified: Added drop control logic for detonation ==========
		// If the block is destroyed by an explosion, drop nothing.
		if (isExploding) {
			return null;
		}

		// When NBT saving is enabled, drops are handled entirely by breakBlock.
		if (GeneralConfig.enableBlockItemNBTSaving) {
			return null; // Return null, drops are handled by breakBlock.
		}
		// ========== End of modification ==========

		return Item.getItemFromBlock(ModBlocks.nuke_tsar);
	}

	// ========== Added: Override removedByPlayer to capture the player who broke the block ==========
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		// Store the player who is breaking the block
		this.lastBreaker = player;

		// Call the super method to continue with the breaking logic
		boolean result = super.removedByPlayer(state, world, pos, player, willHarvest);

		// Clear the stored player information
		this.lastBreaker = null;

		return result;
	}

	// ========== Modified: Use player information captured by removedByPlayer ==========
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		// ========== If destroyed by an explosion, clear the tile entity and drop nothing ==========
		if (isExploding) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity != null) {
				world.removeTileEntity(pos);
			}
			return;
		}

		TileEntity tileentity = world.getTileEntity(pos);

		// ========== New: NBT saving controlled by configuration file ==========
		if (!GeneralConfig.enableBlockItemNBTSaving) {
			// NBT saving is disabled, use original logic.
			InventoryHelper.dropInventoryItems(world, pos, world.getTileEntity(pos));
		} else {
			if (tileentity instanceof TileEntityNukeTsar) {
				TileEntityNukeTsar nukeTsar = (TileEntityNukeTsar)tileentity;

				// Create NBT tag to store tile entity data
				NBTTagCompound tileData = new NBTTagCompound();
				nukeTsar.writeToNBT(tileData);

				// ========== Check if there are any items inside ==========
				boolean hasItems = false;
				if (tileData.hasKey("inventory") && tileData.getCompoundTag("inventory").hasKey("Items")) {
					NBTTagList itemsList = tileData.getCompoundTag("inventory").getTagList("Items", 10);
					hasItems = itemsList.tagCount() > 0;
				}

				// ========== Use player information captured by removedByPlayer ==========
				boolean isCreativeMode = (lastBreaker != null && lastBreaker.capabilities.isCreativeMode);

				if (hasItems) {
					// ========== Has items inside: Drop a nuke block with NBT data ==========
					ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this), 1);
					NBTTagCompound nbttagcompound = new NBTTagCompound();

					// ========== Simplify NBT data: Only keep BlockEntityTag -> inventory -> Items ==========
					NBTTagCompound blockEntityTag = new NBTTagCompound();
					NBTTagCompound inventoryTag = new NBTTagCompound();

					if (tileData.hasKey("inventory") && tileData.getCompoundTag("inventory").hasKey("Items")) {
						// Copy only the Items tag and all its child tags.
						NBTTagList items = tileData.getCompoundTag("inventory").getTagList("Items", 10).copy();
						inventoryTag.setTag("Items", items);
					}

					blockEntityTag.setTag("inventory", inventoryTag);

					// Write the BlockEntityTag to the item's NBT
					nbttagcompound.setTag("BlockEntityTag", blockEntityTag);
					itemstack.setTagCompound(nbttagcompound);

					// Spawn the item drop
					spawnAsEntity(world, pos, itemstack);

					// Clear the inventory contents
					nukeTsar.clearSlots();
				} else if (!isCreativeMode) {
					// ========== Survival mode, empty nuke: Drop a regular nuke block =========
					spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this), 1));
				}
				// Creative mode, empty nuke: Drop nothing
			}
		}
		// Call super.breakBlock but prevent it from handling drops
		super.breakBlock(world, pos, state);
	}
	// ========== End of modification ==========

	// ========== Modified: onBlockPlacedBy method - Support data recovery from simplified NBT ==========
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));

		// ========== Modified: Recover data from simplified NBT ==========
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof TileEntityNukeTsar) {
				NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

				if (blockEntityTag.hasKey("inventory")) {
					NBTTagCompound savedInventory = blockEntityTag.getCompoundTag("inventory");

					// Create complete tile entity NBT data
					NBTTagCompound tileData = new NBTTagCompound();
					tileData.setInteger("x", pos.getX());
					tileData.setInteger("y", pos.getY());
					tileData.setInteger("z", pos.getZ());

					// ========== Restore only the Items data in inventory ==========
					NBTTagCompound newInventory = new NBTTagCompound();
					if (savedInventory.hasKey("Items")) {
						newInventory.setTag("Items", savedInventory.getTagList("Items", 10).copy());
					}

					tileData.setTag("inventory", newInventory);

					// Load data from NBT into the tile entity
					((TileEntityNukeTsar) tileentity).readFromNBT(tileData);

					// Mark the block for update
					world.notifyBlockUpdate(pos, state, state, 3);

					// Mark tile entity as dirty to ensure data is saved
					tileentity.markDirty();
				}
			}
		}
	}
	// ========== End of modification ==========

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityNukeTsar entity = (TileEntityNukeTsar) world.getTileEntity(pos);
			if(entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_tsar, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		TileEntityNukeTsar entity = (TileEntityNukeTsar) worldIn.getTileEntity(pos);
		if(worldIn.getRedstonePowerFromNeighbors(pos) > 0 && !worldIn.isRemote) {
			boolean isReady = entity.isReady();
			boolean isStage1Filled = entity.isStage1Filled();
			boolean isStage2Filled = entity.isStage2Filled();
			boolean isStage3Filled = entity.isStage3Filled();
			if(isStage3Filled) {
				// ========== Modified: Set detonation flag, then clear the block ==========
				this.isExploding = true;
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.tsarRadius);
				this.isExploding = false;
			}else if(isStage1Filled) {
				this.isExploding = true;
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.tsarRadius/2);
				this.isExploding = false;
			}else if(isStage2Filled) {
				this.isExploding = true;
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.tsarRadius/3);
				this.isExploding = false;
			}else if(isReady) {
				this.isExploding = true;
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.tsarRadius/5);
				this.isExploding = false;
			}			
		}
	}

	public boolean igniteTestBomb(World world, int x, int y, int z, int r) {
		if(!world.isRemote) {
			world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.1F + 0.9F);

			world.spawnEntity(EntityNukeExplosionMK5.statFac(world, r, x + 0.5, y + 0.5, z + 0.5));
			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, x + 0.5, y + 0.5, z + 0.5, r);
			}
		}
		return false;
	}

	@Override
	public void explode(World worldIn, BlockPos pos) {
		TileEntityNukeTsar entity = (TileEntityNukeTsar) worldIn.getTileEntity(pos);
		boolean isReady = entity.isReady();
		boolean isStage1Filled = entity.isStage1Filled();
		boolean isStage2Filled = entity.isStage2Filled();
		boolean isStage3Filled = entity.isStage3Filled();
		if(isStage3Filled) {
			// ========== Modified: Set detonation flag, then clear the block ==========
			this.isExploding = true;
			entity.clearSlots();
			worldIn.setBlockToAir(pos);
			igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.tsarRadius);
			this.isExploding = false;
		}else if(isStage1Filled) {
			this.isExploding = true;
			entity.clearSlots();
			worldIn.setBlockToAir(pos);
			igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.tsarRadius/2);
			this.isExploding = false;
		}else if(isStage2Filled) {
			this.isExploding = true;
			entity.clearSlots();
			worldIn.setBlockToAir(pos);
			igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.tsarRadius/3);
			this.isExploding = false;
		}else if(isReady) {
			this.isExploding = true;
			entity.clearSlots();
			worldIn.setBlockToAir(pos);
			igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.tsarRadius/5);
			this.isExploding = false;
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
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§6["+ I18nUtil.resolveKey("trait.thermobomb")+"]"+"§r");
		tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", BombConfig.tsarRadius)+"§r");
		if(!BombConfig.disableNuclear){
			tooltip.add("§2["+ I18nUtil.resolveKey("trait.fallout")+"]"+"§r");
			tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)(BombConfig.tsarRadius*(1+BombConfig.falloutRange/100.0)))+"§r");
		}

		// ========== Modified: Adjusted tooltip display logic as required ==========
		// Display in priority order: isItemReady < isItemStage1Filled / isItemStage2Filled < isItemStage3Filled
		if (isItemStage3Filled(stack)) {
			tooltip.add("§c[Is ready]§r"); // Red when isItemStage3Filled condition is true
		} else if (isItemStage1Filled(stack) || isItemStage2Filled(stack)) {
			tooltip.add("§6[Is ready]§r"); // Gold when isItemStage1Filled or isItemStage2Filled condition is true
		} else if (isItemReady(stack)) {
			tooltip.add("§2[Is ready]§r"); // Dark green when isItemReady condition is true
		}
	}

	// ========== Modified: Use specific condition checks matching entity.isReady ==========
	private boolean isItemReady(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				// Use condition matching isReady directly: core and four lens slots
				return checkSlotItem(inventoryTag, 0, ModItems.man_core) && // Core slot
					   checkSlotItem(inventoryTag, 1, ModItems.man_explosive8) && // Top-left lens slot
					   checkSlotItem(inventoryTag, 2, ModItems.man_explosive8) && // Top-right lens slot
					   checkSlotItem(inventoryTag, 3, ModItems.man_explosive8) && // Bottom-left lens slot
					   checkSlotItem(inventoryTag, 4, ModItems.man_explosive8); // Bottom-right lens slot
			}
		}
		
		return false;
	}
	
	private boolean isItemStage1Filled(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				// Use condition matching isStage1Filled directly: isReady + first stage slots
				return isItemReady(stack) && // Base condition
					   checkSlotItem(inventoryTag, 5, ModItems.mike_core) && // First stage slot
					   checkSlotItem(inventoryTag, 6, ModItems.mike_deut); // First stage slot
			}
		}
		
		return false;
	}
	
	private boolean isItemStage2Filled(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				// Use condition matching isStage2Filled directly: isReady + second stage slots
				return isItemReady(stack) && // Base condition
					   checkSlotItem(inventoryTag, 7, ModItems.mike_core) && // Second stage slot
					   checkSlotItem(inventoryTag, 8, ModItems.mike_deut); // Second stage slot
			}
		}
		
		return false;
	}
	
	private boolean isItemStage3Filled(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				// Use condition matching isStage3Filled directly: isStage1Filled + second stage slots
				return isItemStage1Filled(stack) && // First stage condition
					   checkSlotItem(inventoryTag, 7, ModItems.mike_core) && // Second stage slot
					   checkSlotItem(inventoryTag, 8, ModItems.mike_deut); // Second stage slot
			}
		}
		
		return false;
	}

	// ========== Added: Helper method for checking item slots ==========
	private boolean checkSlotItem(NBTTagCompound inventoryTag, int slot, Item expectedItem) {
		if (inventoryTag.hasKey("Items")) {
			NBTTagList itemsList = inventoryTag.getTagList("Items", 10);
			
			for (int i = 0; i < itemsList.tagCount(); i++) {
				NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);
				if (itemTag.getByte("Slot") == slot) {
					// Use string registry name instead of numeric ID
					String itemId = itemTag.getString("id");

					// Check if items match
					return itemId.equals(expectedItem.getRegistryName().toString());
				}
			}
		}
		
		return false;
	}
}
