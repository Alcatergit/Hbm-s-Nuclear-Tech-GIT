package com.hbm.blocks.test;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.deco.TileEntityTestRender;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

//Drillgon200: The heck is all this german stuff?
//Drillgon200: Anyway, I don't know why I'm even doing this block; it's quite unnecessary.

//Testblock zum Rendern/�ndern der visuellen Eigenschaften eines Tiles (Blockes)

//"extends BlockContainer" um ein TileEntity zu erschaffen

public class TestRender extends BlockContainer {

	// Y U NO WANTED WORKINGS? NAO U WORKINGS AN I KICK UR ARSE

	//Drillgon200: 0.0625 is one pixel in world space; 1/16.
	public static final AxisAlignedBB TEST_RENDER_BB = new AxisAlignedBB(2 * 0.0625F, 0.0F, 2 * 0.0625F, 14 * 0.0625F, 1.0F, 14 * 0.0625F);
	
	// Normaler Matrial-Constructor
	//Drillgon200: I set the unlocalized name and registry name, and add it the all block list so adding a block is easier and only needs one line of code.
	public TestRender(Material p_i45394_1_, String s) {
		super(p_i45394_1_);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.blockTab);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	// Nicht verf�gbarer Rendertyp, setzt den Switch auf "Default" und
	// erm�glicht einen Customrenderer
	// Drillgon200: Not a thing in 1.12.2
	/*
	 * @Override public int getRenderType(){ return -1; }
	 */

	// Ob der Block transparent ist (Glas, Glowstone, Wasser, etc)
	// Drillgon200: Now takes an IBlockState and is depreciated, I don't know.
	/*
	 * @Override public boolean isOpaqueCube() { return false; }
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	// "Default" beim Switch zum Rendern erm�glicht die Abfrage
	// "renderAsNormalBlock", "true" um es als einen normalen Block rendern zu
	// lassen, "false" f�r einen Customrenderer
	/*
	 * @Override public boolean renderAsNormalBlock() { return false; }
	 */
	// Drillgon200: Tells the block it uses full on special rendering
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	//Drillgon200: Needed so the server doesn't go crazy when you enter a "full block" and try to push you out
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	// Erstellen eines TileEntitys
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityTestRender();
	}

	// GUI Blocktextur muss f�r Custommodel-Blocke nachtr�glich ge�ndert
	// werden
	// Drillgon200: Doesn't exist anymore; handled by the JSON model.
	/*
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public void registerBlockIcons(IIconRegister
	 * iconregister) { this.blockIcon =
	 * iconregister.registerIcon(RefStrings.MODID + ":test_render"); }
	 */

	// Setzt die Blockkollisionsbox (xMin, yMin, zMin, xMax, yMax, zMax)
	// Drillgon200: This seems to have been replaced by some other methods,
	// which all appear to be depreciated. It's probably handled in the JSON
	// model or something now, but this method seems to do the trick.
	/*
	 * @Override public void setBlockBoundsBasedOnState(IBlockAccess
	 * p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) { float f
	 * = 0.0625F; this.setBlockBounds(2*f, 0.0F, 2*f, 14*f, 1.0F, 14*f); }
	 */
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return TEST_RENDER_BB;
	}

}
