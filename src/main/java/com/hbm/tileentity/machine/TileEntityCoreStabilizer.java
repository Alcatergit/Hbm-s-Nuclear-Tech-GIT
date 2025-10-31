package com.hbm.tileentity.machine;

import api.hbm.energy.IEnergyUser;
import com.hbm.inventory.control_panel.*;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemLens;
import com.leafia.contents.machines.powercores.dfc.DFCBaseTE;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreStabilizer extends DFCBaseTE implements ITickable, IEnergyUser, LeafiaPacketReceiver, SimpleComponent, IControllable {

    public long power;
    public static final long maxPower = 10000000000000L;

    @Override
    public String getPacketIdentifier() {
        return "dfc_stabilizer";
    }

    public boolean cl_hasLens = false;

    @SideOnly(Side.CLIENT)
    @Override
    public void onReceivePacketLocal(byte key, Object value) {
        if (key == 0) {
            cl_hasLens = (int) value >= 0;
            if (cl_hasLens)
                this.lens = LensType.values()[(int) value];
        }
        if (key == 1)
            this.isOn = (boolean) value;
        //if (key == 2)
        //this.innerColor = (int)value;
        super.onReceivePacketLocal(key, value);
    }

    @Override
    public void onReceivePacketServer(byte key, Object value, EntityPlayer plr) {
        super.onReceivePacketServer(key,value,plr);
    }

    @Override
    public void onPlayerValidate(EntityPlayer plr) {
        super.onPlayerValidate(plr);
    }

    @Override
    public String getComponentName() {
        return "dfc_communicator";
    }

    @Callback
    public Object[] analyze(Context context, Arguments args) {
        TileEntityCore core = getCore();
        if (isOn && core != null) {
            LinkedHashMap<String, Object> mop = new LinkedHashMap<>();
            mop.put("temperature", core.temperature);
            mop.put("stabilization", core.stabilization);
            mop.put("containedEnergy", core.containedEnergy*1000_000);
            mop.put("expellingEnergy", core.expellingEnergy*1000_000);
            mop.put("potentialRelease", core.potentialGain*100);
            mop.put("collapse", Math.pow(core.collapsing,4)*100);
            mop.put("fuelA", core.tanks[0].getFluidAmount());
            mop.put("fuelB", core.tanks[1].getFluidAmount());
            return new Object[]{mop};
        }
        return new Object[]{"COULDN'T CONNECT TO THE CORE"};
    }

    @Callback(doc = "setLevel(newLevel: number)->(previousLevel: number)")
    public Object[] setLevel(Context context, Arguments args) {
        Object[] prev = new Object[]{watts};
        watts = MathHelper.clamp(args.checkInteger(0), 1, 100);
        return prev;
    }

    @Callback(doc = "getLevel()->(level: number)")
    public Object[] getLevel(Context context, Arguments args) {
        return new Object[]{watts};
    }

    @Callback(doc = "validate()->(success: boolean) - Whether the stabilizer is working or not")
    public Object[] validate(Context context, Arguments args) {
        return new Object[]{isOn};
    }

    @Callback(doc = "durability()->(lensDurability: int, maximum: int) - Returns currently installed lens' durability, or 0 if missing.")
    public Object[] durability(Context context, Arguments args) {
        ItemStack stack = inventory.getStackInSlot(0);
        if (stack.getItem() instanceof ItemLens) {
            ItemLens lens = (ItemLens) inventory.getStackInSlot(0).getItem();
            return new Object[]{ItemLens.getLensDamage(stack), lens.maxDamage};
        }
        return new Object[]{0, 0};
    }

    @Callback(doc = "getPower(); returns the current power level - long")
    public Object[] getPower(Context context, Arguments args) {
        return new Object[]{power};
    }

    @Callback(doc = "getMaxPower(); returns the maximum power level - long")
    public Object[] getMaxPower(Context context, Arguments args) {
        return new Object[]{getMaxPower()};
    }

    @Callback(doc = "getChargePercent(); returns the charge in percent - double")
    public Object[] getChargePercent(Context context, Arguments args) {
        return new Object[]{100D * getPower() / (double) getMaxPower()};
    }

    @Nullable
    TileEntityCore getCore() {
        return super.getCore(range);
		/*
		EnumFacing dir = EnumFacing.getFront(this.getBlockMetadata());
		for(int i = 1; i <= range; i++) {
			int x = pos.getX() + dir.getFrontOffsetX() * i;
			int y = pos.getY() + dir.getFrontOffsetY() * i;
			int z = pos.getZ() + dir.getFrontOffsetZ() * i;
			BlockPos pos1 = new BlockPos(x, y, z);
			TileEntity te = world.getTileEntity(pos1);
			if(te instanceof TileEntityCore) {
				beam = i;
				return (TileEntityCore)te;
			}
			if(te instanceof TileEntityCoreStabilizer)
				continue;
			if(world.getBlockState(pos1).getBlock() != Blocks.AIR)
				break;
		}
		return null;*/
    }

    @Override
    public BlockPos getControlPos() {
        return getPos();
    }

    @Override
    public World getControlWorld() {
        return getWorld();
    }

    @Override
    public void receiveEvent(BlockPos from,ControlEvent e) {
        if (e.name.equals("set_stabilizer_level")) {
            watts = Math.round(e.vars.get("level").getNumber());
        }
    }
    @Override
    public Map<String,DataValue> getQueryData() {
        Map<String,DataValue> map = new HashMap<>();
        map.put("active",new DataValueFloat(isOn ? 1 : 0));
        map.put("level",new DataValueFloat(watts));
        map.put("core_temp",new DataValueFloat(0));
        map.put("core_energy",new DataValueFloat(0));
        map.put("core_expel",new DataValueFloat(0));
        map.put("core_potent",new DataValueFloat(0));
        map.put("core_collapse",new DataValueFloat(0));
        TileEntityCore core = getCore();
        if (isOn && core != null) {
            map.put("core_temp",new DataValueFloat((float)core.temperature));
            map.put("core_energy",new DataValueFloat((float)core.containedEnergy*1000_000));
            map.put("core_expel",new DataValueFloat((float)core.expellingEnergy*1000_000));
            map.put("core_potent",new DataValueFloat((float)core.potentialGain*100));
            map.put("core_collapse",new DataValueFloat((float)Math.pow(core.collapsing,4)*100));
        }
        return map;
    }

    @Override
    public List<String> getInEvents() {
        return Collections.singletonList("set_stabilizer_level");
    }

    @Override
    public void validate(){
        super.validate();
        ControlEventSystem.get(world).addControllable(this);
    }

    @Override
    public void invalidate(){
        super.invalidate();
        ControlEventSystem.get(world).removeControllable(this);
    }

    public enum LensType {
        STANDARD(0x0c222c, 0x7F7F7F, ModItems.ams_lens),
        BLANK(0x121212, 0x646464, ModItems.ams_focus_blank),
        LIMITER(0x001733, 0x7F7F7F, ModItems.ams_focus_limiter),
        BOOSTER(0x4f1600, 0x7F7F7F, ModItems.ams_focus_booster),
        OMEGA(0x64001e, 0x9A9A9A, ModItems.ams_focus_omega);
        public final int outerColor;
        public final int innerColor;
        public final Item item;

        LensType(int outerColor, int innerColor, Item item) {
            this.outerColor = outerColor;
            this.innerColor = innerColor;
            this.item = item;
        }
    }

    public int watts;
    //public int beam;
    public LensType lens = LensType.STANDARD;
    public boolean isOn;

    public static final int range = 50;

    public TileEntityCoreStabilizer() {
        super(1);

    }

    @Override
    public void update() {
        if (!world.isRemote) {
            LeafiaPacket._start(this).__write(31,targetPosition).__sendToAffectedClients();

            this.updateStandardConnections(world, pos);

            watts = MathHelper.clamp(watts, 1, 100);
            long demand = (long) Math.pow(watts, 6);
            isOn = false;

            //beam = 0;

            ItemLens lens = null;
            if (inventory.getStackInSlot(0).getItem() instanceof ItemLens) {
                lens = (ItemLens) inventory.getStackInSlot(0).getItem();
                for (LensType type : LensType.values()) {
                    if (type.item == lens) {
                        this.lens = type;
                        break;
                    }
                }
//				if (lens == ModItems.ams_focus_blank) wtf is this stupid shit
//					this.lens = LensType.BLANK;
//				else if (lens == ModItems.ams_lens)
//					this.lens = LensType.STANDARD;
//				else if (lens == ModItems.ams_focus_limiter)
//					this.lens = LensType.LIMITER;
//				else if (lens == ModItems.ams_focus_booster)
//					this.lens = LensType.BOOSTER;
//				else if (lens == ModItems.ams_focus_omega)
//					this.lens = LensType.OMEGA;
            }

            if (lens != null && power >= demand * lens.drainMod) {
                isOn = true;
                TileEntityCore core = getCore();
                if (core != null) {
                    //core.field += (int)(watts * lens.fieldMod);
                    core.stabilization += lens.fieldMod * (watts / 100d);
                    core.stabilizers++;
                    core.energyMod *= lens.energyMod;
                    this.power -= (long) (demand * lens.drainMod);

                    long dmg = ItemLens.getLensDamage(inventory.getStackInSlot(0));
                    dmg += watts;

                    if (dmg >= lens.maxDamage)
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                    else
                        ItemLens.setLensDamage(inventory.getStackInSlot(0), dmg);
                }
            }
            //PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, beam, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 250));
            LeafiaPacket._start(this)
                    .__write((byte) 0, lens != null ? this.lens.ordinal() : -1)
                    .__write(1, isOn)
                    //.__write((byte)1,this.lens.outerColor)
                    //.__write((byte)2,this.lens.innerColor)
                    .__sendToClients(250);
        } else if (isOn)
            lastGetCore = getCore();
    }

    @Override
    public void networkUnpack(NBTTagCompound data) {
        power = data.getLong("power");
        watts = data.getInteger("watts");
        isOn = data.getBoolean("isOn");
    }

    @Override
    public String getName() {
        return "container.dfcStabilizer";
    }

    public long getPowerScaled(long i) {
        return (power * i) / maxPower;
    }

    public int getWattsScaled(int i) {
        return (watts * i) / 100;
    }

    @Override
    public void setPower(long i) {
        this.power = i;
    }

    @Override
    public long getPower() {
        return this.power;
    }

    @Override
    public long getMaxPower() {
        return maxPower;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        power = compound.getLong("power");
        watts = compound.getInteger("watts");
        isOn = compound.getBoolean("isOn");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setLong("power", power);
        compound.setInteger("watts", watts);
        compound.setBoolean("isOn", isOn);
        return super.writeToNBT(compound);
    }
}
