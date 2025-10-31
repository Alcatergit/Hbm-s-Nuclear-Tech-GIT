package com.leafia.contents.machines.reactors.pwr.blocks.wreckage;

import com.hbm.items.ModItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PWRWreckMetal extends PWRMeshedWreck {
	//public static final HFRWavefrontObject mesh = meshFromString("wreck_stone");
	public static final VariationGroup variations = new VariationGroupConstructor()
			.setErosion(Erosion.NONE)
			.addVariation("intact",(WreckBound[])null)
			.setErosion(Erosion.SLIGHT)
			.addVariation("metal_slight_0",new WreckBound(0,0,2,2,0,1.9))
			.addVariation("metal_slight_1",WreckBound.blend(-5,-5,5,5,0,9.5))
			.setErosion(Erosion.NORMAL)
			.addVariation("metal_wreck_0",WreckBound.blend(-5,-5,5,5,0,8))
			.setErosion(Erosion.RUBBLE)
			.addVariation("metal_rubble_0",
					WreckBound.blend(-5,1,4,5,0,0.75),
					WreckBound.blend(-5,-5,5,1,0,0.5),
					WreckBound.blend(-2,-4,4,1,0,2)
			)
			.compile();
	public PWRWreckMetal() {
		super(Material.IRON,SoundType.METAL,"pwrwreck_metal");
		setCreativeTab(null);
	}

	@Override
	public Item getDebrisItem() {
		return ModItems.pwr_shrapnel;
	}

	@Override
	public VariationGroup getVariations() {
		return variations;
	}

	@Override
	public void onBlockPlacedBy(World world,BlockPos pos,IBlockState state,EntityLivingBase placer,ItemStack stack) {
		super.onBlockPlacedBy(world,pos,state,placer,stack);
		TileEntity entity = world.getTileEntity(pos);
		if (entity instanceof PWRMeshedWreckEntity) {
			((PWRMeshedWreckEntity) entity).resourceLocation = "hbm:block_thorium";
			((PWRMeshedWreckEntity) entity).variation = world.rand.nextInt(variations.NORMAL.length);
			((PWRMeshedWreckEntity) entity).scorch = world.rand.nextInt(8);
			entity.markDirty();
		}
	}
}
