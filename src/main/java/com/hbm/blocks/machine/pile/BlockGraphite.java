package com.hbm.blocks.machine.pile;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockHazardFuel;
import com.hbm.items.ModItems;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.ParticleBurstPacket;

import api.hbm.block.IToolable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class BlockGraphite extends BlockHazardFuel implements IToolable {

	
	
	public BlockGraphite(Material mat, String s, int en, int flam, int burntime) {
		super(mat, s, en, flam, burntime);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, EnumFacing side, float fX, float fY, float fZ, EnumHand hand, ToolType tool) {
		if(tool != ToolType.HAND_DRILL)
			return false;
		IBlockState state = world.getBlockState(new BlockPos(x, y, z));
		
		if(!world.isRemote) {
			world.setBlockState(new BlockPos(x, y, z), ModBlocks.block_graphite_drilled.getDefaultState().withProperty(BlockGraphiteDrilledBase.AXIS, side.getAxis()));
			PacketDispatcher.wrapper.sendToAllAround(new ParticleBurstPacket(x, y, z, Block.getIdFromBlock(this), 0), new TargetPoint(world.provider.getDimension(), x, y, z, 50));
			world.playSound(null, x + 0.5, y + 0.5, z + 0.5, this.blockSoundType.getStepSound(), SoundCategory.BLOCKS, (this.blockSoundType.getVolume() + 1.0F) / 2.0F, this.blockSoundType.pitch * 0.8F);

			BlockGraphiteRod.ejectItem(world, x, y, z, side, new ItemStack(ModItems.powder_coal));
		}
		
		return true;
	}
	
}
