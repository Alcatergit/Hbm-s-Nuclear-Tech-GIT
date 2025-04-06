package com.hbm.items.machine;

import com.hbm.items.ItemEnumMulti;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;

public class ItemBreedingRod extends ItemEnumMulti {

    public ItemBreedingRod(String registryName) {
        super( registryName, BreedingRodType.class, true, true);
    }

    public enum BreedingRodType {
        LITHIUM,
        TRITIUM,
        CO,
        CO60,
        TH232,
        THF,
        U235,
        NP237,
        U238,
        PU238,
        PU239,
        RGP,
        WASTE,

        //Required for prototype
        LEAD,
        URANIUM,

        RA226,
        AC227
    }
}
