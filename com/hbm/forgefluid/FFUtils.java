package com.hbm.forgefluid;

import com.hbm.inventory.gui.GuiInfoContainer;
import com.hbm.render.RenderHelper;
import com.hbm.tileentity.machine.TileEntityDummy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
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

//Drillgon200: Let's hope this works without bugs in 1.12.2...
//Drillgon200: Still mad they removed the fluid container registry.
public class FFUtils {

	//Drillgon200: Wow that took a while to fix. Now the code is ugly and I'll probably never fix it because it works. Dang it.
	/**
	 * Tessellates a liquid texture across a rectangle without looking weird and stretched.
	 * @param tank - the tank with the fluid to render
	 * @param guiLeft - the left side of the gui
	 * @param guiTop - the top of the gui
	 * @param zLevel - the z level of the gui
	 * @param sizeX - how big the rectangle should be
	 * @param sizeY - how tall the rectangle should be
	 * @param offsetX - where the starting x of the rectangle should be on screen
	 * @param offsetY - where the starting y of the rectangle should be on screen
	 */
	public static void drawLiquid(FluidTank tank, int guiLeft, int guiTop, float zLevel, int sizeX, int sizeY, int offsetX, int offsetY) {
		RenderHelper.bindBlockTexture();
		
		if (tank.getFluid() != null) {
			TextureAtlasSprite liquidIcon = getTextureFromFluid(tank.getFluid().getFluid());

			if (liquidIcon != null) {
				int level = (int) (((double) tank.getFluidAmount() / (double) tank.getCapacity()) * sizeY);

				drawFull(tank, guiLeft, guiTop, zLevel, liquidIcon, level, sizeX, offsetX, offsetY, sizeY);
			}
		}
	}
	
