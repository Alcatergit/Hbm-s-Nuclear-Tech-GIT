package com.leafia.unsorted.recipe_book;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.ArmorSets;
import com.hbm.items.ModItems.Materials.Ingots;
import com.hbm.items.ModItems.ToolSets;
import com.leafia.unsorted.recipe_book.system.LeafiaRecipeBookServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LeafiaRecipeUnlocker {
    public static void onCraft(EntityPlayer player,ItemStack stack) {
        //if (stack.getItem() == ItemBlock.getItemFromBlock(Blocks.CRAFTING_TABLE))
        //    LeafiaRecipeBookServer.unlockRecipe(player,ItemBlock.getItemFromBlock(ModBlocks.machine_press));
    }
    public static void machineOutputTake(EntityPlayer player,int windowId,ItemStack stack) { // fired by SlotMachineOutput.onTake
        //player.sendMessage(new TextComponentString("AAAA "+windowId+" : "+player.world.isRemote));
        // ok ngl windowid sucks af i am scrapping it
        /*
        switch(windowId) {
            case (ModBlocks.guiID_machine_press):
            case (ModBlocks.guiID_machine_epress):*/
        if (stack.getItem() == ModItems.plate_copper) {
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.board_copper);
            LeafiaRecipeBookServer.unlockRecipe(player,ItemBlock.getItemFromBlock(ModBlocks.machine_difurnace_off));
        }
                //break;
            /*case (ModBlocks.guiID_test_difurnace):
            case (ModBlocks.guiID_rtg_difurnace):*/
        else if (stack.getItem() == Ingots.ingot_steel) {
            LeafiaRecipeBookServer.unlockRecipe(player,Ingots.ingot_red_copper);
            LeafiaRecipeBookServer.unlockRecipe(player,Ingots.ingot_advanced_alloy);
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.steel_axe);
            LeafiaRecipeBookServer.unlockRecipe(player,ArmorSets.steel_boots);
            LeafiaRecipeBookServer.unlockRecipe(player,ArmorSets.steel_helmet);
            LeafiaRecipeBookServer.unlockRecipe(player,ArmorSets.steel_plate);
            LeafiaRecipeBookServer.unlockRecipe(player,ArmorSets.steel_legs);
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.steel_hoe);
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.steel_pickaxe);
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.steel_shovel);
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.steel_sword);
        } else if (stack.getItem() == Ingots.ingot_red_copper) {
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.wire_red_copper);
        } else if (stack.getItem() == ModItems.wire_red_copper) {
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.plate_iron);
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.coil_copper); // TODO: fix these being broken
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.coil_copper_torus);// (Yes these do not work)
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.motor);
        } else if (stack.getItem() == ModItems.plate_iron) {
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.plate_steel);
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.plate_titanium);
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.wire_aluminium);
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.tank_steel);
        } else if (stack.getItem() == ModItems.wire_aluminium) {
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.circuit_raw);
            LeafiaRecipeBookServer.unlockRecipe(player,ModItems.circuit_aluminium);
        } else if (stack.getItem() == Ingots.ingot_advanced_alloy) {
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.alloy_axe);
            LeafiaRecipeBookServer.unlockRecipe(player,ArmorSets.alloy_boots);
            LeafiaRecipeBookServer.unlockRecipe(player,ArmorSets.alloy_helmet);
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.alloy_hoe);
            LeafiaRecipeBookServer.unlockRecipe(player,ArmorSets.alloy_legs);
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.alloy_pickaxe);
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.alloy_sword);
            LeafiaRecipeBookServer.unlockRecipe(player,ToolSets.alloy_shovel);
            LeafiaRecipeBookServer.unlockRecipe(player,ArmorSets.alloy_plate);
        }
                //break;
        //}
    }
}
