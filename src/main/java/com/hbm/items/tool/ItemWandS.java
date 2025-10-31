package com.hbm.items.tool;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import com.hbm.world.*;
import com.leafia.contents.worldgen.NTMStructBuffer;
import com.leafia.dev.custompacket.LeafiaCustomPacket;
import com.leafia.dev.custompacket.LeafiaCustomPacketEncoder;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.llib.exceptions.messages.TextWarningLeafia;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class ItemWandS extends Item {

	public ItemWandS(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18nUtil.resolveKey("desc.creative"));
		tooltip.add(I18nUtil.resolveKey("desc.structurewand.1"));
		tooltip.add(I18nUtil.resolveKey("desc.structurewand.2"));
		tooltip.add(I18nUtil.resolveKey("desc.structurewand.3"));
		if(stack.getTagCompound() != null)
		{
			switch(stack.getTagCompound().getInteger("building")) {
				case 0:
					tooltip.add(I18nUtil.resolveKey("desc.structurewand.factory"));
					break;
				case 1:
					tooltip.add(I18nUtil.resolveKey("desc.structurewand.factoryadvanced"));
					break;
				case 2:
					tooltip.add(I18nUtil.resolveKey("desc.structurewand.nuclear"));
					break;
				case 3:
					tooltip.add(I18nUtil.resolveKey("desc.structurewand.hadron"));
					break;
				case 4:
					tooltip.add(I18nUtil.resolveKey("desc.structurewand.watz"));
					break;
				case 5:
					tooltip.add(I18nUtil.resolveKey("desc.structurewand.safe"));
					break;
				default:
					tooltip.add(I18nUtil.resolveKey("desc.structurewand",stack.getTagCompound().getString("filename")));
					break;
			}
		}
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if(stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("building", 0);
		}
		
		boolean up = player.rotationPitch <= 0.5F;
		
		if(!world.isRemote)
		{
			Random rand = new Random();
			
			switch(stack.getTagCompound().getInteger("building"))
			{
				case 0:
					new FactoryTitanium().generate(world, rand, new BlockPos(pos.getX(), up ? pos.getY() - 2 : pos.getY(), pos.getZ()));
					//world.setBlock(x, y + 1, z, Blocks.chest, 2, 3);
					//if(world.getBlock(x, y + 1, z) == Blocks.chest)
					//{
					//	WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(1), (TileEntityChest)world.getTileEntity(x, y + 1, z), 10);
					//}
					break;
				case 1:
					new FactoryAdvanced().generate(world, rand, new BlockPos(pos.getX(), up ? pos.getY() - 2 : pos.getY(), pos.getZ()));
					break;
				case 2:
					new NuclearReactor().generate(world, rand, new BlockPos(pos.getX(), up ? pos.getY() - 4 : pos.getY(), pos.getZ()));
					break;
				case 3:
					new ParticleAccelerator().generate(world, rand, new BlockPos(pos.getX(), up ? pos.getY()-5 : pos.getY(), pos.getZ()));
					break;
				case 4:
					new Watz().generateReactor(world, rand, new BlockPos(pos.getX(), up ? pos.getY() - 12 : pos.getY(), pos.getZ()));
					break;
				case 5:
					new FWatz().generateHull(world, rand, new BlockPos(pos.getX(), up ? pos.getY() - 18 : pos.getY(), pos.getZ()));
					break;
				default:
					if (stack.getTagCompound().hasKey("filename")) {
						String path = "ntmstructs/"+stack.getTagCompound().getString("filename");
						if (!Files.exists(Paths.get(path)))
							player.sendMessage(new TextWarningLeafia("Structure "+path+" doesn't exist in your file system!"));
						else {
							NTMStructBuffer.fromFiles(path).rotateToFace(player.getHorizontalFacing()).build(world,pos.offset(facing));
						}
					}
					break;
			}
			
		}
		
		return EnumActionResult.SUCCESS;
	}
	public static class WandStructurePacket implements LeafiaCustomPacketEncoder {
		int index = 0;
		String name;
		@Override
		public void encode(LeafiaBuf buf) {
			buf.writeInt(index);
			if (index < 0)
				buf.writeUTF8String(name);
		}
		@Nullable
		@Override
		public Consumer<MessageContext> decode(LeafiaBuf buf) {
			index = buf.readInt();
			if (index < 0)
				name = buf.readUTF8String();
			return (ctx)->{
				ItemStack stack = ctx.getServerHandler().player.getHeldItemMainhand();
				if (stack.getItem() != ModItems.wand_s)
					stack = ctx.getServerHandler().player.getHeldItemOffhand();
				if (stack.getItem() != ModItems.wand_s) {
					ctx.getServerHandler().player.connection.disconnect(new TextComponentString("Chill bro"));
					return;
				}
				NBTTagCompound nbt = stack.getTagCompound();
				if (nbt == null) nbt = new NBTTagCompound();
				nbt.setInteger("building",index);
				if (name != null)
					nbt.setString("filename",name);
				else
					nbt.removeTag("filename");
				stack.setTagCompound(nbt);
			};
		}
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
		if(player.isSneaking())
		{
			if(world.isRemote)
			{
				ItemStack stack = player.getHeldItem(handIn);
				int index = 0;
				if (stack.getTagCompound() != null) {
					index = stack.getTagCompound().getInteger("building");
					if (index < 0)
						index--;
					else
						index++;
				}
				String filename = null;
				if (index > 5 || index < 0) {
					if (index > 0)
						index = -1;
					List<String> structs = new ArrayList<>();
					File dir = new File("ntmstructs");
					File[] files = dir.listFiles();
					if (files != null) {
						for (File file : files) {
							if (file.canRead()) {
								if (file.getName().endsWith(".ntmstruct")) {
									structs.add(file.getName());
								}
							}
						}
					}
					if (-index-1 < structs.size())
						filename = structs.get(-index-1);
					else
						index = 0;
				}
				WandStructurePacket packet = new WandStructurePacket();
				if (filename != null)
					packet.name = filename;
				packet.index = index;
				LeafiaCustomPacket.__start(packet).__sendToServer();
				switch(index)
				{
					case 0:
						player.sendMessage(new TextComponentTranslation("chat.structurewand.set.factory"));
						break;
					case 1:
						player.sendMessage(new TextComponentTranslation("chat.structurewand.set.factoryadvanced"));
						break;
					case 2:
						player.sendMessage(new TextComponentTranslation("chat.structurewand.set.nuclear"));
						break;
					case 3:
						player.sendMessage(new TextComponentTranslation("chat.structurewand.set.hadron"));
						break;
					case 4:
						player.sendMessage(new TextComponentTranslation("chat.structurewand.set.watz"));
						break;
					case 5:
						player.sendMessage(new TextComponentTranslation("chat.structurewand.set.safe"));
						break;
					default:
						player.sendMessage(new TextComponentTranslation("chat.structurewand.set",filename));
						break;
				}
			}
		}
				
		return super.onItemRightClick(world, player, handIn);
	}
}
