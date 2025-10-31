package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.inlet;

import com.hbm.blocks.ModBlocks.PWR;
import com.hbm.forgefluid.ModForgeFluids;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.PWRVentBlockBase;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.element.PWRVentElementBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.outlet.PWRVentOutletBlock;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PWRVentInletBlock extends PWRVentBlockBase {
	public PWRVentInletBlock() {
		super(Material.IRON,"pwr_vent_inlet");
		setSoundType(SoundType.METAL);
	}
	@Override
	public boolean tileEntityShouldCreate(World world,BlockPos pos) {
		return false;
	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new PWRVentInletTE();
	}

	@Override
	public boolean correctDirection(World world,BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (!(state.getBlock() instanceof PWRVentBlockBase)) return false;
		EnumFacing face = state.getValue(PWRVentBlockBase.FACING);
		pos = pos.offset(face);
		while (world.isValid(pos)) {
			IBlockState state2 = world.getBlockState(pos);
			if (state2.getBlock() instanceof PWRVentOutletBlock) {
				return (state2.getValue(PWRVentBlockBase.FACING).equals(face));
			}
			if (!(state2.getBlock() instanceof PWRVentElementBlock)) return false;
			rotateTarget(world,pos,face);
			pos = pos.offset(face);
		}
		return false;
	}
	@Override
	public void onBlockPlacedBy(World world,BlockPos pos,IBlockState state,EntityLivingBase placer,ItemStack stack) {
		if(!(placer instanceof EntityPlayer))
			return;
		world.setBlockToAir(pos);
		EnumFacing face = EnumFacing.getDirectionFromEntityLiving(pos,placer);

		EntityPlayer pl = (EntityPlayer) placer;
		EnumHand hand = pl.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;

		if (world.isValid(pos.offset(face.getOpposite()))) {
			IBlockState detect = world.getBlockState(pos.offset(face.getOpposite()));
			if (!detect.getMaterial().isReplaceable()) {
				if (detect.getBlock() instanceof PWRVentElementBlock)
					face = face.getOpposite();
				else
					pos = pos.offset(face);
			}
		} else
			pos = pos.offset(face);

		if (!world.isValid(pos) || !world.isValid(pos.offset(face.getOpposite()))
				|| !world.getBlockState(pos).getMaterial().isReplaceable()
				|| !world.getBlockState(pos.offset(face.getOpposite())).getMaterial().isReplaceable()
		) {
			if(!pl.capabilities.isCreativeMode) {
				ItemStack stk = pl.inventory.mainInventory.get(pl.inventory.currentItem);
				Item item = Item.getItemFromBlock(this);

				if(stk.isEmpty()) {
					pl.inventory.mainInventory.set(pl.inventory.currentItem, new ItemStack(this));
				} else {
					if(stk.getItem() != item || stk.getCount() == stk.getMaxStackSize()) {
						pl.inventory.addItemStackToInventory(new ItemStack(this));
					} else {
						pl.getHeldItem(hand).grow(1);
					}
				}
			}

			return;
		}
		world.setBlockState(pos,state.withProperty(FACING,face));
		world.setBlockState(pos.offset(face.getOpposite()),PWR.ventDuct.getDefaultState());
		correctDirection(world,pos);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof PWRVentInletTE) {
			((PWRVentInletTE) te).rebuildMap();
		}
		if(world.getTileEntity(pos.offset(face.getOpposite())) instanceof PWRVentDuctTE)
			((PWRVentDuctTE)world.getTileEntity(pos.offset(face.getOpposite()))).setType(ModForgeFluids.CRYOGEL);
	}
	@Override
	public void neighborChanged(IBlockState state,World worldIn,BlockPos pos,Block blockIn,BlockPos fromPos) {
		if (!(worldIn.getBlockState(pos.offset(state.getValue(PWRVentBlockBase.FACING).getOpposite())).getBlock() instanceof PWRVentDuctBlock))
			worldIn.setBlockToAir(pos);
	}
	@Override
	public void addInformation(ItemStack stack,World player,List<String> list,ITooltipFlag advanced) {
		MachineTooltip.addWIP(list);
		MachineTooltip.addMultiblock(list);
		MachineTooltip.addModular(list);
		super.addInformation(stack,player,list,advanced);
	}
}