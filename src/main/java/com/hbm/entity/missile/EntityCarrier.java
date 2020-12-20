package com.hbm.entity.missile;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.main.AdvancementManager;
import com.hbm.saveddata.satellites.Satellite;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCarrier extends EntityThrowable {

	public static final DataParameter<Boolean> HASBOOSTERS = EntityDataManager.createKey(EntityCarrier.class, DataSerializers.BOOLEAN);
	
	double acceleration = 0.00D;
	
	public double prevPosX2;
	public double prevPosY2;
	public double prevPosZ2;
	
	private ItemStack payload;

	public EntityCarrier(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        this.setSize(3.0F, 26.0F);
	}
	
	@Override
	public void onUpdate() {
		
		//this.setDead();
		prevPosX2 = this.posX;
		prevPosY2 = this.posY;
		prevPosZ2 = this.posZ;
		if(motionY < 3.0D) {
			acceleration += 0.0005D;
			motionY += acceleration;
		}
		
		this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
		
		if(!world.isRemote) {
			for(int i = 0; i < 10; i++) {
				EntityGasFlameFX fx = new EntityGasFlameFX(world);
				fx.posY = posY - 0.25D;
				fx.posX = posX + rand.nextGaussian() * 0.75D;
				fx.posZ = posZ + rand.nextGaussian() * 0.75D;
				fx.motionY = -0.2D;
				
				world.spawnEntity(fx);
			}
			
			if(this.getDataManager().get(HASBOOSTERS))
				for(int i = 0; i < 2; i++) {
					EntityGasFlameFX fx1 = new EntityGasFlameFX(world);
					fx1.posY = posY - 0.25D;
					fx1.posX = posX + rand.nextGaussian() * 0.15D + 2.5D;
					fx1.posZ = posZ + rand.nextGaussian() * 0.15D;
					fx1.motionY = -0.2D;
					
					world.spawnEntity(fx1);
	
					EntityGasFlameFX fx2 = new EntityGasFlameFX(world);
					fx2.posY = posY - 0.25D;
					fx2.posX = posX + rand.nextGaussian() * 0.15D - 2.5D;
					fx2.posZ = posZ + rand.nextGaussian() * 0.15D;
					fx2.motionY = -0.2D;
					
					world.spawnEntity(fx2);
	
					EntityGasFlameFX fx3 = new EntityGasFlameFX(world);
					fx3.posY = posY - 0.25D;
					fx3.posX = posX + rand.nextGaussian() * 0.15D;
					fx3.posZ = posZ + rand.nextGaussian() * 0.15D + 2.5D;
					fx3.motionY = -0.2D;
					
					world.spawnEntity(fx3);
	
					EntityGasFlameFX fx4 = new EntityGasFlameFX(world);
					fx4.posY = posY - 0.25D;
					fx4.posX = posX + rand.nextGaussian() * 0.15D;
					fx4.posZ = posZ + rand.nextGaussian() * 0.15D - 2.5D;
					fx4.motionY = -0.2D;
					
					world.spawnEntity(fx4);
				}
			
			
			if(this.ticksExisted < 20) {
				ExplosionLarge.spawnShock(world, posX, posY, posZ, 13 + rand.nextInt(3), 4 + rand.nextGaussian() * 2);
			}
		}
		
		if(this.posY > 300 && this.getDataManager().get(HASBOOSTERS))
			this.disengageBoosters();
			//this.setDead();
		
		if(this.posY > 600) {
			deployPayload();
		}
	}
	
	private void deployPayload() {
		if(payload != null) {
			
			if(payload.getItem() == ModItems.flame_pony) {
				ExplosionLarge.spawnTracers(world, posX, posY, posZ, 25);
				for(EntityPlayer p : world.playerEntities)
					AdvancementManager.grantAchievement(p, AdvancementManager.achSpace);
			}
			
			if(payload.getItem() == ModItems.sat_foeq) {
				for(EntityPlayer p : world.playerEntities)
					AdvancementManager.grantAchievement(p, AdvancementManager.achFOEQ);
			}
			
			if(payload.getItem() instanceof ItemSatChip) {
				
			    int freq = ItemSatChip.getFreq(payload);
		    	
		    	Satellite.orbit(world, Satellite.getIDFromItem(payload.getItem()), freq, posX, posY, posZ);
			}
		}
		
		this.setDead();
	}

	@Override
	protected void entityInit() {
        this.getDataManager().register(HASBOOSTERS, true);
	}
	
	public void setPayload(ItemStack stack) {
		this.payload = stack.copy();
	}
	
	private void disengageBoosters() {
		this.getDataManager().set(HASBOOSTERS, false);
		
		if(!world.isRemote) {
			EntityBooster boost1 = new EntityBooster(world);
			boost1.posX = posX + 1.5D;
			boost1.posY = posY;
			boost1.posZ = posZ;
			boost1.motionX = 0.45D + rand.nextDouble() * 0.2D;
			boost1.motionY = motionY;
			boost1.motionZ = rand.nextGaussian() * 0.1D;
			world.spawnEntity(boost1);
			
			EntityBooster boost2 = new EntityBooster(world);
			boost2.posX = posX - 1.5D;
			boost2.posY = posY;
			boost2.posZ = posZ;
			boost2.motionX = -0.45D - rand.nextDouble() * 0.2D;
			boost2.motionY = motionY;
			boost2.motionZ = rand.nextGaussian() * 0.1D;
			world.spawnEntity(boost2);
			
			EntityBooster boost3 = new EntityBooster(world);
			boost3.posX = posX;
			boost3.posY = posY;
			boost3.posZ = posZ + 1.5D;
			boost3.motionZ = 0.45D + rand.nextDouble() * 0.2D;
			boost3.motionY = motionY;
			boost3.motionX = rand.nextGaussian() * 0.1D;
			world.spawnEntity(boost3);
			
			EntityBooster boost4 = new EntityBooster(world);
			boost4.posX = posX;
			boost4.posY = posY;
			boost4.posZ = posZ - 1.5D;
			boost4.motionZ = -0.45D - rand.nextDouble() * 0.2D;
			boost4.motionY = motionY;
			boost4.motionX = rand.nextGaussian() * 0.1D;
			world.spawnEntity(boost4);
		}
	}

	@Override
	protected void onImpact(RayTraceResult p_70184_1_) {}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }
}