	/**
	 * Internal method to actually render the fluid
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
		for(int i = 0; i < level; i += 16){
			for(int j = 0; j < sizeX; j += 16){
				int drawX = Math.min(16, sizeX-j);
				int drawY = Math.min(16, level-i);
				drawScaledTexture(liquidIcon, guiLeft + offsetX +j,  offsetY-i + (16-drawY), drawX, drawY, zLevel);
			}
		}
		RenderHelper.draw();
	}
	
	private static void drawScaledTexture(TextureAtlasSprite icon, int posX, int posY, int sizeX, int sizeY, float zLevel){
		if(sizeX < 0)
			sizeX = 0;
		if(sizeX > 16)
			sizeX = 16;
		if(sizeY < 0)
			sizeY = 0;
		if(sizeY > 16)
			sizeY = 16;
		float up = icon.getInterpolatedV(16);
		float down = icon.getInterpolatedV(16-sizeY);
		float left = icon.getInterpolatedU(0);
		float right = icon.getInterpolatedU(sizeX);
		RenderHelper.addVertexWithUV(posX, posY + sizeY, zLevel, left, up);
		RenderHelper.addVertexWithUV(posX + sizeX, posY + sizeY, zLevel, right, up);
		RenderHelper.addVertexWithUV(posX + sizeX, posY, zLevel, right, down);
		RenderHelper.addVertexWithUV(posX, posY, zLevel, left, down);
		
		
	}
	
	/**
	 * Renders tank info, like fluid type and millibucket amount. Same as the hbm
	 * one, just centrallized to a utility file.
	 * 
	 * @param gui       - the gui to render the fluid info on
	 * @param mouseX    - the cursor's x position
	 * @param mouseY    - the cursor's y position
	 * @param x         - the x left corner of where to render the info
	 * @param y         - the y top corner of where to render the info
	 * @param width     - how wide the area to render info inside is
	 * @param height    - how tall the area to render info inside is
	 * @param fluidTank - the tank to render info of
	 */
	public static void renderTankInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height,
			FluidTank fluidTank) {
		if (x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY) {
			if (fluidTank.getFluid() != null) {
				gui.drawFluidInfo(new String[] { ""
						+ fluidTank.getFluid().getFluid().getLocalizedName(fluidTank.getFluid()),
						fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity() + "mB" }, mouseX, mouseY);
			} else {
				gui.drawFluidInfo(new String[] { net.minecraft.client.resources.I18n.format("None"),
						fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity() + "mB" }, mouseX, mouseY);
			}
		}
	}

	public static void renderTankInfo(GuiInfoContainer gui, int mouseX, int mouseY, int x, int y, int width, int height,
			FluidTank fluidTank, Fluid fluid) {
		if (x <= mouseX && x + width > mouseX && y < mouseY && y + height >= mouseY) {
			if (fluid != null) {
				gui.drawFluidInfo(
						new String[] { "" + (fluid.getLocalizedName(new FluidStack(fluid, 1))),
								fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity() + "mB" },
						mouseX, mouseY);
			} else {
				gui.drawFluidInfo(new String[] { net.minecraft.client.resources.I18n.format("None"),
						fluidTank.getFluidAmount() + "/" + fluidTank.getCapacity() + "mB" }, mouseX, mouseY);
			}
		}
	}
	
	/**
	 * Replacement method for the old method of transferring fluids out of a machine
	 * 
	 * @param tileEntity - the tile entity it is filling from
	 * @param tank       - the fluid tank to fill from
	 * @param world      - the world the filling is taking place in
	 * @param i          - x coord of place to fill
	 * @param j          - y coord of place to fill
	 * @param k          - z coord of place to fill
	 * @param maxDrain   - the maximum amount that can be drained from the tank at a
	 *                   time
	 * @return Whether something was actually filled or not, or whether it needs an
	 *         update
	 */

	public static boolean fillFluid(TileEntity tileEntity, FluidTank tank, World world, BlockPos toFill, int maxDrain) {
		if (tank.getFluidAmount() <= 0 || tank.getFluid() == null) {
			return false;
		}
		TileEntity te = world.getTileEntity(toFill);

		if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
			if (te instanceof TileEntityDummy) {
				TileEntityDummy ted = (TileEntityDummy) te;
				if (world.getTileEntity(ted.target) == tileEntity) {
					return false;
				}
			}
			IFluidHandler tef = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			if (tef.fill(new FluidStack(tank.getFluid(), Math.min(maxDrain, tank.getFluidAmount())), false) > 0) {
				tank.drain(tef.fill(new FluidStack(tank.getFluid(), Math.min(maxDrain, tank.getFluidAmount())), true),
						true);
				return true;
			}
		}
		return false;
	}

	/**
	 * Fills a fluid handling item from a tank
	 * 
	 * @param slots - the slot inventory
	 * @param tank  - the tank to fill from
	 * @param slot1 - the slot with an empty container
	 * @param slot2 - the output slot.
	 * @return true if something was actually filled
	 */
	public static boolean fillFromFluidContainer(IItemHandlerModifiable slots, FluidTank tank, int slot1, int slot2) {
		
		if (slots == null || tank == null || slots.getSlots() < slot1 || slots.getSlots() < slot2
				|| slots.getStackInSlot(slot1) == null || slots.getStackInSlot(slot1).isEmpty()) {
			return false;
		}
		
		if (FluidUtil.getFluidContained(slots.getStackInSlot(slot1)) == null) {
			
			moveItems(slots, slot1, slot2);
			return false;
		}
		if (slots.getStackInSlot(slot1).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			boolean returnValue = false;
			
			IFluidHandlerItem ifhi = slots.getStackInSlot(slot1)
					.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			if (ifhi != null && (tank.getFluid() == null || FluidUtil.getFluidContained(slots.getStackInSlot(slot1))
					.getFluid() == tank.getFluid().getFluid())) {
				tank.fill(ifhi.drain(Math.min(6000, tank.getCapacity() - tank.getFluidAmount()), true), true);
				returnValue = true;
			}
			if (ifhi.drain(Integer.MAX_VALUE, false) == null) {
				
				moveItems(slots, slot1, slot2);
			}
			return returnValue;
		}
		return false;
	}

	/**
	 * Fills a tank from a fluid handler item.
	 * 
	 * @param slots - the slot inventory
	 * @param tank  - the tank to be filled
	 * @param slot1 - the slot with the full container
	 * @param slot2 - the output slot
	 */
	public static boolean fillFluidContainer(IItemHandlerModifiable slots, FluidTank tank, int slot1, int slot2) {
		if (slots == null || tank == null || tank.getFluid() == null || slots.getSlots() < slot1
				|| slots.getSlots() < slot2 || slots.getStackInSlot(slot1) == null
				|| slots.getStackInSlot(slot1).isEmpty()) {
			return false;
		}
		if (slots.getStackInSlot(slot1).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			boolean returnValue = false;
			IFluidHandlerItem ifhi = FluidUtil.getFluidHandler(slots.getStackInSlot(slot1));
			FluidStack stack = FluidUtil.getFluidContained(slots.getStackInSlot(slot1));
			if (stack != null && ifhi.fill(tank.getFluid(), false) <= 0) {
				moveItems(slots, slot1, slot2);
				return false;
			}
			if (stack == null || stack.getFluid() == tank.getFluid().getFluid()) {
				tank.drain(ifhi.fill(new FluidStack(tank.getFluid(), Math.min(6000, tank.getFluidAmount())), true),
						true);
				returnValue = true;
			}
			stack = FluidUtil.getFluidContained(slots.getStackInSlot(slot1));
			if (stack != null && ifhi.fill(new FluidStack(stack.getFluid(), Integer.MAX_VALUE), false) <= 0) {
				moveItems(slots, slot1, slot2);
			}
			return returnValue;
		}
		return false;
	}

	private static boolean moveItems(IItemHandlerModifiable slots, int in, int out) {
		if (slots.getStackInSlot(in) != null && !slots.getStackInSlot(in).isEmpty()) {
			if(slots.getStackInSlot(in).getItem().hasContainerItem(slots.getStackInSlot(in))){
				slots.setStackInSlot(in, slots.getStackInSlot(in).getItem().getContainerItem(slots.getStackInSlot(in)));
			}
			if (slots.getStackInSlot(out) == null || slots.getStackInSlot(out).isEmpty()) {

				slots.setStackInSlot(out, slots.getStackInSlot(in));
				slots.setStackInSlot(in, ItemStack.EMPTY);
				return true;
			} else {
				int amountToTransfer = Math.min(
						slots.getStackInSlot(out).getMaxStackSize() - slots.getStackInSlot(out).getCount(),
						slots.getStackInSlot(in).getCount());
				slots.getStackInSlot(in).shrink(amountToTransfer);
				if (slots.getStackInSlot(in).getCount() <= 0)
					slots.setStackInSlot(in, ItemStack.EMPTY);

				slots.getStackInSlot(out).grow(amountToTransfer);
				return true;
			}
		}
		return false;
	}

	public static FluidTank changeTankSize(FluidTank fluidTank, int i) {
		FluidTank newTank = new FluidTank(i);
		if (fluidTank.getFluid() == null) {
			return newTank;
		} else {
			newTank.fill(fluidTank.getFluid(), true);
			return newTank;
		}
	}
	public static TextureAtlasSprite getTextureFromFluid(Fluid f){
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(f.getStill().toString());
	}
}
