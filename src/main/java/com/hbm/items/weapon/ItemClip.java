package com.hbm.items.weapon;

import static com.hbm.items.ItemEnums.EnumAmmo;
import static com.hbm.items.ItemEnums.EnumClip;
import java.util.List;

import com.hbm.items.special.ItemSimpleConsumable;
import com.hbm.items.ItemEnumMulti;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemClip extends ItemEnumMulti {

	public ItemClip(String s) {
		super(EnumClip.class, s, ".");
		this.maxStackSize = 32;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
		ItemStack stack = player.getHeldItem(handIn);
		int meta = stack.getItemDamage();
		
		if(meta == EnumClip.REVOLVER_IRON.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_iron_ammo, 24));
		else if(meta == EnumClip.REVOLVER.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_ammo, 12));
		else if(meta == EnumClip.REVOLVER_GOLD.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_gold_ammo, 12));
		else if(meta == EnumClip.REVOLVER_LEAD.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_lead_ammo, 12));
		else if(meta == EnumClip.REVOLVER_SCHRABIDIUM.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_schrabidium_ammo, 12));
		else if(meta == EnumClip.REVOLVER_CURSED.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_cursed_ammo, 17));
		else if(meta == EnumClip.REVOLVER_NIGHTMARE.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_nightmare_ammo, 6));
		else if(meta == EnumClip.REVOLVER_NIGHTMARE2.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_nightmare2_ammo, 6));
		else if(meta == EnumClip.REVOLVER_PIP.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 6, EnumAmmo.M44_PIP.ordinal()));
		else if(meta == EnumClip.REVOLVER_NOPIP.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.M44.ordinal()));
		else if(meta == EnumClip.RPG.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 6, EnumAmmo.ROCKET.ordinal()));
		else if(meta == EnumClip.STINGER.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_stinger_ammo, 6));
		else if(meta == EnumClip.FATMAN.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 6, EnumAmmo.NUKE_STANDARD.ordinal()));
		else if(meta == EnumClip.MIRV.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 3, EnumAmmo.MIRV_STANDARD.ordinal()));
		else if(meta == EnumClip.BF.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 2, EnumAmmo.NUKE_BALEFIRE.ordinal()));
		else if(meta == EnumClip.MP40.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 32, EnumAmmo.P9.ordinal()));
		else if(meta == EnumClip.UZI.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 32, EnumAmmo.P22.ordinal()));
		else if(meta == EnumClip.UBOINIK.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 24, EnumAmmo.G12.ordinal()));
		else if(meta == EnumClip.LEVER_ACTION.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 24, EnumAmmo.G20.ordinal()));
		else if(meta == EnumClip.BOLT_ACTION.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 24, EnumAmmo.G20_SLUG.ordinal()));
		else if(meta == EnumClip.OSIPR.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_osipr_ammo, 30));
		else if(meta == EnumClip.IMMOLATOR.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_immolator_ammo, 64));
		else if(meta == EnumClip.CRYOLATOR.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_cryolator_ammo, 64));
		else if(meta == EnumClip.MP.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_566_gold, 40));
		else if(meta == EnumClip.XVL1456.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_xvl1456_ammo, 64));
		else if(meta == EnumClip.EMP.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_emp_ammo, 6));
		else if(meta == EnumClip.JACK.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_jack_ammo, 12));
		else if(meta == EnumClip.SPARK.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_spark_ammo, 4));
		else if(meta == EnumClip.HP.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_hp_ammo, 12));
		else if(meta == EnumClip.EUTHANASIA.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_euthanasia_ammo, 16));
		else if(meta == EnumClip.DEFABRICATOR.ordinal())
			ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_defabricator_ammo, 16));
		else if(meta == EnumClip.CONTAINER.ordinal()) {
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_iron))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_iron_ammo, 24));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_ammo, 12));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_gold))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_gold_ammo, 4));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_lead))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_lead_ammo, 6));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_schrabidium))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_schrabidium_ammo, 2));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_cursed))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_cursed_ammo, 8));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_nightmare))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_nightmare_ammo, 6));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_nightmare2))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_nightmare2_ammo, 3));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_pip))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.M44_PIP.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_nopip))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.M44.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_blackjack))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.M44_BJ.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_red))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.M44.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_calamity))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 16, EnumAmmo.BMG50.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_calamity_dual))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 32, EnumAmmo.BMG50.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_minigun)) {
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 64, EnumAmmo.R5.ordinal()));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 64, EnumAmmo.R5.ordinal()));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 64, EnumAmmo.R5.ordinal()));
			}
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_avenger)) {
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 64, EnumAmmo.R5.ordinal()));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 64, EnumAmmo.R5.ordinal()));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 64, EnumAmmo.R5.ordinal()));
			}
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_lacunae)) {
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 64, EnumAmmo.R5.ordinal()));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 64, EnumAmmo.R5.ordinal()));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 64, EnumAmmo.R5.ordinal()));
			}
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_rpg))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 3, EnumAmmo.ROCKET.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_stinger))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_stinger_ammo, 2));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_skystinger))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_stinger_ammo, 2));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_fatman))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 2, EnumAmmo.NUKE_STANDARD.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_mp40))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 32, EnumAmmo.P9.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_uzi))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 32, EnumAmmo.P22.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_uzi_silencer))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 32, EnumAmmo.P22.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_uzi_saturnite))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 32, EnumAmmo.P22.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_uzi_saturnite_silencer))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 32, EnumAmmo.P22.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_uboinik))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.G12.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_lever_action))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.G20.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_lever_action_dark))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.G20.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_lever_action_sonata))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 1, EnumAmmo.G20.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_bolt_action))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.G20_FLECHETTE.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_bolt_action_green))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_standard, 12, EnumAmmo.G20_FLECHETTE.ordinal()));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_xvl1456))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_xvl1456_ammo, 40));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_osipr)) {
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_osipr_ammo, 30));
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_osipr_ammo2, 1));
			}
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_immolator))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_immolator_ammo, 40));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_cryolator))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_cryolator_ammo, 40));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_mp))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.ammo_566_gold, 34));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_zomg))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.nugget_euphemium, 1));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_emp))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_emp_ammo, 8));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_inverted))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_ammo, 1));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_revolver_inverted))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_revolver_ammo, 1));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_jack))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_jack_ammo, 3));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_spark))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_spark_ammo, 2));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_hp))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_hp_ammo, 6));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_euthanasia))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_euthanasia_ammo, 8));
			if(Library.hasInventoryItem(player.inventory, ModItems.gun_defabricator))
				ItemSimpleConsumable.tryAddItem(player, new ItemStack(ModItems.gun_defabricator_ammo, 6));
		}
		stack.shrink(1);
		return super.onItemRightClick(worldIn, player, handIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if(stack.getItemDamage() == EnumClip.CONTAINER.ordinal())
			tooltip.add("Gives ammo for all held weapons.");
	}
}
