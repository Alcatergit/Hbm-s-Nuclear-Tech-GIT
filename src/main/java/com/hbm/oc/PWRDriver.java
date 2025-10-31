package com.hbm.oc;

import com.leafia.contents.machines.reactors.pwr.PWRData;
import com.leafia.contents.machines.reactors.pwr.blocks.components.control.PWRControlTE;
import com.leafia.contents.machines.reactors.pwr.blocks.components.element.PWRElementTE;
import com.leafia.contents.machines.reactors.pwr.blocks.components.terminal.PWRTerminalTE;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverSidedTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;

import java.util.HashMap;
import java.util.Optional;

public class PWRDriver extends DriverSidedTileEntity {
    @Override
    public Class<?> getTileEntityClass() {
        return PWRTerminalTE.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos blockPos, EnumFacing enumFacing) {
        TileEntity entity = world.getTileEntity(blockPos);

        if (entity instanceof PWRTerminalTE) {
            PWRTerminalTE te = (PWRTerminalTE) entity;
            return new PWREnvironment(world, te.getPos());
        }

        return null;
    }

    public static class PWREnvironment extends HBMDriver<PWRTerminalTE> {

        public PWREnvironment(World w, BlockPos pos) {
            super(PWRTerminalTE.class, w, pos);
        }

        @Override
        public String preferredName() {
            return "pwr";
        }

