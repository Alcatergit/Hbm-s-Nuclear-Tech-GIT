package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ItemGunDart extends ItemGunBase {

	public ItemGunDart(GunConfiguration config, String s) {
		super(config, s);
	}
	
	public static void writePlayer(ItemStack stack, EntityPlayer player) {

		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setString("player", player.getDisplayName().getUnformattedText());
	}

	public static EntityPlayer readPlayer(ItemStack stack) {

		if(!stack.hasTagCompound())
			return null;

		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(stack.getTagCompound().getString("player"));
	}
	
	@Override
	public void startAction(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(main) {
			super.startAction(stack, world, player, main, hand);
		} else {

			EntityPlayer target = readPlayer(stack);

			if(target != null) {

				int dim = target.world.provider.getDimension();
				int x = (int)target.posX;
				int y = (int)target.posY;
				int z = (int)target.posZ;
				int dist = (int) target.getDistance(player);

				player.sendMessage(new TextComponentString(target.getDisplayName().getUnformattedText()).setStyle(new Style().setColor(TextFormatting.YELLOW)));
				player.sendMessage(new TextComponentString("Dim: " + dim + " X:" + x + " Y:" + y + " Z:" + z + " (" + dist + " blocks away)").setStyle(new Style().setColor(TextFormatting.YELLOW)));
			} else {

				player.sendMessage(new TextComponentString("No Target").setStyle(new Style().setColor(TextFormatting.RED)));
			}
		}
	}

}
