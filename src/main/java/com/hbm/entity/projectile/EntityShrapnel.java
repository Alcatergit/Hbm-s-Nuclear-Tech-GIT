package com.hbm.entity.projectile;

import com.hbm.config.CompatibilityConfig;
import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.lib.ModDamageSource;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityShrapnel extends EntityThrowable {
	
	public static final DataParameter<Byte> TRAIL = EntityDataManager.<Byte>createKey(EntityShrapnel.class, DataSerializers.BYTE);

    public EntityShrapnel(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityShrapnel(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
	public void entityInit() {
        this.dataManager.register(TRAIL, Byte.valueOf((byte)0));
    }

    public EntityShrapnel(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }
    
    @Override
    public void onUpdate() {
    	super.onUpdate();
    	if(world.isRemote)
    		world.spawnParticle(EnumParticleTypes.FLAME, posX, posY, posZ, 0.0, 0.0, 0.0);
    }

    @Override
	protected void onImpact(RayTraceResult mop)
    {	
    	if(!CompatibilityConfig.isWarDim(world)){
			this.setDead();
			return;
		}
        if (mop.entityHit != null)
        {
            byte b0 = 15;

            mop.entityHit.attackEntityFrom(ModDamageSource.shrapnel, b0);
        }

        if(this.ticksExisted > 5) {
        	this.setDead();
        	if(this.dataManager.get(TRAIL) == 2) {
				
				if(!world.isRemote && mop.typeOfHit == Type.BLOCK && mop.getBlockPos() != null) {
					if(motionY < -0.2D) {
						
						if(world.getBlockState(mop.getBlockPos().up()).getBlock().isReplaceable(world, mop.getBlockPos().up()))
							world.setBlockState(mop.getBlockPos().up(), ModBlocks.volcanic_lava_block.getDefaultState());
						
						for(int x = mop.getBlockPos().getX() - 1; x <= mop.getBlockPos().getX() + 1; x++) {
							for(int y = mop.getBlockPos().getY(); y <= mop.getBlockPos().getY() + 2; y++) {
								for(int z = mop.getBlockPos().getZ() - 1; z <= mop.getBlockPos().getZ() + 1; z++) {
									if(world.getBlockState(new BlockPos(x, y, z)).getBlock() == Blocks.AIR)
										world.setBlockState(new BlockPos(x, y, z), ModBlocks.gas_monoxide.getDefaultState());
								}
							}
						}
					}
					
					if(motionY > 0) {
						ExplosionNT explosion = new ExplosionNT(world, null, mop.getBlockPos().getX() + 0.5, mop.getBlockPos().getY() + 0.5, mop.getBlockPos().getZ() + 0.5, 7);
						explosion.addAttrib(ExAttrib.NODROP);
						explosion.addAttrib(ExAttrib.LAVA_V);
						explosion.addAttrib(ExAttrib.NOSOUND);
						explosion.addAttrib(ExAttrib.ALLMOD);
						explosion.addAttrib(ExAttrib.NOHURT);
						explosion.explode();
					}
				}
				
			} else {
				for(int i = 0; i < 5; i++)
	        		world.spawnParticle(EnumParticleTypes.LAVA, posX, posY, posZ, 0.0, 0.0, 0.0);
			}

        	world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.HOSTILE, 1.0F, 1.0F);
        }
    }
    
    public void setTrail(boolean b) {
        	this.dataManager.set(TRAIL, (byte)(b ? 1 : 0));
    }
    
    public void setVolcano(boolean b) {
		this.dataManager.set(TRAIL, (byte) (b ? 2 : 0));
	}
}