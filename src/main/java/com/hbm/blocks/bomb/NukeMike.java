package com.hbm.blocks.bomb;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeMike;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
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

public class NukeMike extends BlockNukeBase {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public NukeMike(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.nukeTab);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNukeMike();
	}

	@Override
	protected Item getBlockItem() {
		return Item.getItemFromBlock(ModBlocks.nuke_mike);
	}

	@Override
	protected Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityNukeMike.class;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityNukeMike entity = (TileEntityNukeMike) world.getTileEntity(pos);
			if(entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_mike, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		TileEntityNukeMike entity = (TileEntityNukeMike) worldIn.getTileEntity(pos);
		if(worldIn.getRedstonePowerFromNeighbors(pos) > 0 && !worldIn.isRemote) {
			if(entity.isReady() && !entity.isFilled()) {
				// ========== Modified: Set detonation flag, then clear the block ==========
				this.isExploding = true;
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.manRadius);
				this.isExploding = false;
			}

			if(entity.isFilled()) {
				// ========== Modified: Set detonation flag, then clear the block ==========
				this.isExploding = true;
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.mikeRadius);
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
	public void explode(World world, BlockPos pos) {
		TileEntityNukeMike entity = (TileEntityNukeMike) world.getTileEntity(pos);
		if(entity.isReady() && !entity.isFilled()) {
			// ========== Modified: Set detonation flag, then clear the block ==========
			this.isExploding = true;
			entity.clearSlots();
			world.setBlockToAir(pos);
			igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ(), BombConfig.manRadius);
			this.isExploding = false;
		}

		if(entity.isFilled()) {
			// ========== Modified: Set detonation flag, then clear the block ==========
			this.isExploding = true;
			entity.clearSlots();
			world.setBlockToAir(pos);
			igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ(), BombConfig.mikeRadius);
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
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§6["+ I18nUtil.resolveKey("trait.thermobomb")+"]"+"§r");
		tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", BombConfig.mikeRadius)+"§r");
		if(!BombConfig.disableNuclear){
			tooltip.add("§2["+ I18nUtil.resolveKey("trait.fallout")+"]"+"§r");
			tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)(BombConfig.mikeRadius*(1+BombConfig.falloutRange/100.0)))+"§r");
		}

		// ========== Modified: Adjusted tooltip display logic as required ==========
		// Display in priority order: isItemReady < isItemFilled
		if (isItemFilled(stack)) {
			tooltip.add("§c[Is ready]§r"); // Red when isItemFilled condition is true
		} else if (isItemReady(stack)) {
			tooltip.add("§2[Is ready]§r"); // Dark green when isItemReady condition is true
		}
	}

	// ========== Modified: Correctly parse the NBT format of ItemStackHandler ==========
	private boolean isItemReady(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				// Use the condition matching isReady directly
				return checkSlotItem(inventoryTag, 0, ModItems.man_explosive8) &&
					   checkSlotItem(inventoryTag, 1, ModItems.man_explosive8) &&
					   checkSlotItem(inventoryTag, 2, ModItems.man_explosive8) &&
					   checkSlotItem(inventoryTag, 3, ModItems.man_explosive8) &&
					   checkSlotItem(inventoryTag, 4, ModItems.man_core);
			}
		}
		
		return false;
	}

	// ========== Modified: Correctly parse the NBT format of ItemStackHandler ==========
	private boolean isItemFilled(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				// Use the condition matching isFilled directly
				return checkSlotItem(inventoryTag, 0, ModItems.man_explosive8) &&
					   checkSlotItem(inventoryTag, 1, ModItems.man_explosive8) &&
					   checkSlotItem(inventoryTag, 2, ModItems.man_explosive8) &&
					   checkSlotItem(inventoryTag, 3, ModItems.man_explosive8) &&
					   checkSlotItem(inventoryTag, 4, ModItems.man_core) &&
					   checkSlotItem(inventoryTag, 5, ModItems.mike_core) &&
					   checkSlotItem(inventoryTag, 6, ModItems.mike_deut) &&
					   checkSlotItem(inventoryTag, 7, ModItems.mike_cooling_unit);
			}
		}
		
		return false;
	}

	// ========== Modified: Improved NBT parsing method ==========
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
