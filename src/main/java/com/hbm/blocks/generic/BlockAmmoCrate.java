package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import static com.hbm.items.ItemEnums.EnumAmmo;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockAmmoCrate extends Block {

	public BlockAmmoCrate(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
	
	Random rand = new Random();
	
	@Override
	public void getDrops(NonNullList<ItemStack> ret, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		ret.add(new ItemStack(ModItems.cap_nuka, 12 + rand.nextInt(21)));
        ret.add(new ItemStack(ModItems.syringe_metal_stimpak, 1 + rand.nextInt(3)));
        
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_standard, 16 + rand.nextInt(17), EnumAmmo.P22.ordinal()));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_standard, 6 + rand.nextInt(13), EnumAmmo.P9.ordinal()));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_standard, 6 + rand.nextInt(4), EnumAmmo.G12.ordinal()));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_standard, 3 + rand.nextInt(4), EnumAmmo.G20.ordinal()));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.gun_revolver_ammo, 10 + rand.nextInt(11)));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.gun_revolver_iron_ammo, 12 + rand.nextInt(15)));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_standard, 2 + rand.nextInt(7), EnumAmmo.BMG50.ordinal()));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.ROCKET.ordinal()));
        if(rand.nextBoolean()) ret.add(new ItemStack(ModItems.ammo_standard, 1 + rand.nextInt(2), EnumAmmo.GRENADE.ordinal()));

        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 3, EnumAmmo.G12_INCENDIARY.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 3, EnumAmmo.G20_INCENDIARY.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 3, EnumAmmo.G20_CAUSTIC.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 3, EnumAmmo.G20_FLECHETTE.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 7, EnumAmmo.P9_AP.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.ROCKET_INCENDIARY.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.ROCKET_SLEEK.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.GRENADE_HE.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.GRENADE_INCENDIARY.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.GRENADE_SLEEK.ordinal()));
        if(rand.nextInt(10) == 0) ret.add(new ItemStack(ModItems.syringe_metal_super, 2));
	}

}
