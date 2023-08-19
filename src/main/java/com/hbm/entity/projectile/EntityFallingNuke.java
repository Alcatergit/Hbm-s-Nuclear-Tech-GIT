package com.hbm.entity.projectile;

import com.hbm.blocks.bomb.NukeCustom;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityFallingNuke extends EntityThrowable {

	public static final DataParameter<EnumFacing> FACING = EntityDataManager.createKey(EntityFallingNuke.class, DataSerializers.FACING);
	
	float tnt;
	float nuke;
	float hydro;
	float bale;
	float dirty;
	float schrab;
	float sol;
	float euph;
	
	public EntityFallingNuke(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
	}
	
	public EntityFallingNuke(World p_i1582_1_, float tnt, float nuke, float hydro, float bale, float dirty, float schrab, float sol, float euph) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		
		this.tnt = tnt;
		this.nuke = nuke;
		this.hydro = hydro;
		this.bale = bale;
		this.dirty = dirty;
		this.schrab = schrab;
		this.sol = sol;
		this.euph = euph;
        this.prevRotationYaw = this.rotationYaw = 90;
        this.prevRotationPitch = this.rotationPitch = 90;
	}
	
	@Override
	protected void entityInit() {
		this.getDataManager().register(FACING, EnumFacing.NORTH);
	}
	
	@Override
	public void onUpdate() {
		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);
		
		/*this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;*/
		
		this.motionX *= 0.99;
		this.motionZ *= 0.99;
		this.motionY -= 0.05D;
		
		if(motionY < -1)
			motionY = -1;
        
        this.rotation();
        
        if(this.world.getBlockState(new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock() != Blocks.AIR)
        {
    		if(!this.world.isRemote)
    		{
				NukeCustom.explodeCustom(world, posX, posY, posZ, tnt, nuke, hydro, bale, dirty, schrab, sol, euph);
	    		this.setDead();
    		}
        }
	}
	
	public void rotation() {

		this.prevRotationPitch = rotationPitch;
		
		if(rotationPitch > -75)
			this.rotationPitch -= 2;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }

}
