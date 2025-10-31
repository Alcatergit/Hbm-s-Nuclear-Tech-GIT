package com.hbm.entity.missile;

import com.hbm.blocks.bomb.NukeCustom;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Armory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityMissileCustomNuke extends EntityMissileBaseAdvanced {

	public EntityMissileCustomNuke(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(1F, 7F);
	}
	float tnt;
	float nuke;
	float hydro;
	float bale;
	float dirty;
	float schrab;
	float sol;
	float euph;

	public EntityMissileCustomNuke(World world, float x, float y, float z, int a, int b, float tnt, float nuke, float hydro, float bale, float dirty, float schrab, float sol, float euph) {
		super(world, x, y, z, a, b);
		this.tnt = tnt;
		this.nuke = nuke;
		this.hydro = hydro;
		this.bale = bale;
		this.dirty = dirty;
		this.schrab = schrab;
		this.sol = sol;
		this.euph = euph;
		this.setSize(1F, 7F);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.tnt = nbt.getFloat("custom_tnt");
		this.nuke = nbt.getFloat("custom_nuke");
		this.hydro = nbt.getFloat("custom_hydro");
		this.bale = nbt.getFloat("custom_bale");
		this.dirty = nbt.getFloat("custom_dirty");
		this.schrab = nbt.getFloat("custom_schrab");
		this.sol = nbt.getFloat("custom_sol");
		this.euph = nbt.getFloat("custom_euph");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("custom_tnt",this.tnt);
		nbt.setFloat("custom_nuke",this.nuke);
		nbt.setFloat("custom_hydro",this.hydro);
		nbt.setFloat("custom_bale",this.bale);
		nbt.setFloat("custom_dirty",this.dirty);
		nbt.setFloat("custom_schrab",this.schrab);
		nbt.setFloat("custom_sol",this.sol);
		nbt.setFloat("custom_euph",this.euph);
	}

	@Override
	public void onImpact() {
        if (!this.world.isRemote)
        {
			NukeCustom.explodeCustom(this.world,this.posX,this.posY,this.posZ,
					this.tnt,
					this.nuke,
					this.hydro,
					this.bale,
					this.dirty,
					this.schrab,
					this.sol,
					this.euph
			);
        }
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.wire_aluminium, 4));
		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.hull_small_aluminium, 2));
		list.add(new ItemStack(ModItems.ducttape, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(Armory.ammo_nuke, 1);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER0;
	}
}
