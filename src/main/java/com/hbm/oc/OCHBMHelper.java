package com.hbm.oc;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class OCHBMHelper {

    public static HashMap<String, Object> toHashMap(@Nullable BlockPos pos) {
        if (pos == null) {
            return new HashMap<>(0);
        }
        return new HashMap<String, Object>(3) {{
            put("x", pos.getX());
            put("y", pos.getY());
            put("z", pos.getZ());
        }};
    }

    public static HashMap<String, Object> toHashMap(@Nullable Item item) {
        if (item == null) {
            return new HashMap<>(0);
        }
        return new HashMap<String, Object>(5) {{
            put("name", Objects.requireNonNull(item.getRegistryName()).toString());
            put("translation_key", item.getTranslationKey());
            put("damageable", item.isDamageable());
            put("repairable", item.isRepairable());
        }};
    }

    public static HashMap<String, Object> toHashMap(@Nullable NBTTagCompound nbt) {
        if (nbt == null) {
            return new HashMap<>(0);
        }

        HashMap<String, Object> map = new HashMap<>();

        for (String key : nbt.getKeySet()) {
            NBTBase base = nbt.getTag(key);
            map.put(key, convertNBT(base));
        }

        return map;
    }

    public static HashMap<String, Object> toHashMap(@Nullable ItemStack stack) {
        if (stack == null) {
            return new HashMap<>(0);
        }
        return new HashMap<String, Object>(4) {{
            put("count", stack.getCount());
            put("maxCount", stack.getMaxStackSize());
            put("displayName", stack.getDisplayName());
            put("item", toHashMap(stack.getItem()));
            put("nbt", toHashMap(stack.getTagCompound()));
        }};
    }

    private static Object convertNBT(NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
            return toHashMap((NBTTagCompound) nbt);
        } else if (nbt instanceof NBTTagList) {
            return convertNBTList((NBTTagList) nbt);
        } else if (nbt instanceof NBTTagByte) {
            return ((NBTTagByte) nbt).getByte();
        } else if (nbt instanceof NBTTagShort) {
            return ((NBTTagShort) nbt).getShort();
        } else if (nbt instanceof NBTTagInt) {
            return ((NBTTagInt) nbt).getInt();
        } else if (nbt instanceof NBTTagLong) {
            return ((NBTTagLong) nbt).getLong();
        } else if (nbt instanceof NBTTagFloat) {
            return ((NBTTagFloat) nbt).getFloat();
        } else if (nbt instanceof NBTTagDouble) {
            return ((NBTTagDouble) nbt).getDouble();
        } else if (nbt instanceof NBTTagString) {
            return ((NBTTagString) nbt).getString();
        } else if (nbt instanceof NBTTagByteArray) {
            return ((NBTTagByteArray) nbt).getByteArray();
        } else if (nbt instanceof NBTTagIntArray) {
            return ((NBTTagIntArray) nbt).getIntArray();
        } else if (nbt instanceof NBTTagEnd) {
            return null; // NBT End tag (shouldn't be encountered in a normal compound)
        }
        return null;
    }

    private static Object convertNBTList(NBTTagList nbtList) {
        List<Object> list = new ArrayList<>();

        for (int i = 0; i < nbtList.tagCount(); i++) {
            NBTBase element = nbtList.get(i);
            list.add(convertNBT(element));
        }

        return list;
    }

    public static HashMap<String, Object> toHashMap(@Nullable Fluid fluid)
    {
        if (fluid == null) {
            return new HashMap<>(0);
        }
        return new HashMap<String, Object>(3) {{
            put("name", fluid.getName());
            put("temperature", fluid.getTemperature());
            put("density", fluid.getDensity());
        }};
    }

    public static HashMap<String, Object> toHashMap(@Nullable FluidStack stack)
    {
        if (stack == null) {
            return new HashMap<>(0);
        }
        return new HashMap<String, Object>(3) {{
            put("amount", stack.amount);
            put("fluid", toHashMap(stack.getFluid()));
            put("tag", toHashMap(stack.tag));
        }};
    }

    public static HashMap<String, Object> toHashMap(@Nullable FluidTankInfo info)
    {
        if (info == null) {
            return new HashMap<>(0);
        }
        return new HashMap<String, Object>(2) {{
            put("capacity", info.capacity);
            put("fluid", toHashMap(info.fluid));
        }};
    }

    public static HashMap<String, Object> toHashMap(@Nullable FluidTank tank)
    {
        if (tank == null) {
            return new HashMap<>(0);
        }
        return new HashMap<String, Object>(3) {{
            put("capacity", tank.getCapacity());
            put("amount", tank.getFluidAmount());
            put("fluid", toHashMap(tank.getFluid()));
            put("info", toHashMap(tank.getInfo()));
        }};
    }
}