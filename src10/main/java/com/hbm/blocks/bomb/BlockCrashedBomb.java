package com.hbm.blocks.bomb;

import java.util.List;
import java.util.Random;

import api.hbm.block.IToolable;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.main.MainRegistry;
import com.hbm.util.EnchantmentUtil;
import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.TileEntityCrashedBomb;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;


public class BlockCrashedBomb extends BlockContainer implements IBomb, IToolable {

    public static final int chanceDefuse = 100;
    public static final int chanceDefuseDesh = 1000;
    public static final int chanceJump = 10;
    public static final int chanceMine = 5;

    public enum EnumDudType implements IStringSerializable {
        BALEFIRE("bale"),
        CONVENTIONAL("n2"),
        NUKE("nuke"),
        SALTED("salt");

        private final String name;
        EnumDudType(String n){
            this.name = n;
        }

        @Override
        public @NotNull String getName() {
            return this.name;
        }
    }

    public static final PropertyEnum<BlockCrashedBomb.EnumDudType> VARIANT = PropertyEnum.<BlockCrashedBomb.EnumDudType>create("variant", BlockCrashedBomb.EnumDudType.class);

	public BlockCrashedBomb(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumDudType.BALEFIRE));
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCrashedBomb();
	}

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (int i = 0; i < 4; ++i){
            items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        if(fallDistance > 1 && worldIn.rand.nextInt(chanceJump) == 0) explode(worldIn, pos);
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.AIR;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if(!player.capabilities.isCreativeMode){
            if(worldIn.rand.nextInt(chanceMine) == 0 && !EnchantmentUtil.hasEnchantment(player.getHeldItemMainhand(), Enchantments.SILK_TOUCH)) explode(worldIn, pos);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }


    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        ItemStack itemstack = placer.getHeldItem(hand);
        if(itemstack.getItem() == Item.getItemFromBlock(this)){
            return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, itemstack.getItemDamage(), placer, hand);
        }
        return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
    }

    @Override
    public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, EnumFacing side, float fX, float fY, float fZ, EnumHand hand, ToolType tool) {
        if(tool != ToolType.DEFUSER) return false;
        if(!world.isRemote) {
            Item toolItem = player.getHeldItem(hand).getItem();
            int detChance = chanceDefuseDesh;
            if(toolItem.getMaxDamage(player.getHeldItem(hand)) > 0) {
                detChance = chanceDefuse;
            }
            BlockPos pos = new BlockPos(x, y, z);
            if(world.rand.nextInt(detChance) == 0 && !EnchantmentUtil.hasEnchantment(player.getHeldItem(hand), Enchantments.SILK_TOUCH)) {
                explode(world, pos);
                return true;
            }

            int type = getMetaFromState(world.getBlockState(pos));
            world.destroyBlock(pos, false);

            if(type == 0) {
                world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.egg_balefire_shard)));
                world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.plate_steel, 10 + world.rand.nextInt(15))));
                world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.plate_titanium, 2 + world.rand.nextInt(7))));
            } else if(type == 1) {
                world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.ball_tnt, 16)));
            } else if(type == 2) {
                world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.ball_tnt, 8)));
                world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.billet_plutonium, 4)));
            } else if(type == 3) {
                world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.ball_tnt, 8)));
                world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.billet_plutonium, 2)));
                world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(ModItems.ingot_cobalt, 12)));
            }
            return true;
        }
        return true;
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
		return new BlockStateContainer(this, VARIANT);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).ordinal();
	}

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }
	
	@Override
	public @NotNull IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, EnumDudType.values()[meta]);
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        if(stack.getItemDamage() == 0) {
            tooltip.add("§a[" + I18nUtil.resolveKey("trait.balefirebomb") + "]" + "§r");
            tooltip.add(" §e" + I18nUtil.resolveKey("desc.radius", (int) (BombConfig.fatmanRadius * 1.25)) + "§r");
        } else if(stack.getItemDamage() == 1) {
            tooltip.add("§c[" + I18nUtil.resolveKey("trait.extremebomb") + "]" + "§r");
            tooltip.add(" §e" + I18nUtil.resolveKey("desc.radius", 40) + "§r");
        } else if(stack.getItemDamage() == 2) {
            tooltip.add("§a[" + I18nUtil.resolveKey("trait.nuclearbomb") + "]" + "§r");
            tooltip.add(" §e" + I18nUtil.resolveKey("desc.radius", 35) + "§r");
            if(!BombConfig.disableNuclear){
                tooltip.add("§2[Fallout]§r");
                tooltip.add(" §aRadius: "+70+"m§r");
            }
        } else if(stack.getItemDamage() == 3) {
            tooltip.add("§a[" + I18nUtil.resolveKey("trait.nuclearbomb") + "]" + "§r");
            tooltip.add(" §e" + I18nUtil.resolveKey("desc.radius", 25) + "§r");
            if(!BombConfig.disableNuclear){
                tooltip.add("§2[Fallout]§r");
                tooltip.add(" §aRadius: "+75+"m§r");
            }
        }
	}

	@Override
	public void explode(World world, BlockPos pos) {
		if (!world.isRemote) {

            int type = getMetaFromState(world.getBlockState(pos));
            world.setBlockToAir(pos);

            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            if(type == 0) {
                EntityBalefire bf = new EntityBalefire(world);
                bf.posX = x;
                bf.posY = y;
                bf.posZ = z;
                bf.destructionRange = (int) (BombConfig.fatmanRadius * 1.25);
                world.spawnEntity(bf);

                if(BombConfig.enableNukeClouds) {
                    EntityNukeTorex.statFacBale(world, pos.getX() + 0.5, pos.getY() + 5, pos.getZ() + 0.5, (int) (BombConfig.fatmanRadius * 1.25));
                }
            } else if(type == 1) {
                world.spawnEntity(EntityNukeExplosionMK5.statFacNoRad(world, 40, x + 0.5, y + 0.5, z + 0.5));
                if(BombConfig.enableNukeClouds) {
                    EntityNukeTorex.statFac(world, x + 0.5, y + 5, z + 0.5, 40);
                }
            } else if(type == 2) {
                world.spawnEntity(EntityNukeExplosionMK5.statFac(world, 35, x + 0.5, y + 0.5, z + 0.5));
                if(BombConfig.enableNukeClouds) {
                    if(MainRegistry.polaroidID == 11 || world.rand.nextInt(100) == 0)
                        EntityNukeTorex.statFacBale(world, x + 0.5, y + 5, z + 0.5, 35);
                    else EntityNukeTorex.statFac(world, x + 0.5, y + 5, z + 0.5, 35);
                }
            } else if(type == 3) {
                world.spawnEntity(EntityNukeExplosionMK5.statFac(world, 25, x + 0.5, y + 0.5, z + 0.5).moreFallout(25));
                if(BombConfig.enableNukeClouds) {
                    if(MainRegistry.polaroidID == 11 || world.rand.nextInt(100) == 0)
                        EntityNukeTorex.statFacBale(world, x + 0.5, y + 5, z + 0.5, 25);
                    else EntityNukeTorex.statFac(world, x + 0.5, y + 5, z + 0.5, 25);
                }
            }
        }
	}
}
