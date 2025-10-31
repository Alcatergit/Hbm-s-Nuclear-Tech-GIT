package com.leafia.contents.machines.reactors.pwr.debris;

import com.hbm.blocks.machine.pile.BlockGraphite;
import com.hbm.blocks.machine.pile.BlockGraphiteDrilledBase;
import com.hbm.entity.projectile.EntityDebrisBase;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.ShredderRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Materials.Nuggies;
import com.hbm.main.MainRegistry;
import com.leafia.contents.machines.reactors.pwr.blocks.components.channel.PWRChannelBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.channel.PWRConductorBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.control.PWRControlBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.leafia.contents.machines.reactors.pwr.debris.PWRDebrisEntity.DebrisType.*;

public class PWRDebrisEntity extends EntityDebrisBase {
	public static final DataParameter<String> BLOCK_RSC = EntityDataManager.createKey(PWRDebrisEntity.class, DataSerializers.STRING);
	public static final DataParameter<Integer> BLOCK_META = EntityDataManager.createKey(PWRDebrisEntity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> FLAMMABILITY = EntityDataManager.createKey(PWRDebrisEntity.class,DataSerializers.VARINT);
	public static final DataParameter<Integer> COMBUSTION = EntityDataManager.createKey(PWRDebrisEntity.class,DataSerializers.VARINT);
	public static final DataParameter<Boolean> EXTINGUISHED = EntityDataManager.createKey(PWRDebrisEntity.class,DataSerializers.BOOLEAN);

	public PWRDebrisEntity(World world){
		super(world);
	}

	public PWRDebrisEntity(World world,double x,double y,double z,IBlockState state){
		super(world, x, y, z);
		DebrisType type = DebrisType.BLANK;
		Block block = state.getBlock();
		int encouragement = block.getFireSpreadSpeed(world,new BlockPos(x,y,z),EnumFacing.UP);
		int flammability = block.getFlammability(world,new BlockPos(x,y,z),EnumFacing.UP);
		if (encouragement > 0)
			this.getDataManager().set(FLAMMABILITY,(int)Math.max(Math.pow(300-flammability-190,3),600));
		this.getDataManager().set(BLOCK_RSC,block.getRegistryName().toString());
		this.getDataManager().set(BLOCK_META,block.getMetaFromState(state));
		if (block instanceof PWRControlBlock)
			type = (world.rand.nextInt(3) == 0) ? CONTROL_FRAME : CONTROL_ROD;
		else if (block instanceof PWRChannelBlock || block instanceof PWRConductorBlock)
			type = (world.rand.nextInt(3) == 0) ? CHANNEL_3X : CHANNEL_1X;
		else if (block instanceof BlockGraphite || block instanceof BlockGraphiteDrilledBase)
			type = GRAPHITE;
		else if (state.getMaterial().equals(Material.IRON)) {
			if (world.rand.nextInt(3) == 0)
				type = BLANK;
			else {
				if (block.getSoundType().equals(SoundType.STONE))
					type = CONCRETE;
				else
					type = SHRAPNEL;
			}
		} else
			type = CONCRETE;
		this.setType(type);
	}
	@SideOnly(Side.CLIENT)
	boolean wasExtinguished = true;
	@Override
	public void onUpdate() {
		int flammability = this.dataManager.get(FLAMMABILITY);
		int combustion = this.dataManager.get(COMBUSTION);
		boolean extinguished = this.dataManager.get(EXTINGUISHED);
		BlockPos pos = getPosition();
		if (!world.isRemote) {
			/*
			if (this.getType() == DebrisType.ELEMENT) {
				BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
				RadiationSavedData.incrementRad(world, pos, 20, 150);
			}*/
			if (flammability > 0) {
				if (combustion < flammability && !extinguished) {
					if (world.rand.nextInt(10) > 0)
						this.getDataManager().set(COMBUSTION,combustion+1);
					if (world.isValid(pos)) {
						IBlockState state = world.getBlockState(pos);
						if (state.getMaterial().equals(Material.WATER)) {
							this.dataManager.set(EXTINGUISHED,true);
							world.playSound(null,pos,SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,SoundCategory.BLOCKS,1,1);
						}
					}
				}
			}
		} else
			remoteUpdate(flammability,combustion,extinguished,pos);
		super.onUpdate();
	}
	@SideOnly(Side.CLIENT)
	public void remoteUpdate(int flammability,int combustion,boolean extinguished,BlockPos pos) { // stupid minecraft needs this as separate method
		if (extinguished && !wasExtinguished) {
			for (int i = world.rand.nextInt(3); i < 5; i++) {
				world.spawnParticle(EnumParticleTypes.CLOUD,posX,posY,posZ,0,0,0);
				world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,posX,posY,posZ,0,0,0);
			}
		}
		wasExtinguished = extinguished;
		if (flammability > 0 && !extinguished) {
			if (world.rand.nextInt(50) == 0) {
				if (flammability >= 1000000) {
					if (world.rand.nextInt(5) == 0) {
						NBTTagCompound data = new NBTTagCompound();
						data.setString("type","rbmkflame");
						data.setInteger("maxAge",300);
						data.setDouble("posX",posX);
						data.setDouble("posY",posY+1.75);
						data.setDouble("posZ",posZ+0.5);
						MainRegistry.proxy.effectNT(data);
					}
				} else {
					for (int i = world.rand.nextInt(2); i < 3; i++)
						world.spawnParticle(EnumParticleTypes.FLAME,posX,posY,posZ,0,0,0);
					for (int i = world.rand.nextInt(2); i < 2; i++)
						world.spawnParticle(EnumParticleTypes.LAVA,posX,posY,posZ,0,0,0);
				}
				world.playSound(Minecraft.getMinecraft().player,pos.getX() + 0.5F,pos.getY() + 0.5,pos.getZ() + 0.5,SoundEvents.BLOCK_FIRE_AMBIENT,SoundCategory.BLOCKS,1.0F + rand.nextFloat(),rand.nextFloat() * 0.7F + 0.3F);
			}
		}
	}

