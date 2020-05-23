package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCell;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineCyclotron extends TileEntity implements ITickable, ISource {

	public ItemStackHandler inventory;

	public long power;
	public int progress;
	public int soundCycle = 0;
	public static final long maxPower = 1000000000;
	public static final int processTime = 690;
	public boolean isOn = false;
	public int age = 0;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	Random rand = new Random();
	public ICapabilityProvider dropProvider;

	//private static final int[] slots_top = new int[] { 0 };
	//private static final int[] slots_bottom = new int[] { 0, 0 };
	//private static final int[] slots_side = new int[] { 0 };

	private String customName;
	
	public TileEntityMachineCyclotron() {
		inventory = new ItemStackHandler(16){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		dropProvider = new ICapabilityProvider(){

			@Override
			public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
				return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
			}

			@Override
			public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
				return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : null;
			}
			
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.cyclotron";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.power = compound.getLong("power");
		this.progress = compound.getInteger("progress");
		this.isOn = compound.getBoolean("isOn");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setInteger("progress", progress);
		compound.setBoolean("isOn", isOn);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			
			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19)
				ffgeuaInit();

			if(!isOn && hasFuse() && getHeatLevel() != 4 && hasEnergy() && (isPart(inventory.getStackInSlot(0)) || isPart(inventory.getStackInSlot(1)) || isPart(inventory.getStackInSlot(2)))) {
				isOn = true;
				inventory.setStackInSlot(6, ItemStack.EMPTY);
			}
			
			if(isOn && (!hasFuse() || (!isPart(inventory.getStackInSlot(0)) && !isPart(inventory.getStackInSlot(1)) && !isPart(inventory.getStackInSlot(2))))) {
				isOn = false;
			}
			
			if(isOn) {

				this.power += getPower(inventory.getStackInSlot(0));
				this.power += getPower(inventory.getStackInSlot(1));
				this.power += getPower(inventory.getStackInSlot(2));
				if(this.power > maxPower)
					power = maxPower;
				
				if(progress < processTime) {
					progress++;
				} else {
					progress = 0;
					process();
				}
				
				if(!inventory.getStackInSlot(7).isEmpty()) {
					inventory.getStackInSlot(7).setItemDamage(inventory.getStackInSlot(7).getItemDamage() + 1);
					if(inventory.getStackInSlot(7).getItemDamage() >= inventory.getStackInSlot(7).getMaxDamage())
						inventory.setStackInSlot(7, ItemStack.EMPTY);
				}
				
				if(getCoolantTicksLeft() == 100) {
			        this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.shutdown, SoundCategory.BLOCKS, 10.0F, 1.0F);
				}
				
				if(getHeatLevel() == 1) {
					ExplosionChaos.flameDeath(world, pos, 15);
				}
				
				if(getHeatLevel() == 2) {
					ExplosionChaos.flameDeath(world, pos, 25);
					ExplosionChaos.burn(world, pos, 7);
					ExplosionThermo.setEntitiesOnFire(world, pos.getX(), pos.getY(), pos.getZ(), 7);
				}
				
				if(getHeatLevel() == 3) {
					ExplosionChaos.flameDeath(world, pos, 35);
					ExplosionChaos.burn(world, pos, 15);
					ExplosionThermo.setEntitiesOnFire(world, pos.getX(), pos.getY(), pos.getZ(), 25);
					ExplosionThermo.scorchLight(world, pos.getX(), pos.getY(), pos.getZ(), 5);
					if(rand.nextInt(50) == 0)
						ExplosionLarge.spawnTracers(world, pos.getX() + 0.5, pos.getY() + 5, pos.getZ() + 0.5, 3);
				}
				
				if(getHeatLevel() == 4) {
					int i = rand.nextInt(4);

					world.setBlockToAir(pos);
					
					if(i == 0) {
						ExplosionLarge.explodeFire(world, pos.getX(), pos.getY(), pos.getZ(), 35 + rand.nextInt(21), true, true, true);
					}
					if(i == 1) {
						world.spawnEntity(EntityNukeExplosionMK4.statFac(world, (int)(MainRegistry.fatmanRadius * 1.5), pos.getX(), pos.getY(), pos.getZ()));
						ExplosionParticleB.spawnMush(world, pos.getX(), pos.getY() - 3, pos.getZ());
					}
					if(i == 2) {
					
						EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
						entity.posX = pos.getX();
						entity.posY = pos.getY();
						entity.posZ = pos.getZ();
						int j = 15 + rand.nextInt(21);
						entity.destructionRange = j;
						entity.speed = 25;
						entity.coefficient = 1.0F;
						entity.waste = false;
		    	
						world.spawnEntity(entity);
		    		
						EntityCloudFleija cloud = new EntityCloudFleija(world, j);
						cloud.posX = pos.getX();
						cloud.posY = pos.getY();
						cloud.posZ = pos.getZ();
						world.spawnEntity(cloud);
					}
					if(i == 3) {
						EntityBlackHole bl = new EntityBlackHole(world, 1.5F + rand.nextFloat());
						bl.posX = pos.getX() + 0.5F;
						bl.posY = pos.getY() + 3.5F;
						bl.posZ = pos.getZ() + 0.5F;
						world.spawnEntity(bl);
					}
				}
				
			} else {
				progress = 0;
			}
			
			power = Library.chargeItemsFromTE(inventory, 9, power, maxPower);
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos.getX(), pos.getY(), pos.getZ(), power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20));
		}
	}
	
	public void process() {
		ItemStack stack1 = MachineRecipes.getCyclotronOutput(inventory.getStackInSlot(0), inventory.getStackInSlot(3));
		ItemStack stack2 = MachineRecipes.getCyclotronOutput(inventory.getStackInSlot(1), inventory.getStackInSlot(4));
		ItemStack stack3 = MachineRecipes.getCyclotronOutput(inventory.getStackInSlot(2), inventory.getStackInSlot(5));
		
		if(stack1 != null && hasSpaceForItem(stack1)) {
			addItemPlox(stack1);
			inventory.getStackInSlot(0).shrink(1);
			inventory.getStackInSlot(3).shrink(1);
			if(inventory.getStackInSlot(0).isEmpty())
				inventory.setStackInSlot(0, ItemStack.EMPTY);
			if(inventory.getStackInSlot(3).isEmpty())
				inventory.setStackInSlot(3, ItemStack.EMPTY);
		}
		if(stack2 != null && hasSpaceForItem(stack2)) {
			addItemPlox(stack2);
			inventory.getStackInSlot(1).shrink(1);
			inventory.getStackInSlot(4).shrink(1);
			if(inventory.getStackInSlot(1).isEmpty())
				inventory.setStackInSlot(1, ItemStack.EMPTY);
			if(inventory.getStackInSlot(4).isEmpty())
				inventory.setStackInSlot(4, ItemStack.EMPTY);
		}
		if(stack3 != null && hasSpaceForItem(stack3)) {
			addItemPlox(stack3);
			inventory.getStackInSlot(2).shrink(1);
			inventory.getStackInSlot(5).shrink(1);
			if(inventory.getStackInSlot(2).isEmpty())
				inventory.setStackInSlot(2, ItemStack.EMPTY);
			if(inventory.getStackInSlot(5).isEmpty())
				inventory.setStackInSlot(5, ItemStack.EMPTY);
		}
		
		if(!inventory.getStackInSlot(0).isEmpty() && stack1 == null) {
			if(rand.nextInt(100) < getAmatChance(inventory.getStackInSlot(0)))
				if(hasSpaceForItem(ItemCell.getFullCell(ModForgeFluids.amat)) && useCell())
					addItemPlox(ItemCell.getFullCell(ModForgeFluids.amat));

			inventory.getStackInSlot(0).shrink(1);
			if(inventory.getStackInSlot(0).isEmpty())
				inventory.setStackInSlot(0, ItemStack.EMPTY);
					
		}
		
		if(!inventory.getStackInSlot(1).isEmpty() && stack1 == null) {
			if(rand.nextInt(100) < getAmatChance(inventory.getStackInSlot(1)))
				if(hasSpaceForItem(ItemCell.getFullCell(ModForgeFluids.amat)) && useCell())
					addItemPlox(ItemCell.getFullCell(ModForgeFluids.amat));
			
			inventory.getStackInSlot(1).shrink(1);
			if(inventory.getStackInSlot(1).isEmpty())
				inventory.setStackInSlot(1, ItemStack.EMPTY);
					
		}
		
		if(!inventory.getStackInSlot(2).isEmpty() && stack1 == null) {
			if(rand.nextInt(100) < getAmatChance(inventory.getStackInSlot(2)))
				if(hasSpaceForItem(ItemCell.getFullCell(ModForgeFluids.amat)) && useCell())
					addItemPlox(ItemCell.getFullCell(ModForgeFluids.amat));

			inventory.getStackInSlot(2).shrink(1);
			if(inventory.getStackInSlot(2).isEmpty())
				inventory.setStackInSlot(2, ItemStack.EMPTY);
					
		}
	}
	
	public boolean hasSpaceForItem(Item item) {
		
		if(inventory.getStackInSlot(11).isEmpty() || inventory.getStackInSlot(12).isEmpty() || inventory.getStackInSlot(13).isEmpty() || inventory.getStackInSlot(14).isEmpty() || inventory.getStackInSlot(15).isEmpty())
			return true;

		if(inventory.getStackInSlot(11).getItem() == item && inventory.getStackInSlot(11).getCount() < inventory.getStackInSlot(11).getMaxStackSize())
			return true;
		if(inventory.getStackInSlot(12).getItem() == item && inventory.getStackInSlot(12).getCount() < inventory.getStackInSlot(12).getMaxStackSize())
			return true;
		if(inventory.getStackInSlot(13).getItem() == item && inventory.getStackInSlot(13).getCount() < inventory.getStackInSlot(13).getMaxStackSize())
			return true;
		if(inventory.getStackInSlot(14).getItem() == item && inventory.getStackInSlot(14).getCount() < inventory.getStackInSlot(14).getMaxStackSize())
			return true;
		if(inventory.getStackInSlot(15).getItem() == item && inventory.getStackInSlot(15).getCount() < inventory.getStackInSlot(15).getMaxStackSize())
			return true;
		
		return false;
	}
	
	public boolean hasSpaceForItem(ItemStack item){
		if(inventory.getStackInSlot(11).isEmpty() || inventory.getStackInSlot(12).isEmpty() || inventory.getStackInSlot(13).isEmpty() || inventory.getStackInSlot(14).isEmpty() || inventory.getStackInSlot(15).isEmpty())
			return true;

		if(Library.areItemStacksCompatible(item, inventory.getStackInSlot(11)) && inventory.getStackInSlot(11).getCount() < inventory.getStackInSlot(11).getMaxStackSize())
			return true;
		if(Library.areItemStacksCompatible(item, inventory.getStackInSlot(12)) && inventory.getStackInSlot(12).getCount() < inventory.getStackInSlot(12).getMaxStackSize())
			return true;
		if(Library.areItemStacksCompatible(item, inventory.getStackInSlot(13)) && inventory.getStackInSlot(13).getCount() < inventory.getStackInSlot(13).getMaxStackSize())
			return true;
		if(Library.areItemStacksCompatible(item, inventory.getStackInSlot(14)) && inventory.getStackInSlot(14).getCount() < inventory.getStackInSlot(14).getMaxStackSize())
			return true;
		if(Library.areItemStacksCompatible(item, inventory.getStackInSlot(15)) && inventory.getStackInSlot(15).getCount() < inventory.getStackInSlot(15).getMaxStackSize())
			return true;
		
		return false;
	}
	
	public boolean useCell() {
		if(ItemCell.isEmptyCell(inventory.getStackInSlot(10))) {
			inventory.getStackInSlot(10).shrink(1);
			if(inventory.getStackInSlot(10).isEmpty())
				inventory.setStackInSlot(10, ItemStack.EMPTY);
			return true;
		}
		return false;
	}
	
	public void addItemPlox(Item item) {
		if(inventory.getStackInSlot(11).getItem() == item && inventory.getStackInSlot(11).getCount() < inventory.getStackInSlot(11).getMaxStackSize()) {
			inventory.getStackInSlot(11).grow(1);
			return;
		}
		if(inventory.getStackInSlot(12).getItem() == item && inventory.getStackInSlot(12).getCount() < inventory.getStackInSlot(12).getMaxStackSize()) {
			inventory.getStackInSlot(12).grow(1);
			return;
		}
		if(inventory.getStackInSlot(13).getItem() == item && inventory.getStackInSlot(13).getCount() < inventory.getStackInSlot(13).getMaxStackSize()) {
			inventory.getStackInSlot(13).grow(1);
			return;
		}
		if(inventory.getStackInSlot(14).getItem() == item && inventory.getStackInSlot(14).getCount() < inventory.getStackInSlot(14).getMaxStackSize()) {
			inventory.getStackInSlot(14).grow(1);
			return;
		}
		if(inventory.getStackInSlot(15).getItem() == item && inventory.getStackInSlot(15).getCount() < inventory.getStackInSlot(15).getMaxStackSize()) {
			inventory.getStackInSlot(15).grow(1);
			return;
		}
		if(inventory.getStackInSlot(11).isEmpty()) {
			inventory.setStackInSlot(11, new ItemStack(item, 1));
			return;
		}
		if(inventory.getStackInSlot(12).isEmpty()) {
			inventory.setStackInSlot(12, new ItemStack(item, 1));
			return;
		}
		if(inventory.getStackInSlot(13).isEmpty()) {
			inventory.setStackInSlot(13, new ItemStack(item, 1));
			return;
		}
		if(inventory.getStackInSlot(14).isEmpty()) {
			inventory.setStackInSlot(14, new ItemStack(item, 1));
			return;
		}
		if(inventory.getStackInSlot(15).isEmpty()) {
			inventory.setStackInSlot(15, new ItemStack(item, 1));
			return;
		}
	}
	
	public void addItemPlox(ItemStack stack) {
		for(int i = 11; i < 16; i ++){
			if(inventory.getStackInSlot(i).isEmpty()) {
				inventory.setStackInSlot(i, stack);
				return;
			} else if(Library.areItemStacksEqualIgnoreCount(stack, inventory.getStackInSlot(i)) && stack.getCount() + inventory.getStackInSlot(i).getCount() <= inventory.getStackInSlot(i).getMaxStackSize()){
				inventory.getStackInSlot(i).grow(stack.getCount());
				return;
			}
		}
		/*if(inventory.getStackInSlot(11).isEmpty()) {
			inventory.setStackInSlot(11, stack);
			return;
		}
		if(inventory.getStackInSlot(12).isEmpty()) {
			inventory.setStackInSlot(12, stack);
			return;
		}
		if(inventory.getStackInSlot(13).isEmpty()) {
			inventory.setStackInSlot(13, stack);
			return;
		}
		if(inventory.getStackInSlot(14).isEmpty()) {
			inventory.setStackInSlot(14, stack);
			return;
		}
		if(inventory.getStackInSlot(15).isEmpty()) {
			inventory.setStackInSlot(15, stack);
			return;
		}*/
	}
	
	public boolean hasFuse() {
		return inventory.getStackInSlot(8).getItem() == ModItems.fuse || inventory.getStackInSlot(8).getItem() == ModItems.screwdriver;
	}
	
	public boolean hasEnergy() {
		return inventory.getStackInSlot(6).getItem() == ModItems.crystal_energy;
	}
	
	public int getHeatLevel() {
		if(inventory.getStackInSlot(7).getItem() == ModItems.pellet_coolant) {
			int i = (inventory.getStackInSlot(7).getItemDamage() * 100) / inventory.getStackInSlot(7).getMaxDamage();
			if(i < 75)
				return 0;
			if(i < 85)
				return 1;
			if(i < 95)
				return 2;
			return 3;
		}
		
		return 4;
	}
	
	public int getCoolantTicksLeft() {
		if(inventory.getStackInSlot(7).getItem() == ModItems.pellet_coolant) {
			int i = inventory.getStackInSlot(7).getMaxDamage() - inventory.getStackInSlot(7).getItemDamage();
			return i;
		}
		
		return 0;
	}
	
	public boolean isPart(ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() == ModItems.part_lithium)
				return true;
			if(stack.getItem() == ModItems.part_beryllium)
				return true;
			if(stack.getItem() == ModItems.part_carbon)
				return true;
			if(stack.getItem() == ModItems.part_copper)
				return true;
			if(stack.getItem() == ModItems.part_plutonium)
				return true;
		}
		return false;
	}
	
	public int getPower(ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() == ModItems.part_lithium)
				return 250;
			if(stack.getItem() == ModItems.part_beryllium)
				return 350;
			if(stack.getItem() == ModItems.part_carbon)
				return 600;
			if(stack.getItem() == ModItems.part_copper)
				return 750;
			if(stack.getItem() == ModItems.part_plutonium)
				return 1000;
		}
		return 0;
	}
	
	public int getAmatChance(ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() == ModItems.part_lithium)
				return 2;
			if(stack.getItem() == ModItems.part_beryllium)
				return 3;
			if(stack.getItem() == ModItems.part_carbon)
				return 6;
			if(stack.getItem() == ModItems.part_copper)
				return 29;
			if(stack.getItem() == ModItems.part_plutonium)
				return 94;
		}
		return 0;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / processTime;
	}
	
	@Override
	public void ffgeuaInit() {
		ffgeua(pos.add(2, 0, 0), getTact());
		ffgeua(pos.add(-2, 0, 0), getTact());
		ffgeua(pos.add(0, 0, 2), getTact());
		ffgeua(pos.add(0, 0, -2), getTact());
	}

	@Override
	public void ffgeua(BlockPos pos, boolean newTact) {
		Library.ffgeua(new BlockPos.MutableBlockPos(pos), newTact, this, world);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		list.clear();
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
	

}
