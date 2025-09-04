package com.hbm.items.machine;

import com.hbm.items.ItemEnumMultiColor;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.render.icon.RGBMutatorInterpolatedComponentRemap;
import com.hbm.render.icon.TextureAtlasSpriteMutatable;
import com.hbm.util.EnumUtil;
import com.hbm.util.Function;
import com.hbm.util.Function.FunctionLinear;
import com.hbm.util.Function.FunctionQuadratic;
import com.hbm.util.Function.FunctionSqrt;
import com.hbm.util.Function.FunctionSqrtFalling;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Locale;

/*
 * Watz Isotropic Fuel, Oxidized
 */
public class ItemWatzPellet extends ItemEnumMultiColor {

    final boolean isDesaturated;

    public ItemWatzPellet(String s, boolean isDesaturated) {
        super(s, EnumWatzType.class, true, true);
        this.setMaxStackSize(16);
        this.setCreativeTab(MainRegistry.controlTab);
        this.isDesaturated = isDesaturated;
    }

    public static int desaturate(int color) {
        int r = (color & 0xff0000) >> 16;
        int g = (color & 0x00ff00) >> 8;
        int b = (color & 0x0000ff);

        int avg = (r + g + b) / 3;
        double approach = 0.9;
        double mult = 0.75;

        r -= (r - avg) * approach;
        g -= (g - avg) * approach;
        b -= (b - avg) * approach;

        r *= mult;
        g *= mult;
        b *= mult;

        return (r << 16) | (g << 8) | b;
    }

