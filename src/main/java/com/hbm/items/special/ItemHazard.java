package com.hbm.items.special;

import java.util.List;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

@Deprecated()
public class ItemHazard extends ItemCustomLore implements IItemHazard {
	
	//CO60		             5a		β−	030.00Rad/s	Spicy
	//SR90		            29a		β−	015.00Rad/s Spicy
	//TC99		       211,000a		β−	002.75Rad/s	Spicy
	//I181		           192h		β−	150.00Rad/s	2 much spice :(
	//XE135		             9h		β−	aaaaaaaaaaaaaaaa
	//CS137		            30a		β−	020.00Rad/s	Spicy
	//AU192		            64h		β−	500.00Rad/s	2 much spice :(
	//PB209		             3h		β−	10,000.00Rad/s mama mia my face is melting off
	//AT209		             5h		β+	like 2k or sth idk bruv
	//PO210		           138d		α	075.00Rad/s	Spicy
	//RA226		         1,600a		α	007.50Rad/s
	//AC227		            22a		β−	030.00Rad/s Spicy
	//TH232		14,000,000,000a		α	000.10Rad/s
	//U233		       160,000a		α	005.00Rad/s
	//U235		   700,000,000a		α	001.00Rad/s
	//U238		 4,500,000,000a		α	000.25Rad/s
	//NP237		     2,100,000a		α	002.50Rad/s
	//PU238		            88a		α	010.00Rad/s	Spicy
	//PU239		        24,000a		α	005.00Rad/s
	//PU240		         6,600a		α	007.50Rad/s
	//PU241		            14a		β−	025.00Rad/s	Spicy
	//AM241		           432a		α	008.50Rad/s
	//AM242		           141a		β−	009.50Rad/s

	public static final float co60 = 30.0F;
	public static final float sr90 = 15.0F;
	public static final float tc99 = 2.75F;
	public static final float i131 = 150.0F;
	public static final float xe135 = 1250.0F;
	public static final float cs137 = 20.0F;
	public static final float au198 = 500.0F;
	public static final float pb209 = 10000.0F;
	public static final float at209 = 7500.0F;
	public static final float po210 = 75.0F;
	public static final float ra226 = 7.5F;
	public static final float ac227 = 30.0F;
	public static final float th232 = 0.1F; //Thorium-232
	public static final float thf = 1.75F; //Thorium Fuel
	public static final float u = 0.35F; //Uranium
	public static final float u233 = 5.0F; //Uranium-233
	public static final float u235 = 1.0F; //Uranium-235
	public static final float u238 = 0.25F; //Uranium-238
	public static final float uf = 0.5F; //Uranium Fuel
	public static final float np237 = 2.5F; //Neptunium-237
	public static final float npf = 1.5F; //Neptunium Fuel
	public static final float pu = 7.5F; //Plutonium
	public static final float purg = 6.25F; //Plutonium Reactor Grade
	public static final float pu238 = 10.0F; //Plutonium-238
	public static final float pu239 = 5.0F; //Plutonium-239
	public static final float pu240 = 7.5F; //Plutonium-240
	public static final float pu241 = 25.0F; //Plutonium-241
	public static final float puf = 4.25F; //Plutonium Fuel
	public static final float am241 = 8.5F; //Americium-241
	public static final float am242 = 9.5F; //Americium-242
	public static final float amrg = 9.0F; //Americium Reactor Grade
	public static final float amf = 4.75F; //Americium Fuel
	public static final float mox = 2.5F; //Moxie ^w^
	public static final float sa326 = 15.0F; //Schrabidium
	public static final float sa327 = 17.5F; //Solinium
	public static final float saf = 5.85F; //Schrabidium Fuel
	public static final float les = 2.52F;
	public static final float mes = 5.25F;
	public static final float hes = 8.8F;
	public static final float gh336 = 5.0F; //Ghisorium
	public static final float radsource_mult = 0.5F;
	public static final float pobe = po210 * radsource_mult;
	public static final float rabe = ra226 * radsource_mult;
	public static final float pube = pu238 * radsource_mult;
	public static final float aupb = au198 + pb209 + 2000F;
	public static final float zfb_bi = u235 * 0.35F;
	public static final float zfb_pu241 = pu241 * 0.5F;
	public static final float zfb_am_mix = amrg * 0.5F;
	public static final float bf = 300_000.0F; //Balefire

	public static final float sr = sa326 * 0.1F; //Scharanium
	public static final float sb = sa326 * 0.2F; //Schrabidate
	public static final float trx = 25.0F;
	public static final float trn = 0.1F; //Trinitite
	public static final float wst = 150.0F; //Nuclear Waste
	public static final float wstv = 75F; //Nuclear Waste Vitrified
	public static final float yc = u * 1.2F; //Yellowcake
	public static final float fo = 10F; //Fallout
	public static final float radspice = 20_000.0F;
	public static final float unof = 10_000.0F;

	public static final float ore = 0.01F;
	public static final float nugget = 0.1F;
	public static final float ingot = 1.0F;
	public static final float gem = 1.0F;
	public static final float plate = 1.0F;
	public static final float wire = 0.1F;
	public static final float powder_mult = 3.0F;
	public static final float powder = ingot * powder_mult;
	public static final float powder_tiny = nugget * powder_mult;
	public static final float block = 10.0F;
	public static final float crystal = 5.0F;
	public static final float billet = 0.5F;
	public static final float rtg = billet * 3;
	public static final float rod = 0.5F;
	public static final float rod_dual = rod * 2;
	public static final float rod_quad = rod * 4;
	public static final float rod_rbmk = rod * 8;
	public static final float magt = nugget * 0.5F * sa326;

		
		ItemHazardModule module;
		
		public ItemHazard(String s) {
			super(s);
			this.module = new ItemHazardModule();
		}

		@Override
		public ItemHazardModule getModule() {
			return this.module;
		}
		
		@Override
		public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected){
			if(!worldIn.isRemote && entity instanceof EntityLivingBase)
				this.module.applyEffects((EntityLivingBase) entity, stack.getCount(), itemSlot, isSelected, ((EntityLivingBase)entity).getHeldItem(EnumHand.MAIN_HAND) == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
		}
		
		@Override
		public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn){
			this.module.addInformation(stack, list, flagIn);
			super.addInformation(stack, world, list, flagIn);
		}
		
		@Override
		public boolean onEntityItemUpdate(EntityItem item){
			boolean m = this.module.onEntityItemUpdate(item);
			boolean i = super.onEntityItemUpdate(item);
			return m || i;
		}
		
		/*
		 * DEPRECATED CTORS
		 */
		@Deprecated()
		public ItemHazard(float radiation, String s) {
			this(s);
			this.module.addRadiation(radiation);
		}

		@Deprecated()
		public ItemHazard(float radiation, boolean fire, String s) {
			this(s);
			this.module.addRadiation(radiation);
			if(fire) this.module.addFire(5);
		}

		@Deprecated()
		public ItemHazard(float radiation, boolean fire, boolean blinding, String s) {
			this(s);
			this.module.addRadiation(radiation);
			if(blinding) this.module.addBlinding();
			if(fire) this.module.addFire(5);
		}
}
