package com.hbm.inventory.leafia;

import com.hbm.config.BombConfig;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ohno.ItemMissileCustomNuke;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MissileCustomNukeContainer extends Container {
	NBTTagCompound nbt;
	ItemStackHandler inventory;
	public MissileCustomNukeContainer(InventoryPlayer invPlayer, ItemStack stack) {
		nbt = stack.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		inventory = new ItemStackHandler(27) {
			@Override
			protected void onContentsChanged(int slot) {
				nbt.setTag("inventory",inventory.serializeNBT());
				float tnt = 0F,		tntMod = 1F;
				float nuke = 0F,	nukeMod = 1F;
				float hydro = 0F,	hydroMod = 1F;
				float bale = 0F,	baleMod = 1F;
				float dirty = 0F,	dirtyMod = 1F;
				float schrab = 0F,	schrabMod = 1F;
				float sol = 0F,		solMod = 1F;
				float euph = 0F;

				for(int i = 0; i < inventory.getSlots(); i ++) {
					ItemStack stack = inventory.getStackInSlot(i);
					if(stack.isEmpty())
						continue;

					RecipesCommon.ComparableStack comp = new RecipesCommon.NbtComparableStack(stack).makeSingular();
					TileEntityNukeCustom.CustomNukeEntry ent = TileEntityNukeCustom.entries.get(comp);

					if(ent == null)
						continue;

					if(ent.entry == TileEntityNukeCustom.EnumEntryType.ADD) {

						switch(ent.type) {
							case TNT: tnt += ent.value; break;
							case NUKE: nuke += ent.value; break;
							case HYDRO: hydro += ent.value; break;
							case BALE: bale += ent.value; break;
							case DIRTY: dirty += ent.value; break;
							case SCHRAB: schrab += ent.value; break;
							case SOL: sol += ent.value; break;
							case EUPH: euph += ent.value; break;
						}

					} else if(ent.entry == TileEntityNukeCustom.EnumEntryType.MULT) {

						switch(ent.type) {
							case TNT: tntMod *= ent.value; break;
							case NUKE: nukeMod *= ent.value; break;
							case HYDRO: hydroMod *= ent.value; break;
							case BALE: baleMod *= ent.value; break;
							case DIRTY: dirtyMod *= ent.value; break;
							case SOL: solMod *= ent.value; break;
							case SCHRAB: schrabMod *= ent.value; break;
						}
					}
				}
				tnt *= tntMod;
				nuke *= nukeMod;
				hydro *= hydroMod;
				bale *= baleMod;
				dirty *= dirtyMod;
				sol *= solMod;
				schrab *= schrabMod;

				if(tnt < 16) nuke = 0;
				if(nuke < 100) hydro = 0;
				if(nuke < 50) bale = 0;
				if(nuke < 50) schrab = 0;
				if(nuke < 25) sol = 0;
				if(schrab < 1 || sol < 1) euph = 0;

				nbt.setFloat("tnt",Math.min(tnt,BombConfig.maxCustomTNTRadius));
				nbt.setFloat("nuke",Math.min(nuke,BombConfig.maxCustomNukeRadius));
				nbt.setFloat("hydro",Math.min(hydro,BombConfig.maxCustomHydroRadius));
				nbt.setFloat("bale",Math.min(bale,BombConfig.maxCustomBaleRadius));
				nbt.setFloat("dirty",Math.min(dirty,BombConfig.maxCustomDirtyRadius));
				nbt.setFloat("schrab",Math.min(schrab,BombConfig.maxCustomSchrabRadius));
				nbt.setFloat("sol",Math.min(sol,BombConfig.maxCustomSolRadius));
				nbt.setFloat("euph",Math.min(euph,BombConfig.maxCustomEuphLvl));
				super.onContentsChanged(slot);
			}
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				validateSlotIndex(slot);
				ItemStack extracting = stacks.get(slot);
				if (extracting.isEmpty()) return ItemStack.EMPTY;
				return (slot == ItemMissileCustomNuke.lastSlotEw) ? ItemStack.EMPTY : super.extractItem(slot,amount,simulate);
			}
		};
		if (nbt.hasKey("inventory"))
			inventory.deserializeNBT(nbt.getCompoundTag("inventory"));

		this.addSlotToContainer(new SlotItemHandler(inventory, 0, 8, 18));
		this.addSlotToContainer(new SlotItemHandler(inventory, 1, 26, 18));
		this.addSlotToContainer(new SlotItemHandler(inventory, 2, 44, 18));
		this.addSlotToContainer(new SlotItemHandler(inventory, 3, 62, 18));
		this.addSlotToContainer(new SlotItemHandler(inventory, 4, 80, 18));
		this.addSlotToContainer(new SlotItemHandler(inventory, 5, 98, 18));
		this.addSlotToContainer(new SlotItemHandler(inventory, 6, 116, 18));
		this.addSlotToContainer(new SlotItemHandler(inventory, 7, 134, 18));
		this.addSlotToContainer(new SlotItemHandler(inventory, 8, 152, 18));
		this.addSlotToContainer(new SlotItemHandler(inventory, 9, 8, 36));
		this.addSlotToContainer(new SlotItemHandler(inventory, 10, 26, 36));
		this.addSlotToContainer(new SlotItemHandler(inventory, 11, 44, 36));
		this.addSlotToContainer(new SlotItemHandler(inventory, 12, 62, 36));
		this.addSlotToContainer(new SlotItemHandler(inventory, 13, 80, 36));
		this.addSlotToContainer(new SlotItemHandler(inventory, 14, 98, 36));
		this.addSlotToContainer(new SlotItemHandler(inventory, 15, 116, 36));
		this.addSlotToContainer(new SlotItemHandler(inventory, 16, 134, 36));
		this.addSlotToContainer(new SlotItemHandler(inventory, 17, 152, 36));
		this.addSlotToContainer(new SlotItemHandler(inventory, 18, 8, 54));
		this.addSlotToContainer(new SlotItemHandler(inventory, 19, 26, 54));
		this.addSlotToContainer(new SlotItemHandler(inventory, 20, 44, 54));
		this.addSlotToContainer(new SlotItemHandler(inventory, 21, 62, 54));
		this.addSlotToContainer(new SlotItemHandler(inventory, 22, 80, 54));
		this.addSlotToContainer(new SlotItemHandler(inventory, 23, 98, 54));
		this.addSlotToContainer(new SlotItemHandler(inventory, 24, 116, 54));
		this.addSlotToContainer(new SlotItemHandler(inventory, 25, 134, 54));
		this.addSlotToContainer(new SlotItemHandler(inventory, 26, 152, 54));
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 56));
			}
		}
		
		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142 + 56) {
				@Override
				public boolean canTakeStack(EntityPlayer player) { // bruh
					return (this.getSlotIndex() != ItemMissileCustomNuke.lastSlotEw);
				}
			});
		}
	}
	
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);
		
		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			
            if (par2 <= 26) {
				if (!this.mergeItemStack(var5, 27, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			} else {
				if (!this.mergeItemStack(var5, 0, 27, true))
					return ItemStack.EMPTY;
			}
            
			if (var5.isEmpty())
			{
				var4.putStack(ItemStack.EMPTY);
			}
			else
			{
				var4.onSlotChanged();
			}
		}
		
		return var3;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}
}