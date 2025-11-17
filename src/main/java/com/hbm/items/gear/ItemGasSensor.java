package com.hbm.items.gear;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.armor.ItemArmorMod;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.util.I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")})
public class ItemGasSensor extends ItemArmorMod implements IBauble {

	public ItemGasSensor(String s) {
		super(ArmorModHandler.extra, true, true, true, true, s);
	}

	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(TextFormatting.YELLOW + "  " + stack.getDisplayName() + " ("+I18nUtil.resolveKey("item.gas_sensor.mod")+")");
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if (entityIn instanceof EntityLivingBase b)
			modUpdate(b,null);
	}

    public static int getGas(World world, int x, int y, int z){
        for(int i = -2; i < 3; i++) {
            for(int j = -1; j < 2; j++) {
                for(int k = -2; k < 3; k++) {
                    Block b = world.getBlockState(new BlockPos(x + i, y + j, z + k)).getBlock();
                    if(b == ModBlocks.gas_radon || b == ModBlocks.gas_radon_dense || b == ModBlocks.gas_radon_tomb){
                        return 1;
                    } else if (b == ModBlocks.gas_explosive) {
                        return 2;
                    } else if(b == ModBlocks.gas_flammable) {
                        return 3;
                    } else if(b == ModBlocks.gas_monoxide) {
                        return 4;
                    } else if(b == ModBlocks.gas_asbestos) {
                        return 5;
                    } else if(b == ModBlocks.gas_coal) {
                        return 6;
                    }
                }
            }
        }
        return -1;
    }

	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor) {

		if(entity.world.isRemote || entity.world.getTotalWorldTime() % 20 != 0) return;

		int x = (int) Math.floor(entity.posX);
		int y = (int) Math.floor(entity.posY + entity.getEyeHeight() - entity.getYOffset());
		int z = (int) Math.floor(entity.posZ);

        int gasType = getGas(entity.world, x, y, z);
        if(gasType > 0){
            switch (gasType){
                case 1 -> Library.warnEntity(entity, HBMSoundHandler.buttonNo, "§2§l", "chat.gas_sensor.rad", 2F, 0.5F);
                case 2 -> Library.warnEntity(entity, HBMSoundHandler.follyAquired, "§c§l", "chat.gas_sensor.explosive", 1.25F, 1.0F);
                case 3 -> Library.warnEntity(entity, HBMSoundHandler.follyAquired, "§6§l", "chat.gas_sensor.flammable", 1F, 0.5F);
                case 4 -> Library.warnEntity(entity, HBMSoundHandler.techBoop, "§7§l", "chat.gas_sensor.mono", 1.5F, 1.5F);
                case 5 -> Library.warnEntity(entity, HBMSoundHandler.techBoop, "§f§l", "chat.gas_sensor.asbestos", 1.25F, 1.25F);
                case 6 -> Library.warnEntity(entity, HBMSoundHandler.techBoop, "§0§l", "chat.gas_sensor.coal", 1F, 1.0F);
            }
        }
	}

    @Override
    public BaubleType getBaubleType(ItemStack itemstack){
        return BaubleType.TRINKET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        onUpdate(itemstack, player.world, player, 0, true);
    }
}