        @Override
        public int priority() {
            return 1000;
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function():table -- Get the control rods of the reactor")
        public Object[] getControlRods(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            HashMap<Object, Object> result = new HashMap<Object, Object>(core.controls.size() + 1) {{
                put("master", core.masterControl);
            }};
            BlockPos[] controlRodPositions = core.controls.stream().sorted().toArray(BlockPos[]::new);
            int i = 1;
            for (BlockPos pos : controlRodPositions) {
                TileEntity control = world.getTileEntity(pos);
                if (control instanceof PWRControlTE) {
                    PWRControlTE c = (PWRControlTE) control;
                    result.put(i, new HashMap<String, Object>() {{
                        put("world_pos", OCHBMHelper.toHashMap(c.getPos()));
                        put("target_pos", c.targetPosition);
                        put("pos", c.position);
                        put("height", c.height);
                        put("name", c.name);
                    }});
                    i++;
                }
            }
            return new Object[]{result};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function():table -- Get the fuel rods of the reactor")
        public Object[] getFuelRods(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            HashMap<Integer, Object> result = new HashMap<>(core.fuels.size());
            int i = 1;
            for (BlockPos pos : core.fuels) {
                TileEntity fuel = world.getTileEntity(pos);
                if (fuel instanceof PWRElementTE) {
                    PWRElementTE f = (PWRElementTE) fuel;
                    result.put(i, new HashMap<String, Object>() {{
                        put("world_pos", OCHBMHelper.toHashMap(f.getPos()));
                        put("scale", f.channelScale);
                        put("exchange_scale", f.exchangerScale);
                        put("heat", f.getHeat());
                        put("height", f.getHeight());
                        put("fuel", OCHBMHelper.toHashMap(f.inventory.getStackInSlot(0)));
                    }});
                    i++;
                }
            }
            return new Object[]{result};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function():table -- Get the tanks of the reactor")
        public Object[] getTanks(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            HashMap<Integer, Object> result = new HashMap<>(core.tanks.length);
            for (int i = 0, tanksLength = core.tanks.length; i < tanksLength; i++) {
                FluidTank tank = core.tanks[i];
                Fluid type = core.tankTypes[i];
                result.put(i, new HashMap<String, Object>() {{
                    put("tank", OCHBMHelper.toHashMap(tank));
                    put("type", OCHBMHelper.toHashMap(type));
                }});
                i++;
            }
            return new Object[]{result};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function():table -- Get the cool coolant of the reactor")
        public Object[] getCoolant(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            return new Object[]{new HashMap<String, Object>() {{
                put("tank", OCHBMHelper.toHashMap(core.tanks[0]));
                put("type", OCHBMHelper.toHashMap(core.tankTypes[0]));
            }}};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function():table -- Get the hot coolant of the reactor")
        public Object[] getHotCoolant(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            return new Object[]{new HashMap<String, Object>() {{
                put("tank", OCHBMHelper.toHashMap(core.tanks[1]));
                put("type", OCHBMHelper.toHashMap(core.tankTypes[1]));
            }}};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function():table -- Get the boiling coolant of the reactor")
        public Object[] getBoilingCoolant(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            return new Object[]{new HashMap<String, Object>() {{
                put("tank", OCHBMHelper.toHashMap(core.tanks[2]));
                put("type", OCHBMHelper.toHashMap(core.tankTypes[2]));
            }}};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function(int):nil -- set master control rod height")
        public Object[] setMasterControlHeight(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            core.masterControl = args.checkInteger(0);
            return new Object[]{null};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function(str, int):bool -- Set control rod height")
        public Object[] setControlHeightByName(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            boolean found = false;
            for (BlockPos pos : core.controls) {
                TileEntity control = world.getTileEntity(pos);
                if (control instanceof PWRControlTE) {
                    PWRControlTE c = (PWRControlTE) control;
                    if (c.name.equals(args.checkString(0))) {
                        c.targetPosition = args.checkDouble(1);
                        found = true;
                    }
                }
            }
            return new Object[]{found};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function(int, int):bool -- Set control rod height")
        public Object[] setControlHeightByIndex(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            int index = args.checkInteger(0) - 1;
            if (core.controls.size() > index) {
                Optional<BlockPos> pos = core.controls.stream().sorted().skip(index).findFirst();
                if (!pos.isPresent()) {
                    return new Object[]{false};
                }
                TileEntity control = world.getTileEntity(pos.get());
                if (control instanceof PWRControlTE) {
                    PWRControlTE c = (PWRControlTE) control;
                    c.targetPosition = args.checkDouble(1);
                    return new Object[]{true};
                }
            }
            return new Object[]{false};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function():nil -- gets the amount of hot coolant generated")
        public Object[] getProduction(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            return new Object[]{core.lastTickDrain};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function(int):str -- gets the name of control rods by index")
        public Object[] getControlName(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            int index = args.checkInteger(0) - 1;
            if (core.controls.size() > index) {
                Optional<BlockPos> pos = core.controls.stream().sorted().skip(index).findFirst();
                if (!pos.isPresent()) {
                    return new Object[]{null};
                }
                TileEntity control = world.getTileEntity(pos.get());
                if (control instanceof PWRControlTE) {
                    PWRControlTE c = (PWRControlTE) control;
                    return new Object[]{c.name};
                }
            }
            return new Object[]{null};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function(int, str):bool -- rename control rods by index")
        public Object[] renameControlByIndex(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            int index = args.checkInteger(0) - 1;
            if (core.controls.size() > index) {
                Optional<BlockPos> pos = core.controls.stream().sorted().skip(index).findFirst();
                if (!pos.isPresent()) {
                    return new Object[]{false};
                }
                TileEntity control = world.getTileEntity(pos.get());
                if (control instanceof PWRControlTE) {
                    PWRControlTE c = (PWRControlTE) control;
                    c.name = args.checkString(1);
                    core.resyncControls();
                    return new Object[]{true};
                }
            }
            return new Object[]{false};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function():nil -- gets the origin of the core")
        public Object[] getCoreOrigin(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            return new Object[]{OCHBMHelper.toHashMap(core.corePos)};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function(str):bool -- rename all control rods")
        public Object[] renameAll(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            for (BlockPos pos : core.controls) {
                TileEntity control = world.getTileEntity(pos);
                if (control instanceof PWRControlTE) {
                    PWRControlTE c = (PWRControlTE) control;
                    c.name = args.checkString(0);
                }
            }
            core.resyncControls();
            return new Object[]{true};
        }

        @SuppressWarnings("unused")
        @Callback(doc = "function(int):bool -- set all control rods to a specific height")
        public Object[] setAllControlHeights(Context context, Arguments args) {
            PWRData core = getTileEntity().getLinkedCore();
            if (core == null) return new Object[]{null};
            for (BlockPos pos : core.controls) {
                TileEntity control = world.getTileEntity(pos);
                if (control instanceof PWRControlTE) {
                    PWRControlTE c = (PWRControlTE) control;
                    c.targetPosition = args.checkDouble(0);
                }
            }
            return new Object[]{true};
        }

    }
}
