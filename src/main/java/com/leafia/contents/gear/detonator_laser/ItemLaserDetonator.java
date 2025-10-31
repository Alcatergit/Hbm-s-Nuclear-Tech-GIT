package com.leafia.contents.gear.detonator_laser;

import com.hbm.config.GeneralConfig;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IHoldableWeapon;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.RenderScreenOverlay;
import com.hbm.util.I18nUtil;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Random;

public class ItemLaserDetonator extends Item implements IHoldableWeapon {
	@Override
	public RenderScreenOverlay.Crosshair getCrosshair() {
		return RenderScreenOverlay.Crosshair.L_ARROWS;
	}
	public ItemLaserDetonator(String s) {
		this.setRegistryName(s);
		this.setTranslationKey(s);
		this.setCreativeTab(MainRegistry.controlTab);
		
		ModItems.ALL_ITEMS.add(this);
	}
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(I18nUtil.resolveKey("item.detonator_laser.desc"));
		list.add(I18nUtil.resolveKey("item.detonator_laser.desc2"));
	}
	@Override
	public float getADS() {
		return 0.3f;
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		boolean rem = world.isRemote;
		int distance = 128; // vanilla raytrace (which it used to use) has force max range of 200 and even less depending on angle
		if (player.isSneaking()) {
			if (player.getHeldItemMainhand().isEmpty() != player.getHeldItemOffhand().isEmpty())
				distance = 512;
		}
		EnumHandSide side = player.getPrimaryHand();
		if (hand == EnumHand.OFF_HAND)
			side = side.opposite();
		Vec3d startPos = player.getPositionVector().add(0,player.getEyeHeight(),0);
		Vec3d vecLook = player.getLook(1);
		// thanks https://www.geogebra.org/m/psMTGDgc
		Vec3d vecRight = Vec3d.fromPitchYaw(0,player.rotationYawHead).crossProduct(new Vec3d(0,1,0));
		Vec3d vecUp = vecLook.crossProduct(vecRight.scale(-1));
		Vec3d rayStart = startPos.add(vecLook.scale(0.5)).add(vecRight.scale((distance < 200) ? (side == EnumHandSide.RIGHT) ? 0.1 : -0.1 : 0)).add(vecUp.scale(-0.16));
		RayTraceResult ray = Library.leafiaRayTraceBlocks(
				world,
				rayStart,
				startPos.add(vecLook.scale(distance)),
				false,false,true
		); //Library.rayTrace(player, distance, 1);
		if (ray != null) {
			BlockPos pos = ray.getBlockPos();
			Vec3 vec = Vec3.createVectorHelper(pos.getX() + 0.5 - rayStart.x,pos.getY() + 0.5 - rayStart.y,pos.getZ() + 0.5 - rayStart.z);
			PacketDispatcher.wrapper.sendToAllAround(
					new LaserDetonatorPacket().set(new Vec3(rayStart),vec),
					new NetworkRegistry.TargetPoint(player.dimension,pos.getX(),pos.getY(),pos.getZ(),distance * 2)
			);
			if (world.getBlockState(pos).getBlock() instanceof IBomb) {
				if (!rem)
					((IBomb) world.getBlockState(pos).getBlock()).explode(world,pos);

				if (GeneralConfig.enableExtendedLogging)
					MainRegistry.logger.log(Level.INFO,"[DET] Tried to detonate block at " + pos.getX() + " / " + pos.getY() + " / " + pos.getZ() + " by " + player.getDisplayName() + "!");

				if (rem)
					player.sendMessage(new TextComponentString("§2[" + I18nUtil.resolveKey("chat.detonated") + "]" + "§r"));
				else
					world.playSound(null,player.posX,player.posY,player.posZ,HBMSoundEvents.techBleep,SoundCategory.AMBIENT,1.0F,1.0F);

			} else {
				if (rem)
					player.sendMessage(new TextComponentString("§c" + I18nUtil.resolveKey("chat.posbadrror") + "§r"));
				else
					world.playSound(null,player.posX,player.posY,player.posZ,HBMSoundEvents.techBleep,SoundCategory.AMBIENT,1.0F,1.0F);
			}
		} else {
			if (rem)
				player.sendMessage(new TextComponentString("§c" + I18nUtil.resolveKey("chat.postoofarerror") + "§r"));
			else
				world.playSound(null,player.posX,player.posY,player.posZ,HBMSoundEvents.techBleep,SoundCategory.AMBIENT,1.0F,1.0F);
		}
		return super.onItemRightClick(world, player, hand);
	}
	public static class LaserDetonatorPacket extends RecordablePacket {
		public Vec3 startPoint;
		public Vec3 direction;
		public LaserDetonatorPacket() {
		}
		public LaserDetonatorPacket set(Vec3 startPoint,Vec3 direction) {
			this.startPoint = startPoint;
			this.direction = direction;
			return this;
		}
		@Override
		public void fromBits(LeafiaBuf buf) {
			startPoint = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
			direction = new Vec3(buf.readDouble(),buf.readDouble(),buf.readDouble());
		}
		@Override
		public void toBits(LeafiaBuf buf) {
			buf.writeDouble(startPoint.xCoord);
			buf.writeDouble(startPoint.yCoord);
			buf.writeDouble(startPoint.zCoord);
			buf.writeDouble(direction.xCoord);
			buf.writeDouble(direction.yCoord);
			buf.writeDouble(direction.zCoord);
		}
		public static class Handler implements IMessageHandler<LaserDetonatorPacket, IMessage> {
			@Override
			@SideOnly(Side.CLIENT)
			public IMessage onMessage(LaserDetonatorPacket message,MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					double length = message.direction.length();
					Vec3 unit = message.direction.normalize();
					Random rand = Minecraft.getMinecraft().world.rand;
					for (int i = 0; i <= 1; i++) {
						for (double p = 0; p < length/2;) {
							p+=(Math.pow(p/30d,1.5)+1)*0.2;
							double d = (length-p)*i + p*(1-i);
							NBTTagCompound particle = new NBTTagCompound();
							particle.setString("type", "rslight");
							particle.setDouble("posX",message.startPoint.xCoord+unit.xCoord*d);
							particle.setDouble("posY",message.startPoint.yCoord+unit.yCoord*d);
							particle.setDouble("posZ",message.startPoint.zCoord+unit.zCoord*d);
							particle.setDouble("mX",(rand.nextDouble()-0.5)*0.05);
							particle.setDouble("mY",(rand.nextDouble()-0.5)*0.05);
							particle.setDouble("mZ",(rand.nextDouble()-0.5)*0.05);
							particle.setFloat("red",1);
							particle.setFloat("green",0);
							particle.setFloat("blue",0);
							particle.setFloat("scale",0.5f);
							MainRegistry.proxy.effectNT(particle);
						}
					}
				});
				return null;
			}
		}
	}
}
