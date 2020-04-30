package com.hbm.blocks.bomb;

import java.util.List;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityTurretCIWS;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TurretCIWS extends TurretBase {

	public TurretCIWS(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityTurretCIWS();
	}

	@Override
	public boolean executeHoldAction(World world, int i, double yaw, double pitch, BlockPos pos) {
		boolean flag = false;
		
		if(pitch < -60)
			pitch = -60;
		if(pitch > 30)
			pitch = 30;
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		TileEntityTurretCIWS te = (TileEntityTurretCIWS)world.getTileEntity(pos);
		
		if(i == 0 && te.spin < 10)
			world.playSound(null, x + 0.5, y + 0.5, z + 0.5, HBMSoundHandler.ciwsSpinup, SoundCategory.BLOCKS, 1.0F, 1.0F);
		
		if(te.spin < 35)
			te.spin += 5;
		
		if(te.spin > 25 && i % 2 == 0) {
			Vec3d vector = new Vec3d(
					-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI),
					-Math.sin(pitch / 180.0F * (float) Math.PI),
					Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI));
			
			vector.normalize();
			
			if(!world.isRemote) {
				
				rayShot(world, vector, x + vector.x * 2.5 + 0.5, y + vector.y * 2.5 + 0.5, z + vector.z * 2.5 + 0.5, 100, 10.0F, MainRegistry.ciwsHitrate);
				
				EntityGasFlameFX smoke = new EntityGasFlameFX(world);
				smoke.posX = x + vector.x * 2.5 + 0.5;
				smoke.posY = y + vector.y * 2.5 + 1.5;
				smoke.posZ = z + vector.z * 2.5 + 0.5;
				
				smoke.motionX = vector.x * 0.25;
				smoke.motionY = vector.y * 0.25;
				smoke.motionZ = vector.z * 0.25;
				
				world.spawnEntity(smoke);
			}

			world.playSound(null, x + 0.5, y + 0.5, z + 0.5, HBMSoundHandler.ciwsFiringLoop, SoundCategory.BLOCKS, 1.0F, 1.25F);
			
			flag = true;
		}
		
		return flag;
	}
	//Drillgon200: This should make the hitscan actually hit entities.
	private void rayShot(World world, Vec3d vec, double posX, double posY, double posZ, int range, float damage, int hitPercent) {
		
		/*for(float i = 0; i < range; i += 0.25F) {
			double pX = posX + vec.z * i;
			double pY = posY + vec.y * i;
			double pZ = posZ + vec.z * i;
			
			if(world.getBlockState(new BlockPos((int)pX, (int)pY, (int)pZ)).getMaterial() != Material.AIR)
				break;
			
			List<Entity> hit = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pX - 0.125, pY - 0.125, pZ - 0.125, pX + 0.125, pY + 0.125, pZ + 0.125));
			
			for(int j = 0; j < hit.size(); j++) {
				Entity ent = hit.get(j);
				
				if(rand.nextInt(100) < hitPercent) {
					ent.attackEntityFrom(ModDamageSource.shrapnel, 10.0F);
				}
			}
		}*/
		Vec3d test = new Vec3d(vec.x * range + posX, vec.y * range + posY, vec.z * range + posZ);
		Vec3d pos = new Vec3d(posX, posY, posZ);
		AxisAlignedBB box = new AxisAlignedBB(pos.x, pos.y, pos.z, test.x, test.y, test.z).grow(1.0D);
		List<Entity> ents = world.getEntitiesWithinAABBExcludingEntity(null, box);
		for(Entity ent : ents){
			if(ent.getEntityBoundingBox().grow(1.0D).calculateIntercept(pos, test) != null){
				if(rand.nextInt(100) < hitPercent) {
					if(ent instanceof EntityLivingBase){
						ent.hurtResistantTime = 0;
						((EntityLivingBase) ent).hurtTime = 0;
						ent.attackEntityFrom(ModDamageSource.shrapnel, 10.0F);
					} else 
						ent.attackEntityFrom(ModDamageSource.shrapnel, 10.0F);
				}
			}
		}
	}
	

	@Override
	public void executeReleaseAction(World world, int i, double yaw, double pitch, BlockPos pos) {
		TileEntityTurretCIWS te = (TileEntityTurretCIWS)world.getTileEntity(pos);
		
		if(te.spin > 10)
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.ciwsSpindown, SoundCategory.BLOCKS, 1.0F, 1.0F);
		
	}

}
