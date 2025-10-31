package com.hbm.entity.projectile;

import com.hbm.items.ModItems;
import com.hbm.saveddata.RadiationSavedData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityRBMKDebris extends EntityDebrisBase {

	public EntityRBMKDebris(World world){
		super(world);
	}

	public EntityRBMKDebris(World world, double x, double y, double z, DebrisType type){
		super(world, x, y, z);
		this.setType(type);
	}
	@Override
	public void onUpdate() {
		if (!world.isRemote) {
			if (this.getType() == DebrisType.FUEL) {
				BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
				RadiationSavedData.incrementRad(world, pos, 20, 1500);
			}
		}
		super.onUpdate();
	}
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand){
		if(!world.isRemote && !isDead) {
			switch(this.getType()){
			case BLANK:
				if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal)))
					this.setDead();
				break;
			case ELEMENT:
				if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal)))
					this.setDead();
				break;
			case FUEL:
				if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_fuel)))
					this.setDead();
				break;
			case GRAPHITE:
				if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_graphite)))
					this.setDead();
				break;
			case LID:
				if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.rbmk_lid)))
					this.setDead();
				break;
			case ROD:
				if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal)))
					this.setDead();
				break;
			}

			player.inventoryContainer.detectAndSendChanges();
		}

		return false;
	}
	@Override
	public void setSize() {
		switch(this.getType()){
			case BLANK:
				this.setSize(0.5F, 0.5F);
				break;
			case ELEMENT:
				this.setSize(1F, 1F);
				break;
			case FUEL:
				this.setSize(0.25F, 0.25F);
				this.setContaminating(16,100);
				break;
			case GRAPHITE:
				this.setSize(0.25F, 0.25F);
				break;
			case LID:
				this.setSize(1F, 0.5F);
				break;
			case ROD:
				this.setSize(0.75F, 0.5F);
				break;
		}
	}
	@Override
	public int getLifetime(){

		switch(this.getType()){
		case BLANK:
			return 30 * 60 * 20;
		case ELEMENT:
			return 30 * 60 * 20;
		case FUEL:
			return 100 * 60 * 20;
		case GRAPHITE:
			return 150 * 60 * 20;
		case LID:
			return 300 * 20;
		case ROD:
			return 600 * 20;
		default:
			return 0;
		}
	}

	public void setType(DebrisType type){
		this.getDataManager().set(TYPE_ID, type.ordinal());
	}

	public DebrisType getType(){
		return DebrisType.values()[Math.abs(this.getDataManager().get(TYPE_ID)) % DebrisType.values().length];
	}

	public static enum DebrisType {
		BLANK, //just a metal beam
		ELEMENT, //the entire casing of a fuel assembly because fuck you
		FUEL, //spicy
		ROD, //solid boron rod
		GRAPHITE, //spicy rock
		LID; //the all destroying harbinger of annihilation
	}
}