package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityHadronDiode;
import com.hbm.tileentity.machine.TileEntityHadronDiode.DiodeConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHadronDiode extends BlockContainer {

	public static final PropertyInteger[] textures = new PropertyInteger[6];
	static {
		for(int i = 0; i < textures.length; i ++){
			textures[i] = PropertyInteger.create(EnumFacing.VALUES[i].getName2(), 0, 2);
		}
	}
	
	public BlockHadronDiode(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityHadronDiode();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.getHeldItem(hand).getItem() == ModItems.screwdriver) {

    		if(!world.isRemote) {
    			TileEntityHadronDiode diode = (TileEntityHadronDiode) world.getTileEntity(pos);
    			int config = diode.getConfig(facing.ordinal()).ordinal();
    			config += 1;
    			config %= DiodeConfig.values().length;
    			diode.setConfig(facing.ordinal(), config);
    			resetBlockState(world, pos);
    		}

			//world.markBlockRangeForRenderUpdate(pos, pos);

    		return true;
    	} else {
    		return false;
    	}
	}
	
	public static void resetBlockState(World world, BlockPos pos){
		TileEntityHadronDiode diode = (TileEntityHadronDiode) world.getTileEntity(pos);
		IBlockState newState = ModBlocks.hadron_diode.getDefaultState();
		for(int i = 0; i < 6; i++){
			newState = newState.withProperty(BlockHadronDiode.textures[i], diode.sides[i].ordinal());
		}
		world.setBlockState(pos, newState);
		diode.validate();
		world.setTileEntity(pos, diode);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, textures);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}
	
}
