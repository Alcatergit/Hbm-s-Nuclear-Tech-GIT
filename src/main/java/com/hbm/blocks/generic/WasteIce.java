package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.hazard.HazardSystem;
import net.minecraft.block.BlockIce;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WasteIce extends BlockIce {

    public WasteIce(String s) {
        super();
        this.setTranslationKey(s);
        this.setRegistryName(s);
        this.setSoundType(SoundType.GLASS);
        this.setHarvestLevel("pickaxe", -1);
        ModBlocks.ALL_BLOCKS.add(this);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entity) {
        if (entity instanceof EntityLivingBase)
            HazardSystem.applyHazards(this, (EntityLivingBase)entity);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entity) {
        if (entity instanceof EntityLivingBase)
            HazardSystem.applyHazards(this, (EntityLivingBase)entity);
    }
}
