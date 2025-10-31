package com.hbm.items.special;

import java.util.function.BiConsumer;

import com.hbm.util.Tuple.Pair;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundCategory;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSimpleConsumable extends ItemCustomLore {
	
	//if java is giving me the power of generics and delegates then i'm going to use them, damn it!
	private BiConsumer<ItemStack, EntityPlayer> useAction;
	private BiConsumer<ItemStack, EntityPlayer> useActionServer;
	private BiConsumer<ItemStack, Pair<EntityLivingBase, EntityLivingBase>> hitAction;
	private BiConsumer<ItemStack, Pair<EntityLivingBase, EntityLivingBase>> hitActionServer;

	public ItemSimpleConsumable(String s){
		super(s);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);
		if(this.useAction != null)
			this.useAction.accept(stack, player);
		
		if(!world.isRemote && this.useActionServer != null)
			this.useActionServer.accept(stack, player);
		
		return ActionResult.<ItemStack> newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase entityPlayer) {
		
		if(this.hitAction != null)
			this.hitAction.accept(stack, new Pair(entity, entityPlayer));
		
		if(!entity.world.isRemote && this.hitActionServer != null)
			this.hitActionServer.accept(stack, new Pair(entity, entityPlayer));
		
		return false;
	}
	
	public static void giveSoundAndDecrement(ItemStack stack, EntityLivingBase entity, SoundEvent sound, ItemStack container) {
		stack.shrink(1);
		entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
		ItemSimpleConsumable.tryAddItem(entity, container);
	}
	
	public static void addPotionEffect(EntityLivingBase entity, Potion effect, int duration, int level) {
		
		if(!entity.isPotionActive(effect)) {
			entity.addPotionEffect(new PotionEffect(effect, duration, level));
		} else {
			int d = duration;
    		if(level == entity.getActivePotionEffect(effect).getAmplifier())
    			d += entity.getActivePotionEffect(effect).getDuration();
			entity.addPotionEffect(new PotionEffect(effect, d, level));
		}
	}
	
	public static void tryAddItem(EntityLivingBase entity, ItemStack stack) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			if(!player.inventory.addItemStackToInventory(stack)) {
				player.dropItem(stack, false);
			}
		}
	}
	
	//this formatting style probably already has a name but i will call it "the greg"
	public ItemSimpleConsumable setUseAction(		BiConsumer<ItemStack, EntityPlayer> delegate) {								this.useAction = delegate;			return this; }
	public ItemSimpleConsumable setUseActionServer(	BiConsumer<ItemStack, EntityPlayer> delegate) {								this.useActionServer = delegate;	return this; }
	public ItemSimpleConsumable setHitAction(		BiConsumer<ItemStack, Pair<EntityLivingBase, EntityLivingBase>> delegate) {	this.hitAction = delegate;			return this; }
	public ItemSimpleConsumable setHitActionServer(	BiConsumer<ItemStack, Pair<EntityLivingBase, EntityLivingBase>> delegate) {	this.hitActionServer = delegate;	return this; }
}
