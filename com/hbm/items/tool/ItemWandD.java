package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
import com.hbm.tileentity.machine.TileEntityPylonRedWire;
import com.hbm.world.generator.CellularDungeonFactory;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemWandD extends Item {

	public ItemWandD(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block b = world.getBlockState(pos).getBlock();

		if(!world.isRemote)
		{
			if (b == ModBlocks.ore_aluminium)
				MainRegistry.x++;
			if (b == ModBlocks.block_aluminium)
				MainRegistry.x--;
			if (b == ModBlocks.ore_beryllium)
				MainRegistry.y++;
			if (b == ModBlocks.block_beryllium)
				MainRegistry.y--;
			if (b == ModBlocks.ore_copper)
				MainRegistry.z++;
			if (b == ModBlocks.block_copper)
				MainRegistry.z--;
			if (b == ModBlocks.red_pylon) {
				TileEntityPylonRedWire te = (TileEntityPylonRedWire) world.getTileEntity(pos);
				for(int i = 0; i < te.connected.size(); i++)
					if(world.isRemote)
						player.sendMessage(new TextComponentTranslation(te.connected.get(i).getPos().getX() + " " + te.connected.get(i).getPos().getY() + " " + te.connected.get(i).getPos().getZ()));
			}
			
			if(player.isSneaking()){
				RayTraceResult pos1 = Library.rayTrace(player, 500, 1);
				if(pos1 != null && pos1.typeOfHit == RayTraceResult.Type.BLOCK) {
					
					int x = pos1.getBlockPos().getX();
					int y = pos1.getBlockPos().getY();
					int z = pos1.getBlockPos().getZ();
					CellularDungeonFactory.test.generate(world, x, y, z, world.rand);
				}
			}
		}
		if(b == ModBlocks.fluid_duct_mk2){
			System.out.println("client: " + world.isRemote + " " + ((TileEntityFFDuctBaseMk2)world.getTileEntity(pos)).getNetwork() + " " + ((TileEntityFFDuctBaseMk2)world.getTileEntity(pos)).getNetwork().size());
			System.out.println(((TileEntityFFDuctBaseMk2)world.getTileEntity(pos)).connections);
		}
		
		MainRegistry.time = System.currentTimeMillis();
		
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(player.isSneaking())
		{
			if(world.isRemote)
				player.sendMessage(new TextComponentTranslation(MainRegistry.x + " " + MainRegistry.y + " " + MainRegistry.z));
		}
		
		return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Used for debugging purposes.");
	}
}
