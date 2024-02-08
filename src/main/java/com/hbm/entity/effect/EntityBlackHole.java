package com.hbm.entity.effect;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlackHole extends Entity implements IConstantRenderer {

	public static final DataParameter<Float> SIZE = EntityDataManager.createKey(EntityBlackHole.class, DataSerializers.FLOAT);
	
	public EntityBlackHole(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	public EntityBlackHole(World w, float size){
		this(w);
		this.getDataManager().set(SIZE, size);
	}

	public boolean isImmuneToExplosions() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(!CompatibilityConfig.isWarDim(world)){
			this.setDead();
			return;
		}
		float size = this.dataManager.get(SIZE);
		
		if(!world.isRemote) {
			for(int k = 0; k < size * 2; k++) {
				double phi = rand.nextDouble() * (Math.PI * 2);
				double costheta = rand.nextDouble() * 2 - 1;
				double theta = Math.acos(costheta);
				double x = Math.sin( theta) * Math.cos( phi );
				double y = Math.sin( theta) * Math.sin( phi );
				double z = Math.cos( theta );
				
				Vec3 vec = Vec3.createVectorHelper(x, y, z);
				int length = (int)Math.ceil(size * 15);
				
				for(int i = 0; i < length; i ++) {
					int x0 = (int)(this.posX + (vec.xCoord * i));
					int y0 = (int)(this.posY + (vec.yCoord * i));
					int z0 = (int)(this.posZ + (vec.zCoord * i));
					
					BlockPos des = new BlockPos(x0, y0, z0);
					
					if(world.getBlockState(des).getMaterial().isLiquid()) {
						world.setBlockState(des, Blocks.AIR.getDefaultState());
					}
					
					if(world.getBlockState(des).getBlock() != Blocks.AIR) {
						EntityRubble rubble = new EntityRubble(world);
						rubble.posX = x0 + 0.5F;
						rubble.posY = y0;
						rubble.posZ = z0 + 0.5F;
						IBlockState st = world.getBlockState(new BlockPos(x0, y0, z0));
						rubble.setMetaBasedOnBlock(st.getBlock(), st.getBlock().getMetaFromState(st));
						
						world.spawnEntity(rubble);
					
						world.setBlockState(des, Blocks.AIR.getDefaultState());
						break;
					}
				}
			}
		}
		
		double range = size * 15;
		
		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(this, new AxisAlignedBB(
				posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));
		
		for(Entity e : entities) {
			
			if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
				continue;
			
			if(e instanceof EntityFallingBlock && !world.isRemote && e.ticksExisted > 1) {
				
				double x = e.posX;
				double y = e.posY;
				double z = e.posZ;
				Block b = ((EntityFallingBlock)e).getBlock().getBlock();
				int meta = b.getMetaFromState(((EntityFallingBlock)e).getBlock());
				
				e.setDead();
				
				EntityRubble rubble = new EntityRubble(world);
				rubble.setMetaBasedOnBlock(b, meta);
				rubble.setPositionAndRotation(x, y, z, 0, 0);
				rubble.motionX = e.motionX;
				rubble.motionY = e.motionY;
				rubble.motionZ = e.motionZ;
				world.spawnEntity(rubble);
			}
			
			Vec3 vec = Vec3.createVectorHelper(posX - e.posX, posY - e.posY, posZ - e.posZ);
			
			double dist = vec.lengthVector();
			
			if(dist > range)
				continue;
			
			vec = vec.normalize();
			
			if(!(e instanceof EntityItem))
				vec.rotateAroundY((float)Math.toRadians(15));
			
			double speed = 0.1D;
			e.motionX += vec.xCoord * speed;
			e.motionY += vec.yCoord * speed * 2;
			e.motionZ += vec.zCoord * speed;
			
			if(e instanceof EntityBlackHole)
				continue;
			
			if(dist < size * 1.5) {
				e.attackEntityFrom(ModDamageSource.blackhole, 1000);
				
				if(!(e instanceof EntityLivingBase))
					e.setDead();
				
				if(!world.isRemote && e instanceof EntityItem) {
					EntityItem item = (EntityItem) e;
					ItemStack stack = item.getItem();
					
					if(stack.getItem() == ModItems.pellet_antimatter || stack.getItem() == ModItems.flame_pony) {
						this.setDead();
						world.createExplosion(null, this.posX, this.posY, this.posZ, 5.0F, true);
						return;
					}
				}
			}
		}
		
		this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		
		this.motionX *= 0.99D;
		this.motionY *= 0.99D;
		this.motionZ *= 0.99D;
	}
	
	@Override
	protected void entityInit() {
		this.getDataManager().register(SIZE, 0.5F);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.getDataManager().set(SIZE, compound.getFloat("size"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("size", this.getDataManager().get(SIZE));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
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

}
