package com.leafia.contents.machines;

import com.hbm.blocks.BlockContainerBase;
import com.hbm.lib.InventoryHelper;
import com.hbm.util.I18nUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Reserved6Block extends BlockContainerBase {
	final int slots;
	final ItemStack droppedItem;
	public Reserved6Block(String s,int slots,ItemStack droppedItem) {
		super(Material.IRON,s);
		this.slots = slots;
		this.droppedItem = droppedItem;
		setHardness(1.5F);
		setResistance(10.0F);
		setSoundType(SoundType.STONE);
	}
	@Override
	public @Nullable TileEntity createNewTileEntity(World worldIn,int meta) {
		return new Reserved6TE(slots);
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
	@Override
	public void breakBlock(World worldIn,BlockPos pos,IBlockState state) {
		InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
		spawnAsEntity(worldIn,pos,droppedItem.copy());
	}
	@Override
	public void getDrops(NonNullList<ItemStack> drops,IBlockAccess world,BlockPos pos,IBlockState state,int fortune) {
		//drops.add(droppedItem.copy());
	}
	@Override
	public boolean onBlockActivated(World worldIn,BlockPos pos,IBlockState state,EntityPlayer playerIn,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		if (worldIn.isRemote) {
			playerIn.sendMessage(new TextComponentTranslation("tile.reserved6.desc1"));
			playerIn.sendMessage(new TextComponentTranslation("tile.reserved6.desc2"));
			playerIn.sendMessage(new TextComponentTranslation("tile.reserved6.desc3","§3["+I18nUtil.resolveKey("trait.cleanroom")+"]§r"));
		}
		return true;
	}
}
