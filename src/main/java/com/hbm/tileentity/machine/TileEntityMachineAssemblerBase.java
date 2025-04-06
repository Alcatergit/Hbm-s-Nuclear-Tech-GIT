package com.hbm.tileentity.machine;

import api.hbm.energymk2.IEnergyReceiverMK2;
import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.ChemplantRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TileEntityMachineAssemblerBase extends TileEntityMachineBase implements IEnergyReceiverMK2, ITickable {

    public long power;
    public int[] progress;
    public int[] maxProgress;
    public boolean isProgressing;
    public boolean[] needsTemplateSwitch;

    int consumption = 100;
    int speed = 100;

    public TileEntityMachineAssemblerBase(int scount) {
        super(scount);

        int count = this.getRecipeCount();

        progress = new int[count];
        maxProgress = new int[count];
        needsTemplateSwitch = new boolean[count];
    }

    @Override
    public void update() {

        if(!world.isRemote) {

            int count = this.getRecipeCount();

            this.isProgressing = false;
            this.power = Library.chargeTEFromItems(this.inventory, getPowerSlot(), power, this.getMaxPower());

            for(int i = 0; i < count; i++) {
                unloadItems(i);
                loadItems(i);
            }


            for(int i = 0; i < count; i++) {
                if(!canProcess(i)) {
                    this.progress[i] = 0;
                } else {
                    isProgressing = true;
                    process(i);
                }
            }
        }
    }

    protected boolean canProcess(int index) {

        int template = getTemplateIndex(index);

        if (template < 0 || template >= this.inventory.getSlots()) {
            return false;
        }

        ItemStack templateStack = this.inventory.getStackInSlot(template);
        if(templateStack == ItemStack.EMPTY || templateStack.getItem() != ModItems.assembly_template)
            return false;

        List<RecipesCommon.AStack> recipe = AssemblerRecipes.getRecipeFromTempate(this.inventory.getStackInSlot(template));
        ItemStack output = AssemblerRecipes.getOutputFromTempate(this.inventory.getStackInSlot(template));

        if(recipe == null)
            return false;

        if(this.power < this.consumption) return false;
        if(!hasRequiredItems(recipe, index)) return false;
        if(!hasSpaceForItems(output, index)) return false;

        return true;
    }

    private boolean hasRequiredItems(List<RecipesCommon.AStack> recipe, int index) {
        int[] indices = getSlotIndicesFromIndex(index);
        return InventoryUtil.doesArrayHaveIngredients(this.inventory, indices[0], indices[1], recipe.toArray(new RecipesCommon.AStack[0]));
    }

    private boolean hasSpaceForItems(ItemStack recipe, int index) {
        int[] indices = getSlotIndicesFromIndex(index);
        return InventoryUtil.doesArrayHaveSpace(this.inventory, indices[2], indices[2], new ItemStack[] { recipe });
    }

    protected void process(int index) {

        this.power -= this.consumption;
        this.progress[index]++;

        if(this.inventory.getStackInSlot(0) != ItemStack.EMPTY && this.inventory.getStackInSlot(0).getItem() == ModItems.meteorite_sword_alloyed)
            this.inventory.setStackInSlot(0, new ItemStack(ModItems.meteorite_sword_machined)); //fisfndmoivndlmgindgifgjfdnblfm

        int template = getTemplateIndex(index);

        List<RecipesCommon.AStack> recipe = AssemblerRecipes.getRecipeFromTempate(this.inventory.getStackInSlot(template));
        ItemStack output = AssemblerRecipes.getOutputFromTempate(this.inventory.getStackInSlot(template));
        int time = ItemAssemblyTemplate.getProcessTime(this.inventory.getStackInSlot(template));

        this.maxProgress[index] = time * this.speed / 100;

        if(this.progress[index] >= this.maxProgress[index]) {
            consumeItems(recipe, index);
            produceItems(output, index);
            this.progress[index] = 0;
            this.needsTemplateSwitch[index] = true;
            this.markDirty();
        }
    }

    private void consumeItems(List<RecipesCommon.AStack> recipe, int index) {

        int[] indices = getSlotIndicesFromIndex(index);

        for(RecipesCommon.AStack in : recipe) {
            if(in != null)
                InventoryUtil.tryConsumeAStack(this.inventory, indices[0], indices[1], in);
        }
    }

    private void produceItems(ItemStack out, int index) {

        int[] indices = getSlotIndicesFromIndex(index);

        if(out != null) {
            InventoryUtil.tryAddItemToInventory(this.inventory, indices[2], indices[2], out.copy());
        }
    }

    private void loadItems(int index) {
        int template = getTemplateIndex(index);
        if(inventory.getStackInSlot(template).isEmpty() || inventory.getStackInSlot(template).getItem() != ModItems.chemistry_template)
            return;

        ChemplantRecipes.ChemRecipe recipe = ChemplantRecipes.indexMapping.get(inventory.getStackInSlot(template).getItemDamage());

        if(recipe != null) {

            ImmutablePair<BlockPos, ForgeDirection>[] positions = getInputPositions();
            int[] indices = getSlotIndicesFromIndex(index);

            for(ImmutablePair<BlockPos, ForgeDirection> pos : positions) {
                TileEntity te = world.getTileEntity(pos.left);

                if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {
                    IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
                    int[] slots;
                    if(te instanceof TileEntityMachineBase) {
                        ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset).getOpposite();
                        slots = ((TileEntityMachineBase) te).getAccessibleSlotsFromSide(dir.toEnumFacing());
                        tryFillAssemblerCap(cap, slots, (TileEntityMachineBase) te, indices[0], indices[1], recipe.inputs);
                    } else {
                        slots = new int[cap.getSlots()];
                        for(int i = 0; i < slots.length; i++)
                            slots[i] = i;
                        tryFillAssemblerCap(cap, slots, null, indices[0], indices[1], recipe.inputs);
                    }
                }
            }
        }
    }

    private void unloadItems(int index) {
        ImmutablePair<BlockPos, ForgeDirection>[] positions = getOutputPositions();
        int[] indices = getSlotIndicesFromIndex(index);

        for(ImmutablePair<BlockPos, ForgeDirection> pos : positions) {
            TileEntity te = world.getTileEntity(pos.left);
            if(te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)) {
                IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
                tryFillContainerCap(cap, indices[2]);
            }
        }
    }

    //Unloads output into chests. Capability version.
    public boolean tryFillContainerCap(IItemHandler chest, int slot) {
        //Check if we have something to output
        if(inventory.getStackInSlot(slot).isEmpty())
            return false;

        for(int i = 0; i < chest.getSlots(); i++) {

            ItemStack outputStack = inventory.getStackInSlot(slot).copy();
            if(outputStack.isEmpty())
                return false;

            ItemStack chestItem = chest.getStackInSlot(i).copy();
            if(chestItem.isEmpty() || (Library.areItemStacksCompatible(outputStack, chestItem, false) && chestItem.getCount() < chestItem.getMaxStackSize())) {
                inventory.getStackInSlot(slot).shrink(1);

                outputStack.setCount(1);
                chest.insertItem(i, outputStack, false);

                return true;
            }
        }

        return false;
    }

    public boolean tryFillAssemblerCap(IItemHandler container, int[] allowedSlots, TileEntityMachineBase te, int minSlot, int maxSlot, RecipesCommon.AStack[] recipeIngredients) {
        if(allowedSlots.length < 1)
            return false;

        if(recipeIngredients == null) //No recipe template found
            return false;
        else {
            Map<Integer, ItemStack> itemStackMap = new HashMap<Integer, ItemStack>();

            for(int slot : allowedSlots) {
                container.getStackInSlot(slot);
                if(container.getStackInSlot(slot).isEmpty()) { // check next slot in chest if it is empty
                    continue;
                } else { // found an item in chest
                    itemStackMap.put(slot, container.getStackInSlot(slot).copy());
                }
            }
            if(itemStackMap.size() == 0) {
                return false;
            }

            for(int ig = 0; ig < recipeIngredients.length; ig++) {

                RecipesCommon.AStack nextIngredient = recipeIngredients[ig].copy(); // getting new ingredient

                int ingredientSlot = getValidSlot(nextIngredient, minSlot, maxSlot);


                if(ingredientSlot < minSlot)
                    continue; // Ingredient filled or Assembler is full

                int possibleAmount = inventory.getStackInSlot(ingredientSlot).getMaxStackSize() - inventory.getStackInSlot(ingredientSlot).getCount(); // how many items do we need to fill the stack?

                if(possibleAmount == 0) { // full
                    System.out.println("This should never happen method getValidSlot broke");
                    continue;
                }
                // Ok now we know what we are looking for(nexIngredient) and where to put it (ingredientSlot) - So lets see if we find some of it in containers
                for(Map.Entry<Integer, ItemStack> set :
                        itemStackMap.entrySet()) {
                    ItemStack stack = set.getValue();
                    int slot = set.getKey();
                    ItemStack compareStack = stack.copy();
                    compareStack.setCount(1);

                    if(isItemAcceptable(nextIngredient.getStack(), compareStack)) { // bingo found something

                        int foundCount = Math.min(stack.getCount(), possibleAmount);
                        if(te != null && !te.canExtractItem(slot, stack, foundCount))
                            continue;
                        if(foundCount > 0) {
                            possibleAmount -= foundCount;
                            container.extractItem(slot, foundCount, false);
                            inventory.getStackInSlot(ingredientSlot);
                            if(inventory.getStackInSlot(ingredientSlot).isEmpty()) {

                                stack.setCount(foundCount);
                                inventory.setStackInSlot(ingredientSlot, stack);

                            } else {
                                inventory.getStackInSlot(ingredientSlot).grow(foundCount); // transfer complete
                            }
                        } else {
                            break; // ingredientSlot filled
                        }
                    }
                }

            }
            return true;
        }
    }

    private int getValidSlot(RecipesCommon.AStack nextIngredient, int minSlot, int maxSlot) {
        int firstFreeSlot = -1;
        int stackCount = (int) Math.ceil(nextIngredient.count() / 64F);
        int stacksFound = 0;

        nextIngredient = nextIngredient.singulize();

        for(int k = minSlot; k <= maxSlot; k++) { //scaning inventory if some of the ingredients allready exist
            if(stacksFound < stackCount) {
                ItemStack assStack = inventory.getStackInSlot(k).copy();
                if(assStack.isEmpty()) {
                    if(firstFreeSlot < minSlot)
                        firstFreeSlot = k;
                    continue;
                } else { // check if there are already enough filled stacks is full

                    assStack.setCount(1);
                    if(nextIngredient.isApplicable(assStack)) { // check if it is the right item

                        if(inventory.getStackInSlot(k).getCount() < assStack.getMaxStackSize()) // is that stack full?
                            return k; // found a not full slot where we already have that ingredient
                        else
                            stacksFound++;
                    }
                }
            } else {
                return -1; // All required stacks are full
            }
        }
        if(firstFreeSlot < minSlot) // nothing free in assembler inventory anymore
            return -2;
        return firstFreeSlot;
    }

    public boolean isItemAcceptable(ItemStack stack1, ItemStack stack2) {

        if(stack1 != null && stack2 != null && stack1.getItem() != Items.AIR && stack1.getItem() != Items.AIR) {
            if(Library.areItemStacksCompatible(stack1, stack2))
                return true;

            int[] ids1 = OreDictionary.getOreIDs(stack1);
            int[] ids2 = OreDictionary.getOreIDs(stack2);

            if(ids1.length > 0 && ids2.length > 0) {
                for(int i = 0; i < ids1.length; i++)
                    for(int j = 0; j < ids2.length; j++)
                        if(ids1[i] == ids2[j])
                            return true;
            }
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.power = nbt.getLong("power");
        if(nbt.hasKey("progress")) this.progress = nbt.getIntArray("progress");
        if(nbt.hasKey("maxProgress")) this.maxProgress = nbt.getIntArray("maxProgress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setLong("power", power);
        nbt.setIntArray("progress", progress);
        nbt.setIntArray("maxProgress", maxProgress);
        return nbt;
    }

    @Override
    public long getPower() {
        return this.power;
    }

    @Override
    public void setPower(long power) {
        this.power = power;
    }

    public abstract int getRecipeCount();
    public abstract int getTemplateIndex(int index);

    /**
     * @param index
     * @return A size 4 int array containing min input, max input, min output and max output indices in that order.
     */
    public abstract int[] getSlotIndicesFromIndex(int index);

    public abstract ImmutablePair<BlockPos, ForgeDirection>[] getInputPositions();

    public abstract ImmutablePair<BlockPos, ForgeDirection>[] getOutputPositions();
    public abstract int getPowerSlot();
}
