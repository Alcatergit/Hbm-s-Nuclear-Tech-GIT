package com.hbm.tileentity.machine;

import api.hbm.energy.IEnergyUser;
import com.hbm.inventory.container.ContainerAutocrafter;
import com.hbm.inventory.gui.GUIAutocrafter;
import com.hbm.lib.Library;
import com.hbm.modules.ModulePatternMatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class TileEntityMachineAutocrafter extends TileEntityMachineBase implements ITickable, IGUIProvider, IEnergyUser {

    public ModulePatternMatcher matcher;
    protected InventoryCraftingAuto craftingInventory = new InventoryCraftingAuto(3, 3);

    public List<IRecipe> recipes = new ArrayList<>();
    public int recipeIndex;
    public int recipeCount;

    public static int consumption = 1000;
    public static long maxPower = consumption * 1000;
    public long power;

    public TileEntityMachineAutocrafter() {
        super(21);
        this.matcher = new ModulePatternMatcher(9);
    }

    public void nextTemplate() {

        if(world.isRemote) return;

        this.recipeIndex++;

        if(this.recipeIndex >= this.recipes.size())
            this.recipeIndex = 0;

        if(!this.recipes.isEmpty()) {
            inventory.setStackInSlot(9, this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid()));
        } else {
            inventory.setStackInSlot(9, ItemStack.EMPTY);
        }
    }

    @Override
    public String getName() {
        return "container.autocrafter";
    }


    public void nextMode(int i) {
        this.matcher.nextMode(world, inventory.getStackInSlot(i), i);
    }

    public void initPattern(ItemStack stack, int index) {
        this.matcher.initPatternSmart(world, stack, index);
    }

    @Override
    public void update() {

        if(!world.isRemote) {

            this.power = Library.chargeTEFromItems(inventory, 20, power, maxPower);
            this.updateStandardConnections(world, pos);

            if(!this.recipes.isEmpty() && this.power >= consumption) {
                IRecipe recipe = this.recipes.get(recipeIndex);

                if(recipe.matches(this.getRecipeGrid(), this.world)) {
                    ItemStack stack = recipe.getCraftingResult(this.getRecipeGrid());

                    if(!stack.isEmpty()) {

                        boolean didCraft = false;

                        if(this.inventory.getStackInSlot(19) == ItemStack.EMPTY) {
                            inventory.setStackInSlot(19, stack.copy());
                            didCraft = true;
                        } else if(this.inventory.getStackInSlot(19).isItemEqual(stack) && ItemStack.areItemStackTagsEqual(stack, this.inventory.getStackInSlot(19)) && this.inventory.getStackInSlot(19).getCount() + stack.getCount() <= this.inventory.getStackInSlot(19).getMaxStackSize()) {
                            inventory.getStackInSlot(19).setCount(inventory.getStackInSlot(19).getCount() + stack.getCount());
                            didCraft = true;
                        }

                        if(didCraft) {
                            for(int i = 10; i < 19; i++) {

                                ItemStack ingredient = this.inventory.getStackInSlot(i);

                                if(ingredient != ItemStack.EMPTY) {
                                    this.inventory.getStackInSlot(i).shrink(1);

                                    if(this.inventory.getStackInSlot(i) == ItemStack.EMPTY && ingredient.getItem().hasContainerItem(ingredient)) {
                                        ItemStack container = ingredient.getItem().getContainerItem(ingredient);

                                        if(!container.isEmpty() && container.isItemStackDamageable() && container.getItemDamage() > container.getMaxDamage()) {
                                            continue;
                                        }

                                        this.inventory.setStackInSlot(i, container);
                                    }
                                }
                            }

                            this.power -= consumption;
                        }
                    }
                }
            }

            NBTTagCompound data = new NBTTagCompound();
            data.setLong("power", power);
            this.matcher.writeToNBT(data);
            data.setInteger("rC", recipeCount);
            data.setInteger("rI", recipeIndex);
            this.networkPack(data, 50);
        }
    }

    @Override
    public void networkUnpack(NBTTagCompound nbt) {
        this.power = nbt.getLong("power");
        this.matcher.modes = new String[this.matcher.modes.length];
        this.matcher.readFromNBT(nbt);
        this.recipeCount = nbt.getInteger("rC");
        this.recipeIndex = nbt.getInteger("rI");
    }

    public void updateTemplateGrid() {

        this.recipes = getMatchingRecipes(this.getTemplateGrid());
        this.recipeCount = recipes.size();
        this.recipeIndex = 0;

        if(!this.recipes.isEmpty()) {
            this.inventory.setStackInSlot(9, this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid()));
        } else {
            this.inventory.setStackInSlot(9, ItemStack.EMPTY);
        }
    }

    public List<IRecipe> getMatchingRecipes(InventoryCrafting grid) {
        List<IRecipe> recipes = new ArrayList<>();

        for(IRecipe recipe : ForgeRegistries.RECIPES.getValues()) {

            if(recipe.matches(grid, world)) {
                recipes.add(recipe);
            }
        }

        return recipes;
    }

    public int[] access = new int[] { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };

    @Override
    public int[] getAccessibleSlotsFromSide(EnumFacing e) {
        return access;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack stack, int j) {
        if(i == 19)
            return true;

        if(i > 9 && i < 19) {
            ItemStack filter = this.inventory.getStackInSlot(i-10);
            String mode = matcher.modes[i - 10];

            if(filter == null || mode == null || mode.isEmpty()) return true;

            return !matcher.isValidForFilter(filter, i - 10, stack);
        }

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {

        //automatically prohibit any stacked item with a container
        if(stack.getCount() > 1 && stack.getItem().hasContainerItem(stack))
            return false;

        //only allow insertion for the nine recipe slots
        if(slot < 10 || slot > 18)
            return false;

        //is the filter at this space null? no input.
        if(this.inventory.getStackInSlot(slot - 10).isEmpty())
            return false;

        //let's find all slots that this item could potentially go in
        List<Integer> validSlots = new ArrayList<>();
        for(int i = 0; i < 9; i++) {
            ItemStack filter = this.inventory.getStackInSlot(i);

            if(matcher.isValidForFilter(filter, i, stack)) {
                validSlots.add(i + 10);

                //if the current slot is valid and has no item in it, shortcut to true [*]
                if(i + 10 == slot && this.inventory.getStackInSlot(slot).isEmpty()) {
                    return true;
                }
            }
        }

        //if the slot we are looking at isn't valid, skip
        if(!validSlots.contains(slot)) {
            return false;
        }

        //assumption from [*]: the slot has to be valid by now, and it cannot be null
        int size = this.inventory.getStackInSlot(slot).getCount();

        //now we decide based on stacksize, woohoo
        for(Integer i : validSlots) {
            ItemStack valid = this.inventory.getStackInSlot(i);

            if(valid.isEmpty()) return false; //null? since slots[slot] is not null by now, this other slot needs the item more
            if(!(valid.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(valid, stack))) continue; //different item anyway? out with it

            //if there is another slot that actually does need the same item more, cancel
            if(valid.getCount() < size)
                return false;
        }

        //prevent items with containers from stacking
        return !stack.getItem().hasContainerItem(stack);

        //by now, we either already have filled the slot (if valid by filter and null) or weeded out all other options, which means it is good to go
    }

    public InventoryCrafting getTemplateGrid() {
        this.craftingInventory.loadInventory(inventory, 0);
        return this.craftingInventory;
    }

    public InventoryCrafting getRecipeGrid() {
        this.craftingInventory.loadInventory(inventory, 10);
        return this.craftingInventory;
    }

    public static class InventoryCraftingAuto extends InventoryCrafting {

        public InventoryCraftingAuto(int width, int height) {
            super(new ContainerBlank() /* "can't be null boo hoo" */, width, height);
        }

        public void loadInventory(ItemStackHandler inv, int start) {

            for(int i = 0; i < this.getSizeInventory(); i++) {
                this.setInventorySlotContents(i, inv.getStackInSlot(start + i));
            }
        }

        public void clear() {
            for(int i = 0; i < this.getSizeInventory(); i++) this.setInventorySlotContents(i, null);
        }

        public static class ContainerBlank extends Container {
            @Override public void onCraftMatrixChanged(IInventory inventory) { }
            @Override public boolean canInteractWith(EntityPlayer player) { return false; }
        }
    }



    @Override
    public long getPower() {
        return power;
    }

    @Override
    public long getMaxPower() {
        return maxPower;
    }

    @Override
    public void setPower(long power) {
        this.power = power;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.power = nbt.getLong("power");

        this.matcher.modes = new String[this.matcher.modes.length];
        this.matcher.readFromNBT(nbt);

        this.recipes = getMatchingRecipes(this.getTemplateGrid());
        this.recipeCount = recipes.size();
        this.recipeIndex = nbt.getInteger("rec");

        if(!this.recipes.isEmpty()) {
            this.inventory.setStackInSlot(9, this.recipes.get(this.recipeIndex).getCraftingResult(getTemplateGrid()));
        } else {
            this.inventory.setStackInSlot(9, ItemStack.EMPTY);
        }
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setLong("power", power);
        this.matcher.writeToNBT(nbt);
        nbt.setInteger("rec", this.recipeIndex);
        return super.writeToNBT(nbt);
    }

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerAutocrafter(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GUIAutocrafter(player.inventory, this);
    }
}