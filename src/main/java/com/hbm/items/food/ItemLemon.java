package com.hbm.items.food;

import com.hbm.entity.effect.EntityVortex;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.Foods;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.List;

public class ItemLemon extends ItemFood {

	public ItemLemon(int amount, float saturation, boolean isWolfFood, String s) {
		super(amount, saturation, isWolfFood);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(this == Foods.lemon) {
			list.add("Eh, good enough.");
		}
		
		if(this == Foods.definitelyfood) {
			list.add("A'right, I got sick and tired of");
			list.add("having to go out, kill things just");
			list.add("to get food and not die, so here is ");
			list.add("my absolutely genius solution:");
			list.add("");
			list.add("Have some edible dirt.");
		}
		
		if(this == Foods.med_ipecac) {
			list.add("Bitter juice that will cause your stomach");
			list.add("to forcefully eject it's contents.");
		}
		
		if(this == Foods.med_ptsd) {
			list.add("This isn't even PTSD mediaction, it's just");
			list.add("Ipecac in a different bottle!");
		}
		
		if(this == Foods.med_schizophrenia) {
			list.add("Makes the voices go away. Just for a while.");
			list.add("");
			list.add("...");
			list.add("Better not take it.");
		}
		
		if(this == Foods.loops) {
			list.add("Brøther, may I have some lööps?");
		}
		
		if(this == Foods.loop_stew) {
			list.add("A very, very healthy breakfast.");
		}
		
		if(this == Foods.twinkie) {
			list.add("Expired 600 years ago!");
		}

		if(this == Foods.canned_beef) {
			list.add("A few centuries ago, a cow died for this.");
		}

		if(this == Foods.canned_tuna) {
			list.add("I can't tell if that's actually tuna or dried cement.");
		}

		if(this == Foods.canned_mystery) {
			list.add("What's inside? Only one way to find out!");
		}

		if(this == Foods.canned_pashtet) {
			list.add("услуги перевода недоступны!");
		}

		if(this == Foods.canned_cheese) {
			list.add("Is it cheese? Is it rubber cement? Who knows, who cares.");
		}

		if(this == Foods.canned_milk) {
			list.add("Milk 2: More solid than ever before!");
		}

		if(this == Foods.canned_ass) {
			list.add("100% quality donkey meat!*");
		}

		if(this == Foods.canned_pizza) {
			list.add("A crime against humanity.");
		}

		if(this == Foods.canned_tube) {
			list.add("Tasty mush.");
		}

		if(this == Foods.canned_tomato) {
			list.add("Who wants some thick red paste?");
		}

		if(this == Foods.canned_asbestos) {
			list.add("TASTE the asbestosis!");
		}

		if(this == Foods.canned_bhole) {
			list.add("Singularity is yum yum in my tum tum!");
		}

		if(this == Foods.canned_jizz) {
			list.add("Wait wh-");
		}

		if(this == Foods.canned_hotdogs) {
			list.add("Not to be confused with cool cats.");
		}

		if(this == Foods.canned_leftovers) {
			list.add("ur 2 slow");
		}

		if(this == Foods.canned_yogurt) {
			list.add("Probably spoiled, but whatever.");
		}

		if(this == Foods.canned_stew) {
			list.add("...");
		}

		if(this == Foods.canned_chinese) {
			list.add("In China, Chinese food is just called food.");
		}

		if(this == Foods.canned_oil) {
			list.add("It makes motors go, so why not humans?");
		}

		if(this == Foods.canned_fist) {
			list.add("Yowser!");
		}

		if(this == Foods.canned_spam) {
			list.add("The three-and-a-half-minute sketch is set in the fictional Green Midget Cafe in Bromley.");
			list.add("An argument develops between the waitress, who recites a menu in which nearly");
			list.add("every dish contains Spam, and Mrs. Bun, who does not like Spam. She asks for a");
			list.add("dish without Spam, much to the amazement of her Spam-loving husband. The waitress");
			list.add("responds to this request with disgust. Mr. Bun offers to take her Spam instead,");
			list.add("and asks for a dish containing a lot of Spam and baked beans. The waitress says");
			list.add("no since they are out of baked beans; when Mr. Bun asks for a substitution of Spam,");
			list.add("the waitress again responds with disgust. At several points, a group of Vikings in");
			list.add("the restaurant interrupts conversation by loudly singing about Spam.");
			list.add("The irate waitress orders them to shut up, but they resume singing more loudly.");
			list.add("A Hungarian tourist comes to the counter, trying to order by using a wholly");
			list.add("inaccurate Hungarian/English phrasebook (a reference to a previous sketch).");
			list.add("He is rapidly escorted away by a police constable. The sketch abruptly cuts to a");
			list.add("historian in a television studio talking about the origin of the Vikings in the café.");
			list.add("As he goes on, he begins to increasingly insert the word \"Spam\" into every");
			list.add("sentence, and the backdrop is raised to reveal the restaurant set behind.");
			list.add("The historian joins the Vikings in their song, and Mr. and Mrs. Bun are lifted by");
			list.add("wires out of the scene while the singing continues. In the original televised performance,");
			list.add("the closing credits begin to scroll with the singing still audible in the background.");
		}

		if(this == Foods.canned_fried) {
			list.add("Even the can is deep fried!");
		}

		if(this == Foods.canned_napalm) {
			list.add("I love the smell of old memes in the morning!");
		}

		if(this == Foods.canned_diesel) {
			list.add("I'm slowly running out of jokes for these.");
		}

		if(this == Foods.canned_kerosene) {
			list.add("Just imagine a witty line here.");
		}

		if(this == Foods.canned_recursion) {
			list.add("Canned Recursion");
		}

		if(this == Foods.canned_bark) {
			list.add("Extra cronchy!");
		}

		if(this == Foods.pudding) {
			list.add("What if he did?");
			list.add("What if he didn't?");
			list.add("What if the world was made of pudding?");
		}
		if(this == ModItems.ingot_semtex) {
			list.add("Semtex H Plastic Explosive");
			list.add("Performant explosive for many applications.");
			list.add("Edible");
		}

		if(this == Foods.marshmallow) {
			list.add("Gets grilled in the heat of burning nuclear failure");
		}

		if(this == Foods.marshmallow_roasted) {
			list.add("Hmmm... tastes a bit metallic");
		}
	}
	
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if(this == Foods.med_ipecac || this == Foods.med_ptsd) {
			player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 50, 49));
		}
		
		if(this == Foods.loop_stew) {
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20 * 20, 1));
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 60 * 20, 2));
			player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60 * 20, 1));
			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * 20, 2));
		}
		
		if(this == Foods.canned_beef ||
				this == Foods.canned_tuna ||
				this == Foods.canned_mystery ||
				this == Foods.canned_pashtet ||
				this == Foods.canned_cheese ||
				this == Foods.canned_jizz ||
				this == Foods.canned_milk ||
				this == Foods.canned_ass ||
				this == Foods.canned_pizza ||
				this == Foods.canned_tomato ||
				this == Foods.canned_asbestos ||
				this == Foods.canned_bhole ||
				this == Foods.canned_hotdogs ||
				this == Foods.canned_yogurt ||
				this == Foods.canned_stew ||
				this == Foods.canned_chinese ||
				this == Foods.canned_oil ||
				this == Foods.canned_fist ||
				this == Foods.canned_spam ||
				this == Foods.canned_fried ||
				this == Foods.canned_napalm ||
				this == Foods.canned_diesel ||
				this == Foods.canned_kerosene ||
				this == Foods.canned_recursion ||
				this == Foods.canned_bark)
        	tryAddItem(player, new ItemStack(Foods.can_key));
		
		if(this == Foods.canned_recursion && worldIn.rand.nextInt(10) > 0)
        	tryAddItem(player, new ItemStack(Foods.canned_recursion));
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		ItemStack sta = super.onItemUseFinish(stack, worldIn, entityLiving);
        
        if(this == Foods.loop_stew)
        	return new ItemStack(Items.BOWL);
        

    	
        if (this == Foods.canned_bhole && !worldIn.isRemote) {
    		EntityVortex vortex = new EntityVortex(worldIn, 0.5F);
    		vortex.posX = entityLiving.posX;
    		vortex.posY = entityLiving.posY;
    		vortex.posZ = entityLiving.posZ;
    		worldIn.spawnEntity(vortex);
        }
        
        return sta;
	}

	public static void tryAddItem(EntityPlayer player, ItemStack stack) {
		if(!player.inventory.addItemStackToInventory(stack)) {
			player.dropItem(stack, false);
		}
	}
}
