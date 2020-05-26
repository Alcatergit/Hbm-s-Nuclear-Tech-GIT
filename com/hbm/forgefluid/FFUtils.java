package com.hbm.forgefluid;

import com.hbm.interfaces.IFluidPipe;
import com.hbm.interfaces.IFluidPipeMk2;
import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidTank;
import com.hbm.items.special.ItemCell;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.lib.Library;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.machine.TileEntityDummy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.IItemHandlerModifiable;

//Drillgon200: This is Library.java except for fluids
//Drillgon200: Let's hope this works without bugs in 1.12.2...
//Drillgon200: Still mad they removed the fluid container registry.
public class FFUtils {

	// Drillgon200: Wow that took a while to fix. Now the code is ugly and I'll
	// probably never fix it because it works. Dang it.
	/**
	 * Tessellates a liquid texture across a rectangle without looking weird and
	 * stretched.
	 * 
	 * @param tank
	 *            - the tank with the fluid to render
	 * @param guiLeft
	 *            - the left side of the gui
	 * @param guiTop
	 *            - the top of the gui
	 * @param zLevel
	 *            - the z level of the gui
	 * @param sizeX
	 *            - how big the rectangle should be
	 * @param sizeY
	 *            - how tall the rectangle should be
	 * @param offsetX
	 *            - where the starting x of the rectangle should be on screen
	 * @param offsetY
	 *            - where the starting y of the rectangle should be on screen
	 */
	public static void drawLiquid(FluidTank tank, int guiLeft, int guiTop, float zLevel, int sizeX, int sizeY, int offsetX, int offsetY) {
		//This is retarded, but it would be too much of a pain to fix it
		offsetY -= 44;
		RenderHelper.bindBlockTexture();

		if(tank.getFluid() != null) {
			TextureAtlasSprite liquidIcon = getTextureFromFluid(tank.getFluid().getFluid());

			if(liquidIcon != null) {
				int level = (int) (((double) tank.getFluidAmount() / (double) tank.getCapacity()) * sizeY);

				drawFull(tank, guiLeft, guiTop, zLevel, liquidIcon, level, sizeX, offsetX, offsetY, sizeY);
			}
		}
	}

	/**
	 * Internal method to actually render the fluid
	 * 
	 * @param tank
	 * @param guiLeft
	 * @param guiTop
	 * @param zLevel
	 * @param liquidIcon
	 * @param level
	 * @param sizeX
	 * @param offsetX
	 * @param offsetY
	 */
	private static void drawFull(FluidTank tank, int guiLeft, int guiTop, float zLevel, TextureAtlasSprite liquidIcon, int level, int sizeX, int offsetX, int offsetY, int sizeY) {
		int color = tank.getFluid().getFluid().getColor();
		RenderHelper.setColor(color);
		RenderHelper.startDrawingTexturedQuads();
		for(int i = 0; i < level; i += 16) {
			for(int j = 0; j < sizeX; j += 16) {
				int drawX = Math.min(16, sizeX - j);
				int drawY = Math.min(16, level - i);
				drawScaledTexture(liquidIcon, guiLeft + offsetX + j, guiTop + offsetY - i + (16 - drawY), drawX, drawY, zLevel);
			}
		}
		RenderHelper.draw();
	}

	private static void drawScaledTexture(TextureAtlasSprite icon, int posX, int posY, int sizeX, int sizeY, float zLevel) {
		if(sizeX < 0)
			sizeX = 0;
		if(sizeX > 16)
			sizeX = 16;
		if(sizeY < 0)
			sizeY = 0;
		if(sizeY > 16)
			sizeY = 16;
		float up = icon.getInterpolatedV(16);
		float down = icon.getInterpolatedV(16 - sizeY);
		float left = icon.getInterpolatedU(0);
		float right = icon.getInterpolatedU(sizeX);
		RenderHelper.addVertexWithUV(posX, posY + sizeY, zLevel, left, up);
		RenderHelper.addVertexWithUV(posX + sizeX, posY + sizeY, zLevel, right, up);
		RenderHelper.addVertexWithUV(posX + sizeX, posY, zLevel, right, down);
		RenderHelper.addVertexWithUV(posX, posY, zLevel, left, down);

	}

