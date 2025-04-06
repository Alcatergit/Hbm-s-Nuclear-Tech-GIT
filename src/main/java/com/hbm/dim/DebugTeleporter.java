package com.hbm.dim;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;

public class DebugTeleporter extends Teleporter {

	private final WorldServer sourceServer;
	private final WorldServer targetServer;

	private double x;
	private double y;
	private double z;

	private boolean grounded; // Should we be placed directly on the first ground block below?

	private EntityPlayerMP playerMP;

	public DebugTeleporter(WorldServer sourceServer, WorldServer targetServer, EntityPlayerMP playerMP, double x, double y, double z, boolean grounded) {
		super(targetServer);
		this.sourceServer = sourceServer;
		this.targetServer = targetServer;
		this.playerMP = playerMP;
		this.x = x;
		this.y = y;
		this.z = z;
		this.grounded = grounded;
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {
		int ix = (int)x;
		int iy = (int)y;
		int iz = (int)z;

		if (grounded) {
			for (int i = targetServer.getHeight(); i > 0; i--) {
				if (!targetServer.isAirBlock(new BlockPos(ix, i, iz))) {
					y = i + 5;
					break;
				}
			}
		} else {
			targetServer.getChunk(new BlockPos(ix, MathHelper.clamp(iy, 1, 255), iz)); // dummy load to maybe gen chunk
		}

		entity.setPositionAndUpdate(x, y, z);
	}

	private void runTeleport() {
		MinecraftServer server = playerMP.getServer();
		if (server == null) return;

		int fromDimension = playerMP.dimension;
		Entity ridingEntity = playerMP.getRidingEntity();

		playerMP.changeDimension(targetServer.provider.getDimension(), this);

		if (ridingEntity != null && !ridingEntity.isDead) {
			ridingEntity.dimension = fromDimension;
			ridingEntity.setDead();

			Entity newEntity = EntityList.createEntityByID(ridingEntity.getEntityId(), targetServer);
			if (newEntity != null) {
				NBTTagCompound nbttagcompound = ridingEntity.writeToNBT(new NBTTagCompound());
				nbttagcompound.removeTag("Dimension");
				newEntity.readFromNBT(nbttagcompound);
				newEntity.timeUntilPortal = ridingEntity.timeUntilPortal;
				ObfuscationReflectionHelper.setPrivateValue(Entity.class, newEntity,
						ObfuscationReflectionHelper.getPrivateValue(Entity.class, ridingEntity, "field_181016_an"), "field_181016_an");
				ObfuscationReflectionHelper.setPrivateValue(Entity.class, newEntity,
						ObfuscationReflectionHelper.getPrivateValue(Entity.class, ridingEntity, "field_181017_ao"), "field_181017_ao");
				ObfuscationReflectionHelper.setPrivateValue(Entity.class, newEntity,
						ObfuscationReflectionHelper.getPrivateValue(Entity.class, ridingEntity, "field_181018_ap"), "field_181018_ap");
				targetServer.spawnEntity(newEntity);
			}

			playerMP.startRiding(newEntity, true);
		}
	}

	public static void runQueuedTeleport() {
		if(queuedTeleport == null) return;

		queuedTeleport.runTeleport();

		queuedTeleport = null;
	}

	private static DebugTeleporter queuedTeleport;

	public static void teleport(EntityPlayer player, int dim, double x, double y, double z, boolean grounded) {
		if (player.dimension == dim) return; // ignore if we're teleporting to the same place

		MinecraftServer mServer = FMLCommonHandler.instance().getMinecraftServerInstance();
		Side sidex = FMLCommonHandler.instance().getEffectiveSide();
		if (sidex == Side.SERVER) {
			if (player instanceof EntityPlayerMP) {
				EntityPlayerMP playerMP = (EntityPlayerMP) player;
				WorldServer sourceServer = playerMP.getServerWorld();
				WorldServer targetServer = mServer.getWorld(dim);

				queuedTeleport = new DebugTeleporter(sourceServer, targetServer, playerMP, x, y, z, grounded);
			}
		}
	}

}
