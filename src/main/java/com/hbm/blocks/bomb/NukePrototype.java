package com.hbm.blocks.bomb;

import java.util.List;
import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.special.ItemCell;
import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukePrototype;

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

public class NukePrototype extends BlockContainer implements IBomb {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	// ========== Added: Field for storing information about players who have been vandalized ==========
	private EntityPlayer lastBreaker = null;
	// ========== Added: Mark whether the destruction was caused by an explosion ==========
	private boolean isExploding = false;
	
	public NukePrototype(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNukePrototype();
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

		return Item.getItemFromBlock(ModBlocks.nuke_prototype);
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
			if (tileentity instanceof TileEntityNukePrototype) {
				TileEntityNukePrototype nukePrototype = (TileEntityNukePrototype)tileentity;

				// Create NBT tags to store block entity data
				NBTTagCompound tileData = new NBTTagCompound();
				nukePrototype.writeToNBT(tileData);

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
					nukePrototype.clearSlots();
				} else if (!isCreativeMode) {
					// ========== Survival Mode Empty bomb: Drops a regular bomb =========
					spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this), 1));
				}
			}
		}
		// Creative Mode Empty Bomb: Nothing drops
		super.breakBlock(world, pos, state);
	}
	// ========== Modification complete ==========

	// ========== Modification: Restoring from simplified NBT data ==========
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));

		// ========== Edit: Recovering from Simplified NBT Data ==========
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			TileEntity tileentity = world.getTileEntity(pos);
			if (tileentity instanceof TileEntityNukePrototype) {
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
					((TileEntityNukePrototype) tileentity).readFromNBT(tileData);

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
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking() && player.getHeldItem(hand).getItem() == ModItems.igniter) {
			TileEntityNukePrototype entity = (TileEntityNukePrototype) world.getTileEntity(pos);
			if(entity.isReady())
			{
				// ========== Modification: Set a detonation flag, then clear the blocks ==========
				this.isExploding = true;
				entity.clearSlots();
				world.setBlockToAir(pos);
				igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ(), BombConfig.prototypeRadius);
				this.isExploding = false;
			}
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityNukePrototype entity = (TileEntityNukePrototype) world.getTileEntity(pos);
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_prototype, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		TileEntityNukePrototype entity = (TileEntityNukePrototype) worldIn.getTileEntity(pos);
        if (worldIn.getRedstonePowerFromNeighbors(pos) > 0 && !worldIn.isRemote)
        {
        	if(entity.isReady())
        	{
				// ========== Modification: Set a detonation flag, then clear the blocks ==========
				this.isExploding = true;
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.prototypeRadius);
				this.isExploding = false;
        	}
        }
	}
	
	public boolean igniteTestBomb(World world, int x, int y, int z, int r)
	{
		if (!world.isRemote)
		{
			world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.1F + 0.9F);
		
			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
			entity.posX = x;
    		entity.posY = y;
    		entity.posZ = z;
			if(!EntityNukeExplosionMK3.isJammed(world, entity)){
	    		entity.destructionRange = r;
	    		entity.speed = BombConfig.blastSpeed;
	    		entity.coefficient = 1.0F;
	    		entity.waste = false;
	    	
	    		world.spawnEntity(entity);
	    		
	    		EntityCloudFleija cloud = new EntityCloudFleija(world, r);
	    		cloud.posX = x;
	    		cloud.posY = y;
	    		cloud.posZ = z;
	    		world.spawnEntity(cloud);
	    	}
    	}
    	
		return false;
	}

	@Override
	public void explode(World world, BlockPos pos) {
		if(!(world.getTileEntity(pos) instanceof TileEntityNukePrototype))
			return;
		TileEntityNukePrototype entity = (TileEntityNukePrototype) world.getTileEntity(pos);
        //if (world.getStrongPower(x, y, z))
        {
        	if(entity.isReady())
        	{
				// ========== Modification: Set a detonation flag, then clear the blocks ==========
				this.isExploding = true;
				entity.clearSlots();
				world.setBlockToAir(pos);
				igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ(), BombConfig.prototypeRadius);
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
		tooltip.add("§b["+ I18nUtil.resolveKey("trait.schrabbomb")+"]§r");
		tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", BombConfig.prototypeRadius)+"§r");
		
		// ========== Modified: Check if the item's NBT data meets the detonation conditions ==========
		if (isItemReady(stack)) {
			tooltip.add("§2[Is ready]§r");
		}
	}
	
	// ========== Modified: Use the specific conditional judgment corresponding to entity.isReady ==========
	private boolean isItemReady(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");
				
				// Check if the NBT data contains the Items tag.
				if (inventoryTag.hasKey("Items")) {
					NBTTagList itemsList = inventoryTag.getTagList("Items", 10);
					
					// Create item slot mapping
					ItemStack[] slots = new ItemStack[14];
					for (int i = 0; i < itemsList.tagCount(); i++) {
						NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);
						int slot = itemTag.getByte("Slot") & 255;
						if (slot >= 0 && slot < 14) {
							slots[slot] = new ItemStack(itemTag);
						}
					}
					
					//========== Specific conditional checks using entity.isReady ==========
					// Check slots 0 and 1: SAS3 is full cells.
					boolean slot0Ready = slots[0] != null && ItemCell.isFullCell(slots[0], ModForgeFluids.SAS3);
					boolean slot1Ready = slots[1] != null && ItemCell.isFullCell(slots[1], ModForgeFluids.SAS3);
					
					// Check slots 2 and 3: Uranium quadruple rods
					boolean slot2Ready = slots[2] != null && slots[2].getItem() == ModItems.rod_quad_uranium;
					boolean slot3Ready = slots[3] != null && slots[3].getItem() == ModItems.rod_quad_uranium;
					
					// Check slots 4 and 5: Lead quadruple bars
					boolean slot4Ready = slots[4] != null && slots[4].getItem() == ModItems.rod_quad_lead;
					boolean slot5Ready = slots[5] != null && slots[5].getItem() == ModItems.rod_quad_lead;
					
					// Check slots 6 and 7: neptunium quadruple rod
					boolean slot6Ready = slots[6] != null && slots[6].getItem() == ModItems.rod_quad_neptunium;
					boolean slot7Ready = slots[7] != null && slots[7].getItem() == ModItems.rod_quad_neptunium;
					
					// Check slots 8 and 9: Lead quadruple bars
					boolean slot8Ready = slots[8] != null && slots[8].getItem() == ModItems.rod_quad_lead;
					boolean slot9Ready = slots[9] != null && slots[9].getItem() == ModItems.rod_quad_lead;
					
					// Check slots 10 and 11: Uranium quadruple rods
					boolean slot10Ready = slots[10] != null && slots[10].getItem() == ModItems.rod_quad_uranium;
					boolean slot11Ready = slots[11] != null && slots[11].getItem() == ModItems.rod_quad_uranium;
					
					// Check slots 12 and 13: SAS3 is full cells.
					boolean slot12Ready = slots[12] != null && ItemCell.isFullCell(slots[12], ModForgeFluids.SAS3);
					boolean slot13Ready = slots[13] != null && ItemCell.isFullCell(slots[13], ModForgeFluids.SAS3);
					
					// Return whether all conditions are met.
					return slot0Ready && slot1Ready && slot2Ready && slot3Ready && 
						   slot4Ready && slot5Ready && slot6Ready && slot7Ready && 
						   slot8Ready && slot9Ready && slot10Ready && slot11Ready && 
						   slot12Ready && slot13Ready;
				}
			}
		}
		
		return false;
	}
}
