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

import java.util.List;

public class ItemModLens extends ItemArmorMod implements ISatChip {

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

    @Override
    public void modUpdate(EntityLivingBase entity, ItemStack armor) {
        World world = entity.world;
        if(world.isRemote) return;
        if(!(entity instanceof EntityPlayerMP)) return;

        EntityPlayerMP player = (EntityPlayerMP) entity;
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

                        if(addIf(ModBlocks.ore_oil, b, 300, aX, seg, aZ, "Oil", 0xa0a0a0, player)) hits++;
                        if(addIf(ModBlocks.ore_bedrock_oil, b, 300, aX, seg, aZ, "Bedrock Oil", 0xa0a0a0, player)) hits++;
                        if(addIf(ModBlocks.ore_coltan, b, 5, aX, seg, aZ, "Coltan", 0xa0a000, player)) hits++;
                        if(addIf(ModBlocks.stone_gneiss, b, 5000, aX, seg, aZ, "Schist", 0x8080ff, player)) hits++;
                        if(addIf(ModBlocks.ore_australium, b, 1000, aX, seg, aZ, "Australium", 0xffff00, player)) hits++;
                        if(addIf(Blocks.END_PORTAL_FRAME, b, 1, aX, seg, aZ, "End Portal", 0x40b080, player)) hits++;
                        if(addIf(ModBlocks.volcano_core, b, 1, aX, seg, aZ, "Volcano Core", 0xff4000, player)) hits++;
                        if(addIf(ModBlocks.pink_log, b, 1, aX, seg, aZ, "Pink Log", 0xff00ff, player)) hits++;
                        if(addIf(ModBlocks.crate_ammo, b, 1, aX, seg, aZ, null, 0x800000, player)) hits++;
                        if(addIf(ModBlocks.crate_can, b, 1, aX, seg, aZ, null, 0x800000, player)) hits++;
                        if(addIf(ModBlocks.ore_bedrock_block, b, 1, aX, seg, aZ, "Bedrock Ore", 0xff0000, player)) hits++;
                        if(addIf(ModBlocks.ore_bedrock_oil, b, 1, aX, seg, aZ, "Bedrock Ore", 0xff0000, player)) hits++;

                        if(hits > 100) return;
                    }
                }
            }
        }
    }

    private boolean addIf(Block target, Block b, int chance, int x, int y, int z, String label, int color, EntityPlayerMP player) {

        if(target == b && player.getRNG().nextInt(chance) == 0) {
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
