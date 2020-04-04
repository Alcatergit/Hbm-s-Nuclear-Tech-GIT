package com.hbm.entity.missile;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class EntityBobmazon extends Entity {

	public ItemStack payload;
	
	public EntityBobmazon(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
        this.setSize(1F, 3F);
	}

	@Override
	protected void entityInit() {
	}

	@Override
	public void onUpdate() {
		motionY = -0.5;
		motionX = 0;
		motionZ = 0;

		this.lastTickPosX = this.prevPosX = this.posX;
		this.lastTickPosY = this.prevPosY = this.posY;
		this.lastTickPosZ = this.prevPosZ = this.posZ;
		
		for(int i = 0; i < 4; i++) {
			
			if(!this.world.isRemote && i % 2 == 0)
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(posX, posY + 1, posZ, 2), new TargetPoint(world.provider.getDimension(), posX, posY + 1, posZ, 300));
			
			if(world.getBlockState(new BlockPos((int)(posX - 0.5), (int)(posY + 1), (int)(posZ - 0.5))).getMaterial() != Material.AIR && !world.isRemote) {
				this.setDead();
				ExplosionLarge.spawnParticles(world, posX, posY, posZ, 50);

	            this.world.playSound(null, this.posX, this.posY, this.posZ, HBMSoundHandler.oldExplosion, SoundCategory.NEUTRAL, 10.0F, 0.5F + this.rand.nextFloat() * 0.1F);
				
				if(payload != null)
					world.spawnEntity(new EntityItem(world, posX, posY, posZ, payload));
				
				break;
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
		}
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if(payload != null)
			compound.setTag("payload", payload.writeToNBT(new NBTTagCompound()));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		if(compound.hasKey("payload"))
			payload = new ItemStack(compound.getCompoundTag("payload"));
	}

}
