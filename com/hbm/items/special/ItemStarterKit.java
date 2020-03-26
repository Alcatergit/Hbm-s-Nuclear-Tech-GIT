package com.hbm.items.special;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemStarterKit extends Item {

	public ItemStarterKit(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.maxStackSize = 1;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	private void giveHaz(World world, EntityPlayer p, int tier) {
    	
    	for(int i = 0; i < 4; i++) {
    		
    		if(!p.inventory.armorInventory.get(i).isEmpty() && !world.isRemote) {
    			world.spawnEntity(new EntityItem(world, p.posX, p.posY + p.eyeHeight, p.posZ, p.inventory.armorInventory.get(i)));
    		}
    	}

    	switch(tier) {
    	case 0:
	    	p.inventory.armorInventory.set(3, new ItemStack(ModItems.hazmat_helmet));
	    	p.inventory.armorInventory.set(2, new ItemStack(ModItems.hazmat_plate));
	    	p.inventory.armorInventory.set(1, new ItemStack(ModItems.hazmat_legs));
	    	p.inventory.armorInventory.set(0, new ItemStack(ModItems.hazmat_boots));
	    	break;
    	case 1:
	    	p.inventory.armorInventory.set(3, new ItemStack(ModItems.hazmat_helmet_red));
	    	p.inventory.armorInventory.set(2, new ItemStack(ModItems.hazmat_plate_red));
	    	p.inventory.armorInventory.set(1, new ItemStack(ModItems.hazmat_legs_red));
	    	p.inventory.armorInventory.set(0, new ItemStack(ModItems.hazmat_boots_red));
	    	break;
    	case 2:
	    	p.inventory.armorInventory.set(3, new ItemStack(ModItems.hazmat_helmet_grey));
	    	p.inventory.armorInventory.set(2, new ItemStack(ModItems.hazmat_plate_grey));
	    	p.inventory.armorInventory.set(1, new ItemStack(ModItems.hazmat_legs_grey));
	    	p.inventory.armorInventory.set(0, new ItemStack(ModItems.hazmat_boots_grey));
	    	break;
    	}
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		
		if(this == ModItems.grenade_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_generic, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_strong, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_frag, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_fire, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_shrapnel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cluster, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_flare, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_electric, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_poison, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gas, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cloud, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_pink_cloud, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_smart, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_mirv, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_breach, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_burst, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_pulse, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_plasma, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_tau, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_schrabidium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_lemon, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gascan, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_mk2, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_aschrab, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_nuke, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_nuclear, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_zomg, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_black_hole, 16));
		}
		
		if(this == ModItems.gadget_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_gadget), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_wireing, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.gadget_core, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.boy_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_boy), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_shielding, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_target, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_bullet, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.boy_igniter, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.man_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_man), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_core, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.mike_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_mike), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.mike_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.mike_deut, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.mike_cooling_unit, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.tsar_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_tsar), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_explosive8, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.man_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.tsar_core, 1));
			
			giveHaz(world, player, 0);
		}
		
		if(this == ModItems.multi_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.bomb_multi), 6));
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(Blocks.TNT), 26));
			player.inventory.addItemStackToInventory(new ItemStack(Items.GUNPOWDER, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_cluster, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.powder_fire, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.powder_poison, 2));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.pellet_gas, 2));
		}
		
		if(this == ModItems.custom_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.nuke_custom));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_tnt, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_nuke, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_nuke, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_nuke, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_nuke, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_hydro, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_hydro, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_amat, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_amat, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_dirty, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_dirty, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_dirty, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_schrab, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.custom_fall, 1));
		}
		
		if(this == ModItems.grenade_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_generic, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_strong, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_frag, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_fire, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_shrapnel, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cluster, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_flare, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_electric, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_poison, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gas, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_cloud, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_pink_cloud, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_smart, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_mirv, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_breach, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_burst, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_pulse, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_plasma, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_tau, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_schrabidium, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_lemon, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_gascan, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_mk2, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_aschrab, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_nuke, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_nuclear, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_zomg, 16));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.grenade_black_hole, 16));
		}
		
		if(this == ModItems.fleija_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_fleija), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.fleija_core, 1));
			
			giveHaz(world, player, 2);
		}
		
		if(this == ModItems.solinium_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_solinium), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_igniter, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_propellant, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.solinium_core, 1));
			
			giveHaz(world, player, 1);
		}
		
		if(this == ModItems.prototype_kit)
		{
			player.inventory.addItemStackToInventory(new ItemStack(Item.getItemFromBlock(ModBlocks.nuke_prototype), 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.igniter, 1));
			for(int i = 0; i < 4; i ++)
				player.inventory.addItemStackToInventory(ItemCell.getFullCell(ModForgeFluids.sas3));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_uranium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_uranium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_lead, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_lead, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_neptunium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_neptunium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_lead, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_lead, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_uranium, 1));
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.rod_quad_uranium, 1));
			
			giveHaz(world, player, 2);
		}
		
		world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.itemUnpack, SoundCategory.PLAYERS, 1.0F, 1.0F);
		stack.shrink(1);;
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(this == ModItems.gadget_kit ||
    			this == ModItems.boy_kit ||
    			this == ModItems.man_kit ||
    			this == ModItems.mike_kit ||
    			this == ModItems.tsar_kit ||
    			this == ModItems.prototype_kit ||
    			this == ModItems.fleija_kit ||
    			this == ModItems.solinium_kit ||
    			this == ModItems.grenade_kit ||
    			this == ModItems.multi_kit) {
			tooltip.add("Please empty inventory before opening!");
    	}
		if(this == ModItems.gadget_kit ||
    			this == ModItems.boy_kit ||
    			this == ModItems.man_kit ||
    			this == ModItems.mike_kit ||
    			this == ModItems.tsar_kit ||
    			this == ModItems.prototype_kit ||
    			this == ModItems.fleija_kit ||
    			this == ModItems.solinium_kit) {
			tooltip.add("Armor will be displaced by hazmat suit.");
    	}
	}
}
