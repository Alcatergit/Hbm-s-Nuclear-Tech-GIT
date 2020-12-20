package com.hbm.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AssemblerRecipeSyncPacket implements IMessage {

	//public List<AssemblerRecipe> recipes;
	
	public AssemblerRecipeSyncPacket() {
	}
	
	/*public AssemblerRecipeSyncPacket(List<AssemblerRecipe> recipes){
		this.recipes = recipes;
	}*/
	
	@Override
	public void fromBytes(ByteBuf buf) {
		/*recipes = new ArrayList<AssemblerRecipe>();
		int size = buf.readInt();
		for(int i = 0; i < size; i ++){
			int inputSize = buf.readInt();
			List<ItemStack> inputs = new ArrayList<ItemStack>();
			for(int j = 0; j < inputSize; j ++){
				int id = buf.readInt();
				int meta = buf.readInt();
				int amount = buf.readInt();
				ItemStack stack = new ItemStack(Item.getItemById(id), amount, meta);
				if(buf.readBoolean()){
					try {
						stack.setTagCompound(CompressedStreamTools.read(new ByteBufInputStream(buf), new NBTSizeTracker(2097152L)));
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
				inputs.add(stack);
			}
			int id = buf.readInt();
			int meta = buf.readInt();
			int amount = buf.readInt();
			ItemStack output = new ItemStack(Item.getItemById(id), amount, meta);
			int time = buf.readInt();
			recipes.add(new AssemblerRecipe(time, inputs, output));
		}*/
	}

	@Override
	public void toBytes(ByteBuf buf) {
		/*buf.writeInt(recipes.size());
		for(int i = 0; i < recipes.size(); i ++){
			AssemblerRecipe recipe = recipes.get(i);
			List<ItemStack> inputs = recipe.getInputs();
			buf.writeInt(inputs.size());
			for(int j = 0; j < inputs.size(); j ++){
				ItemStack stack = inputs.get(j);
				buf.writeInt(Item.getIdFromItem(stack.getItem()));
				buf.writeInt(stack.getMetadata());
				buf.writeInt(stack.getCount());
				if(stack.hasTagCompound()){
					buf.writeBoolean(true);
					try {
						CompressedStreamTools.write(stack.getTagCompound(), new ByteBufOutputStream(buf));
					} catch(IOException e) {
						e.printStackTrace();
					}
				} else {
					buf.writeBoolean(false);
				}
			}
			buf.writeInt(Item.getIdFromItem(recipes.get(i).getOutput().getItem()));
			buf.writeInt(recipes.get(i).getOutput().getMetadata());
			buf.writeInt(recipes.get(i).getOutput().getCount());
			buf.writeInt(recipes.get(i).getTime());
		}*/
	}
	
	public static class Handler implements IMessageHandler<AssemblerRecipeSyncPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(AssemblerRecipeSyncPacket message, MessageContext ctx) {
			
			/*Minecraft.getMinecraft().addScheduledTask(() -> {
				ItemAssemblyTemplate.recipesBackup = ItemAssemblyTemplate.recipes;
				ItemAssemblyTemplate.recipes = message.recipes;
			});*/
			return null;
		}
		
	}

}
