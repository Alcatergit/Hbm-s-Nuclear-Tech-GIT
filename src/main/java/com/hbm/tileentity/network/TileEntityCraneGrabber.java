package com.hbm.tileentity.network;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerCraneGrabber;
import com.hbm.inventory.gui.GUICraneGrabber;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.modules.ModulePatternMatcher;
import com.hbm.tileentity.IGUIProvider;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class TileEntityCraneGrabber extends TileEntityCraneBase implements IGUIProvider, IControlReceiver {
    public boolean isWhitelist = false;
    public ModulePatternMatcher matcher;
    private int tickCounter = 0;
    private int amount = 1;

    public TileEntityCraneGrabber() {
        super(11);
        this.matcher = new ModulePatternMatcher(9);
    }

    @Override
    public String getName() {
        return "container.craneGrabber";
    }

    @Override
    public void update() {
        super.update();
        if(!world.isRemote) {
            tickCounter++;

            int delay = 20;

            if(inventory.getStackInSlot(10) != null && inventory.getStackInSlot(10) != ItemStack.EMPTY){
                if(inventory.getStackInSlot(10).getItem() == ModItems.upgrade_ejector_1) {
                    delay = 10;
                } else if(inventory.getStackInSlot(10).getItem() == ModItems.upgrade_ejector_2){
                    delay = 5;
                } else if(inventory.getStackInSlot(10).getItem() == ModItems.upgrade_ejector_3){
                    delay = 2;
                }
            }

            if(tickCounter >= delay && !this.world.isBlockPowered(pos)) {
                tickCounter = 0;

                if(inventory.getStackInSlot(9)!= null && inventory.getStackInSlot(9) != ItemStack.EMPTY){
                    if(inventory.getStackInSlot(9).getItem() == ModItems.upgrade_stack_1) {
                        amount = 4;
                    } else if(inventory.getStackInSlot(9).getItem() == ModItems.upgrade_stack_2){
                        amount = 16;
                    } else if(inventory.getStackInSlot(9).getItem() == ModItems.upgrade_stack_3){
                        amount = 64;
                    }
                }

                EnumFacing inputSide = getInputSide();
                double reach = 1D;
                Block b = world.getBlockState(pos.offset(inputSide)).getBlock();
                if(b == ModBlocks.conveyor_double) reach = 0.5D;
                if(b == ModBlocks.conveyor_triple) reach = 0.33D;
                double x = pos.offset(inputSide).getX() * reach;
                double y = pos.offset(inputSide).getY() * reach;
                double z = pos.offset(inputSide).getZ() * reach;
                List<EntityMovingItem> items = world.getEntitiesWithinAABB(EntityMovingItem.class, new AxisAlignedBB(x + 0.1875D, y + 0.1875D, z + 0.1875D, x + 0.8125D, y + 0.8125D, z + 0.8125D));
                for(EntityMovingItem item : items){
                    ItemStack stack = item.getItemStack();
                    boolean match = this.matchesFilter(stack);
                    if(this.isWhitelist && !match || !this.isWhitelist && match){
                        continue;
                    }
                    tryFillTe();
                } // I probably should've taken this bunch of code into tryInsertItemCap instead of update and it'd work without any buffers (with a bit of adjustments)


            }


            NBTTagCompound data = new NBTTagCompound();
            data.setBoolean("isWhitelist", isWhitelist);
            this.matcher.writeToNBT(data);
            this.networkPack(data, 15);
        }
    }

    public void tryFillTe(){
        EnumFacing outputSide = getOutputSide();
        TileEntity te = world.getTileEntity(pos.offset(outputSide));
            if (te != null) {
                System.out.println("5");
                ICapabilityProvider capte = te;
                if (capte.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputSide)) {
                    IItemHandler cap = capte.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputSide);

                    for (int i = 0; i < inventory.getSlots(); i++) {
                        tryFillContainerCap(cap, i);
                    }
                }
            }
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
        if(stack.isEmpty()){
            return false;
        }

        for(int i = 0; i < chest.getSlots(); i++) {

            ItemStack outputStack = stack.copy();
            if(outputStack.isEmpty() || outputStack.getCount() == 0)
                return true;

            ItemStack chestItem = chest.getStackInSlot(i).copy();
            if(chestItem.isEmpty() || (Library.areItemStacksCompatible(outputStack, chestItem, false) && chestItem.getCount() < chestItem.getMaxStackSize())) {
                int fillAmount = Math.min(chestItem.getMaxStackSize()-chestItem.getCount(), amount);

                outputStack.setCount(fillAmount);

                ItemStack rest = chest.insertItem(i, outputStack, true);
                if(rest.getItem() == Item.getItemFromBlock(Blocks.AIR)){
                    stack.shrink(amount);
                    chest.insertItem(i, outputStack, false);
                }
            }
        }

        return false;
    }

    public void networkUnpack(NBTTagCompound nbt) {
        this.isWhitelist = nbt.getBoolean("isWhitelist");
        this.matcher.modes = new String[this.matcher.modes.length];
        this.matcher.readFromNBT(nbt);
    }

    public boolean matchesFilter(ItemStack stack) {

        for(int i = 0; i < 9; i++) {
            ItemStack filter = inventory.getStackInSlot(i);

            if(filter != null && this.matcher.isValidForFilter(filter, i, stack)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {

        return new ContainerCraneGrabber(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUICraneGrabber(player.inventory, this);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.isWhitelist = nbt.getBoolean("isWhitelist");
        this.matcher.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("isWhitelist", this.isWhitelist);
        this.matcher.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public boolean hasPermission(EntityPlayer player) {
        int xCoord = pos.getX();
        int yCoord = pos.getY();
        int zCoord = pos.getZ();
        return new Vec3d(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
    }

    @Override
    public void receiveControl(NBTTagCompound data) {
        if(data.hasKey("isWhitelist")) {
            this.isWhitelist = !this.isWhitelist;
        }
    }

}
