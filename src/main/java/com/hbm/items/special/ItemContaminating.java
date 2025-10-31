package com.hbm.items.special;

import com.hbm.blocks.generic.BlockClean;
import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.effect.EntityFalloutUnderGround;
import com.hbm.util.I18nUtil;
import com.leafia.dev.MultiRad.RadiationType;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemContaminating extends ItemHazard {
	
	private int burntime;
	private int falloutBallRadius = 0;

	public ItemContaminating(float radiation, String s){
		super(radiation, s);
		this.falloutBallRadius = (int)Math.min(Math.sqrt(radiation)+0.5D, 500);
	}

	public ItemContaminating(float radiation, boolean fire, String s){
		super(radiation, fire, s);
		this.falloutBallRadius = (int)Math.min(Math.sqrt(radiation)+0.5D, 500);
	}

	public ItemContaminating(float radiation, boolean fire, boolean blinding, String s){
		super(radiation, fire, blinding, s);
		this.falloutBallRadius = (int)Math.min(Math.sqrt(radiation)+0.5D, 500);
	}

	public ItemContaminating(RadiationType type,float radiation,String s){
		super(type,radiation, s);
		this.falloutBallRadius = (int)Math.min(Math.sqrt(radiation)+0.5D, 500);
	}

	public ItemContaminating(RadiationType type,float radiation, boolean fire, String s){
		super(type,radiation, fire, s);
		this.falloutBallRadius = (int)Math.min(Math.sqrt(radiation)+0.5D, 500);
	}

	public ItemContaminating(RadiationType type,float radiation, boolean fire, boolean blinding, String s){
		super(type,radiation, fire, blinding, s);
		this.falloutBallRadius = (int)Math.min(Math.sqrt(radiation)+0.5D, 500);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem){
		boolean m = this.module.onEntityItemUpdate(entityItem);
		if(entityItem != null && !entityItem.world.isRemote && (entityItem.onGround || entityItem.isBurning()) && CompatibilityConfig.isWarDim(entityItem.world)) {
			if(isCleanGround(new BlockPos(entityItem.posX, entityItem.posY, entityItem.posZ), entityItem.world)){
				return false;
			}
			if(falloutBallRadius > 1){
				EntityFalloutUnderGround falloutBall = new EntityFalloutUnderGround(entityItem.world);
				falloutBall.posX = entityItem.posX;
				falloutBall.posY = entityItem.posY+0.5F;
				falloutBall.posZ = entityItem.posZ;
				falloutBall.setScale(falloutBallRadius);
				entityItem.world.spawnEntity(falloutBall);
			}
			entityItem.setDead();
			return true;
		}
		return false || m;
	}

	public static boolean isCleanGround(BlockPos pos, World world){
		Block b = world.getBlockState(pos.down()).getBlock();
		boolean isClean = b instanceof BlockClean;
		if(isClean){
			BlockClean.getUsed(b, pos.down(), world);
		}
		return isClean;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn){
		super.addInformation(stack, world, list, flagIn);
		if(falloutBallRadius > 1){
			list.add("§2["+I18nUtil.resolveKey("trait._hazarditem.contaminating")+"§2]");
			list.add("§2 -::§a"+I18nUtil.resolveKey("trait._hazarditem.contaminating.radius", falloutBallRadius));
		}
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return burntime;
	}
}
