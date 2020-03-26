package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityNukeCloudBig;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.logic.EntityNukeExplosionPlus;
import com.hbm.entity.projectile.EntityFallingNuke;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NukeCustom extends BlockContainer implements IBomb {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public NukeCustom(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNukeCustom();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityNukeCustom entity = (TileEntityNukeCustom) world.getTileEntity(pos);
			if(entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_custom, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		TileEntityNukeCustom entity = (TileEntityNukeCustom) worldIn.getTileEntity(pos);
		if(worldIn.isBlockIndirectlyGettingPowered(pos) > 0 && !worldIn.isRemote) {
			if(entity.isReady()) {
				float[] f = entity.returnAllValues();
				boolean fall = entity.falls;
				EnumFacing e = worldIn.getBlockState(pos).getValue(FACING);
				this.onBlockDestroyedByPlayer(worldIn, pos, worldIn.getBlockState(pos));
				entity.clearSlots();
				worldIn.setBlockToAir(pos);
				igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ(), f, fall, e);
			}
		}
	}

	public boolean igniteTestBomb(World world, int x, int y, int z, float[] f, boolean fall, EnumFacing facing) {
		if(!world.isRemote) {
			float tnt = f[0];
			float nuke = f[1];
			float hydro = f[2];
			float amat = f[3];
			float dirty = f[4];
			float schrab = f[5];
			float euph = f[6];

			if(!fall) {

				explodeCustom(world, x + 0.5, y + 0.5, z + 0.5, tnt, nuke, hydro, amat, dirty, schrab, euph);
				world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.1F + 0.9F);

			} else {
				EntityFallingNuke bomb = new EntityFallingNuke(world, tnt, nuke, hydro, amat, dirty, schrab, euph);
				bomb.getDataManager().set(EntityFallingNuke.FACING, facing);
				bomb.setPositionAndRotation(x + 0.5, y, z + 0.5, 0, 0);

				world.spawnEntity(bomb);
			}
		}
		return false;
	}

	public static void explodeCustom(World world, double posX, double posY, double posZ, float tnt, float nuke, float hydro, float amat, float dirty, float schrab, float euph) {

		if(euph > 0) {
			EntityGrenadeZOMG zomg = new EntityGrenadeZOMG(world);
			zomg.posX = posX;
			zomg.posY = posY;
			zomg.posZ = posZ;
			ExplosionChaos.zomg(world, posX, posY, posZ, 1000, null, zomg);
		} else if(schrab > 0) {
			nuke += (tnt / 2);
			hydro += (nuke / 2);
			amat += (hydro / 2);
			schrab += (amat / 2);

			if(schrab > 300)
				schrab = 300;

			EntityNukeExplosionPlus entity = new EntityNukeExplosionPlus(world);
			entity.posX = posX;
			entity.posY = posY;
			entity.posZ = posZ;
			entity.destructionRange = (int) schrab;
			entity.speed = MainRegistry.blastSpeed;
			entity.coefficient = 1.0F;
			entity.waste = false;

			world.spawnEntity(entity);

			EntityCloudFleija cloud = new EntityCloudFleija(world, (int) schrab);
			cloud.posX = posX;
			cloud.posY = posY;
			cloud.posZ = posZ;
			world.spawnEntity(cloud);

		} else if(amat > 0) {
			nuke += (tnt / 2);
			hydro += (nuke / 2);
			amat += (hydro / 2);

			if(amat > 350)
				amat = 350;

			EntityNukeExplosionPlus entity = new EntityNukeExplosionPlus(world);
			entity.posX = posX;
			entity.posY = posY;
			entity.posZ = posZ;
			entity.destructionRange = (int) amat;
			entity.speed = 25;
			entity.coefficient = 10.0F;
			entity.wasteRange = (int) (amat * 1.4) + (int) dirty;

			world.spawnEntity(entity);

			if(amat < 75) {
				ExplosionParticleB.spawnMush(world, posX, posY, posZ);
			} else if(amat < 200) {
				if(MainRegistry.enableNukeClouds) {
					EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000, amat * 0.005F);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				} else {
					EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				}
			} else {
				if(MainRegistry.enableNukeClouds) {
					EntityNukeCloudBig entity2 = new EntityNukeCloudBig(world, 1000);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				} else {
					EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				}
			}

		} else if(hydro > 0) {
			nuke += (tnt / 2);
			hydro += (nuke / 2);

			if(hydro > 350)
				hydro = 350;

			EntityNukeExplosionPlus entity = new EntityNukeExplosionPlus(world);
			entity.posX = posX;
			entity.posY = posY;
			entity.posZ = posZ;
			entity.destructionRange = (int) hydro;
			entity.speed = 25;
			entity.coefficient = 10.0F;
			entity.wasteRange = (int) (hydro * 1.4) + (int) dirty;

			world.spawnEntity(entity);

			if(hydro < 75) {
				ExplosionParticle.spawnMush(world, posX, posY, posZ);
			} else if(hydro < 200) {
				if(MainRegistry.enableNukeClouds) {
					EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000, hydro * 0.005F);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				} else {
					EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				}
			} else {
				if(MainRegistry.enableNukeClouds) {
					EntityNukeCloudBig entity2 = new EntityNukeCloudBig(world, 1000);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				} else {
					EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				}
			}

		} else if(nuke > 0) {
			nuke += (tnt / 2);

			if(nuke > 350)
				nuke = 350;

			EntityNukeExplosionPlus entity = new EntityNukeExplosionPlus(world);
			entity.posX = posX;
			entity.posY = posY;
			entity.posZ = posZ;
			entity.destructionRange = (int) nuke;
			entity.speed = 25;
			entity.coefficient = 10.0F;
			entity.wasteRange = (int) (nuke * 1.4) + (int) dirty;

			world.spawnEntity(entity);

			if(nuke < 75) {
				ExplosionParticle.spawnMush(world, posX, posY, posZ);
			} else if(nuke < 200) {
				if(MainRegistry.enableNukeClouds) {
					EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000, nuke * 0.005F);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				} else {
					EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				}
			} else {
				if(MainRegistry.enableNukeClouds) {
					EntityNukeCloudBig entity2 = new EntityNukeCloudBig(world, 1000);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				} else {
					EntityNukeCloudSmall entity2 = new EntityNukeCloudNoShroom(world, 1000);
					entity2.posX = posX;
					entity2.posY = posY;
					entity2.posZ = posZ;
					world.spawnEntity(entity2);
				}
			}

		} else if(tnt > 0) {

			if(tnt > 100)
				tnt = 100;
			ExplosionLarge.explode(world, posX, posY, posZ, tnt, true, true, true);
		}
	}

	@Override
	public void explode(World world, BlockPos pos) {
		TileEntityNukeCustom entity = (TileEntityNukeCustom) world.getTileEntity(pos);
		if(entity.isReady()) {
			float[] f = entity.returnAllValues();
			boolean fall = entity.falls;
			EnumFacing e = world.getBlockState(pos).getValue(FACING);
			this.onBlockDestroyedByPlayer(world, pos, world.getBlockState(pos));
			entity.clearSlots();
			world.setBlockToAir(pos);
			igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ(), f, fall, e);
		}
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
	   return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

}
