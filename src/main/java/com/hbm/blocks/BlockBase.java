package com.hbm.blocks;

import java.util.List;

import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockBase extends Block {
	
	public BlockBase(Material m, String s){
		super(m);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.meteor_battery)){
			tooltip.add("Provides infinite charge to tesla coils");
		}
		if(this == ModBlocks.ore_australium) {
			tooltip.add("Australium ore");
			tooltip.add("Deposit location: X:-400; Z:-400");
			tooltip.add("Estimated quantity: 490");
		}
		
		if(this == ModBlocks.ore_weidanium) {
			tooltip.add("Weidanium ore");
			tooltip.add("Deposit location: X:0; Z:300");
			tooltip.add("Estimated quantity: 2800");
		}
			
		if(this == ModBlocks.ore_reiium) {
			tooltip.add("Reiium ore");
			tooltip.add("Deposit location: X:0; Z:0");
			tooltip.add("Estimated quantity: 2800");
		}
			
		if(this == ModBlocks.ore_unobtainium) {
			tooltip.add("Unobtainium ore");
			tooltip.add("Deposit location: X:200; Z:200");
			tooltip.add("Estimated quantity: 12480");
		}
			
		if(this == ModBlocks.ore_daffergon) {
			tooltip.add("Daffergon ore");
			tooltip.add("Deposit location: X:400; Z:-200");
			tooltip.add("Estimated quantity: 14980");
		}
			
		if(this == ModBlocks.ore_verticium) {
			tooltip.add("Verticium ore");
			tooltip.add("Deposit location: X:-300; Z:200");
			tooltip.add("Estimated quantity: 4680");
		}
	}

	public Block setSoundType(SoundType sound){
		return super.setSoundType(sound);
	}
}
