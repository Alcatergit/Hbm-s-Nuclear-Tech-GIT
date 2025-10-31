package com.hbm.tileentity.network;

import com.hbm.lib.Library;
import com.hbm.blocks.network.CraneInserter;
import com.hbm.inventory.container.ContainerCraneInserter;
import com.hbm.inventory.gui.GUICraneInserter;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;

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

        int meta = this.getBlockMetadata();
        if(te != null){
            ICapabilityProvider capte = te;
            if(capte.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputSide)) {
                IItemHandler cap = capte.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputSide);
            
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
                if(rest.getItem() == Item.getItemFromBlock(Blocks.AIR)){
                    stack.shrink(outputStack.getCount());
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

}
