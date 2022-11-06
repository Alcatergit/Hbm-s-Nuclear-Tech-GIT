package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.blocks.machine.rbmk.RBMKRod;
import com.hbm.entity.projectile.EntityRBMKDebris.DebrisType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.lib.ForgeDirection;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;
import com.hbm.tileentity.machine.rbmk.IRBMKLoadable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class TileEntityRBMKRod extends TileEntityRBMKSlottedBase implements IRBMKFluxReceiver, IRBMKLoadable {
	
	//amount of "neutron energy" buffered for the next tick to use for the reaction
	public double fluxFast;
	public double fluxSlow;
	public boolean hasRod;

	public TileEntityRBMKRod() {
		super(1);
	}

	@Override
	public String getName() {
		return "container.rbmkRod";
	}
	
	@Override
	public boolean isModerated() {
		return ((RBMKRod)this.getBlockType()).moderated;
	}

	@SuppressWarnings("incomplete-switch") //shut the fuck up
	@Override
	public void receiveFlux(NType type, double flux) {
		
		switch(type) {
		case FAST: this.fluxFast += flux; break;
		case SLOW: this.fluxSlow += flux; break;
		}
	}
	
	@Override
	public void update() {

		if(!world.isRemote) {
			
			if(inventory.getStackInSlot(0).getItem() instanceof ItemRBMKRod) {
				ItemRBMKRod rod = ((ItemRBMKRod)inventory.getStackInSlot(0).getItem());
				
				double fluxIn = fluxFromType(rod.nType);
				//System.out.println(fluxIn + " - " + this.fluxFast + " - " + this.fluxSlow);
				double fluxOut = rod.burn(world, inventory.getStackInSlot(0), fluxIn);
				NType rType = rod.rType;
				
				rod.updateHeat(world, inventory.getStackInSlot(0), 1.0D);
				this.heat += rod.provideHeat(world, inventory.getStackInSlot(0), heat, 1.0D);
				
				
				if(this.heat > this.maxHeat()) {
					this.meltdown();
					return;
				}
				
				if(!this.hasLid()) {
					RadiationSavedData.incrementRad(world, pos, (float) ((this.fluxFast + this.fluxSlow) * 0.05F), Float.MAX_VALUE);
				}
				
				super.update();
				//for spreading, we want the buffered flux to be 0 because we want to know exactly how much gets reflected back
				this.fluxFast = 0;
				this.fluxSlow = 0;
				
				spreadFlux(rType, fluxOut);
				
				hasRod = true;
			} else {

				this.fluxFast = 0;
				this.fluxSlow = 0;
				
				hasRod = false;
				
				super.update();
			}
		}
	}
	
	/**
	 * SLOW: full efficiency for slow neutrons, fast neutrons have half efficiency
	 * FAST: fast neutrons have 100% efficiency, slow only 30%
	 * ANY: just add together whatever we have because who cares
	 * @param type
	 * @return
	 */
	
	private double fluxFromType(NType type) {
		
		switch(type) {
		case SLOW: return this.fluxFast * 0.5D + this.fluxSlow;
		case FAST: return this.fluxFast + this.fluxSlow * 0.3D;
		case ANY: return this.fluxFast + this.fluxSlow;
		}
		
		return 0.0D;
	}
	
	public static final ForgeDirection[] fluxDirs = new ForgeDirection[] {
			ForgeDirection.NORTH,
			ForgeDirection.EAST,
			ForgeDirection.SOUTH,
			ForgeDirection.WEST
	};
	
	protected static NType stream;
	
	protected void spreadFlux(NType type, double fluxOut) {
		
		int range = RBMKDials.getFluxRange(world);
		
		for(ForgeDirection dir : fluxDirs) {
			
			stream = type;
			double flux = fluxOut;
			
			for(int i = 1; i <= range; i++) {
				
				flux = runInteraction(pos.getX() + dir.offsetX * i, pos.getY(), pos.getZ() + dir.offsetZ * i, flux);
				
				if(flux <= 0)
					break;
			}
		}
	}
	
	protected double runInteraction(int x, int y, int z, double flux) {
		
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		if(te instanceof TileEntityRBMKBase) {
			TileEntityRBMKBase base = (TileEntityRBMKBase) te;
			
			if(!base.hasLid())
				RadiationSavedData.incrementRad(world, pos, (float) (flux * 0.05F), Float.MAX_VALUE);
			
			if(base.isModerated()) {
				TileEntityRBMKRod.stream = NType.SLOW;
			}
		}

		//burn baby burn
		if(te instanceof TileEntityRBMKRod) {
			TileEntityRBMKRod rod = (TileEntityRBMKRod)te;
			
			if(rod.inventory.getStackInSlot(0).getItem() instanceof ItemRBMKRod) {
				rod.receiveFlux(stream, flux);
				return 0;
			}
		}
		if(te instanceof IRBMKFluxReceiver) {
			IRBMKFluxReceiver rod = (IRBMKFluxReceiver)te;
			rod.receiveFlux(stream, flux);
			return 0;
		}
		
		//set neutrons to slow
		if(te instanceof TileEntityRBMKControl) {
			TileEntityRBMKControl control = (TileEntityRBMKControl)te;
			
			if(control.getMult() == 0.0D)
				return 0;
			
			flux *= control.getMult();
			
			return flux;
		}
		
		//set neutrons to slow
		if(te instanceof TileEntityRBMKModerator) {
			stream = NType.SLOW;
			return flux;
		}
		
		//return the neutrons back to this with no further action required
		if(te instanceof TileEntityRBMKReflector) {
			this.receiveFlux(stream, flux);
			return 0;
		}
		
		//break the neutron flow and nothign else
		if(te instanceof TileEntityRBMKAbsorber) {
			return 0;
		}
		
		if(te instanceof TileEntityRBMKBase) {
			return flux;
		}
		
		int limit = RBMKDials.getColumnHeight(world);
		int hits = 0;
		for(int h = 0; h <= limit; h++) {
			
			if(!world.getBlockState(new BlockPos(x, y, z)).isOpaqueCube())
				hits++;
		}
		
		if(hits > 0)
			RadiationSavedData.incrementRad(world, pos, (float) (flux * 0.05F * hits / (float)limit), Float.MAX_VALUE);
		
		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.fluxFast = nbt.getDouble("fluxFast");
		this.fluxSlow = nbt.getDouble("fluxSlow");
		this.hasRod = nbt.getBoolean("hasRod");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("fluxFast", this.fluxFast);
		nbt.setDouble("fluxSlow", this.fluxSlow);
		nbt.setBoolean("hasRod", this.hasRod);
		return nbt;
	}
	
	public void getDiagData(NBTTagCompound nbt) {
		this.writeToNBT(nbt);
		
		if(inventory.getStackInSlot(0).getItem() instanceof ItemRBMKRod) {
			
			ItemRBMKRod rod = ((ItemRBMKRod)inventory.getStackInSlot(0).getItem());

			nbt.setString("f_yield", ItemRBMKRod.getYield(inventory.getStackInSlot(0)) + " / " + rod.yield + " (" + (ItemRBMKRod.getEnrichment(inventory.getStackInSlot(0)) * 100) + "%)");
			nbt.setString("f_xenon", ItemRBMKRod.getPoison(inventory.getStackInSlot(0)) + "%");
			nbt.setString("f_heat", ItemRBMKRod.getCoreHeat(inventory.getStackInSlot(0)) + " / " + ItemRBMKRod.getHullHeat(inventory.getStackInSlot(0))  + " / " + rod.meltingPoint);
		}
	}
	
	@Override
	public void onMelt(int reduce) {
		int h = RBMKDials.getColumnHeight(world);
		reduce = MathHelper.clamp(reduce, 1, h);
		
		if(world.rand.nextInt(3) == 0)
			reduce++;
		
		boolean corium = inventory.getStackInSlot(0).getItem() instanceof ItemRBMKRod;
		
		if(corium && inventory.getStackInSlot(0).getItem() == ModItems.rbmk_fuel_drx) 
			RBMKBase.digamma = true;
		
		inventory.setStackInSlot(0, ItemStack.EMPTY);

		if(corium) {
			
			for(int i = h; i >= 0; i--) {
				
				if(i <= h + 1 - reduce) {
					world.setBlockState(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), ModBlocks.corium_block.getDefaultState());
				} else {
					world.setBlockState(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), Blocks.AIR.getDefaultState());
				}
				IBlockState state = world.getBlockState(pos.up(i));
				world.notifyBlockUpdate(pos.up(i), state, state, 3);
			}
			
			int count = 1 + world.rand.nextInt(RBMKDials.getColumnHeight(world));
			
			for(int i = 0; i < count; i++) {
				spawnDebris(DebrisType.FUEL);
			}
		} else {
			this.standardMelt(reduce);
		}
		
		spawnDebris(DebrisType.ELEMENT);
		
		if(this.getBlockMetadata() == RBMKBase.DIR_NORMAL_LID.ordinal() + RBMKBase.offset)
			spawnDebris(DebrisType.LID);
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.FUEL;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = new NBTTagCompound();
		
		if(inventory.getStackInSlot(0).getItem() instanceof ItemRBMKRod) {
			
			ItemRBMKRod rod = ((ItemRBMKRod)inventory.getStackInSlot(0).getItem());
			data.setDouble("enrichment", ItemRBMKRod.getEnrichment(inventory.getStackInSlot(0)));
			data.setDouble("xenon", ItemRBMKRod.getPoison(inventory.getStackInSlot(0)));
			data.setDouble("c_heat", ItemRBMKRod.getHullHeat(inventory.getStackInSlot(0)));
			data.setDouble("c_coreHeat", ItemRBMKRod.getCoreHeat(inventory.getStackInSlot(0)));
			data.setDouble("c_maxHeat", rod.meltingPoint);
		}
		
		return data;
	}

	@Override
	public boolean canLoad(ItemStack toLoad) {
		return toLoad != null && inventory.getStackInSlot(0).isEmpty();
	}

	@Override
	public void load(ItemStack toLoad) {
		inventory.setStackInSlot(0, toLoad.copy());
		this.markDirty();
	}

	@Override
	public boolean canUnload() {
		return !inventory.getStackInSlot(0).isEmpty();
	}

	@Override
	public ItemStack provideNext() {
		return inventory.getStackInSlot(0);
	}

	@Override
	public void unload() {
		inventory.setStackInSlot(0, ItemStack.EMPTY);
		this.markDirty();
	}
}