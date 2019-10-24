package com.hbm.entity.projectile;

import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.ParticleBurstPacket;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityRubble extends EntityThrowable {
	
	public static final DataParameter<Integer> BLOCKID = EntityDataManager.createKey(EntityRubble.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> BLOCKMETA = EntityDataManager.createKey(EntityRubble.class, DataSerializers.VARINT);
	
	public EntityRubble(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityRubble(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
	public void entityInit() {
        this.dataManager.register(BLOCKID, (int)Integer.valueOf(0));
        this.dataManager.register(BLOCKMETA, (int)Integer.valueOf(0));
    }

    public EntityRubble(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
	protected void onImpact(RayTraceResult p_70184_1_)
    {
        if (p_70184_1_.entityHit != null)
        {
            byte b0 = 15;

            p_70184_1_.entityHit.attackEntityFrom(ModDamageSource.rubble, b0);
        }

        if(this.ticksExisted > 2) {
        	this.setDead();
        	
    		world.playSound(this.posX, this.posY, this.posZ, HBMSoundHandler.blockDebris, SoundCategory.BLOCKS, 1.5F, 1.0F, true);
            //worldObj.playAuxSFX(2001, (int)posX, (int)posY, (int)posZ, this.dataWatcher.getWatchableObjectInt(16) + (this.dataWatcher.getWatchableObjectInt(17) << 12));
    		if(!world.isRemote)
    			PacketDispatcher.wrapper.sendToAll(new ParticleBurstPacket((int)posX - 1, (int)posY, (int)posZ - 1, this.dataManager.get(BLOCKID), this.dataManager.get(BLOCKMETA)));
        }
    }
    
    public void setMetaBasedOnBlock(Block b, int i) {

    	this.dataManager.set(BLOCKID, Block.getIdFromBlock(b));
    	this.dataManager.set(BLOCKMETA, i);
    }
}
