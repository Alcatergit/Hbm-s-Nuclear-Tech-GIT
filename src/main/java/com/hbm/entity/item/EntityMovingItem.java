package com.hbm.entity.item;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMovingItem extends Entity {

	public static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(EntityMovingItem.class, DataSerializers.ITEM_STACK);
	
	public EntityMovingItem(World p_i1582_1_) {
		super(p_i1582_1_);
        this.setSize(0.5F, 0.25F);
        this.noClip = true;
	}

    public void setItemStack(ItemStack stack) {
        this.getDataManager().set(STACK, stack);
    }

    public ItemStack getItemStack() {

        ItemStack stack = this.getDataManager().get(STACK);
        return stack == null ? new ItemStack(Blocks.STONE) : stack;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public boolean interactFirst(EntityPlayer player) {

		if(!world.isRemote && player.inventory.addItemStackToInventory(this.getItemStack().copy())) {
			this.setDead();
		}

		return false;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {

    	if(!world.isRemote) {
			world.spawnEntity(new EntityItem(world, posX, posY, posZ, this.getItemStack()));
	    	this.setDead();
    	}
    	return true;
    }

    public boolean canAttackWithItem() {
        return true;
    }

    public boolean hitByEntity(Entity attacker) {

    	if(attacker instanceof EntityPlayer) {
    	}

		this.setDead();

        return false;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    private int schedule = 0;

    public void onUpdate() {

    	if(!world.isRemote) {

    		if(world.getBlockState(new BlockPos((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ))).getBlock() != ModBlocks.conveyor) {
    			this.setDead();
    			world.spawnEntity(new EntityItem(world, posX, posY, posZ, this.getItemStack()));
    			return;
    		}
    	}

    	IBlockState b = world.getBlockState(new BlockPos((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ)));
    	if(b.getBlock() == ModBlocks.conveyor) {

    		if(schedule <= 0) {
    			EnumFacing dir = b.getValue(BlockHorizontal.FACING);

    			if(world.getBlockState(new BlockPos((int)Math.floor(posX), (int)Math.floor(posY) + 1, (int)Math.floor(posZ))).getBlock() == ModBlocks.conveyor && motionY >= 0) {
    				dir = EnumFacing.DOWN;
    			}

    			if(world.getBlockState(new BlockPos((int)Math.floor(posX), (int)Math.floor(posY) - 1, (int)Math.floor(posZ))).getBlock() == ModBlocks.conveyor && motionY <= 0) {
    				dir = EnumFacing.UP;
    			}

        		double speed = 0.1;

        		schedule = (int) (1 / speed);
        		motionX = -speed * dir.getFrontOffsetX();
        		motionY = -speed * dir.getFrontOffsetY();
        		motionZ = -speed * dir.getFrontOffsetZ();
    		}

    		this.move(MoverType.SELF, motionX, motionY, motionZ);
    		schedule--;
    	}
    }

	@Override
    protected void entityInit() {
        this.getDataManager().register(STACK, ItemStack.EMPTY);
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {

        NBTTagCompound compound = nbt.getCompoundTag("Item");
        this.setItemStack(new ItemStack(compound));

        ItemStack stack = this.getDataManager().get(STACK);

        schedule = nbt.getInteger("schedule");

        if (stack == null || stack.isEmpty())
            this.setDead();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {

        if (this.getItemStack() != null)
        	nbt.setTag("Item", this.getItemStack().writeToNBT(new NBTTagCompound()));

        nbt.setInteger("schedule", schedule);
	}

}