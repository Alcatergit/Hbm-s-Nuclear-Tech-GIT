package com.hbm.entity.effect;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.explosion.ExplosionNukeGeneric;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlackHole extends Entity {

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

	@Override
	public void onUpdate() {
		float size = this.getDataManager().get(SIZE);
		
		MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for(int k = 0; k < size * 5; k++) {
			double phi = rand.nextDouble() * (Math.PI * 2);
			double costheta = rand.nextDouble() * 2 - 1;
			double theta = Math.acos(costheta);
			double x = Math.sin( theta) * Math.cos( phi );
			double y = Math.sin( theta) * Math.sin( phi );
			double z = Math.cos( theta );
			
			Vec3d vec = new Vec3d(x, y, z);
			int length = (int)Math.ceil(size * 15);
			
			for(int i = 0; i < length; i ++) {
				int x0 = (int)(this.posX + (vec.x * i));
				int y0 = (int)(this.posY + (vec.y * i));
				int z0 = (int)(this.posZ + (vec.z * i));
				pos.setPos(x0, y0, z0);
				if(!world.isRemote) {
					
					if(world.getBlockState(pos).getMaterial().isLiquid()) {
						world.setBlockToAir(pos);
					}
					
					if(world.getBlockState(pos).getBlock() != Blocks.AIR) {
						EntityRubble rubble = new EntityRubble(world);
						rubble.posX = x0 + 0.5F;
						rubble.posY = y0;
						rubble.posZ = z0 + 0.5F;
						IBlockState state = world.getBlockState(pos);
						Block block = state.getBlock();
						rubble.setMetaBasedOnBlock(block, block.getMetaFromState(state));
						
						world.spawnEntity(rubble);
					
						world.setBlockToAir(pos);
						break;
					}
				}
			}
		}

		ExplosionNukeGeneric.succ(world, (int)this.posX, (int)this.posY, (int)this.posZ, (int)Math.ceil(size * 15));
		
		if(ExplosionNukeGeneric.dedify(world, (int)this.posX, (int)this.posY, (int)this.posZ, (int)Math.ceil(size * 2))) {
			this.setDead();
			MutableBlockPos deathPos = new BlockPos.MutableBlockPos();
			int r = (int)Math.ceil(size);
			int r2 = r * r;
			int r22 = r2 / 2;
			for (int xx = -r; xx < r; xx++) {
				int X = xx + (int)this.posX;
				int XX = xx * xx;
				for (int yy = -r; yy < r; yy++) {
					int Y = yy + (int)this.posY;
					int YY = XX + yy * yy;
					for (int zz = -r; zz < r; zz++) {
						int Z = zz + (int)this.posZ;
						int ZZ = YY + zz * zz;
						if (ZZ < r22) {
							deathPos.setPos(X, Y, Z);
							world.setBlockState(deathPos, ModBlocks.gravel_obsidian.getDefaultState());
						}
					}
				}
			}
			
			world.createExplosion(null, this.posX, this.posY, this.posZ, 5.0F, true);
		}
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
