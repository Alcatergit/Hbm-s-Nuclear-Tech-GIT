package com.hbm.packet;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemAssemblyTemplate.AssemblerRecipe;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AssemblerRecipeSyncPacket implements IMessage {

	public List<AssemblerRecipe> recipes;
	
	public AssemblerRecipeSyncPacket() {
	}
	
	public AssemblerRecipeSyncPacket(List<AssemblerRecipe> recipes){
		this.recipes = recipes;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		recipes = new ArrayList<AssemblerRecipe>();
		int size = buf.readInt();
		for(int i = 0; i < size; i ++){
			int inputSize = buf.readInt();
			List<ItemStack> inputs = new ArrayList<ItemStack>();
			for(int j = 0; j < inputSize; j ++){
				int id = buf.readInt();
				int meta = buf.readInt();
				int amount = buf.readInt();
				inputs.add(new ItemStack(Item.getItemById(id), amount, meta));
			}
			int id = buf.readInt();
			int meta = buf.readInt();
			int amount = buf.readInt();
			ItemStack output = new ItemStack(Item.getItemById(id), amount, meta);
			int time = buf.readInt();
			recipes.add(new AssemblerRecipe(time, inputs, output));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(recipes.size());
		for(int i = 0; i < recipes.size(); i ++){
			buf.writeInt(recipes.get(i).getInputs().size());
			for(int j = 0; j < recipes.get(i).getInputs().size(); j ++){
				AssemblerRecipe recipe = recipes.get(i);
				buf.writeInt(Item.getIdFromItem(recipe.getInputs().get(j).getItem()));
				buf.writeInt(recipe.getInputs().get(j).getMetadata());
				buf.writeInt(recipe.getInputs().get(j).getCount());
			}
			buf.writeInt(Item.getIdFromItem(recipes.get(i).getOutput().getItem()));
			buf.writeInt(recipes.get(i).getOutput().getMetadata());
			buf.writeInt(recipes.get(i).getOutput().getCount());
			buf.writeInt(recipes.get(i).getTime());
		}
	}
	
	public static class Handler implements IMessageHandler<AssemblerRecipeSyncPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(AssemblerRecipeSyncPacket message, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(() -> {
				ItemAssemblyTemplate.recipesBackup = ItemAssemblyTemplate.recipes;
				ItemAssemblyTemplate.recipes = message.recipes;
			});
			return null;
		}
		
	}

}