    public static double getEnrichment(ItemStack stack) {
        EnumWatzType num = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());
        return getYield(stack) / num.yield;
    }

    public static double getYield(ItemStack stack) {
        return getDouble(stack, "yield");
    }

    public static void setYield(ItemStack stack, double yieldv) {
        setDouble(stack, "yield", yieldv);
    }

    public static void setDouble(ItemStack stack, String key, double yieldv) {
        if (!stack.hasTagCompound()) setNBTDefaults(stack);
        stack.getTagCompound().setDouble(key, yieldv);
    }

    public static double getDouble(ItemStack stack, String key) {
        if (!stack.hasTagCompound()) setNBTDefaults(stack);
        return stack.getTagCompound().getDouble(key);
    }

    private static void setNBTDefaults(ItemStack stack) {
        EnumWatzType num = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());
        stack.setTagCompound(new NBTTagCompound());
        setYield(stack, num.yield);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerSprite(TextureMap map) {
        for (int i = 0; i < EnumWatzType.values().length; i++) {
            ResourceLocation spriteLoc = new ResourceLocation(RefStrings.MODID, ROOT_PATH + textures[i]);
            EnumWatzType type = EnumWatzType.values()[i];
            int light = isDesaturated ? desaturate(type.colorLight) : type.colorLight;
            int dark = isDesaturated ? desaturate(type.colorDark) : type.colorDark;
            TextureAtlasSpriteMutatable mutableIcon = new TextureAtlasSpriteMutatable(spriteLoc.toString(), new RGBMutatorInterpolatedComponentRemap(0xD2D2D2, 0x333333, light, dark));
            map.setTextureEntry(mutableIcon);
        }
    }


    @Override
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn) {

        if (this != ModItems.watz_pellet) return;

        EnumWatzType num = EnumUtil.grabEnumSafely(EnumWatzType.class, stack.getItemDamage());


        if (num.passive > 0) {
            list.add(TextFormatting.RED + "Self-igniting!");
        }
        list.add("§aDepletion: " + String.format(Locale.US, "%.1f", getDurabilityForDisplay(stack) * 100D) + "%");

        if (num.burnFunc != null) {
            list.add("§eBase fission rate: §r" + num.passive);
            list.add("§eFlux function: §r" + num.burnFunc.getLabelForFuel());
            list.add("§eFunction type: §r" + num.burnFunc.getDangerFromFuel());
        }
        if (num.heatDiv != null)
            list.add("§cThermal flux divider: §r" + num.heatDiv.getLabelForFuel() + " TU⁻¹");
        if (num.heatEmission > 0)
            list.add("§6Heat output per flux: " + num.heatEmission + " TU");
        else if(num.absorbFunc != null)
            list.add("§6Heat output with flux: " + num.absorbFunc.getLabelForFuel() + " TU");
        if (num.mudContent > 0)
            list.add("§2Mud creation: §r" + (num.mudContent * 1000)+"mB per flux");
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return this == ModItems.watz_pellet && getDurabilityForDisplay(stack) > 0D;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1D - getEnrichment(stack);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if (this != ModItems.watz_pellet) return;
        setNBTDefaults(stack); //minimize the window where NBT screwups can happen
    }

    public static enum EnumWatzType {

        SCHRABIDIUM(0x32FFFF, 0x005C5C, 2_000, 20D, 0.01D, new FunctionLinear(1.5D), new FunctionSqrtFalling(10D), null),
        HES(0x43EDFF, 0x004649, 1_750, 20D, 0.005D, new FunctionLinear(1.25D), new FunctionSqrtFalling(15D), null),
        MES(0xB4EBF4, 0x266270, 1_500, 15D, 0.0025D, new FunctionLinear(1.15D), new FunctionSqrtFalling(15D), null),
        LES(0xC7DEE1, 0x3B555B, 1_250, 15D, 0.00125D, new FunctionLinear(1D), new FunctionSqrtFalling(20D), null),
        HEN(0xA6B2A6, 0x030F03, 0, 10D, 0.0005D, new FunctionSqrt(100), new FunctionSqrtFalling(10D), null),
        MEU(0xC1C7BD, 0x2B3227, 0, 10D, 0.0005D, new FunctionSqrt(75), new FunctionSqrtFalling(10D), null),
        MEP(0x9AA3A0, 0x111A17, 0, 15D, 0.0005D, new FunctionSqrt(150), new FunctionSqrtFalling(10D), null),
        LEAD(0xA6A6B2, 0x03030F, 0, 0, 0.0025D, null, null, new FunctionSqrt(10)), //standard absorber, negative coefficient
        BORON(0xBDC8D2, 0x29343E, 0, 0, 0.0025D, null, null, new FunctionLinear(10)), //improved absorber, linear
        DU(0xC1C7BD, 0x2B3227, 0, 0, 0.0025D, null, null, new FunctionQuadratic(1D, 1D).withDiv(100)), //absorber with positive coefficient
        PU241(0x78817E, 394240, 1_950, 25, 0.0025D, new FunctionLinear(1.30D), new FunctionSqrt(2.66D / 18D).withOff(24D * 24D), null),
        AMRG(0xCEB3B9, 0x3A1C21, 2_888, 48, 0.0035D, new FunctionLinear(1.33D), new FunctionSqrt(4.33D / 25.5D).withOff(28D * 28D), null),
        AMF(0xCEB3B9, 0x3A1C21, 2_333, 44, 0.003D, new FunctionLinear(1.33D), new FunctionSqrt(4.11D / 22.2D).withOff(27D * 27D), null),

        CMRG(0xD8C2C4, 0xAD9799, 2_999, 50, 0.005D, new FunctionLinear(1.5D), new FunctionSqrt(5.5D / 25.5D).withOff(30D * 28D), null),
        CMF(0xD8C2C4, 0xAD9799, 2_444, 48, 0.0045D, new FunctionLinear(1.8D), new FunctionSqrt(5.0D / 20D).withOff(26D * 24D), null),
        BK247(0xC2C9C7, 0x8D9592, 3_000, 55, 0.012D, new FunctionLinear(1.5D), new FunctionSqrt(6.0D / 23.5D).withOff(10D * 10D), null),
        CF251(0x7879B4, 0x4D4E89, 1_250, 60, 0.001D, new FunctionLinear(1.7D), new FunctionSqrt(6.65D / 23.5D).withOff(10D * 10D), null),

        CF252(0x7879B4, 0x4D4E89, 1_050, 120, 0.0015D, new FunctionLinear(1.8D), new FunctionSqrt(8.85D / 28.8D).withOff(10D * 10D), null),
        ES253(0xB9BFB2, 0x594E44, 3_750, 70, 0.0001D, new FunctionLinear(1.3D), new FunctionSqrt(7.0D / 27.7D).withOff(10D * 10D), null);

        public double yield = 500_000_000;
        public final int colorLight;
        public final int colorDark;
        public final double mudContent;    //how much mud per reaction flux should be produced
        public final double passive;        //base flux emission
        public final double heatEmission;    //reactivity(1) to heat (heat per outgoing flux)
        public final Function burnFunc;    //flux to reactivity(0) (classic reactivity)
        public final Function heatDiv;    //reactivity(0) to reactivity(1) based on heat (temperature coefficient)
        public final Function absorbFunc;    //flux to heat (flux absobtion for non-active component)

        private EnumWatzType(int colorLight, int colorDark, double passive, double heatEmission, double mudContent, Function burnFunction, Function heatDivisor, Function absorbFunction) {
            this.colorLight = colorLight;
            this.colorDark = colorDark;
            this.passive = passive;
            this.heatEmission = heatEmission;
            this.mudContent = mudContent / 2D;
            this.burnFunc = burnFunction;
            this.heatDiv = heatDivisor;
            this.absorbFunc = absorbFunction;
        }
    }
}
