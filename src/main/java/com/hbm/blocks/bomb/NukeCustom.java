package com.hbm.blocks.bomb;

import java.util.List;

import com.hbm.inventory.RecipesCommon;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityCloudSolinium;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.grenade.EntityGrenadeZOMG;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.projectile.EntityFallingNuke;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NukeCustom extends BlockNukeBase {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public NukeCustom(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityNukeCustom();
	}

	@Override
	protected Item getBlockItem() {
		return Item.getItemFromBlock(ModBlocks.nuke_custom);
	}

	@Override
	protected Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityNukeCustom.class;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;

		} else if (!player.isSneaking()) {

			TileEntityNukeCustom entity = (TileEntityNukeCustom) world.getTileEntity(pos);

			if (entity != null) {
				player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_custom, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;

		} else {
			return false;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (world.getRedstonePowerFromNeighbors(pos) > 0 && !world.isRemote) {
			// ========== New: Check detonation conditions ==========
			TileEntityNukeCustom entity = (TileEntityNukeCustom) world.getTileEntity(pos);
			// Check if at least one explosive type has a value greater than zero (TNT, nuclear, hydrogen, etc.)
			if(entity != null && (entity.tnt > 0 || entity.nuke > 0 || entity.hydro > 0 ||
				entity.bale > 0 || entity.schrab > 0 || entity.sol > 0 || entity.euph > 0)) {
				this.explode(world, pos);
			}
			// ========== End: Do nothing if detonation conditions are not met ==========
		}
	}

	public static void explodeCustom(World world, double xCoord, double yCoord, double zCoord, float tnt, float nuke, float hydro, float bale, float dirty, float schrab, float sol, float euph) {

		dirty = Math.min(dirty, BombConfig.maxCustomDirtyRadius);

		/// EUPHEMIUM ///
		if(euph > 0) {

			euph = Math.min(euph, BombConfig.maxCustomEuphLvl);
			EntityGrenadeZOMG zomg = new EntityGrenadeZOMG(world, xCoord, yCoord, zCoord);
			ExplosionChaos.zomg(world, xCoord, yCoord, zCoord, (int)(100 * euph), null, zomg);

		// SOLINIUM ///
		} else if(sol > 0) {

			sol += schrab / 2 + bale / 4 + hydro / 8 + nuke / 16 + tnt / 32;
			sol = Math.min(sol, BombConfig.maxCustomSolRadius);

			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
			entity.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
    		entity.destructionRange = (int) sol;
    		entity.speed = BombConfig.blastSpeed;
    		entity.coefficient = 1.0F;
    		entity.waste = false;
    		entity.extType = 1;
    		world.spawnEntity(entity);

    		EntityCloudSolinium cloud = new EntityCloudSolinium(world, (int)sol);
    		cloud.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
    		world.spawnEntity(cloud);

		// SCHRABIDIUM ///
		} else if(schrab > 0) {

			schrab += bale / 2 + hydro / 4 + nuke / 8 + tnt / 16;
			schrab = Math.min(schrab, BombConfig.maxCustomSchrabRadius);

			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
			entity.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
    		entity.destructionRange = (int) schrab;
    		entity.speed = BombConfig.blastSpeed;
    		entity.coefficient = 1.0F;
    		entity.waste = false;
    		world.spawnEntity(entity);

    		EntityCloudFleija cloud = new EntityCloudFleija(world, (int)schrab);
    		cloud.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
    		world.spawnEntity(cloud);

    	/// ANTIMATTER ///
		} else if(bale > 0) {

			bale += hydro / 2 + nuke / 4 + tnt / 8;
			bale = Math.min(bale, BombConfig.maxCustomBaleRadius);

			EntityBalefire bf = new EntityBalefire(world);
    		bf.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
			bf.destructionRange = (int) bale;
			world.spawnEntity(bf);
			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFacBale(world, xCoord + 0.5, yCoord + 5, zCoord + 0.5, bale);
			}

		/// HYDROGEN ///
		} else if(hydro > 0) {

			hydro += nuke / 2 + tnt / 4;
			hydro = Math.min(hydro, BombConfig.maxCustomHydroRadius);
			dirty *= 0.25F;

			world.spawnEntity(EntityNukeExplosionMK5.statFac(world, (int)hydro, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).moreFallout((int)dirty));
			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, xCoord + 0.5, yCoord + 5, zCoord + 0.5, hydro);
			}

		/// NUCLEAR ///
		} else if(nuke > 0) {

			nuke += tnt / 2;
			nuke = Math.min(nuke, BombConfig.maxCustomNukeRadius);

			world.spawnEntity(EntityNukeExplosionMK5.statFac(world, (int)nuke, xCoord + 0.5, yCoord + 5, zCoord + 0.5).moreFallout((int)dirty));
			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, xCoord + 0.5, yCoord + 5, zCoord + 0.5, nuke);
			}

		/// NON-NUCLEAR ///
		} else if(tnt >= 75) {

			tnt = Math.min(tnt, BombConfig.maxCustomTNTRadius);

			world.spawnEntity(EntityNukeExplosionMK5.statFacNoRad(world, (int)tnt, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5));
			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, xCoord + 0.5, yCoord + 5, zCoord + 0.5, tnt);
			}
		} else if(tnt > 0) {

			ExplosionLarge.explode(world, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, tnt, true, true, true);
		}
	}

	@Override
	public void explode(World world, BlockPos pos) {
		TileEntityNukeCustom entity = (TileEntityNukeCustom) world.getTileEntity(pos);

		// ========== New: Check detonation conditions ==========
		// Check if at least one explosive type has a value greater than zero (TNT, nuclear, hydrogen, etc.)
		if(entity != null && (entity.tnt > 0 || entity.nuke > 0 || entity.hydro > 0 ||
			entity.bale > 0 || entity.schrab > 0 || entity.sol > 0 || entity.euph > 0)) {

			if(!entity.isFalling()) {
				// ========== Modified: Set detonation flag, then clear the block ==========
				this.isExploding = true;
				entity.clearSlots();
				world.setBlockToAir(pos);

				NukeCustom.explodeCustom(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, entity.tnt, entity.nuke, entity.hydro, entity.bale, entity.dirty, entity.schrab, entity.sol, entity.euph);
				this.isExploding = false;

			} else {
				// ========== Modified: Set detonation flag, then clear the block ==========
				this.isExploding = true;
				EntityFallingNuke bomb = new EntityFallingNuke(world, entity.tnt, entity.nuke, entity.hydro, entity.bale, entity.dirty, entity.schrab, entity.sol, entity.euph);
				bomb.getDataManager().set(EntityFallingNuke.FACING, world.getBlockState(pos).getValue(FACING));
				bomb.setPositionAndRotation(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
				entity.clearSlots();
				world.setBlockToAir(pos);
				world.spawnEntity(bomb);
				this.isExploding = false;
			}
		}
		// ========== End: Do nothing if detonation conditions are not met ==========
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
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

		if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§d["+ I18nUtil.resolveKey("trait.modularbomb")+"]§r");

		// ========== New: Temporarily call the condition method from TileEntityNukeCustom ==========
		// Create a temporary TileEntityNukeCustom instance to obtain explosion parameters
		TileEntityNukeCustom tempEntity = new TileEntityNukeCustom();

		// If the item has NBT data, restore explosion parameters from NBT
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

			if (blockEntityTag.hasKey("inventory")) {
				// Calculate explosion parameters by simulating the item placement process
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				if (inventoryTag.hasKey("Items")) {
					NBTTagList itemsList = inventoryTag.getTagList("Items", 10);

					// Temporary simulation update method for calculating explosion parameters
					float tnt = 0F, tntMod = 1F;
					float nuke = 0F, nukeMod = 1F;
					float hydro = 0F, hydroMod = 1F;
					float bale = 0F, baleMod = 1F;
					float dirty = 0F, dirtyMod = 1F;
					float schrab = 0F, schrabMod = 1F;
					float sol = 0F, solMod = 1F;
					float euph = 0F;

					for(int i = 0; i < itemsList.tagCount(); i++) {
						NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);
						ItemStack itemStack = new ItemStack(itemTag);

						if(itemStack.isEmpty())
							continue;

						RecipesCommon.ComparableStack comp = new RecipesCommon.NbtComparableStack(itemStack).makeSingular();
						TileEntityNukeCustom.CustomNukeEntry ent = TileEntityNukeCustom.entries.get(comp);

						if(ent == null)
							continue;

						if(ent.entry == TileEntityNukeCustom.EnumEntryType.ADD) {
							switch(ent.type) {
							case TNT: tnt += ent.value * itemStack.getCount(); break;
							case NUKE: nuke += ent.value * itemStack.getCount(); break;
							case HYDRO: hydro += ent.value * itemStack.getCount(); break;
							case BALE: bale += ent.value * itemStack.getCount(); break;
							case DIRTY: dirty += ent.value * itemStack.getCount(); break;
							case SCHRAB: schrab += ent.value * itemStack.getCount(); break;
							case SOL: sol += ent.value * itemStack.getCount(); break;
							case EUPH: euph += ent.value; break;
							}
						} else if(ent.entry == TileEntityNukeCustom.EnumEntryType.MULT) {
							switch(ent.type) {
							case TNT: tntMod *= ent.value; break;
							case NUKE: nukeMod *= ent.value * itemStack.getCount(); break;
							case HYDRO: hydroMod *= ent.value * itemStack.getCount(); break;
							case BALE: baleMod *= ent.value * itemStack.getCount(); break;
							case DIRTY: dirtyMod *= ent.value * itemStack.getCount(); break;
							case SOL: solMod *= ent.value * itemStack.getCount(); break;
							case SCHRAB: schrabMod *= ent.value * itemStack.getCount(); break;
							}
						}
					}

					// Apply multipliers and set upper limits
					tnt *= tntMod;
					nuke *= nukeMod;
					hydro *= hydroMod;
					bale *= baleMod;
					dirty *= dirtyMod;
					sol *= solMod;
					schrab *= schrabMod;

					if(tnt < 16) nuke = 0;
					if(nuke < 100) hydro = 0;
					if(nuke < 50) bale = 0;
					if(nuke < 50) schrab = 0;
					if(nuke < 25) sol = 0;
					if(schrab < 1 || sol < 1) euph = 0;

					tempEntity.tnt = Math.min(tnt, BombConfig.maxCustomTNTRadius);
					tempEntity.nuke = Math.min(nuke, BombConfig.maxCustomNukeRadius);
					tempEntity.hydro = Math.min(hydro, BombConfig.maxCustomHydroRadius);
					tempEntity.bale = Math.min(bale, BombConfig.maxCustomBaleRadius);
					tempEntity.dirty = Math.min(dirty, BombConfig.maxCustomDirtyRadius);
					tempEntity.schrab = Math.min(schrab, BombConfig.maxCustomSchrabRadius);
					tempEntity.sol = Math.min(sol, BombConfig.maxCustomSolRadius);
					tempEntity.euph = Math.min(euph, BombConfig.maxCustomEuphLvl);
				}
			}
		}

		// ========== Display text in different colors based on priority ==========
		// Priority: tnt < nuke < hydro < bale < schrab < sol < euph
		if ((tempEntity.tnt>0 || tempEntity.nuke>0 || tempEntity.hydro>0 || tempEntity.bale>0 || tempEntity.schrab>0 || tempEntity.sol>0 || tempEntity.euph>0) && isFalling(stack)) {
			tooltip.add("[Is falling]");

		}
		if (tempEntity.euph > 0) {
			// Pink text: Anti Mass
			tooltip.add("§d[Anti Mass]§r");
			tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)tempEntity.euph)+"§r");

		} else if (tempEntity.sol > 0) {
			// Light blue text: Solinium
			tooltip.add("§b[Solinium]§r");
			float solAdj = tempEntity.getSolAdj();
			tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)solAdj)+"§r");

		} else if (tempEntity.schrab > 0) {
			// Cyan text: Schrabidium
			tooltip.add("§3[Schrabidium]§r");
			float schrabAdj = tempEntity.getSchrabAdj();
			tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)schrabAdj)+"§r");

        } else if (tempEntity.bale > 0) {
			// Green text: Balefire
            tooltip.add("§a[Balefire]§r");
            float baleAdj = tempEntity.getBaleAdj();
            tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)baleAdj)+"§r");

        } else if (tempEntity.hydro > 0) {
			// Yellow text: Thermonuclear
			tooltip.add("§e[Thermonuclear]§r");
			float hydroAdj = tempEntity.getHydroAdj();
			boolean isSalted = tempEntity.dirty > 0;
			float moreFallout = tempEntity.dirty;
			tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)hydroAdj)+"§r");
			if(!BombConfig.disableNuclear){
				tooltip.add("§2["+ I18nUtil.resolveKey("trait.fallout")+"§r"+(isSalted ? "§a(+Salted)§r" : "")+"§2]§r");
				tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)(hydroAdj*(1+BombConfig.falloutRange/100.0)+(isSalted ? moreFallout*0.25F : 0)))+"§r");
			}

		} else if (tempEntity.nuke > 0) {
			// Gold text: Nuclear
			tooltip.add("§6[Nuclear]§r");
			float nukeAdj = tempEntity.getNukeAdj();
			boolean isSalted = tempEntity.dirty > 0;
			float moreFallout = tempEntity.dirty;
			tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)nukeAdj)+"§r");
			if(!BombConfig.disableNuclear){
				tooltip.add("§2["+ I18nUtil.resolveKey("trait.fallout")+"§r"+(isSalted ? "§a(+Salted)§r" : "")+"§2]§r");
				tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)(nukeAdj*(1+BombConfig.falloutRange/100.0)+(isSalted ? moreFallout : 0)))+"§r");
			}

		} else if (tempEntity.tnt > 0) {
			if (tempEntity.tnt >= 75) {
				// Red text: TNT (>=75)
				tooltip.add("§c[TNT]§r");
				tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)tempEntity.tnt)+"§r");
			} else {
				// Dark green text: TNT (<75)
				tooltip.add("§2[TNT]§r");
				tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)tempEntity.tnt)+"§r");
			}
		}
	}

	private boolean isFalling(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
			NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

			if (blockEntityTag.hasKey("inventory")) {
				NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

				// Check if it contains the Items tag (the serialization format of ItemStackHandler)
				if (inventoryTag.hasKey("Items")) {
					NBTTagList itemsList = inventoryTag.getTagList("Items", 10);

					boolean hasCustomFall = false;

					for(int i = 0; i < itemsList.tagCount(); i++) {
						NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);
						ItemStack itemStack = new ItemStack(itemTag);

						// Check if it is a custom_fall item
						if(itemStack.getItem() == ModItems.custom_fall) {
							hasCustomFall = true;
						}
					}
					return hasCustomFall;
				}
			}
		}
		return false;
	}
}
