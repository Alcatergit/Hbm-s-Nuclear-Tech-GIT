package com.hbm.tileentity.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCell;
import com.hbm.items.tool.ItemFluidCanister;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityNukeCustom extends TileEntity implements ITickable {

	public ItemStackHandler inventory;
	private String customName;
	public float tntStrength;
	public float nukeStrength;
	public float hydroStrength;
	public float amatStrength;
	public float dirtyStrength;
	public float schrabStrength;
	public float euphStrength;
	public boolean falls;
	
	public TileEntityNukeCustom() {
		inventory = new ItemStackHandler(27){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		tntStrength = 0;
		nukeStrength = 0;
		hydroStrength = 0;
		amatStrength = 0;
		dirtyStrength = 0;
		schrabStrength = 0;
		euphStrength = 0;
		falls = false;
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.nukeCustom";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		this.tntStrength = 0;
		this.nukeStrength = 0;
		this.hydroStrength = 0;
		this.amatStrength = 0;
		this.dirtyStrength = 0;
		this.schrabStrength = 0;
		this.euphStrength = 0;
		
		for(int i = 0; i < inventory.getSlots(); i++) {
			if(!inventory.getStackInSlot(i).isEmpty()) {
				setValues(inventory.getStackInSlot(i));
				setMultipliers(inventory.getStackInSlot(i));
			}
		}

		if(this.nukeStrength > 0 && this.tntStrength < 16)
			this.nukeStrength = 0;
		if(this.hydroStrength > 0 && this.nukeStrength < 100)
			this.hydroStrength = 0;
		if(this.amatStrength > 0 && this.nukeStrength < 15)
			this.amatStrength = 0;
		if(this.dirtyStrength > 0 && this.nukeStrength == 0)
			this.dirtyStrength = 0;
		if(this.schrabStrength > 0 && this.nukeStrength < 50)
			this.schrabStrength = 0;
		if(this.euphStrength > 0 && this.schrabStrength == 0)
			this.euphStrength = 0;
	}
	
	public void setValues(ItemStack stack) {
		Item item = stack.getItem();
		
		for(int i = 0; i < stack.getCount(); i++) {
			if(item == Items.GUNPOWDER) {
				this.tntStrength += 0.8F;
			}
			if(item == Item.getItemFromBlock(Blocks.TNT)) {
				this.tntStrength += 4;
			}
			if(item == Item.getItemFromBlock(ModBlocks.det_cord)) {
				this.tntStrength += 1.5F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.det_charge)) {
				this.tntStrength += 15F;
			}
			if(ItemFluidCanister.isFullCanister(stack, ModForgeFluids.diesel)) {
				this.tntStrength += 0.3F;
			}
			if(ItemFluidCanister.isFullCanister(stack, ModForgeFluids.diesel)) {
				this.tntStrength += 0.5F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.red_barrel)) {
				this.tntStrength += 2.5F;
			}
			if(item == ModItems.gun_immolator_ammo) {
				this.tntStrength += 0.055F;
			}
			if(item == ModItems.clip_immolator) {
				this.tntStrength += 3.5F;
			}

			if(item == ModItems.custom_tnt) {
				this.tntStrength += 10F;
			}
			//
			if(item == ModItems.ingot_u235) {
				this.nukeStrength += 15F;
			}
			if(item == ModItems.ingot_pu239) {
				this.nukeStrength += 25F;
			}
			if(item == ModItems.ingot_neptunium) {
				this.nukeStrength += 30F;
			}
			if(item == ModItems.nugget_u235) {
				this.nukeStrength += 1.5F;
			}
			if(item == ModItems.nugget_pu239) {
				this.nukeStrength += 2.5F;
			}
			if(item == ModItems.nugget_neptunium) {
				this.nukeStrength += 3.0F;
			}
			if(item == ModItems.powder_neptunium) {
				this.nukeStrength += 30F;
			}
			
			if(item == ModItems.custom_nuke) {
				this.nukeStrength += 30F;
			}
			//
			if(ItemCell.isFullCell(stack, ModForgeFluids.deuterium)) {
				this.hydroStrength += 20F;
			}
			if(ItemCell.isFullCell(stack, ModForgeFluids.tritium)) {
				this.hydroStrength += 30F;
			}
			if(item == ModItems.lithium) {
				this.hydroStrength += 20F;
			}
			if(item == ModItems.tritium_deuterium_cake) {
				this.hydroStrength += 200F;
			}
			
			if(item == ModItems.custom_hydro) {
				this.hydroStrength += 30F;
			}
			//
			if(ItemCell.isFullCell(stack, ModForgeFluids.amat)) {
				this.amatStrength += 5F;
			}
			
			if(item == ModItems.custom_amat) {
				this.amatStrength += 15F;
			}
			//
			if(item == ModItems.ingot_tungsten) {
				this.dirtyStrength += 10F;
			}
			if(item == ModItems.nuclear_waste) {
				this.dirtyStrength += 2.5F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.yellow_barrel)) {
				this.dirtyStrength += 20F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.block_waste)) {
				this.dirtyStrength += 25F;
			}
			
			if(item == ModItems.custom_dirty) {
				this.dirtyStrength += 10F;
			}
			//
			if(item == ModItems.ingot_schrabidium) {
				this.schrabStrength += 5F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.block_schrabidium)) {
				this.schrabStrength += 50F;
			}
			if(item == ModItems.plate_schrabidium) {
				this.schrabStrength += 1.25F;
			}
			if(item == ModItems.nugget_schrabidium) {
				this.schrabStrength += 0.5F;
			}
			if(ItemCell.isFullCell(stack, ModForgeFluids.sas3)) {
				this.schrabStrength += 7.5F;
			}
			if(ItemCell.isFullCell(stack, ModForgeFluids.aschrab)) {
				this.schrabStrength += 15F;
			}
			
			if(item == ModItems.custom_schrab) {
				this.schrabStrength += 15F;
			}
			//
			if(item == ModItems.nugget_euphemium) {
				this.euphStrength += 1F;
			}
			if(item == ModItems.ingot_euphemium) {
				this.euphStrength += 1F;
			}
			
			if(item == ModItems.custom_fall) {
				this.falls = true;
			}
		}
	}
	
	public void setMultipliers(ItemStack stack) {
		
		Item item = stack.getItem();
		
		for(int i = 0; i < stack.getCount(); i++) {
			if(item == Items.REDSTONE) {
				this.tntStrength *= 1.005F;
			}
			if(item == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK)) {
				this.tntStrength *= 1.05F;
			}
			if(ItemFluidCanister.isFullCanister(stack, ModForgeFluids.diesel)) {
				this.tntStrength *= 1.025F;
			}
			if(item == ModItems.canister_napalm) {
				this.tntStrength *= 1.035F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.red_barrel)) {
				this.tntStrength *= 1.2F;
			}
			if(item == ModItems.gun_immolator_ammo) {
				this.tntStrength *= 1.0004F;
			}
			if(item == ModItems.clip_immolator) {
				this.tntStrength *= 1.025F;
			}
			//
			if(item == ModItems.ingot_u238) {
				this.nukeStrength *= 1.1F;
				this.hydroStrength *= 1.1F;
				this.dirtyStrength *= 1.1F;
			}
			if(item == ModItems.ingot_pu238) {
				this.nukeStrength *= 1.25F;
			}
			if(item == ModItems.ingot_pu240) {
				this.nukeStrength *= 1.05F;
				this.dirtyStrength *= 1.15F;
			}
			if(item == ModItems.ingot_neptunium) {
				this.nukeStrength *= 1.35F;
				this.dirtyStrength *= 1.15F;
			}
			if(item == ModItems.nugget_u238) {
				this.nukeStrength *= 1.01F;
				this.hydroStrength *= 1.01F;
			}
			if(item == ModItems.nugget_pu238) {
				this.nukeStrength *= 1.025F;
			}
			if(item == ModItems.nugget_pu240) {
				this.nukeStrength *= 1.005F;
				this.dirtyStrength *= 1.015F;
			}
			if(item == ModItems.nugget_neptunium) {
				this.nukeStrength *= 1.035F;
				this.dirtyStrength *= 1.015F;
			}
			if(item == ModItems.powder_neptunium) {
				this.nukeStrength *= 1.35F;
				this.dirtyStrength *= 1.15F;
			}
			if(item == ModItems.ingot_uranium) {
				this.nukeStrength *= 1.085F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.block_uranium)) {
				this.nukeStrength *= 1.85F;
			}
			if(item == ModItems.ingot_plutonium) {
				this.nukeStrength *= 1.075F;
			}
			if(item == ModItems.nugget_uranium) {
				this.nukeStrength *= 1.0085F;
			}
			if(item == ModItems.nugget_plutonium) {
				this.nukeStrength *= 1.0075F;
			}
			if(item == ModItems.powder_uranium) {
				this.nukeStrength *= 1.085F;
				this.dirtyStrength *= 1.15F;
			}
			if(item == ModItems.powder_plutonium) {
				this.nukeStrength *= 1.075F;
				this.dirtyStrength *= 1.15F;
			}
			//
			if(ItemCell.isFullCell(stack, ModForgeFluids.amat)) {
				this.amatStrength *= 1.1F;
			}
			//
			if(item == ModItems.nuclear_waste) {
				this.dirtyStrength *= 1.05F;
			}
			if(item == Item.getItemFromBlock(ModBlocks.yellow_barrel)) {
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
				this.dirtyStrength *= 1.05F;
			}
		}
	}
	
	public boolean isReady() {
		if(this.tntStrength > 0)
		{
			return true;
		}
		
		return false;
	}
	
	public float[] returnAllValues() {
		return new float[] { this.tntStrength, this.nukeStrength, this.hydroStrength, this.amatStrength, this.dirtyStrength, this.schrabStrength, this.euphStrength };
	}
	
	public void clearSlots() {
		for(int i = 0; i < inventory.getSlots(); i++)
		{
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}
}
