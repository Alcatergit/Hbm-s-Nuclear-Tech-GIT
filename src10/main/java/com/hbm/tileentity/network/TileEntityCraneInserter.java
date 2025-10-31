package com.hbm.tileentity.network;

import com.hbm.lib.Library;
import com.hbm.inventory.container.ContainerCraneInserter;
import com.hbm.inventory.gui.GUICraneInserter;
import com.hbm.tileentity.IGUIProvider;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCraneInserter extends TileEntityCraneBase implements IGUIProvider {

    public TileEntityCraneInserter() {
        super(21);
    }

    @Override
    public String getName() {
        return "container.craneInserter";
    }

    @Override
    public void update() {
        super.update();
        if(!world.isRemote) {
            tryFillTe();
        }
    }

    public void tryFillTe(){
        EnumFacing outputSide = getOutputSide();
        TileEntity te = world.getTileEntity(pos.offset(outputSide));

        if(te != null){
            if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputSide)) {
                IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputSide);
            
                for(int i = 0; i < inventory.getSlots(); i++) {
                    tryFillContainerCap(cap, i);
                }
            }
        }
    }

    public boolean tryFillTeDirect(ItemStack stack){
        return tryInsertItemCap(inventory, stack);
    }

    //Unloads output into chests. Capability version.
    public boolean tryFillContainerCap(IItemHandler chest, int slot) {
        //Check if we have something to output
        if(inventory.getStackInSlot(slot).isEmpty())
            return false;

        return tryInsertItemCap(chest, inventory.getStackInSlot(slot));
    }

    //Unloads output into chests. Capability version.
    public boolean tryInsertItemCap(IItemHandler chest, ItemStack stack) {
        //Check if we have something to output
        if(stack.isEmpty())
            return false;

        for(int i = 0; i < chest.getSlots(); i++) {

            ItemStack outputStack = stack.copy();
            if(outputStack.isEmpty() || outputStack.getCount() == 0)
                return true;

            ItemStack chestItem = chest.getStackInSlot(i).copy();
            if(chestItem.isEmpty() || (Library.areItemStacksCompatible(outputStack, chestItem, false) && chestItem.getCount() < chestItem.getMaxStackSize())) {
                int fillAmount = Math.min(chestItem.getMaxStackSize()-chestItem.getCount(), outputStack.getCount());

                outputStack.setCount(fillAmount);

                ItemStack rest = chest.insertItem(i, outputStack, true);
                if(rest.getCount() < outputStack.getCount()){
                    stack.shrink(outputStack.getCount()-rest.getCount());
                    chest.insertItem(i, outputStack, false);
                }
            }
        }

        return false;
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerCraneInserter(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUICraneInserter(player.inventory, this);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(EnumFacing e) {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 ,17, 18, 19, 20};
    }
}
