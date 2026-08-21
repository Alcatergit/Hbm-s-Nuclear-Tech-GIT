package com.hbm.blocks.bomb;

import java.util.List;

import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NukeFleija extends BlockNukeBase {

	public static final PropertyInteger FACING = PropertyInteger.create("facing", 2, 5);
	
	public NukeFleija(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, 2));
		this.setCreativeTab(MainRegistry.nukeTab);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNukeFleija();
	}

	@Override
	protected Item getBlockItem() {
		return Item.getItemFromBlock(ModBlocks.nuke_fleija);
	}

	@Override
	protected Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityNukeFleija.class;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		int i = MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if(i == 0) return this.getDefaultState().withProperty(FACING, 5);
		if(i == 1) return this.getDefaultState().withProperty(FACING, 3);
		if(i == 2) return this.getDefaultState().withProperty(FACING, 4);
		return this.getDefaultState().withProperty(FACING, 2);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityNukeFleija entity = (TileEntityNukeFleija) world.getTileEntity(pos);
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_fleija, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		
		TileEntityNukeFleija entity = (TileEntityNukeFleija) worldIn.getTileEntity(pos);
        if (worldIn.getRedstonePowerFromNeighbors(pos) > 0 && !worldIn.isRemote)
        {
        	if(entity.isReady())
        	{
				// ========== Modified: Set detonation flag, then clear the block ==========
        		this.isExploding = true;
        		entity.clearSlots();
            	worldIn.setBlockToAir(pos);
            	igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), BombConfig.fleijaRadius);
            	this.isExploding = false;
        	}
        }
	}
	
	public boolean igniteTestBomb(World world, int x, int y, int z, int r)
	{
		if (!world.isRemote)
		{
			//world.spawnParticle("hugeexplosion", x, y, z, 0, 0, 0);
			world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);
			
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
	public void explode(World world, BlockPos pos) {
		TileEntityNukeFleija entity = (TileEntityNukeFleija) world.getTileEntity(pos);
        //if (p_149695_1_.getStrongPower(x, y, z))
        {
        	if(entity.isReady())
        	{
				// ========== Modified: Set detonation flag, then clear the block ==========
        		this.isExploding = true;
        		entity.clearSlots();
            	world.setBlockToAir(pos);
            	igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ(), BombConfig.fleijaRadius);
            	this.isExploding = false;
        	}
        }
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta >= 2 && meta <=5)
			return this.getDefaultState().withProperty(FACING, meta);
		return this.getDefaultState().withProperty(FACING, 2);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§b["+ I18nUtil.resolveKey("trait.schrabbomb")+"]§r");
		tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", BombConfig.fleijaRadius)+"§r");

		// ========== Added: Check if the item's NBT data meets detonation conditions ==========
		if (isItemReady(stack)) {
			tooltip.add("§2[Is ready]§r");
		}
	}

	// ========== Added: Helper method based on TileEntityNukeFleija's isReady condition ==========
	private boolean isItemReady(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
			
			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				// Check if it contains the Items tag (the serialization format of ItemStackHandler)
				if (inventoryTag.hasKey("Items")) {
					NBTTagList itemsList = inventoryTag.getTagList("Items", 10);

					// Check if all items required by TileEntityNukeFleija's isReady method are present
					boolean hasIgniter1 = false;
					boolean hasIgniter2 = false;
					boolean hasPropellant1 = false;
					boolean hasPropellant2 = false;
					boolean hasPropellant3 = false;
					boolean hasCore1 = false;
					boolean hasCore2 = false;
					boolean hasCore3 = false;
					boolean hasCore4 = false;
					boolean hasCore5 = false;
					boolean hasCore6 = false;
					
					for (int i = 0; i < itemsList.tagCount(); i++) {
						NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);
						int slot = itemTag.getByte("Slot");

						// Check item ID (using registry name instead of string ID)
						String itemId = itemTag.getString("id");

						// Check corresponding item based on slot (following TileEntityNukeFleija's isReady method)
						if (slot == 0 && itemId.equals(ModItems.fleija_igniter.getRegistryName().toString())) {
							hasIgniter1 = true;
						} else if (slot == 1 && itemId.equals(ModItems.fleija_igniter.getRegistryName().toString())) {
							hasIgniter2 = true;
						} else if (slot == 2 && itemId.equals(ModItems.fleija_propellant.getRegistryName().toString())) {
							hasPropellant1 = true;
						} else if (slot == 3 && itemId.equals(ModItems.fleija_propellant.getRegistryName().toString())) {
							hasPropellant2 = true;
						} else if (slot == 4 && itemId.equals(ModItems.fleija_propellant.getRegistryName().toString())) {
							hasPropellant3 = true;
						} else if (slot == 5 && itemId.equals(ModItems.fleija_core.getRegistryName().toString())) {
							hasCore1 = true;
						} else if (slot == 6 && itemId.equals(ModItems.fleija_core.getRegistryName().toString())) {
							hasCore2 = true;
						} else if (slot == 7 && itemId.equals(ModItems.fleija_core.getRegistryName().toString())) {
							hasCore3 = true;
						} else if (slot == 8 && itemId.equals(ModItems.fleija_core.getRegistryName().toString())) {
							hasCore4 = true;
						} else if (slot == 9 && itemId.equals(ModItems.fleija_core.getRegistryName().toString())) {
							hasCore5 = true;
						} else if (slot == 10 && itemId.equals(ModItems.fleija_core.getRegistryName().toString())) {
							hasCore6 = true;
						}
					}

					// Return whether TileEntityNukeFleija's isReady condition is met
					return hasIgniter1 && hasIgniter2 && hasPropellant1 && hasPropellant2 && hasPropellant3 && 
						   hasCore1 && hasCore2 && hasCore3 && hasCore4 && hasCore5 && hasCore6;
				}
			}
		}
		
		return false;
	}
}
