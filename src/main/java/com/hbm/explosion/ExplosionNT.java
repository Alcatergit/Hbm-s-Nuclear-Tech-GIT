package com.hbm.explosion;

import com.google.common.collect.Lists;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.CompatibilityConfig;
import com.hbm.render.amlfrom1710.Vec3;
import com.leafia.dev.LeafiaUtil;
import com.leafia.dev.custompacket.LeafiaCustomPacket;
import com.leafia.dev.custompacket.LeafiaCustomPacketEncoder;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class ExplosionNT extends Explosion {

	public Set<ExAttrib> attributes = new HashSet<>();

	private Random explosionRNG = new Random();
	private World worldObj;
	protected int resolution = 16;
	protected Map affectedEntities = new HashMap();
	public float explosionSize;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public int iterationLimit = -1;

	public float maxExplosionResistance = -1;
	/** A list of ChunkPositions of blocks affected by this explosion */
	public final List<BlockPos> affectedBlockPositions;

	public final List<BlockPos> ignoreBlockPoses = new ArrayList<>();

	public List<BlockPos> fallBlocks;
	
	public static final List<ExAttrib> nukeAttribs = Arrays.asList(new ExAttrib[] {ExAttrib.FIRE, ExAttrib.NOPARTICLE, ExAttrib.NOSOUND, ExAttrib.NODROP, ExAttrib.NOHURT});

	public ExplosionNT(World world, Entity exploder, double x, double y, double z, float strength, List<BlockPos> affected) {
		super(world, exploder, x, y, z, strength, false, true);
		this.worldObj = world;
		this.explosionSize = strength;
		this.explosionX = x;
		this.explosionY = y;
		this.explosionZ = z;
		this.exploder = exploder;
		this.affectedBlockPositions = affected;
	}
	public ExplosionNT(World world, Entity exploder, double x, double y, double z, float strength) {
		this(world,exploder,x,y,z,strength,Lists.<BlockPos> newArrayList());
	}

	public ExplosionNT addAttrib(ExAttrib attrib) {
		attributes.add(attrib);
		return this;
	}
	
	public ExplosionNT addAllAttrib(List<ExAttrib> attrib) {
		attributes.addAll(attrib);
		return this;
	}
	
	public ExplosionNT overrideResolution(int res) {
		resolution = res;
		return this;
	}

	public void explode() {
		if(CompatibilityConfig.isWarDim(worldObj)) {
			doNTExplosionA();
			doNTExplosionB();
			if (!worldObj.isRemote) {
				ExplosionNTSyncPacket packet = new ExplosionNTSyncPacket();
				packet.nt = this;
				LeafiaCustomPacket.__start(packet).__sendToAllAround(worldObj.provider.getDimension(),new Vec3d(explosionX,explosionY,explosionZ),Math.sqrt(4096));
			}
		}
    }
	public static class ExplosionNTSyncPacket implements LeafiaCustomPacketEncoder {
		ExplosionNT nt;
		@Override
		public void encode(LeafiaBuf buf) {
			//x, y, z, strength, explosion.getAffectedBlockPositions()
			buf.writeDouble(nt.explosionX);
			buf.writeDouble(nt.explosionY);
			buf.writeDouble(nt.explosionZ);
			buf.writeFloat(nt.explosionSize);
			List<BlockPos> poses = nt.affectedBlockPositions;
			buf.writeInt(poses.size());
			for (BlockPos p : poses)
				buf.writeVec3i(p);
			buf.writeInt(nt.attributes.size());
			for (ExAttrib a : nt.attributes)
				buf.writeByte(a.ordinal());
		}
		@Override
		@SideOnly(Side.CLIENT)
		public @Nullable Consumer<MessageContext> decode(LeafiaBuf buf) {
			World world = Minecraft.getMinecraft().world;
			double x = buf.readDouble();
			double y = buf.readDouble();
			double z = buf.readDouble();
			float size = buf.readFloat();
			int affecteds = buf.readInt();
			List<BlockPos> affected = new ArrayList<>(affecteds);
			for (int i = 0; i < affecteds; i++)
				affected.add(buf.readPos());
			int attribs = buf.readInt();
			List<ExAttrib> attrib = new ArrayList<>(attribs);
			for (int i = 0; i < attribs; i++)
				attrib.add(ExAttrib.values()[buf.readByte()]);
			return (ctx)->{
				ExplosionNT nt = new ExplosionNT(world,null,x,y,z,size,affected);
				nt.addAllAttrib(attrib);
				nt.doNTExplosionB();
			};
		}
	}
	
	private void doNTExplosionA() {
		float f = this.explosionSize;
		HashSet<BlockPos> hashset = new HashSet();
		int endX;
		int endY;
		int endZ;
		double curX;
		double curY;
		double curZ;
		HashSet<BlockPos> hashset2 = null;
		if (has(ExAttrib.DFC_FALL))
			hashset2 = new HashSet<>();

		for(endX = 0; endX < this.resolution; ++endX) {
			for(endY = 0; endY < this.resolution; ++endY) {
				for(endZ = 0; endZ < this.resolution; ++endZ) {
					/// basically hollow box
					if(endX == 0 || endX == this.resolution- 1 || endY == 0 || endY == this.resolution- 1 || endZ == 0 || endZ == this.resolution- 1) {
						double ratioX = (double) ((float) endX / ((float) this.resolution- 1.0F) * 2.0F - 1.0F);
						double ratioY = (double) ((float) endY / ((float) this.resolution- 1.0F) * 2.0F - 1.0F);
						double ratioZ = (double) ((float) endZ / ((float) this.resolution- 1.0F) * 2.0F - 1.0F);
						double distance = Math.sqrt(ratioX * ratioX + ratioY * ratioY + ratioZ * ratioZ);
						ratioX /= distance;
						ratioY /= distance;
						ratioZ /= distance;
						float power = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
						curX = this.explosionX;
						curY = this.explosionY;
						curZ = this.explosionZ;

						IBlockState lastBlock = Blocks.AIR.getDefaultState();
						IBlockState lastBlock2 = Blocks.AIR.getDefaultState();
						int i = 0;
						for(float weaken = 0.3F; power > 0.0F; power -= weaken * 0.75F) {
							int j1 = MathHelper.floor(curX);
							int k1 = MathHelper.floor(curY);
							int l1 = MathHelper.floor(curZ);
							BlockPos pos = new BlockPos(j1, k1, l1);
							curX += ratioX * (double) weaken;
							curY += ratioY * (double) weaken;
							curZ += ratioZ * (double) weaken;
							if (ignoreBlockPoses.contains(pos))
								continue;
							i++;
							if (i > iterationLimit && iterationLimit >= 0)
								break;

							IBlockState block = this.worldObj.getBlockState(pos);

							if(block.getMaterial() != Material.AIR) {
								float resistance = this.exploder != null ? this.exploder.getExplosionResistance(this, this.worldObj, new BlockPos(j1, k1, l1), block) : block.getBlock().getExplosionResistance(worldObj, new BlockPos(j1, k1, l1), (Entity) null, this);
								if (maxExplosionResistance >= 0 && resistance > maxExplosionResistance)
									power = 0;
								power -= (resistance + 0.3F) * weaken;
								if (has(ExAttrib.DFC_FALL) && power > 0) {
									if (lastBlock.getMaterial() == Material.AIR || lastBlock2.getMaterial() == Material.AIR) {
										hashset2.add(pos);
									} else {
										lastBlock2 = lastBlock;
										lastBlock = block;
										continue;
									}
								}
							}

							if(power > 0.0F && (this.exploder == null || this.exploder.canExplosionDestroyBlock(this, this.worldObj, new BlockPos(j1, k1, l1), block, power))) {
								hashset.add(new BlockPos(j1, k1, l1));
							}

							lastBlock2 = lastBlock;
							lastBlock = block;

						}
					}
				}
			}
		}

		this.affectedBlockPositions.addAll(hashset);
		if (has(ExAttrib.DFC_FALL)) {
			fallBlocks = new ArrayList<>();
			fallBlocks.addAll(hashset2);
		}

		if(!has(ExAttrib.NOHURT)) {

			this.explosionSize *= 2.0F;
			endX = MathHelper.floor(this.explosionX - (double) this.explosionSize - 1.0D);
			endY = MathHelper.floor(this.explosionX + (double) this.explosionSize + 1.0D);
			endZ = MathHelper.floor(this.explosionY - (double) this.explosionSize - 1.0D);
			int i2 = MathHelper.floor(this.explosionY + (double) this.explosionSize + 1.0D);
			int l = MathHelper.floor(this.explosionZ - (double) this.explosionSize - 1.0D);
			int j2 = MathHelper.floor(this.explosionZ + (double) this.explosionSize + 1.0D);
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double) endX, (double) endZ, (double) l, (double) endY, (double) i2, (double) j2));
			net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.worldObj, this, list, this.explosionSize);
			Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);

			for(int i1 = 0; i1 < list.size(); ++i1) {
				Entity entity = (Entity) list.get(i1);
				double d4 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double) this.explosionSize;

				if(d4 <= 1.0D) {
					curX = entity.posX - this.explosionX;
					curY = entity.posY + (double) entity.getEyeHeight() - this.explosionY;
					curZ = entity.posZ - this.explosionZ;
					double d9 = (double) MathHelper.sqrt(curX * curX + curY * curY + curZ * curZ);

					if(d9 != 0.0D) {
						curX /= d9;
						curY /= d9;
						curZ /= d9;
						double d10 = (double) this.worldObj.getBlockDensity(new Vec3d(vec3.xCoord, vec3.yCoord, vec3.zCoord), entity.getEntityBoundingBox());
						double d11 = (1.0D - d4) * d10;
						entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float) ((int) ((d11 * d11 + d11) / 2.0D * 8.0D * (double) this.explosionSize + 1.0D)));
						double d8 = d11;
						if(entity instanceof EntityLivingBase)
							d8 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase) entity, d11);
						entity.motionX += curX * d8;
						entity.motionY += curY * d8;
						entity.motionZ += curZ * d8;

						if(entity instanceof EntityPlayer) {
							this.affectedEntities.put((EntityPlayer) entity, Vec3.createVectorHelper(curX * d11, curY * d11, curZ * d11));
						}
					}
				}
			}

			this.explosionSize = f;
		}
	}

	private void doNTExplosionB() {
		if(!has(ExAttrib.NOSOUND))
			this.worldObj.playSound(null, this.explosionX, this.explosionY, this.explosionZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		if(!has(ExAttrib.NOPARTICLE)) {
			if(this.explosionSize >= 2.0F) {
				this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
			} else {
				this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
			}
		}

		Iterator<BlockPos> iterator;
		BlockPos chunkposition;
		int i;
		int j;
		int k;
		IBlockState block;
		if (!worldObj.isRemote) {
			if (has(ExAttrib.DFC_FALL) && fallBlocks != null) {
				for (BlockPos pos : fallBlocks) {
					IBlockState state = worldObj.getBlockState(pos);
					boolean destroy = false;
					//if (worldObj.getBlockState(pos.down()).getMaterial().isReplaceable()) {
					boolean canFall = true;
					BlockPos checkPos = pos;
					while (true) {
						if (!fallBlocks.contains(checkPos)) {
							canFall = false;
							break;
						}
						if (worldObj.getBlockState(checkPos.down()).getMaterial().isReplaceable())
							break;
						else {
							checkPos = checkPos.down();
						}
					}
					if (canFall) {
						Block bluk = state.getBlock();
						if (LeafiaUtil.isSolidVisibleCube(state)) {
							if (bluk instanceof ITileEntityProvider)
								destroy = true;
							else {
								if (worldObj.rand.nextInt(3) > 0) {
									worldObj.setBlockToAir(pos);
									EntityFallingBlock fallingBlock = new EntityFallingBlock(worldObj,pos.getX()+0.5,pos.getY(),pos.getZ()+0.5,state);
									fallingBlock.fallTime = 1;
									worldObj.spawnEntity(fallingBlock);
								} else
									destroy = true;
							}
						} else destroy = true;
					} else
						destroy = true;
					//} else
					//	destroy = true;
					if (destroy)
						worldObj.setBlockToAir(pos);
				}
			}
		}
		if (!has(ExAttrib.DFC_FALL)) {
			iterator = this.affectedBlockPositions.iterator();
			while (iterator.hasNext()) {
				chunkposition = iterator.next();
				i = chunkposition.getX();
				j = chunkposition.getY();
				k = chunkposition.getZ();
				block = this.worldObj.getBlockState(chunkposition);

				if (!has(ExAttrib.NOPARTICLE)) {
					double d0 = (double) ((float) i+this.worldObj.rand.nextFloat());
					double d1 = (double) ((float) j+this.worldObj.rand.nextFloat());
					double d2 = (double) ((float) k+this.worldObj.rand.nextFloat());
					double d3 = d0-this.explosionX;
					double d4 = d1-this.explosionY;
					double d5 = d2-this.explosionZ;
					double d6 = (double) MathHelper.sqrt(d3*d3+d4*d4+d5*d5);
					d3 /= d6;
					d4 /= d6;
					d5 /= d6;
					double d7 = 0.5D/(d6/(double) this.explosionSize+0.1D);
					d7 *= (double) (this.worldObj.rand.nextFloat()*this.worldObj.rand.nextFloat()+0.3F);
					d3 *= d7;
					d4 *= d7;
					d5 *= d7;
					this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,(d0+this.explosionX*1.0D)/2.0D,(d1+this.explosionY*1.0D)/2.0D,(d2+this.explosionZ*1.0D)/2.0D,d3,d4,d5);
					this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,d0,d1,d2,d3,d4,d5);
				}
				if (!worldObj.isRemote) {
					if (block.getMaterial() != Material.AIR) {
						if (block.getBlock().canDropFromExplosion(this) && !has(ExAttrib.NODROP)) {
							float chance = 1.0F;

							if (!has(ExAttrib.ALLDROP))
								chance = 1.0F/this.explosionSize;

							block.getBlock().dropBlockAsItemWithChance(this.worldObj,chunkposition,this.worldObj.getBlockState(chunkposition),chance,0);
						}

						block.getBlock().onBlockExploded(this.worldObj,new BlockPos(i,j,k),this);

						if (block.isNormalCube()) {

							if (has(ExAttrib.DIGAMMA)) {
								this.worldObj.setBlockState(new BlockPos(i,j,k),ModBlocks.ash_digamma.getDefaultState());

								if (this.explosionRNG.nextInt(5) == 0 && this.worldObj.getBlockState(new BlockPos(i,j+1,k)).getBlock() == Blocks.AIR)
									this.worldObj.setBlockState(new BlockPos(i,j+1,k),ModBlocks.fire_digamma.getDefaultState());

							} else if (has(ExAttrib.DIGAMMA_CIRCUIT)) {

								if (i%3 == 0 && k%3 == 0) {
									this.worldObj.setBlockState(new BlockPos(i,j,k),ModBlocks.pribris_digamma.getDefaultState());
								} else if ((i%3 == 0 || k%3 == 0) && this.explosionRNG.nextBoolean()) {
									this.worldObj.setBlockState(new BlockPos(i,j,k),ModBlocks.pribris_digamma.getDefaultState());
								} else {
									this.worldObj.setBlockState(new BlockPos(i,j,k),ModBlocks.ash_digamma.getDefaultState());

									if (this.explosionRNG.nextInt(5) == 0 && this.worldObj.getBlockState(new BlockPos(i,j+1,k)).getBlock() == Blocks.AIR)
										this.worldObj.setBlockState(new BlockPos(i,j+1,k),ModBlocks.fire_digamma.getDefaultState());
								}
							} else if (has(ExAttrib.LAVA_V)) {
								this.worldObj.setBlockState(new BlockPos(i,j,k),ModBlocks.volcanic_lava_block.getDefaultState());
							}
						}
					}
				}
			}
			if (!worldObj.isRemote) {
				if (has(ExAttrib.FIRE) || has(ExAttrib.BALEFIRE) || has(ExAttrib.LAVA)) {
					iterator = this.affectedBlockPositions.iterator();

					while (iterator.hasNext()) {
						chunkposition = (BlockPos) iterator.next();
						i = chunkposition.getX();
						j = chunkposition.getY();
						k = chunkposition.getZ();
						block = this.worldObj.getBlockState(chunkposition);
						IBlockState block1 = this.worldObj.getBlockState(new BlockPos(i,j-1,k));

						boolean shouldReplace = true;

						if (!has(ExAttrib.ALLMOD))
							shouldReplace = this.explosionRNG.nextInt(3) == 0;

						if (block.getMaterial() == Material.AIR && block1.isFullBlock() && shouldReplace) {
							if (has(ExAttrib.FIRE))
								this.worldObj.setBlockState(chunkposition,Blocks.FIRE.getDefaultState());
							else if (has(ExAttrib.BALEFIRE))
								this.worldObj.setBlockState(chunkposition,ModBlocks.balefire.getDefaultState());
							else if (has(ExAttrib.LAVA))
								this.worldObj.setBlockState(chunkposition,Blocks.FLOWING_LAVA.getDefaultState());
						}
					}
				}
			}
		}
	}

	public Map func_77277_b() {
		return this.affectedEntities;
	}

	public EntityLivingBase getExplosivePlacedBy() {
		return this.exploder == null ? null : (this.exploder instanceof EntityTNTPrimed ? ((EntityTNTPrimed) this.exploder).getTntPlacedBy() : (this.exploder instanceof EntityLivingBase ? (EntityLivingBase) this.exploder : null));
	}

	// unconventional name, sure, but it's short
	public boolean has(ExAttrib attrib) {
		return this.attributes.contains(attrib);
	}

	// this solution is a bit hacky but in the end easier to work with
	public static enum ExAttrib {
		FIRE,		//classic vanilla fire explosion
		BALEFIRE,	//same with but with balefire
		DIGAMMA,
		DIGAMMA_CIRCUIT,
		LAVA,		//again the same thing but lava
		LAVA_V,		//again the same thing but volcaniclava
		ALLMOD,		//block placer attributes like fire are applied for all destroyed blocks
		ALLDROP,	//miner TNT!
		NODROP,		//the opposite
		NOPARTICLE,
		NOSOUND,
		NOHURT,
		DFC_FALL
	}

}