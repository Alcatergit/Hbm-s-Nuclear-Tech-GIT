package com.hbm.items.tool;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.hbm.config.GeneralConfig;
import com.hbm.config.WeaponConfig;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")})
public class ItemBaubleTool extends Item implements IBauble {

    public ItemBaubleTool(String s){
        this.setTranslationKey(s);
        this.setRegistryName(s);

        ModItems.ALL_ITEMS.add(this);
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem) {
        if(this == ModItems.reacher) return super.onEntityItemUpdate(entityItem);
        if(entityItem != null) {

            ItemStack stack = entityItem.getItem();

            if(!stack.isEmpty() && stack.getItem() == ModItems.detonator_deadman) {
                if(!entityItem.world.isRemote) {

                    if(stack.getTagCompound() != null) {

                        int x = stack.getTagCompound().getInteger("x");
                        int y = stack.getTagCompound().getInteger("y");
                        int z = stack.getTagCompound().getInteger("z");

                        if(entityItem.world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof IBomb) {
                            if(!entityItem.world.isRemote) {
                                ((IBomb) entityItem.world.getBlockState(new BlockPos(x, y, z)).getBlock()).explode(entityItem.world, new BlockPos(x, y, z));

                                if(GeneralConfig.enableExtendedLogging)
                                    MainRegistry.logger.log(Level.INFO, "[DET] Tried to detonate block at " + x + " / " + y + " / " + z + " by dead man's switch!");
                            }
                        }
                    }

                    entityItem.world.createExplosion(entityItem, entityItem.posX, entityItem.posY, entityItem.posZ, 0.0F, true);
                    entityItem.setDead();
                }
            }
            if(!stack.isEmpty() && stack.getItem() == ModItems.detonator_de) {
                if(!entityItem.world.isRemote && WeaponConfig.dropDead) {
                    entityItem.world.createExplosion(entityItem, entityItem.posX, entityItem.posY, entityItem.posZ, 15.0F, true);

                    if(GeneralConfig.enableExtendedLogging)
                        MainRegistry.logger.log(Level.INFO, "[DET] Detonated dead man's explosive at " + ((int) entityItem.posX) + " / " + ((int) entityItem.posY) + " / " + ((int) entityItem.posZ) + "!");
                }

                entityItem.setDead();
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
        if(this == ModItems.reacher) {
            tooltip.add("Holding this in main hand or off hand reduces radiation coming from items to its square-root.");
            tooltip.add("It also is useful to handle very hot or cold items.");
        } else {
            if(this == ModItems.detonator_deadman) {
                tooltip.add("Shift right-click to set position,");
                tooltip.add("drop to detonate!");
                if(stack.getTagCompound() == null) {
                    tooltip.add("No position set!");
                } else {
                    tooltip.add("Set pos to " + stack.getTagCompound().getInteger("x") + ", " + stack.getTagCompound().getInteger("y") + ", " + stack.getTagCompound().getInteger("z"));
                }
            }
            if(this == ModItems.detonator_de) {
                tooltip.add("Explodes when dropped!");
            }
            tooltip.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]");
        }
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if(this != ModItems.detonator_deadman) {
            return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
        }

        if(stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }

        if(player.isSneaking()) {
            stack.getTagCompound().setInteger("x", pos.getX());
            stack.getTagCompound().setInteger("y", pos.getY());
            stack.getTagCompound().setInteger("z", pos.getZ());

            if(world.isRemote) {
                player.sendMessage(new TextComponentTranslation("chat.posset"));
            }

            world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBoop, SoundCategory.PLAYERS, 2.0F, 1.0F);

            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack){
        if(itemstack.getItem() == ModItems.reacher) return BaubleType.BELT;
        return BaubleType.TRINKET;
    }
}
