package com.hbm.entity.mob.sodtekhnologiyah;

import com.hbm.interfaces.IRadiationImmune;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.MoverType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityBurrowing extends EntityCreature implements IRadiationImmune {

	protected float dragInAir;
	protected float dragInGround;
	
	public EntityBurrowing(World worldIn) {
		super(worldIn);
	}
	
	/*
	 * No need to update this
	 */
	@Override
	public void fall(float distance, float damageMultiplier) {}
	
	/*
	 * Our "eye"-height should always be centered
	 */
	@Override
	public float getEyeHeight() {
		return this.height * 0.5F;
	}
	
	public boolean getIsHead() {
		return false;
	}

	/*
	 * No fall damage :P
	 */
	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
	
	@Override
	public void travel(float strafe, float vertical, float forward) {
		float drag = this.dragInGround;
        if ((!isEntityInsideOpaqueBlock()) && (!isInWater()) && (!isInLava()))
        {
        	drag = this.dragInAir;
        }
        else
        {
          this.distanceWalkedModified = ((float)(this.distanceWalkedModified + Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 0.4D));
          this.distanceWalkedOnStepModified = ((float)(this.distanceWalkedOnStepModified + Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ + this.motionY * this.motionY) * 0.4D));
          /*if (this.distanceWalkedOnStepModified > this.nextStepDistance)
          {
            //this.nextStepDistance = ((int)this.distanceWalkedOnStepModified + 1);
            if (isInWater())
            {
              float var39 = (float) (Math.sqrt(this.motionX * this.motionX * 0.2D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.2) * 0.35F);
              if (var39 > 1.0F) {
                var39 = 1.0F;
              }
              playSound("liquid.swim", var39, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
            }
            if (getIsHead()) {
            	playSound("alexmod.destroyer.dig", getSoundVolume(), 1.0F);
            }
          }*/
        }
        if (!getIsHead()) {
        	drag *= 0.9F;
        }
        this.moveRelative(strafe, vertical, forward, 0.02F);

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= drag;
        this.motionY *= drag;
        this.motionZ *= drag;

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double deltaX = this.posX - this.prevPosX;
        double deltaZ = this.posZ - this.prevPosZ;
        float dist = (float) Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) * 4.0F;
        if (dist > 1.0F) {
          dist = 1.0F;
        }
        this.limbSwingAmount += (dist - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
	}

	
	@Override
	public boolean isOnLadder() {
		return false;
	}
}
