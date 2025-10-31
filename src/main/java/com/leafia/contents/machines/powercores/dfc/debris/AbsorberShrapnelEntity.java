package com.leafia.contents.machines.powercores.dfc.debris;

import com.hbm.entity.projectile.EntityDebrisBase;
import com.hbm.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class AbsorberShrapnelEntity extends EntityDebrisBase {

	public AbsorberShrapnelEntity(World world){
		super(world);
	}

	public AbsorberShrapnelEntity(World world,double x,double y,double z,DebrisType type){
		super(world, x, y, z);
		this.setType(type);
	}

	@Override
	public boolean destroysBlocks() {
		return false;
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand){
		if(!world.isRemote && !isDead) {
			switch(this.getType()){
				case CABLE:
					if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.dfcsh_cable)))
						this.setDead();
					break;
				case CORE:
					if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.dfcsh_core)))
						this.setDead();
					break;
				case CORNER:
					if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.dfcsh_corner)))
						this.setDead();
					break;
				case FRONT:
					if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.dfcsh_front)))
						this.setDead();
					break;
				case BEAM:
					if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.dfcsh_beam)))
						this.setDead();
					break;
			}

			player.inventoryContainer.detectAndSendChanges();
		}

		return false;
	}
	@Override
	public void setSize() { this.setSize(0.45f,0.3f); }
	@Override
	public int getLifetime(){ return 24*3600*20; }

	public void setType(DebrisType type){
		this.getDataManager().set(TYPE_ID, type.ordinal());
	}

	public DebrisType getType(){
		return DebrisType.values()[Math.abs(this.getDataManager().get(TYPE_ID)) % DebrisType.values().length];
	}

	public static enum DebrisType { CABLE,CORE,CORNER,FRONT,BEAM }
}