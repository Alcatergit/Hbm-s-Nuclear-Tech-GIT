package com.hbm.particle;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockCloudResidue;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleCloudFX extends Particle {

	public TextureAtlasSprite tex;
	public Item[] textureItems;
	public int meta;

	public ParticleCloudFX(World p_i1225_1_, double p_i1225_2_, double p_i1225_4_, double p_i1225_6_, double p_i1225_8_, double p_i1225_10_, double p_i1225_12_)
    {
        this(p_i1225_1_, p_i1225_2_, p_i1225_4_, p_i1225_6_, p_i1225_8_, p_i1225_10_, p_i1225_12_, 1.0F);
    }
	
	public ParticleCloudFX setTexMetaItems(int meta, Item... items){
		this.textureItems = items;
		this.meta = meta;
		return this;
	}

	public ParticleCloudFX(World p_i1226_1_, double p_i1226_2_, double p_i1226_4_, double p_i1226_6_, double p_i1226_8_, double p_i1226_10_, double p_i1226_12_, float p_i1226_14_)
    {
        super(p_i1226_1_, p_i1226_2_, p_i1226_4_, p_i1226_6_, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += p_i1226_8_;
        this.motionY += p_i1226_10_;
        this.motionZ += p_i1226_12_;
        this.particleRed = this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896D);
        this.particleScale *= 0.75F;
        this.particleScale *= p_i1226_14_;
        this.canCollide = true;
    }

	@Override
	public void onUpdate() {

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		if (particleMaxAge < 900) {
			particleMaxAge = rand.nextInt(301) + 900;
		}

		if (!world.isRemote && rand.nextInt(50) == 0)
			ExplosionChaos.c(world, (int) posX, (int) posY, (int) posZ, 2);

		this.particleAge++;
		if (this.particleAge >= particleMaxAge) {
			this.setExpired();
		}

		this.motionX *= 0.7599999785423279D;
		this.motionY *= 0.7599999785423279D;
		this.motionZ *= 0.7599999785423279D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
		
		if(world.isRaining() && world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ)))) {
			this.motionY -= 0.01;
		}
		
		double subdivisions = 4;
		
		for(int i = 0; i < subdivisions; i++) {
	
			this.posX += this.motionX/subdivisions;
			this.posY += this.motionY/subdivisions;
			this.posZ += this.motionZ/subdivisions;
			
			if (world.getBlockState(new BlockPos((int) posX, (int) posY, (int) posZ)).isNormalCube()) {
	
				if(rand.nextInt(5) != 0) {
					this.setExpired();
					
					if(BlockCloudResidue.hasPosNeightbour(world, new BlockPos((int) (posX - motionX/subdivisions), (int) (posY - motionY/subdivisions), (int) (posZ - motionZ/subdivisions))) && world.getBlockState(new BlockPos((int) (posX - motionX/subdivisions), (int) (posY - motionY/subdivisions), (int) (posZ - motionZ/subdivisions))).getBlock().isReplaceable(world, new BlockPos((int) (posX - motionX/subdivisions), (int) (posY - motionY/subdivisions), (int) (posZ - motionZ/subdivisions)))) {
						world.setBlockState(new BlockPos((int) (posX - motionX/subdivisions), (int) (posY - motionY/subdivisions), (int) (posZ - motionZ/subdivisions)), ModBlocks.residue.getDefaultState());
					}
				}
				
				this.posX -= this.motionX/subdivisions;
				this.posY -= this.motionY/subdivisions;
				this.posZ -= this.motionZ/subdivisions;
	
				this.motionX = 0;
				this.motionY = 0;
				this.motionZ = 0;
			}
		}
	}
	
	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX,
			float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		RenderHelper.bindTexture(getEntityTexture());
		this.particleTexture = tex;
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}
	protected ResourceLocation getEntityTexture() {
		
		Item item = textureItems[0];
		
		if (this.particleAge <= this.particleMaxAge && this.particleAge >= this.particleMaxAge / 8 * 7) {
			item = textureItems[7];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 7 && this.particleAge >= this.particleMaxAge / 8 * 6) {
			item = textureItems[6];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 6 && this.particleAge >= this.particleMaxAge / 8 * 5) {
			item = textureItems[5];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 5 && this.particleAge >= this.particleMaxAge / 8 * 4) {
			item = textureItems[4];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 4 && this.particleAge >= this.particleMaxAge / 8 * 3) {
			item = textureItems[3];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 3 && this.particleAge >= this.particleMaxAge / 8 * 2) {
			item = textureItems[2];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 2 && this.particleAge >= this.particleMaxAge / 8 * 1) {
			item = textureItems[1];
		}

		if (this.particleAge < this.particleMaxAge / 8 && this.particleAge >= 0) {
			item = textureItems[0];
		}
		tex = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(new ItemStack(item, 1, meta), null, null).getParticleTexture();
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}
}
