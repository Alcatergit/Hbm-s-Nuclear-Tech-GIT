package com.hbm.dim;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;

public abstract class BiomeGenBaseCelestial extends Biome {

    protected ArrayList<SpawnListEntry> creatures = new ArrayList<SpawnListEntry>();
    protected ArrayList<SpawnListEntry> monsters = new ArrayList<SpawnListEntry>();
    protected ArrayList<SpawnListEntry> waterCreatures = new ArrayList<SpawnListEntry>();
    protected ArrayList<SpawnListEntry> caveCreatures = new ArrayList<SpawnListEntry>();

    public BiomeGenBaseCelestial(BiomeProperties properties) {
        super(properties);
    }

    // Returns a copy of the lists to prevent them being modified
    @SuppressWarnings("rawtypes")
    @Override
    public List getSpawnableList(EnumCreatureType type) {
        switch(type) {
            case MONSTER: return (List)monsters.clone();
            case CREATURE: return (List)creatures.clone();
            case WATER_CREATURE: return (List)waterCreatures.clone();
            case AMBIENT: return (List)caveCreatures.clone();
        default: return new ArrayList<SpawnListEntry>();
        }
    }
    
}
