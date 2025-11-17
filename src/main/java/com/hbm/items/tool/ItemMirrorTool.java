package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.machine.TileEntitySolarMirror;
import com.hbm.util.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMirrorTool extends Item {

	public ItemMirrorTool(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos1, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		Block b = world.getBlockState(pos1).getBlock();

		if(b == ModBlocks.machine_solar_boiler) {

			int[] pos = ((BlockDummyable)b).findCore(world, pos1.getX(), pos1.getY(), pos1.getZ());

			if(pos != null && !world.isRemote) {

				if(!stack.hasTagCompound())
					stack.setTagCompound(new NBTTagCompound());

				stack.getTagCompound().setInteger("posX", pos[0]);
				stack.getTagCompound().setInteger("posY", pos[1] + 1);
				stack.getTagCompound().setInteger("posZ", pos[2]);

				player.sendMessage(new TextComponentTranslation(this.getTranslationKey() + ".linked").setStyle(new Style().setColor(TextFormatting.YELLOW)));
			}

			return EnumActionResult.SUCCESS;
		}

		if(b == ModBlocks.solar_mirror && stack.hasTagCompound()) {

			if(!world.isRemote) {
				int tx = stack.getTagCompound().getInteger("posX");
				int ty = stack.getTagCompound().getInteger("posY");
				int tz = stack.getTagCompound().getInteger("posZ");
                if(player.isSneaking()) {
                    for (int i = -2; i < 3; i++) {
                        for (int j = -2; j < 3; j++) {
                            trySetTarget(world, pos1.add(i, 0, j), tx, ty, tz);
                        }
                    }
                } else {
                    trySetTarget(world, pos1, tx, ty, tz);
                }
			}

			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.PASS;
	}

    public static void trySetTarget(World world, BlockPos pos, int tx, int ty, int tz){
        TileEntitySolarMirror mirror = (TileEntitySolarMirror)world.getTileEntity(pos);
        if(mirror == null) return;
        if(Vec3.createVectorHelper(pos.getX()- tx, pos.getY() - ty, pos.getZ() - tz).length() < 64)
            mirror.setTarget(tx, ty, tz);
    }
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		for(String s : I18nUtil.resolveKeyArray(this.getTranslationKey() + ".desc"))
			tooltip.add(TextFormatting.YELLOW + s);
	}
}
