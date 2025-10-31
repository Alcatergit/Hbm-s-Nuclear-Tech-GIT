package com.leafia.contents.machines.reactors.pwr.blocks.components.terminal;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentEntity;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PWRTerminalBlock extends BlockMachineBase implements ITooltipProvider, PWRComponentBlock, IRadResistantBlock {
	public PWRTerminalBlock() {
		super(Material.IRON,ModBlocks.PWR.guiID,"reactor_hatch");
		this.setTranslationKey("pwr_terminal");
		this.setCreativeTab(MainRegistry.machineTab);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.onBlockAdded(worldIn, pos, state);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
		MachineTooltip.addMultiblock(tooltip);
		MachineTooltip.addModular(tooltip);
		addStandardInfo(tooltip);
		super.addInformation(stack,player,tooltip,advanced);
		tooltip.add("§2[" + I18nUtil.resolveKey("trait.radshield") + "]");
		float hardness = this.getExplosionResistance(null);
		if(hardness > 50){
			tooltip.add("§6" + I18nUtil.resolveKey("trait.blastres", hardness));
		}
		MachineTooltip.addUpdate(tooltip,"tile.reactor_hatch.name");
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new PWRTerminalTE();
	}

	@Override
	public boolean onBlockActivated(World world,BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		PWRComponentEntity entity = getPWR(world,pos);
		if (entity != null && entity.getLinkedCore() != null) {
			return super.onBlockActivated(world,pos,state,player,hand,facing,hitX,hitY,hitZ);
		}
		return false;
	}

	@Override
	protected boolean rotatable() {
		return true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL; // grrrrwl
	}

	@Override
	public boolean tileEntityShouldCreate(World world,BlockPos pos) {
		return true;
	}
}
