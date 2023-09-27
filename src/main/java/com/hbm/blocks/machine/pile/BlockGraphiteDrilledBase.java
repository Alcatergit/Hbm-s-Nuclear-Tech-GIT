package com.hbm.blocks.machine.pile;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockHazardFuel;
import com.hbm.items.ModItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGraphiteDrilledBase extends BlockHazardFuel {

	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class);

	public BlockGraphiteDrilledBase(String s) {
		super(ModBlocks.block_graphite.getDefaultState().getMaterial(), s, ((BlockHazardFuel) ModBlocks.block_graphite).encouragement, ((BlockHazardFuel) ModBlocks.block_graphite).flammability, 16000);
		this.setCreativeTab(null);
		this.setSoundType(SoundType.METAL);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
	}
	
	protected static void ejectItem(World world, int x, int y, int z, EnumFacing dir, ItemStack stack) {
		
		EntityItem dust = new EntityItem(world, x + 0.5D + dir.getFrontOffsetX() * 0.75D, y + 0.5D + dir.getFrontOffsetY() * 0.75D, z + 0.5D + dir.getFrontOffsetZ() * 0.75D, stack);
		dust.motionX = dir.getFrontOffsetX() * 0.25;
		dust.motionY = dir.getFrontOffsetY() * 0.25;
		dust.motionZ = dir.getFrontOffsetZ() * 0.25;
		world.spawnEntity(dust);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return Items.AIR;
	}
	
	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
		drops.add(new ItemStack(ModItems.ingot_graphite, 8));
	}
	
	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(AXIS).ordinal();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta){
		return this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.values()[meta&3]);
	}
	
	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, AXIS);
	}
}