	@Override
	public boolean isBurning() {
		int flammability = this.dataManager.get(FLAMMABILITY);
		int combustion = this.dataManager.get(COMBUSTION);
		boolean extinguished = this.dataManager.get(EXTINGUISHED);
		return flammability > 0 /*&& flammability < 1000000*/ && combustion < flammability && !extinguished;
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand){
		if(!world.isRemote && !isDead) {
			switch(this.getType()){
				case CONTROL_FRAME:
					if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal,3)))
						this.setDead();
					break;
				case CONTROL_ROD:
					if(player.inventory.addItemStackToInventory((world.rand.nextInt(20) == 0 ? new ItemStack(Nuggies.nugget_pb209) : new ItemStack(Nuggies.nugget_lead,4))))
						this.setDead();
					break;
				case GRAPHITE:
					if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_graphite)))
						this.setDead();
					break;
				case CHANNEL_1X:
					if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.plate_steel)))
						this.setDead();
				case CHANNEL_3X:
					if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.hull_tube_steel,2)))
						this.setDead();
				default:
					Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(this.getDataManager().get(BLOCK_RSC)));
					if (block == null) break;
					ComparableStack stack = new ComparableStack(ItemBlock.getItemFromBlock(block));
					if (ShredderRecipes.shredderRecipes.containsKey(stack)) {
						ItemStack out = ShredderRecipes.shredderRecipes.get(stack);
						if (out != null) {
							if(player.inventory.addItemStackToInventory(out))
								this.setDead();
						}
					}
					if (this.isEntityAlive()) {
						Item item;
						switch(this.getType()) {
							case BLANK: case SHRAPNEL:
								item = ModItems.pwr_shrapnel;
								break;
							case CONCRETE: default:
								item = ModItems.pwr_piece;
								break;
						}
						if (getDataManager().get(BLOCK_RSC).contains("glass"))
							item = ModItems.pwr_shard;
						ItemStack out = new ItemStack(item);
						NBTTagCompound nbt = new NBTTagCompound();
						nbt.setString("block",getDataManager().get(BLOCK_RSC));
						nbt.setInteger("meta",getDataManager().get(BLOCK_META));
						out.setTagCompound(nbt);
						if(player.inventory.addItemStackToInventory(out))
							this.setDead();
					}
			}
			player.inventoryContainer.detectAndSendChanges();
		}

		return false;
	}
	@Override
	public void setSize() {
		switch(this.getType()){
			case BLANK: this.setSize(0.5F, 0.5F); break;
			case CONCRETE: this.setSize(0.5F, 0.5F); break;
			case GRAPHITE: this.setSize(0.25F, 0.25F); break;
			case SHRAPNEL: this.setSize(0.5F, 0.5F); break;

			default: this.setSize(1F, 1F);
		}
	}
	@Override
	public int getLifetime(){
		switch(this.getType()){
			default: return 10*60*20;
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataManager().register(BLOCK_RSC, "minecraft:tnt");
		this.getDataManager().register(BLOCK_META, 0);
		this.getDataManager().register(FLAMMABILITY, 0);
		this.getDataManager().register(COMBUSTION, 0);
		this.getDataManager().register(EXTINGUISHED, false);
	}
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.getDataManager().set(BLOCK_RSC, nbt.getString("block_rsc"));
		this.getDataManager().set(BLOCK_META, nbt.getInteger("block_meta"));
		if (nbt.hasKey("flammability")) {
			this.getDataManager().set(FLAMMABILITY, nbt.getInteger("flammability"));
			this.getDataManager().set(COMBUSTION, nbt.getInteger("combustion"));
			this.getDataManager().set(EXTINGUISHED, nbt.getBoolean("extinguished"));
		}
	}
	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setString("block_rsc", this.getDataManager().get(BLOCK_RSC));
		nbt.setInteger("block_meta", this.getDataManager().get(BLOCK_META));
		if (this.getDataManager().get(FLAMMABILITY) > 0) {
			nbt.setInteger("flammability", this.getDataManager().get(FLAMMABILITY));
			nbt.setInteger("combustion", this.getDataManager().get(COMBUSTION));
			nbt.setBoolean("extinguished",this.dataManager.get(EXTINGUISHED));
		}
	}

	public void setType(DebrisType type){
		this.getDataManager().set(TYPE_ID, type.ordinal());
	}

	public DebrisType getType(){
		return DebrisType.values()[Math.abs(this.getDataManager().get(TYPE_ID)) % DebrisType.values().length];
	}

	public static enum DebrisType {
		BLANK,
		CONCRETE(4),
		SHRAPNEL,
		GRAPHITE,
		CONTROL_FRAME(2),
		CONTROL_ROD,
		CHANNEL_3X,
		CHANNEL_1X;
		final double weight;
		DebrisType() {
			this.weight = 1;
		}
		DebrisType(double weight) {
			this.weight = weight;
		}
	}
}