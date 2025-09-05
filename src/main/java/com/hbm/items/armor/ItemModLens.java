package com.hbm.items.armor;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ISatChip;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteSavedData;
import com.hbm.saveddata.satellites.SatelliteScanner;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
import java.util.List;

public class ItemModLens extends ItemArmorMod implements ISatChip {

    public static int countLimit = 100;
    public static HashMap<Block, Object[]> blockList = new HashMap<>();

    public ItemModLens(String s) {
        super(ArmorModHandler.extra, true, false, false, false, s);
    }

    @Override
    public void addInformation(ItemStack itemstack, World world, List<String> list, ITooltipFlag flag) {
        list.add(TextFormatting.AQUA + "Satellite Frequency: " + this.getFreq(itemstack));
        list.add("");

        super.addInformation(itemstack, world, list, flag);
    }

    @Override
    public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
        list.add(TextFormatting.AQUA + "  " + stack.getDisplayName() + " (Freq: " + getFreq(stack) + ")");
    }

    public static void initBlockList(){
        blockList.put(ModBlocks.ore_oil, new Object[]{300, "Oil", 0xa0a0a0});
        blockList.put(ModBlocks.ore_bedrock_oil, new Object[]{10, "Bedrock Oil", 0xa0a0a0});
        blockList.put(ModBlocks.ore_coltan, new Object[]{5, "Coltan", 0xffbd54});
        blockList.put(ModBlocks.ore_asbestos, new Object[]{10, "Asbestos", 0xfffbf7});
        blockList.put(ModBlocks.stone_gneiss, new Object[]{5000, "Schist", 0x8080ff});
        blockList.put(ModBlocks.ore_reiium, new Object[]{10, "Reiium", 0xbe0000});
        blockList.put(ModBlocks.ore_weidanium, new Object[]{10, "Weidanium", 0xff3f00});
        blockList.put(ModBlocks.ore_australium, new Object[]{10, "Australium", 0xffff00});
        blockList.put(ModBlocks.ore_verticium, new Object[]{10, "Verticium", 0x00ff00});
        blockList.put(ModBlocks.ore_unobtainium, new Object[]{10, "Unobtainium", 0x0059ff});
        blockList.put(ModBlocks.ore_daffergon, new Object[]{10, "Daffergon", 0xa500ff});
        blockList.put(Blocks.END_PORTAL_FRAME, new Object[]{1, "End Portal", 0x40b080});
        blockList.put(ModBlocks.basalt_gem, new Object[]{1, "Volcano Gem", 0xff5000});
        blockList.put(ModBlocks.volcano_core, new Object[]{1, "Volcano Core", 0xff4000});
        blockList.put(ModBlocks.pink_log, new Object[]{1, "Pink Log", 0xff00ff});
        blockList.put(ModBlocks.crate_ammo, new Object[]{1, null, 0x800000});
        blockList.put(ModBlocks.crate_can, new Object[]{1, null, 0x800000});
        blockList.put(ModBlocks.ore_schrabidium, new Object[]{1, "Schrabidium", 0x00d0ff});
        blockList.put(ModBlocks.ore_gneiss_schrabidium, new Object[]{1, "Schrabidium", 0x00d0ff});
        blockList.put(ModBlocks.ore_nether_schrabidium, new Object[]{1, "Schrabidium", 0x00d0ff});
        blockList.put(ModBlocks.ore_nether_plutonium, new Object[]{1, "Plutonium", 0x002b38});
        blockList.put(ModBlocks.ore_bedrock_block, new Object[]{10, "Bedrock Ore", 0xff5900});
        blockList.put(ModBlocks.taint, new Object[]{4, "TAINT", 0x00ff74});
    }

    @Override
    public void modUpdate(EntityLivingBase entity, ItemStack armor) {
        World world = entity.world;
        if(world.isRemote) return;
        if(!(entity instanceof EntityPlayerMP player)) return;

        ItemStack lens = ArmorModHandler.pryMods(armor)[ArmorModHandler.extra];

        if(lens == null) return;

        int freq = this.getFreq(lens);
        Satellite sat = SatelliteSavedData.getData(world).getSatFromFreq(freq);
        if(!(sat instanceof SatelliteScanner)) return;

        int x = (int) Math.floor(player.posX);
        int y = (int) Math.floor(player.posY);
        int z = (int) Math.floor(player.posZ);
        int range = 3;

        int cX = x >> 4;
        int cZ = z >> 4;

        int height = Math.max(Math.min(y + 10, 255), 64);
        int seg = (int) (world.getTotalWorldTime() % height);

        int hits = 0;

        for(int chunkX = cX - range; chunkX <= cX + range; chunkX++) {
            for(int chunkZ = cZ - range; chunkZ <= cZ + range; chunkZ++) {
                Chunk c = world.getChunk(chunkX, chunkZ);

                for(int ix = 0; ix < 16; ix++) {
                    for(int iz = 0; iz < 16; iz++) {

                        Block b = c.getBlockState(ix, seg, iz).getBlock();
                        int aX = (chunkX << 4) + ix;
                        int aZ = (chunkZ << 4) + iz;

                        Object[] highlightData = blockList.get(b);
                        if(highlightData != null && addIf((Integer) highlightData[0], aX, seg, aZ, (String) highlightData[1],(Integer)  highlightData[2], player)) hits++;

                        if(hits > countLimit) return;
                    }
                }
            }
        }
    }

    private boolean addIf(int chance, int x, int y, int z, String label, int color, EntityPlayerMP player) {

        if(chance == 1 || player.getRNG().nextInt(chance) == 0) {
            NBTTagCompound data = new NBTTagCompound();
            data.setString("type", "marker");
            data.setInteger("color", color);
            data.setInteger("expires", 15_000);
            data.setDouble("dist", 300D);
            if(label != null) data.setString("label", label);
            PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(data, x, y, z), player);
            return true;
        }

        return false;
    }
}
