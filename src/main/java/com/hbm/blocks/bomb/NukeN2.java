package com.hbm.blocks.bomb;

import java.util.List;

import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeN2;
import com.hbm.items.ModItems;

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

public class NukeN2 extends BlockNukeBase {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public NukeN2(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNukeN2();
	}

	@Override
	protected Item getBlockItem() {
		return Item.getItemFromBlock(ModBlocks.nuke_n2);
	}

	@Override
	protected Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityNukeN2.class;
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
			TileEntityNukeN2 entity = (TileEntityNukeN2) world.getTileEntity(pos);
			if(entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_n2, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		TileEntityNukeN2 entity = (TileEntityNukeN2) worldIn.getTileEntity(pos);
		if(worldIn.getRedstonePowerFromNeighbors(pos) > 0 && !worldIn.isRemote) {
			int charges = entity.countCharges();
			if(charges > 0) {
				// ========== Modified: Set detonation flag, then clear the block ==========
				this.isExploding = true;
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), (int)(BombConfig.n2Radius*charges/12F));
				this.isExploding = false;
			}
		}
	}
	
	public boolean igniteTestBomb(World world, int x, int y, int z, int r)
	{
		if (!world.isRemote)
		{
			world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.1F + 0.9F);

	    	world.spawnEntity(EntityNukeExplosionMK5.statFacNoRad(world, r, x + 0.5, y + 0.5, z + 0.5));
			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, x + 0.5, y + 0.5, z + 0.5, r);
			}
    	}
    	
		return false;
	}

	@Override
	public void explode(World world, BlockPos pos) {
		TileEntityNukeN2 entity = (TileEntityNukeN2) world.getTileEntity(pos);
		int charges = entity.countCharges();
		if(charges > 0) {
			// ========== Modified: Set detonation flag, then clear the block ==========
			this.isExploding = true;
			entity.clearSlots();
			world.setBlockToAir(pos);
			igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ(), (int)(BombConfig.n2Radius*charges/12F));
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
		tooltip.add("§c["+ I18nUtil.resolveKey("trait.extremebomb")+"]§r");
		tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", BombConfig.n2Radius)+"§r");
		// ========== Modified: Calculate and display blast radius based on contained items ==========
		int charges = countChargesFromItemStack(stack);
		if (charges > 0) {
			int explosionRadius = (int)(BombConfig.n2Radius * charges / 12F);
			tooltip.add(" §e"+("(Current radius: "+explosionRadius+")")+"§r");
		}
		tooltip.add("");
		tooltip.add("§e"+I18nUtil.resolveKey("desc.chargeadds", (int)(BombConfig.n2Radius/12))+"§r");

	}

	// ========== Added: Count n2_charge items from item NBT data ==========
	private int countChargesFromItemStack(ItemStack stack) {
		int charges = 0;
		
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				// Check if it contains the Items tag (the serialization format of ItemStackHandler)
				if (inventoryTag.hasKey("Items")) {
					NBTTagList itemsList = inventoryTag.getTagList("Items", 10);

					// Count n2_charge items
					for (int i = 0; i < itemsList.tagCount(); i++) {
						NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);

						// Check item ID (using registry name instead of string ID)
						String itemId = itemTag.getString("id");

						// Check if it is an n2_charge item
						if (itemId.equals(ModItems.n2_charge.getRegistryName().toString())) {
							// Get item count
							int count = itemTag.getByte("Count");
							charges += count;
						}
					}
				}
			}
		}
		
		return charges;
	}
}
