package com.hbm.items.special;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.items.ModItems;

import net.minecraft.block.BlockJukebox;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemModRecord extends ItemRecord {

	private static final Map<String, ItemModRecord> modRecords = new HashMap<String, ItemModRecord>();
	public final String recordName;
	
	public ItemModRecord(String p_i46742_1_, SoundEvent soundIn, String name) {
		super(p_i46742_1_, soundIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.MISC);
		recordName = p_i46742_1_;
		modRecords.put(p_i46742_1_, this);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.getBlockState(pos).getBlock() == Blocks.JUKEBOX && !worldIn.getBlockState(pos).getValue(BlockJukebox.HAS_RECORD).booleanValue()) {
			if (worldIn.isRemote) {
				return EnumActionResult.SUCCESS;
			} else {
				ItemStack stack = player.getHeldItem(hand);
				((BlockJukebox)Blocks.JUKEBOX).insertRecord(worldIn, pos, worldIn.getBlockState(pos), stack);
				worldIn.playEvent(null, 1010, pos, Item.getIdFromItem(this));
				stack.shrink(1);
				player.addStat(StatList.RECORD_PLAYED);
				return EnumActionResult.SUCCESS;
			}
		} else {
			return EnumActionResult.PASS;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(this.getRecordNameLocal());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getRecordNameLocal() {
		return I18n.format("item.record." + this.recordName + ".desc");
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
        return (I18n.format(Items.RECORD_11.getUnlocalizedName() + ".name")).trim();
	}
	
	
}
