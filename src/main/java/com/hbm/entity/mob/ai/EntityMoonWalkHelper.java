package com.hbm.entity.mob.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.MathHelper;

public class EntityMoonWalkHelper extends EntityMoveHelper {

    public EntityMoonWalkHelper(EntityLiving entity) {
        super(entity);
    }

    @Override
    public void onUpdateMoveHelper() {
        this.entity.setMoveForward(0.0F);

        if(this.isUpdating()) {
            this.action = EntityMoveHelper.Action.WAIT;
            int i = MathHelper.floor(this.entity.getEntityBoundingBox().minY + 0.5D);
            double d0 = this.posX - this.entity.posX;
            double d1 = this.posZ - this.entity.posZ;
            double d2 = this.posY - (double) i;
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

            if(d3 >= 2.500000277905201E-7D) {
                float f = (float) (Math.atan2(d1, d0) * 180.0D / Math.PI) + 90.0F;
                this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f, 30.0F);
                float speed = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
                this.entity.setAIMoveSpeed(speed);
                this.entity.setMoveForward(-speed);

                if(d2 > 0.0D && d0 * d0 + d1 * d1 < 1.0D) {
                    this.entity.getJumpHelper().setJumping();
                }
            }
        }
    }

}