	/**
	 * Renders tank info, like fluid type and millibucket amount. Same as the
	 * hbm one, just centralized to a utility file.
	 * 
	 * @param gui
	 *            - the gui to render the fluid info on
	 * @param mouseX
	 *            - the cursor's x position
	 * @param mouseY
	 *            - the cursor's y position
	 * @param x
	 *            - the x left corner of where to render the info
	 * @param y
	 *            - the y top corner of where to render the info
	 * @param width
	 *            - how wide the area to render info inside is
	 * @param height
	 *            - how tall the area to render info inside is
	 * @param fluidTank
	 *            - the tank to render info of
	 */
	public static void renderTankInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height, FluidTank fluidTank) {
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY) {
			if(fluidTank.getFluid() != null) {
				gui.drawFluidInfo(new String[] { "" + fluidTank.getFluid().getFluid().getLocalizedName(fluidTank.getFluid()), fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity() + "mB" }, mouseX, mouseY);
			} else {
				gui.drawFluidInfo(new String[] { net.minecraft.client.resources.I18n.format("None"), fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity() + "mB" }, mouseX, mouseY);
			}
		}
	}

	public static void renderTankInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height, FluidTank fluidTank, Fluid fluid) {
		if(fluidTank.getFluid() != null){
			renderTankInfo(gui, mouseX, mouseY, x, y, width, height, fluidTank);
			return;
		}
		if(x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY) {
			if(fluid != null) {
				gui.drawFluidInfo(new String[] { "" + (fluid.getLocalizedName(new FluidStack(fluid, 1))), fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity() + "mB" }, mouseX, mouseY);
			} else {
				gui.drawFluidInfo(new String[] { net.minecraft.client.resources.I18n.format("None"), fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity() + "mB" }, mouseX, mouseY);
			}
		}
	}

	/**
	 * Replacement method for the old method of transferring fluids out of a
	 * machine
	 * 
	 * @param tileEntity
	 *            - the tile entity it is filling from
	 * @param tank
	 *            - the fluid tank to fill from
	 * @param world
	 *            - the world the filling is taking place in
	 * @param i
	 *            - x coord of place to fill
	 * @param j
	 *            - y coord of place to fill
	 * @param k
	 *            - z coord of place to fill
	 * @param maxDrain
	 *            - the maximum amount that can be drained from the tank at a
	 *            time
	 * @return Whether something was actually filled or not, or whether it needs
	 *         an update
	 */

	public static boolean fillFluid(TileEntity tileEntity, FluidTank tank, World world, BlockPos toFill, int maxDrain) {
		if(tank.getFluidAmount() <= 0 || tank.getFluid() == null) {
			return false;
		}
		TileEntity te = world.getTileEntity(toFill);

		if(te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
			if(te instanceof TileEntityDummy) {
				TileEntityDummy ted = (TileEntityDummy) te;
				if(world.getTileEntity(ted.target) == tileEntity) {
					return false;
				}
			}
			IFluidHandler tef = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if(tef.fill(new FluidStack(tank.getFluid(), Math.min(maxDrain, tank.getFluidAmount())), false) > 0) {
				tank.drain(tef.fill(new FluidStack(tank.getFluid(), Math.min(maxDrain, tank.getFluidAmount())), true), true);
				return true;
			}
		}
		return false;
	}

	/**
	 * Fills a fluid handling item from a tank
	 * 
	 * @param slots
	 *            - the slot inventory
	 * @param tank
	 *            - the tank to fill from
	 * @param slot1
	 *            - the slot with an empty container
	 * @param slot2
	 *            - the output slot.
	 * @return true if something was actually filled
	 */
	public static boolean fillFromFluidContainer(IItemHandlerModifiable slots, FluidTank tank, int slot1, int slot2) {

		if(slots == null || tank == null || slots.getSlots() < slot1 || slots.getSlots() < slot2 || slots.getStackInSlot(slot1) == null || slots.getStackInSlot(slot1).isEmpty()) {
			return false;
		}
		
		if(trySpecialFillFromFluidContainer(slots, tank, slot1, slot2))
			return true;

		if(slots.getStackInSlot(slot1).getItem() == ModItems.fluid_barrel_infinite && tank.getFluid() != null){
			return tank.fill(new FluidStack(tank.getFluid(), Integer.MAX_VALUE), true) > 0 ? true : false;
		}
		if(FluidUtil.getFluidContained(slots.getStackInSlot(slot1)) == null) {

			moveItems(slots, slot1, slot2);
			return false;
		}
		if(slots.getStackInSlot(slot1).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			boolean returnValue = false;

			IFluidHandlerItem ifhi = slots.getStackInSlot(slot1).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if(ifhi != null && (tank.getFluid() == null || FluidUtil.getFluidContained(slots.getStackInSlot(slot1)).getFluid() == tank.getFluid().getFluid())) {
				tank.fill(ifhi.drain(Math.min(6000, tank.getCapacity() - tank.getFluidAmount()), true), true);
				returnValue = true;
			}
			if(ifhi.drain(Integer.MAX_VALUE, false) == null) {

				moveItems(slots, slot1, slot2);
			}
			return returnValue;
		}
		return false;
	}

	//Ah yes, hacky special methods to make stacks drain.
	private static boolean trySpecialFillFromFluidContainer(IItemHandlerModifiable slots, FluidTank tank, int slot1, int slot2){
		ItemStack in = slots.getStackInSlot(slot1);
		ItemStack out = slots.getStackInSlot(slot2);
		
		//Fluid Tank override
		if(in.getItem() == ModItems.fluid_tank_full && tank.fill(FluidUtil.getFluidContained(in), false) == 1000 && ((ItemFluidTank.isEmptyTank(out) && out.getCount() < 64) || out.isEmpty())){
			tank.fill(FluidUtil.getFluidContained(in), true);
			in.shrink(1);
			if(out.isEmpty()){
				slots.setStackInSlot(slot2, new ItemStack(ModItems.fluid_tank_full));
			} else {
				out.grow(1);
			}
			return true;
		}
		
		//Fluid barrel override
		if(in.getItem() == ModItems.fluid_barrel_full && tank.fill(FluidUtil.getFluidContained(in), false) == 16000 && ((ItemFluidTank.isEmptyBarrel(out) && out.getCount() < 64) || out.isEmpty())){
			tank.fill(FluidUtil.getFluidContained(in), true);
			in.shrink(1);
			if(out.isEmpty()){
				slots.setStackInSlot(slot2, new ItemStack(ModItems.fluid_barrel_full));
			} else {
				out.grow(1);
			}
			return true;
		}
		
		//Canister override
		if(in.getItem() == ModItems.canister_generic && tank.fill(FluidUtil.getFluidContained(in), false) == 1000 && ((ItemFluidCanister.isEmptyCanister(out) && out.getCount() < 64) || out.isEmpty())){
			tank.fill(FluidUtil.getFluidContained(in), true);
			in.shrink(1);
			if(out.isEmpty()){
				slots.setStackInSlot(slot2, new ItemStack(ModItems.canister_generic));
			} else {
				out.grow(1);
			}
			return true;
		}
		
		//Cell override
		if(in.getItem() == ModItems.cell && tank.fill(FluidUtil.getFluidContained(in), false) == 1000 && ((ItemCell.isEmptyCell(out) && out.getCount() < 64) || out.isEmpty())){
			tank.fill(FluidUtil.getFluidContained(in), true);
			in.shrink(1);
			if(out.isEmpty()){
				slots.setStackInSlot(slot2, new ItemStack(ModItems.cell));
			} else {
				out.grow(1);
			}
			return true;
		}
		
		return false;
	}
	
	/**
	 * Fills a tank from a fluid handler item.
	 * 
	 * @param slots
	 *            - the slot inventory
	 * @param tank
	 *            - the tank to be filled
	 * @param slot1
	 *            - the slot with the full container
	 * @param slot2
	 *            - the output slot
	 */
	public static boolean fillFluidContainer(IItemHandlerModifiable slots, FluidTank tank, int slot1, int slot2) {
		if(slots == null || tank == null || tank.getFluid() == null || slots.getSlots() < slot1 || slots.getSlots() < slot2 || slots.getStackInSlot(slot1) == null || slots.getStackInSlot(slot1).isEmpty()) {
			return false;
		}
		
		if(trySpecialFillFluidContainer(slots, tank, slot1, slot2))
			return true;
		
		if(slots.getStackInSlot(slot1).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			boolean returnValue = false;
			IFluidHandlerItem ifhi = FluidUtil.getFluidHandler(slots.getStackInSlot(slot1));
			FluidStack stack = FluidUtil.getFluidContained(slots.getStackInSlot(slot1));
			if(stack != null && ifhi.fill(tank.getFluid(), false) <= 0) {
				moveItems(slots, slot1, slot2);
				return false;
			}
			if(stack == null || stack.getFluid() == tank.getFluid().getFluid()) {
				tank.drain(ifhi.fill(new FluidStack(tank.getFluid(), Math.min(6000, tank.getFluidAmount())), true), true);
				returnValue = true;
			}
			stack = FluidUtil.getFluidContained(slots.getStackInSlot(slot1));
			if(stack != null && ifhi.fill(new FluidStack(stack.getFluid(), Integer.MAX_VALUE), false) <= 0) {
				moveItems(slots, slot1, slot2);
			}
			return returnValue;
		}
		return false;
	}
	
	//Ah yes, hacky special methods to make stacks drain.
	private static boolean trySpecialFillFluidContainer(IItemHandlerModifiable slots, FluidTank tank, int slot1, int slot2){
		ItemStack in = slots.getStackInSlot(slot1);
		ItemStack out = slots.getStackInSlot(slot2);
		
		//Fluid Tank override
		if(in.getItem() == ModItems.fluid_tank_full && tank.drain(1000, false) != null && tank.drain(1000, false).amount == 1000 && ((ItemFluidTank.isFullTank(out, tank.getFluid().getFluid()) && out.getCount() < 64) || out.isEmpty())){
			FluidStack f = tank.drain(1000, true);
			if(f == null)
				return false;
			in.shrink(1);
			
			if(out.isEmpty()){
				slots.setStackInSlot(slot2, ItemFluidTank.getFullTank(f.getFluid()));
			} else {
				out.grow(1);
			}
			return true;
		}
		
		//Fluid barrel override
		if(in.getItem() == ModItems.fluid_barrel_full && tank.drain(16000, false) != null && tank.drain(16000, false).amount == 16000 && ((ItemFluidTank.isFullBarrel(out, tank.getFluid().getFluid()) && out.getCount() < 64) || out.isEmpty())){
			FluidStack f = tank.drain(16000, true);
			if(f == null)
				return false;
			in.shrink(1);
			
			if(out.isEmpty()){
				slots.setStackInSlot(slot2, ItemFluidTank.getFullBarrel(f.getFluid()));
			} else {
				out.grow(1);
			}
			return true;
		}
		
		//Canister override
		if(in.getItem() == ModItems.canister_generic && SpecialContainerFillLists.EnumCanister.contains(tank.getFluid().getFluid()) && tank.drain(1000, false) != null && tank.drain(1000, false).amount == 1000 && ((ItemFluidCanister.isFullCanister(out, tank.getFluid().getFluid()) && out.getCount() < 64) || out.isEmpty())){
			FluidStack f = tank.drain(1000, true);
			if(f == null)
				return false;
			in.shrink(1);
			
			if(out.isEmpty()){
				slots.setStackInSlot(slot2, ItemFluidCanister.getFullCanister(f.getFluid()));
			} else {
				out.grow(1);
			}
			return true;
		}
		
		//Cell override
		if(in.getItem() == ModItems.cell && SpecialContainerFillLists.EnumCell.contains(tank.getFluid().getFluid()) && tank.drain(1000, false) != null && tank.drain(1000, false).amount == 1000 && ((ItemCell.isFullCell(out, tank.getFluid().getFluid()) && out.getCount() < 64) || out.isEmpty())){
			FluidStack f = tank.drain(1000, true);
			if(f == null)
				return false;
			in.shrink(1);
			
			if(out.isEmpty()){
				slots.setStackInSlot(slot2, ItemCell.getFullCell(f.getFluid()));
			} else {
				out.grow(1);
			}
			return true;
		}
		
		return false;
	}

	private static boolean moveItems(IItemHandlerModifiable slots, int in, int out) {
		if(slots.getStackInSlot(in) != null && !slots.getStackInSlot(in).isEmpty()) {
			if(slots.getStackInSlot(in).getItem().hasContainerItem(slots.getStackInSlot(in))) {
				slots.setStackInSlot(in, slots.getStackInSlot(in).getItem().getContainerItem(slots.getStackInSlot(in)));
			}
			if(slots.getStackInSlot(out) == null || slots.getStackInSlot(out).isEmpty()) {

				slots.setStackInSlot(out, slots.getStackInSlot(in));
				slots.setStackInSlot(in, ItemStack.EMPTY);
				return true;
			} else if (Library.areItemStacksEqualIgnoreCount(slots.getStackInSlot(in), slots.getStackInSlot(out))) {
				int amountToTransfer = Math.min(slots.getStackInSlot(out).getMaxStackSize() - slots.getStackInSlot(out).getCount(), slots.getStackInSlot(in).getCount());
				slots.getStackInSlot(in).shrink(amountToTransfer);
				if(slots.getStackInSlot(in).getCount() <= 0)
					slots.setStackInSlot(in, ItemStack.EMPTY);

				slots.getStackInSlot(out).grow(amountToTransfer);
				return true;
			}
		}
		return false;
	}

	public static FluidTank changeTankSize(FluidTank fluidTank, int i) {
		FluidTank newTank = new FluidTank(i);
		if(fluidTank.getFluid() == null) {
			return newTank;
		} else {
			newTank.fill(fluidTank.getFluid(), true);
			return newTank;
		}
	}

	public static TextureAtlasSprite getTextureFromFluid(Fluid f) {
		if(f == null) {
			return null;
		}
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(f.getStill().toString());
	}

	public static boolean containsFluid(ItemStack stack, Fluid fluid) {
		if(FluidUtil.getFluidContained(stack) != null && FluidUtil.getFluidContained(stack).getFluid() == fluid)
			return true;
		return false;
	}

	public static NBTTagList serializeTankArray(FluidTank[] tanks) {
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < tanks.length; i++) {
			if(tanks[i] != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("tank", (byte) i);
				tanks[i].writeToNBT(tag);
				list.appendTag(tag);
			}
		}
		return list;
	}

	public static void deserializeTankArray(NBTTagList tankList, FluidTank[] tanks) {
		for(int i = 0; i < tankList.tagCount(); i++) {
			NBTTagCompound tag = tankList.getCompoundTagAt(i);
			byte b0 = tag.getByte("tank");
			if(b0 >= 0 && b0 < tanks.length) {
				tanks[b0].readFromNBT(tag);
			}
		}
	}

	public static boolean areTanksEqual(FluidTank tank1, FluidTank tank2) {
		if(tank1 == null && tank2 == null) {
			return true;
		}
		if(tank1 == null ^ tank2 == null) {
			return false;
		}
		if(tank1.getFluid() == null && tank2.getFluid() == null) {
			return true;
		}
		if(tank1.getFluid() == null ^ tank2.getFluid() == null) {
			return false;
		}
		if(tank1.getFluid().amount == tank2.getFluid().amount && tank1.getFluid().getFluid() == tank2.getFluid().getFluid() && tank1.getCapacity() == tank2.getCapacity()) {
			return true;
		}
		return false;
	}

	public static FluidTank copyTank(FluidTank tank) {
		if(tank == null)
			return null;
		return new FluidTank(tank.getFluid() != null ? tank.getFluid().copy() : null, tank.getCapacity());
	}

	public static boolean checkFluidConnectables(World world, BlockPos pos, FFPipeNetwork net) {
		TileEntity tileentity = world.getTileEntity(pos);
		if(tileentity != null && tileentity instanceof IFluidPipe && ((IFluidPipe) tileentity).getNetworkTrue() == net)
			return true;
		if(tileentity != null && !(tileentity instanceof IFluidPipe) && tileentity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {

			return true;
		}
		return false;
	}
	
	public static boolean checkFluidConnectablesMk2(World world, BlockPos pos, Fluid type) {
		TileEntity tileentity = world.getTileEntity(pos);
		if(tileentity != null && tileentity instanceof IFluidPipeMk2 && ((IFluidPipeMk2) tileentity).getType() == type)
			return true;
		if(tileentity != null && !(tileentity instanceof IFluidPipeMk2) && tileentity.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
			return true;
		}
		return false;
	}

}
