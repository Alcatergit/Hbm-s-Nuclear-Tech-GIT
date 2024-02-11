package com.hbm.items.tool;

import java.util.List;

import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class ItemSurveyScanner extends Item {

	public ItemSurveyScanner(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18nUtil.resolveKey("desc.surveyscanner.1"));
		tooltip.add(I18nUtil.resolveKey("desc.surveyscanner.2"));
		tooltip.add(I18nUtil.resolveKey("desc.surveyscanner.3", (getMode(stack) == 0 ? I18nUtil.resolveKey("desc.surveyscanner.resources") : I18nUtil.resolveKey("desc.surveyscanner.structures"))));
	}
	
	public int getMode(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("mode", 0);
			return 0;
		} else {
			return stack.getTagCompound().getInteger("mode");
		}
	}
	
	public void setMode(ItemStack stack, int mode) {
		if(!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("mode", mode);
	}
	
	public int getLevel(IBlockState state, int i) {
		Block b = state.getBlock();
		if(i == 0) {
			if(b == ModBlocks.ore_bedrock_block) return 1000;
			ItemStack stack = new ItemStack(b, 1, b.getMetaFromState(state));
			if(stack.isEmpty())
				return 0;
			int[] ids = OreDictionary.getOreIDs(stack);
				
			for(int j = 0; j < ids.length; j++) {
					
				String s = OreDictionary.getOreName(ids[j]);
					
				if(s.length() > 3 && s.substring(0, 3).equals("ore"))
					return 1;
			}
		} else {
			if(b == Blocks.PLANKS || b == Blocks.COBBLESTONE || b == Blocks.GLASS || b == Blocks.STONEBRICK)
				return 1;
			if(b instanceof IBomb)
				return 100;
			if(b instanceof ITileEntityProvider)
				return 10;
			if(b == Blocks.NETHER_BRICK)
				return 5;
		}
		
		return 0;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if(player.isSneaking()) {
			int mode = getMode(stack);
			setMode(stack, (mode == 1 ? 0 : 1));
	    	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBoop, SoundCategory.PLAYERS, 1.0F, 1.0F);

			if(world.isRemote)
			{
				player.sendMessage(new TextComponentTranslation(mode == 0 ? "chat.surveyscanner.switchto.structures" : "chat.surveyscanner.switchto.resources"));
			}
			
		} else {

			int x = (int)player.posX;
			int y = (int)player.posY;
			int z = (int)player.posZ;
			MutableBlockPos mPos = new BlockPos.MutableBlockPos();
			int mode = getMode(stack);
			int level = 0;
			int range = 25;
			int samples = 500;

			int lx = 0;
			int lz = 0;
			for(int i = 0; i < samples; i++){
				lx = (int)MathHelper.clamp(world.rand.nextGaussian() * range/3F, -range, range);
				lz = (int)MathHelper.clamp(world.rand.nextGaussian() * range/3F, -range, range);
				for(int ly = y + 15; ly > 0; ly--){
					level += getLevel(world.getBlockState(mPos.setPos(x + lx, ly, z + lz)), mode);
				}
			}

	    	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.techBleep, SoundCategory.PLAYERS, 1.0F, 1.0F);
	    	
			if(world.isRemote)
			{
				player.sendMessage(new TextComponentTranslation(mode == 0 ? "chat.surveyscanner.level.resources" : "chat.surveyscanner.level.structures", level));
			}
		}
		
		player.swingArm(hand);
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		//Alcater: o_o DAMN an Easteregg
		if(world.getBlockState(pos).getBlock() == ModBlocks.block_beryllium && Library.hasInventoryItem(player.inventory, ModItems.entanglement_kit)) {
    		player.changeDimension(1);
    		return EnumActionResult.SUCCESS;
    	}
    	
    	return EnumActionResult.PASS;
	}
}
