package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntityDeconRad;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleCloud;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDeconRad extends BlockContainer {

	public static float radRemove;
	public BlockDeconRad(Material materialIn, String s, float rad) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.radRemove = rad;
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDeconRad(this.radRemove);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		float f = pos.getX() + 0.5F;
		float f1 = pos.getY() + 1.0F;
		float f2 = pos.getZ() + 0.5F;

		//worldIn.spawnParticle(EnumParticleTypes.CLOUD, f, f1, f2, 0.0D, 0.1D, 0.0D);
		Particle p = new ParticleCloud(worldIn, f, f1, f2, 0.0D, 0.1D, 0.0D){
			@Override
			public void onUpdate() {
				this.prevPosX = this.posX;
		        this.prevPosY = this.posY;
		        this.prevPosZ = this.posZ;

		        if (this.particleAge++ >= this.particleMaxAge)
		        {
		            this.setExpired();
		        }
		        
		        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
		        this.move(this.motionX, this.motionY, this.motionZ);
		        this.motionX *= 0.9599999785423279D;
		        this.motionY *= 0.9599999785423279D;
		        this.motionZ *= 0.9599999785423279D;

		        if (this.onGround)
		        {
		            this.motionX *= 0.699999988079071D;
		            this.motionZ *= 0.699999988079071D;
		        }
			}
			//.Factory().createParticle(0, worldIn, f, f1, f2, 0.0D, 0.1D, 0.0D);
		};
		Minecraft.getMinecraft().effectRenderer.addEffect(p);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
