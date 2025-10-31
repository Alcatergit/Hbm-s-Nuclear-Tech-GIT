package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Armory;
import com.hbm.items.ModItems.Batteries;
import com.hbm.items.ModItems.Foods;
import com.hbm.items.ModItems.Materials.Ingots;
import com.hbm.items.ModItems.Materials.Nuggies;
import com.hbm.items.ModItems.Materials.Powders;
import com.hbm.items.special.ItemCell;
import com.hbm.lib.HBMSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockCrate extends BlockFalling {

	private static List<ItemStack> crateList;
	private static List<ItemStack> weaponList;
	private static List<ItemStack> leadList;
	private static List<ItemStack> metalList;
	private static List<ItemStack> redList;
	
	public BlockCrate(Material material, String s) {
		super(material);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.getHeldItemMainhand().getItem().equals(ModItems.crowbar))
    	{
    		dropItems(world, pos.getX(), pos.getY(), pos.getZ());
    		world.setBlockToAir(pos);
    		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundEvents.crateBreak, SoundCategory.BLOCKS, 0.5F, 1.0F);
    		return true;
    	} else {
			if(world.isRemote)
			{
				player.sendMessage(new TextComponentTranslation("chat.crate.needcrowbar"));
			}
    	}
    	
    	return true;
	}
	
	public static void setDrops() {

    	crateList = new ArrayList<ItemStack>();
    	weaponList = new ArrayList<ItemStack>();
    	leadList = new ArrayList<ItemStack>();
    	metalList = new ArrayList<ItemStack>();
    	redList = new ArrayList<ItemStack>();

		//Supply Crate
    	BlockCrate.addToListWithWeight(crateList, ModItems.syringe_metal_stimpak, 10);
    	BlockCrate.addToListWithWeight(crateList, ModItems.syringe_antidote, 5);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_revolver_iron, 9);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_revolver, 7);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_revolver_gold, 4);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_revolver_lead, 6);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_revolver_cursed, 5);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_rpg, 5);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_fatman, 1);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_mp40, 7);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_uzi, 7);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_uboinik, 7);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_lever_action, 5);
    	BlockCrate.addToListWithWeight(crateList, Armory.clip_bolt_action, 5);
    	BlockCrate.addToListWithWeight(crateList, Armory.grenade_generic, 8);
    	BlockCrate.addToListWithWeight(crateList, Armory.grenade_strong, 6);
    	BlockCrate.addToListWithWeight(crateList, Armory.grenade_mk2, 4);
    	BlockCrate.addToListWithWeight(crateList, Armory.grenade_flare, 4);
    	BlockCrate.addToListWithWeight(crateList, Armory.ammo_container, 2);
    	
    	//Weapon Crate
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_revolver_iron, 10);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_revolver, 9);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_revolver_gold, 7);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_revolver_lead, 8);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_revolver_cursed, 7);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_calamity, 3);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_rpg, 7);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_karl, 4);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_panzerschreck, 6);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_hk69, 8);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_stinger, 7);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_mp40, 9);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_uzi, 6);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_uzi_silencer, 5);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_uboinik, 8);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_lever_action, 7);
    	BlockCrate.addToListWithWeight(weaponList, Armory.gun_bolt_action, 7);
    	
    	//Lead Crate
    	BlockCrate.addToListWithWeight(leadList, Ingots.ingot_uranium, 10);
    	BlockCrate.addToListWithWeight(leadList, Ingots.ingot_u238, 8);
    	BlockCrate.addToListWithWeight(leadList, Ingots.ingot_plutonium, 7);
    	BlockCrate.addToListWithWeight(leadList, Ingots.ingot_pu240, 6);
    	BlockCrate.addToListWithWeight(leadList, Ingots.ingot_neptunium, 7);
    	BlockCrate.addToListWithWeight(leadList, Ingots.ingot_uranium_fuel, 8);
    	BlockCrate.addToListWithWeight(leadList, Ingots.ingot_plutonium_fuel, 7);
    	BlockCrate.addToListWithWeight(leadList, Ingots.ingot_mox_fuel, 6);
    	BlockCrate.addToListWithWeight(leadList, Nuggies.nugget_uranium, 10);
    	BlockCrate.addToListWithWeight(leadList, Nuggies.nugget_u238, 8);
    	BlockCrate.addToListWithWeight(leadList, Nuggies.nugget_plutonium, 7);
    	BlockCrate.addToListWithWeight(leadList, Nuggies.nugget_pu240, 6);
    	BlockCrate.addToListWithWeight(leadList, Nuggies.nugget_neptunium, 7);
    	BlockCrate.addToListWithWeight(leadList, Nuggies.nugget_uranium_fuel, 8);
    	BlockCrate.addToListWithWeight(leadList, Nuggies.nugget_plutonium_fuel, 7);
    	BlockCrate.addToListWithWeight(leadList, Nuggies.nugget_mox_fuel, 6);
    	BlockCrate.addToListWithWeight(leadList, ItemCell.getFullCell(ModForgeFluids.DEUTERIUM), 8);
    	BlockCrate.addToListWithWeight(leadList, ItemCell.getFullCell(ModForgeFluids.TRITIUM), 8);
    	BlockCrate.addToListWithWeight(leadList, ItemCell.getFullCell(ModForgeFluids.UF6), 8);
    	BlockCrate.addToListWithWeight(leadList, ItemCell.getFullCell(ModForgeFluids.PUF6), 8);
    	BlockCrate.addToListWithWeight(leadList, ModItems.pellet_rtg, 6);
    	BlockCrate.addToListWithWeight(leadList, ModItems.pellet_rtg_weak, 7);
    	BlockCrate.addToListWithWeight(leadList, ModItems.tritium_deuterium_cake, 5);
    	BlockCrate.addToListWithWeight(leadList, Powders.powder_yellowcake, 10);
    	
    	//Metal Crate
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_press), 10);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 9);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_reactor), 6);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_nuke_furnace_off), 7);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_coal_off), 10);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_diesel), 8);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_selenium), 7);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_rtg_grey), 4);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.red_pylon), 9);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_battery), 8);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_lithium_battery), 5);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 8);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_assembler), 10);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_fluidtank), 7);
		BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_drill), 4);
    	BlockCrate.addToListWithWeight(metalList, Item.getItemFromBlock(ModBlocks.machine_excavator), 4);
    	
    	BlockCrate.addToListWithWeight(metalList, ModItems.centrifuge_element, 6);
    	
    	BlockCrate.addToListWithWeight(metalList, ModItems.motor, 8);
    	BlockCrate.addToListWithWeight(metalList, ModItems.coil_tungsten, 7);
    	BlockCrate.addToListWithWeight(metalList, ModItems.photo_panel, 3);
    	BlockCrate.addToListWithWeight(metalList, ModItems.coil_copper, 10);
    	BlockCrate.addToListWithWeight(metalList, ModItems.tank_steel, 9);
    	BlockCrate.addToListWithWeight(metalList, ModItems.blade_titanium, 3);
    	BlockCrate.addToListWithWeight(metalList, ModItems.bolt_compound, 2);
    	BlockCrate.addToListWithWeight(metalList, ModItems.piston_selenium, 6);
    	
    	//Red Crate
    	BlockCrate.addToListWithWeight(redList, ModItems.mysteryshovel, 1);
    	BlockCrate.addToListWithWeight(redList, Armory.gun_revolver_pip, 1);
    	BlockCrate.addToListWithWeight(redList, Armory.gun_revolver_blackjack, 1);
    	BlockCrate.addToListWithWeight(redList, Armory.gun_revolver_silver, 1);
    	BlockCrate.addToListWithWeight(redList, Armory.ammo_44_pip, 1);
    	BlockCrate.addToListWithWeight(redList, Armory.ammo_44_bj, 1);
    	BlockCrate.addToListWithWeight(redList, Armory.ammo_44_silver, 1);
    	BlockCrate.addToListWithWeight(redList, Batteries.battery_spark, 1);
    	BlockCrate.addToListWithWeight(redList, Foods.bottle_sparkle, 1);
    	BlockCrate.addToListWithWeight(redList, Foods.bottle_rad, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.ring_starmetal, 1);
    	BlockCrate.addToListWithWeight(redList, ModItems.flame_pony, 1);
    	BlockCrate.addToListWithWeight(redList, Item.getItemFromBlock(ModBlocks.ntm_dirt), 1);
    	BlockCrate.addToListWithWeight(redList, Item.getItemFromBlock(ModBlocks.broadcaster_pc), 1);
    }
    
    public void dropItems(World world, int x, int y, int z) {
    	Random rand = new Random();
    	
    	//setDrops();

    	List<ItemStack> list = new ArrayList<ItemStack>();
    	
    	int i = rand.nextInt(3) + 3;
    	
    	if(this == ModBlocks.crate_weapon) {
    		i = 1 + rand.nextInt(2);
    		
    		if(rand.nextInt(100) == 34)
    			i = 25;
    	}
    	
    	for(int j = 0; j < i; j++) {

    		if(this == ModBlocks.crate)
    			list.add(crateList.get(rand.nextInt(crateList.size())));
    		if(this == ModBlocks.crate_weapon)
    			list.add(weaponList.get(rand.nextInt(weaponList.size())));
    		if(this == ModBlocks.crate_lead)
    			list.add(leadList.get(rand.nextInt(leadList.size())));
    		if(this == ModBlocks.crate_metal)
    			list.add(metalList.get(rand.nextInt(metalList.size())));
    		if(this == ModBlocks.crate_red)
    			list.add(redList.get(rand.nextInt(redList.size())));
    	}
    	
    	if(this == ModBlocks.crate_red) {
    		list.clear();
    		
    		for(int k = 0; k < redList.size(); k++) {
    			list.add(redList.get(k));
    		}
    	}
    	
    	for(ItemStack stack : list) {
            float f = rand.nextFloat() * 0.8F + 0.1F;
            float f1 = rand.nextFloat() * 0.8F + 0.1F;
            float f2 = rand.nextFloat() * 0.8F + 0.1F;
        	
            EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, stack.copy());

            float f3 = 0.05F;
            entityitem.motionX = (float)rand.nextGaussian() * f3;
            entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
            entityitem.motionZ = (float)rand.nextGaussian() * f3;
            if(!world.isRemote)
            	world.spawnEntity(entityitem);
    	}
    }
    
    public static void addToListWithWeight(List<ItemStack> list, Item item, int weight) {
    	for(int i = 0; i < weight; i++)
    		list.add(new ItemStack(item));
    }
    public static void addToListWithWeight(List<ItemStack> list, ItemStack item, int weight) {
    	for(int i = 0; i < weight; i++)
    		list.add(item);
    }
}
