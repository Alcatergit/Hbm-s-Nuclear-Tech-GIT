package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityNukeN45 extends TileEntity implements ITickable {

	public ItemStackHandler inventory;
	private String customName;
	
	public boolean primed = false;
	
	public TileEntityNukeN45() {
		inventory = new ItemStackHandler(2){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.nukeN45";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		primed = compound.getBoolean("primed");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("primed", primed);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos, primed ? 1 : 0, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 150));
			
			if(primed) {
				
				if(getType() == 0) {
					return;
				}

				int rad = 0;
				
				if(!inventory.getStackInSlot(1).isEmpty()) {
					
					if(inventory.getStackInSlot(1).getItem() == ModItems.upgrade_effect_1)
						rad = 5;
					if(inventory.getStackInSlot(1).getItem() == ModItems.upgrade_effect_2)
						rad = 10;
					if(inventory.getStackInSlot(1).getItem() == ModItems.upgrade_effect_3)
						rad = 15;
				}
				
				if(rad == 0) {
					primed = false;
					return;
				}
				
				List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() + 0.5 - rad, pos.getY() + 0.5 - rad, pos.getZ() + 0.5 - rad, pos.getX() + 0.5 + rad, pos.getY() + 0.5 + rad, pos.getZ() + 0.5 + rad));
				
				for(Entity e : list) {
					
					if(e instanceof EntityLivingBase && e.width * e.width * e.height >= 0.5 && !((EntityLivingBase)e).isPotionActive(MobEffects.INVISIBILITY)) {
						int t = getType();
						this.clearSlots();
						explode(world, pos.getX(), pos.getY(), pos.getZ(), t);
						break;
					}
				}
			}
		}
	}
	
	public static void explode(World world, int x, int y, int z, int type) {
		
		if(!world.isRemote) {
			world.setBlockToAir(new BlockPos(x, y, z));
			
			//System.out.println(type);
			
			switch(type) {
			case 1:
				world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 1.5F, true);
				break;
			case 2:
		        world.createExplosion(null, x + 0.5, y + 0.5, z + 0.5, 4.0F, true);
				break;
			case 3:
				ExplosionLarge.explode(world, x, y, z, 15, true, false, false);
				break;
			case 4:
				world.spawnEntity(EntityNukeExplosionMK4.statFac(world, (int)(BombConfig.missileRadius * 0.75F), x + 0.5, y + 0.5, z + 0.5));
	
				EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000, BombConfig.missileRadius * 0.005F * 0.75F);
				entity2.posX = x;
				entity2.posY = y;
				entity2.posZ = z;
				world.spawnEntity(entity2);
				break;
			}
		}
	}
	
	public int getType() {
		
		if(!primed && !inventory.getStackInSlot(1).isEmpty()) {
			
			if(inventory.getStackInSlot(1).getItem() == ModItems.upgrade_effect_1 ||
				inventory.getStackInSlot(1).getItem() == ModItems.upgrade_effect_2 ||
				inventory.getStackInSlot(1).getItem() == ModItems.upgrade_effect_3)
				return 100;
		}
		
		if(!inventory.getStackInSlot(0).isEmpty()) {

			if(inventory.getStackInSlot(0).getItem() == Item.getItemFromBlock(ModBlocks.det_cord))
				return 1;
			if(inventory.getStackInSlot(0).getItem() == Item.getItemFromBlock(Blocks.TNT))
				return 2;
			if(inventory.getStackInSlot(0).getItem() == Item.getItemFromBlock(ModBlocks.det_charge))
				return 3;
			if(inventory.getStackInSlot(0).getItem() == Item.getItemFromBlock(ModBlocks.det_nuke))
				return 4;
		}
		
		return 0;
	}
	
	public void clearSlots() {
		for(int i = 0; i < inventory.getSlots(); i++)
		{
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
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
