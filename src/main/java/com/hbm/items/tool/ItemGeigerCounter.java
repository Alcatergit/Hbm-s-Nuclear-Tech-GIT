package com.hbm.items.tool;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.handler.RadiationSystemNT.RadPocket;
import com.hbm.items.ModItems;
import com.hbm.items.gear.ArmorFSB;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ContaminationUtil;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import com.leafia.passive.rendering.TopRender.Highlight;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Optional.InterfaceList({@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")})
public class ItemGeigerCounter extends Item implements IBauble {
	
	public ItemGeigerCounter(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {

		if(!(entity instanceof EntityLivingBase) || world.isRemote)
			return;
		
		if(entity instanceof EntityPlayer) {
			
			if(ArmorFSB.hasFSBArmor((EntityPlayer)entity) && ((ArmorFSB)((EntityPlayer)entity).inventory.armorInventory.get(2).getItem()).geigerSound)
				return;
			
			playGeiger(world, (EntityPlayer)entity);
		}
	}

	public static void playGeiger(World world, EntityPlayer player){
		double x = ContaminationUtil.getActualPlayerRads(player);
		
		if(world.getTotalWorldTime() % 5 == 0) {

			if(x > 0.001) {
				List<Integer> list = new ArrayList<Integer>();

				if(x < 1){
					list.add(0);
				}
				if(x < 5){
					list.add(0);
				}
				if(2 < x && x < 10){
					list.add(1);
				}
				if(5 < x && x < 20){
					list.add(2);
				}
				if(15 < x && x < 40){
					list.add(3);
				}
				if(30 < x && x < 80){
					list.add(4);
				}
				if(60 < x && x < 160){
					list.add(5);
				}
				if(120 < x && x < 320){
					list.add(6);
				}
				if(240 < x && x < 640){
					list.add(7);
				}
				if(480 < x){
					list.add(8);
				}
				if(list.size() > 0){
					int r = list.get(world.rand.nextInt(list.size()));
					
					if(r > 0){
						world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundEvents.geigerSounds[r-1], SoundCategory.PLAYERS, 1.0F, 1.0F);
					}
				}
			} else if(world.rand.nextInt(100) == 0) {
				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundEvents.geigerSounds[(world.rand.nextInt(1))], SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.getBlockState(pos).getBlock() == ModBlocks.block_red_copper) {
    		Library.consumeInventoryItem(player.inventory, ModItems.geiger_counter);
    		player.inventory.addItemStackToInventory(new ItemStack(ModItems.survey_scanner));
    		return EnumActionResult.SUCCESS;
    	}
    	
    	return EnumActionResult.PASS;
	}
	public static class GeigerLeakPacket extends RecordablePacket {
		public ArrayList<BlockPos> positions = new ArrayList<>();
		public GeigerLeakPacket() {
		}
		@Override
		public void fromBits(LeafiaBuf buf) {
			int length = buf.readInt();
			positions = new ArrayList<>(length);
			for (int i = 0; i < length; i++)
				positions.add(buf.readPos());
		}
		@Override
		public void toBits(LeafiaBuf buf) {
			buf.writeInt(positions.size());
			for (BlockPos pos : positions)
				buf.writeVec3i(pos);
		}
		public static class Handler implements IMessageHandler<GeigerLeakPacket,IMessage> {
			@Override
			@SideOnly(Side.CLIENT)
			public IMessage onMessage(GeigerLeakPacket message,MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					for (BlockPos pos : message.positions) {
						Highlight highlight = new Highlight(pos);
						highlight.setLifetime(5);
						highlight.setLabel("Leak",pos.getX()+","+pos.getY()+","+pos.getZ());
						highlight.setColor(0xFF0000);
						highlight.show();
					}
				});
				return null;
			}
		}
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
		if(!world.isRemote) {
			if (!player.isSneaking()) {
				world.playSound(null,player.posX,player.posY,player.posZ,HBMSoundEvents.techBoop,SoundCategory.PLAYERS,1.0F,1.0F);
				ContaminationUtil.printGeigerData(player);
			} else {
				world.playSound(null,player.posX,player.posY,player.posZ,HBMSoundEvents.techBleep,SoundCategory.PLAYERS,1.0F,1.0F);
				RadPocket pocket = RadiationSystemNT.getPocket(world,player.getPosition());
				RadiationSavedData data = RadiationSavedData.getData(player.world);
				boolean sealed = data.isSealed(player.getPosition(),false);
				player.sendMessage(new TextComponentString("===== ☢ ").appendSibling(new TextComponentTranslation("geiger.title")).appendSibling(new TextComponentString(" ☢ =====")).setStyle(new Style().setColor(TextFormatting.GOLD)));
				player.sendMessage(new TextComponentTranslation("geiger.sealed."+(sealed ? "true" : "false")).setStyle(new Style().setColor(sealed ? TextFormatting.AQUA : TextFormatting.GOLD)));
				if (pocket == null) {
					player.sendMessage(new TextComponentString("Entirely unshielded").setStyle(new Style().setColor(TextFormatting.DARK_RED)));
				} else {
					if (pocket.leaks.size() <= 0)
						player.sendMessage(new TextComponentString("Failed to detect leaks").setStyle(new Style().setColor(TextFormatting.DARK_GRAY)));
					else {
						GeigerLeakPacket packet = new GeigerLeakPacket();
						for (BlockPos leak : pocket.leaks) {
							// TODO: add some ahh ahhh hh marker that penetrates through walls
							player.sendMessage(new TextComponentString("Leak detected at "+leak.getX()+", "+leak.getY()+", "+leak.getZ()).setStyle(new Style().setColor(TextFormatting.RED)));
							packet.positions.add(leak);
						}
						if (player instanceof EntityPlayerMP)
							PacketDispatcher.wrapper.sendTo(packet,(EntityPlayerMP)player);
						else
							PacketDispatcher.wrapper.sendToAll(packet);
					}
				}
			}
		}
		
		return super.onItemRightClick(world, player, handIn);
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return !ItemStack.areItemsEqual(oldStack, newStack);
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
