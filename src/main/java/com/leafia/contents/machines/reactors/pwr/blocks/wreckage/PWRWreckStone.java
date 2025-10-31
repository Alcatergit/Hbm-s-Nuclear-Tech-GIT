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

public class PWRWreckStone extends PWRMeshedWreck {
	//public static final HFRWavefrontObject mesh = meshFromString("wreck_stone");
	public static final VariationGroup variations = new VariationGroupConstructor()
			.setErosion(Erosion.NONE)
			.addVariation("intact",(WreckBound[])null)
			.setErosion(Erosion.SLIGHT)
			.addVariation("stone_slight_0",new WreckBound(0,0,2,2,0,1.9))
			.addVariation("stone_slight_1",new WreckBound(0,0,2,2,0,1.9))
			.addVariation("stone_slight_2",new WreckBound(0,0,2,2,0,1.9))
			.setErosion(Erosion.NORMAL)
			.addVariation("wreck_stone",new WreckBound(0,0,2,2,0,1.75))
			//.addVariation("wreck_stone_2",(WreckBound[])null)
			.addVariation("wreck_stone_3",new WreckBound(0,0,2,2,0,1.6))
			.setErosion(Erosion.RUBBLE)
			.addVariation("stone_rubble_0_flip",
					WreckBound.blend(-5,-5,-1,-1,0,0.75),
					WreckBound.blend(-5,-1,-1,3,0,0.5),
					WreckBound.blend(-1,-5,3,-3,0,0.5),
					WreckBound.blend(2,-3,5,1,0,0.75),
					WreckBound.blend(-2,0,2,3,0,0.75),
					WreckBound.blend(1,3,5,5,0,0.5)
			)
			.addVariation("stone_rubble_1",
					WreckBound.blend(-5,-4,5,5,0,0.5)
			)
			.addVariation("stone_rubble_2",
					WreckBound.blend(-5,1,4,5,0,0.75),
					WreckBound.blend(-5,-5,5,1,0,0.5),
					WreckBound.blend(-2,-4,4,1,0,2)
			)
			.addVariation("stone_rubble_3",
					new WreckBound(0.7,0.41,0.6,0.8,0,0.15),
					WreckBound.blend(0,-1,4,3.5,0,0.75),
					WreckBound.blend(-3,1,4,3.5,0,0.75)
			)
			.compile();
	public PWRWreckStone() {
		super(Material.IRON,SoundType.STONE,"pwrwreck_stone");
		setCreativeTab(null);
	}

	@Override
	public Item getDebrisItem() {
		return ModItems.pwr_piece;
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
			((PWRMeshedWreckEntity) entity).resourceLocation = "hbm:brick_concrete";
			((PWRMeshedWreckEntity) entity).variation = world.rand.nextInt(variations.NORMAL.length);
			((PWRMeshedWreckEntity) entity).scorch = world.rand.nextInt(8);
			entity.markDirty();
		}
	}
}
