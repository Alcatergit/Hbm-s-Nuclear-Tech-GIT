package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.InventoryHelper;
import com.hbm.lib.ForgeDirection;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMold;
import com.hbm.items.machine.ItemMold.Mold;
import com.hbm.items.machine.ItemScraps;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityFoundryCastingBase;
import com.hbm.util.I18nUtil;

import api.hbm.block.ICrucibleAcceptor;
import api.hbm.block.IToolable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public abstract class FoundryCastingBase extends BlockContainer implements ICrucibleAcceptor, IToolable, ILookOverlay {

	protected FoundryCastingBase(String s) {
		super(Material.ROCK);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setSoundType(SoundType.METAL);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	public double getPH(){ //particle height
		return 1;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}

	@Override
	public boolean canAcceptPartialPour(World world, BlockPos p, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) Objects.requireNonNull(world.getTileEntity(p))).canAcceptPartialPour(world, p, dX, dY, dZ, side, stack);
	}

	@Override
	public MaterialStack pour(World world, BlockPos p, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) Objects.requireNonNull(world.getTileEntity(p))).pour(world, p, dX, dY, dZ, side, stack);
	}

	@Override
	public boolean canAcceptPartialFlow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) Objects.requireNonNull(world.getTileEntity(p))).canAcceptPartialFlow(world, p, side, stack);
	}
	
	@Override
	public MaterialStack flow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) {
		return ((ICrucibleAcceptor) Objects.requireNonNull(world.getTileEntity(p))).flow(world, p, side, stack);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(world.isRemote) {
			return true;
		}
		
		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(pos);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		//remove casted item
		if(!Objects.requireNonNull(cast).inventory.getStackInSlot(1).isEmpty()) {
			if(!player.inventory.addItemStackToInventory(cast.inventory.getStackInSlot(1).copy())) {
				world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, cast.inventory.getStackInSlot(1).copy()));
			} else {
				player.inventoryContainer.detectAndSendChanges();
			}
			cast.inventory.setStackInSlot(1, ItemStack.EMPTY);
			cast.markDirty();
			world.markBlockRangeForRenderUpdate(pos, pos);
			return true;
		}
		
		//insert mold
		if(!player.getHeldItem(hand).isEmpty() && player.getHeldItem(hand).getItem() == ModItems.mold) {
			Mold mold = ((ItemMold) player.getHeldItem(hand).getItem()).getMold(player.getHeldItem(hand));
			
			if(mold.size == cast.getMoldSize()) {
				if(!cast.inventory.getStackInSlot(0).isEmpty()){
					ItemStack prevMold = cast.inventory.getStackInSlot(0);
					if(!player.inventory.addItemStackToInventory(prevMold)) {
						world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, prevMold));
					}
				}

				ItemStack m = player.getHeldItem(hand).copy();
				m.setCount(1);
				cast.inventory.setStackInSlot(0, m);
				player.getHeldItem(hand).shrink(1);
				world.playSound(null, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, HBMSoundHandler.upgradePlug, SoundCategory.BLOCKS, 1.5F, 1.0F);
				cast.markDirty();
				world.markBlockRangeForRenderUpdate(pos, pos);
				return true;
			}
		}
		//shovel scrap
		if(!player.getHeldItem(hand).isEmpty() && player.getHeldItem(hand).getItem() instanceof ItemTool && ((ItemTool) player.getHeldItem(hand).getItem()).getToolClasses(player.getHeldItem(hand)).contains("shovel")) {
			if(cast.amount > 0) {
				ItemStack scrap = ItemScraps.create(new MaterialStack(cast.type, cast.amount));
				if(!player.inventory.addItemStackToInventory(scrap)) {
					world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, scrap));
				} else {
					player.inventoryContainer.detectAndSendChanges();
				}
				cast.amount = 0;
				cast.type = null;
				cast.markDirty();
				world.markBlockRangeForRenderUpdate(pos, pos);
			}
			return true;
		}
		
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		
		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(pos);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if(Objects.requireNonNull(cast).amount > 0) {
			MainRegistry.logger.info("Foundry casting broken with " + cast.amount + " " + cast.type);
			ItemStack scrap = ItemScraps.create(new MaterialStack(cast.type, cast.amount));
			world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, scrap));
			cast.amount = 0; //just for safety
		}
		InventoryHelper.dropInventoryItems(world, pos, cast);
		
		super.breakBlock(world, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		super.randomDisplayTick(state, world, pos, rand);
		
		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(pos);

		if(Objects.requireNonNull(cast).amount > 0 && cast.amount >= cast.getCapacity()) {
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.25 + rand.nextDouble() * 0.5, pos.getY() + getPH(), pos.getZ() + 0.25 + rand.nextDouble() * 0.5, 0.0, 0.0, 0.0);
		}
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, EnumFacing side, float fX, float fY, float fZ, EnumHand hand, ToolType tool) {
		
		if(tool != ToolType.SCREWDRIVER)
			return false;
		
		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(new BlockPos(x, y, z));
		
		if(Objects.requireNonNull(cast).inventory.getStackInSlot(0).isEmpty()) return false;
		if(cast.amount > 0) return false;
		
		if(!player.inventory.addItemStackToInventory(cast.inventory.getStackInSlot(0).copy())) {
			world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, cast.inventory.getStackInSlot(0).copy()));
		} else {
			player.inventoryContainer.detectAndSendChanges();
		}
		
		cast.inventory.setStackInSlot(0, ItemStack.EMPTY);
		
		return true;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntityFoundryCastingBase cast = (TileEntityFoundryCastingBase) world.getTileEntity(new BlockPos(x, y, z));
		List<String> text = new ArrayList();
		
		if(cast.inventory.getStackInSlot(0).isEmpty()) {
			text.add("§c" + I18nUtil.resolveKey("foundry.noCast"));
		} else if(cast.inventory.getStackInSlot(0).getItem() == ModItems.mold){
			Mold mold = ((ItemMold) cast.inventory.getStackInSlot(0).getItem()).getMold(cast.inventory.getStackInSlot(0));
			text.add("§e" + mold.getTitle());
		}
		
		if(cast.type != null && cast.amount > 0) {
			text.add("&["+ cast.type.moltenColor +"&]"+ I18nUtil.resolveKey(cast.type.getTranslationKey()) + ": " + cast.amount + " / " + cast.getCapacity());
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(this.getTranslationKey() + ".name"), 0xFF4000, 0x401000, text);
	}
}
