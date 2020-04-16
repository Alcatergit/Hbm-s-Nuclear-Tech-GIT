package com.hbm.packet;


import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.main.MainRegistry;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeN45;
import com.hbm.tileentity.bomb.TileEntityRailgun;
import com.hbm.tileentity.bomb.TileEntityTurretCIWS;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnace;
import com.hbm.tileentity.machine.TileEntityMachineBoiler;
import com.hbm.tileentity.machine.TileEntityMachineBoilerElectric;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineCoal;
import com.hbm.tileentity.machine.TileEntityMachineElectricFurnace;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AuxGaugePacket implements IMessage {

	int x;
	int y;
	int z;
	int value;
	int id;

	public AuxGaugePacket()
	{
		
	}

	public AuxGaugePacket(int x, int y, int z, int value, int id)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.value = value;
		this.id = id;
	}
	
	public AuxGaugePacket(BlockPos pos, int value, int id)
	{
		this(pos.getX(), pos.getY(), pos.getZ(), value, id);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		value = buf.readInt();
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(value);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<AuxGaugePacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(AuxGaugePacket m, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(() -> {
				try {
					TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

					if (te instanceof TileEntityAMSLimiter) {
						TileEntityAMSLimiter limiter = (TileEntityAMSLimiter)te;
						if(m.id == 0)
							limiter.locked = m.value == 1;
						else if(m.id == 1)
							limiter.efficiency = m.value;
					}
					if (te instanceof TileEntityAMSEmitter) {
						TileEntityAMSEmitter emitter = (TileEntityAMSEmitter)te;
						if(m.id == 0)
							emitter.locked = m.value == 1;
						else if(m.id == 1)
							emitter.efficiency = m.value;
					}
					if (te instanceof TileEntityAMSBase) {
						TileEntityAMSBase base = (TileEntityAMSBase)te;
						
						if(m.id == 0)
							base.locked = m.value == 1;
						else if(m.id == 1)
							base.color = m.value;
						else if(m.id == 2)
							base.efficiency = m.value;
						else if(m.id == 3)
							base.field = m.value;
					}
					if (te instanceof TileEntityTurretCIWS) {
						TileEntityTurretCIWS cwis = (TileEntityTurretCIWS)te;
						
						cwis.rotation = m.value;
					}
					if (te instanceof TileEntityTurretCheapo) {
						TileEntityTurretCheapo cwis = (TileEntityTurretCheapo)te;
						
						cwis.rotation = m.value;
					}
					if (te instanceof TileEntityMachineSeleniumEngine) {
						TileEntityMachineSeleniumEngine selenium = (TileEntityMachineSeleniumEngine)te;

						if(m.id == 0)
							selenium.pistonCount = m.value;
						if(m.id == 1)
							selenium.powerCap = m.value;
					}
					/*if (te instanceof TileEntityMachineDiesel) {
						TileEntityMachineDiesel selenium = (TileEntityMachineDiesel)te;
						
						selenium.powerCap = m.value;
					}
					*/if (te instanceof TileEntityMachineReactorSmall) {
						TileEntityMachineReactorSmall reactor = (TileEntityMachineReactorSmall)te;
						
						if(m.id == 0)
							reactor.rods = m.value;
						if(m.id == 1)
							reactor.retracting = m.value == 1;
						if(m.id == 2)
							reactor.coreHeat = m.value;
						if(m.id == 3)
							reactor.hullHeat = m.value;
					}
					/*if (te instanceof TileEntityBomber) {
						TileEntityBomber bomber = (TileEntityBomber)te;
						
						if(m.id == 0)
							bomber.yaw = m.value;
						if(m.id == 1)
							bomber.pitch = m.value;
						if(m.id == 2)
							bomber.type = m.value;
					}
					if (te instanceof TileEntityRadioRec) {
						TileEntityRadioRec radio = (TileEntityRadioRec)te;
						
						if(m.id == 0)
							radio.isOn = (m.value == 1);
						if(m.id == 1)
							radio.freq = ((double)m.value) / 10D;
					}*/
					if (te instanceof TileEntityMachineGasCent) {
						TileEntityMachineGasCent cent = (TileEntityMachineGasCent)te;

						if(m.id == 0)
							cent.progress = m.value;
						if(m.id == 1)
							cent.isProgressing = m.value == 1;
					}
					if (te instanceof TileEntityMachineCentrifuge) {
						TileEntityMachineCentrifuge cent = (TileEntityMachineCentrifuge)te;
						
						if(m.id == 0)
							cent.dualCookTime = m.value;
						if(m.id == 1)
							cent.isProgressing = m.value == 1;
					}
					if (te instanceof TileEntityMachineBoiler) {
						TileEntityMachineBoiler boiler = (TileEntityMachineBoiler)te;
						
						if(m.id == 0)
							boiler.heat = m.value;
						if(m.id == 1)
							boiler.burnTime = m.value;
					}
					if (te instanceof TileEntityMachineCoal) {
						TileEntityMachineCoal coalgen = (TileEntityMachineCoal)te;
						
						if(m.id == 0)
							coalgen.burnTime = m.value;
					}
					if (te instanceof TileEntityMachineElectricFurnace) {
						TileEntityMachineElectricFurnace furn = (TileEntityMachineElectricFurnace)te;
						
						if(m.id == 0)
							furn.dualCookTime = m.value;
					}
					if (te instanceof TileEntityMachineArcFurnace) {
						TileEntityMachineArcFurnace furn = (TileEntityMachineArcFurnace)te;
						
						if(m.id == 0)
							furn.dualCookTime = m.value;
					}
					if (te instanceof TileEntityMachineBoilerElectric) {
						TileEntityMachineBoilerElectric boiler = (TileEntityMachineBoilerElectric)te;
						
						if(m.id == 0)
							boiler.heat = m.value;
					}
					if (te instanceof TileEntityNukeCustom) {
						TileEntityNukeCustom nuke = (TileEntityNukeCustom)te;
						
						nuke.falls = m.value == 1;
					}
					if (te instanceof TileEntityNukeN45) {
						TileEntityNukeN45 nuke = (TileEntityNukeN45)te;
						
						nuke.primed = m.value == 1;
					}
					if (te instanceof TileEntityMachineReactorLarge) {
						TileEntityMachineReactorLarge reactor = (TileEntityMachineReactorLarge)te;

						if(m.id == 0)
							reactor.size = m.value;
					}
					if (te instanceof TileEntityCompactLauncher) {
						TileEntityCompactLauncher launcher = (TileEntityCompactLauncher)te;
						
						launcher.solid = m.value;
					}
					if (te instanceof TileEntityLaunchTable) {
						TileEntityLaunchTable launcher = (TileEntityLaunchTable)te;
						
						if(m.id == 0)
							launcher.solid = m.value;
						if(m.id == 1)
							launcher.padSize = PartSize.values()[m.value];
					}
					if (te != null && te instanceof TileEntityRailgun) {
						
						TileEntityRailgun gen = (TileEntityRailgun) te;
						
						if(m.id == 0) {
							Vec3 vec = Vec3.createVectorHelper(5.5, 0, 0);
							vec.rotateAroundZ((float) (gen.pitch * Math.PI / 180D));
							vec.rotateAroundY((float) (gen.yaw * Math.PI / 180D));
		
							double fX = gen.getPos().getX() + 0.5 + vec.xCoord;
							double fY = gen.getPos().getY() + 1 + vec.yCoord;
							double fZ = gen.getPos().getZ() + 0.5 + vec.zCoord;
							
							MainRegistry.proxy.spawnSFX(gen.getWorld(), fX, fY, fZ, 0, vec.normalize());
						}
					
					}
				} catch (Exception x) {}
			});
			
			
			return null;
		}
	}
}
