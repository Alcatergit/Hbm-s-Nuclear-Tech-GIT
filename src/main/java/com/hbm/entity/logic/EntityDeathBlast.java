package com.hbm.entity.logic;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IConstantRenderer;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDeathBlast extends Entity implements IConstantRenderer {

	public static final int maxAge = 60;
	
	public EntityDeathBlast(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
	}

	@Override
	public void onUpdate() {
		if(this.ticksExisted >= maxAge && !world.isRemote) {
			this.setDead();
    		
    		ExplosionLarge.explodeFire(world, posX, posY, posZ, 25, true, true, true);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
	public float getBrightness()
    {
        return 1.0F;
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }
	
	@Override
	protected void entityInit() {
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}

}
