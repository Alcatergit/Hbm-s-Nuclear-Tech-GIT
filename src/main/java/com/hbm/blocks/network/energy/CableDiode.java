package com.hbm.blocks.network.energy;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.main.MainRegistry;
import com.hbm.lib.Library;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.I18nUtil;

import api.hbm.block.IToolable;
import api.hbm.block.IToolable.ToolType;
import api.hbm.energy.IEnergyUser;
import api.hbm.energy.IEnergyConnectorBlock;
import api.hbm.energy.IEnergyConnector.ConnectionPriority;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class CableDiode extends BlockContainer implements IEnergyConnectorBlock, ILookOverlay, IToolable, ITooltipProvider {
	
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	
	public CableDiode(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);
        return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)));
	}

	@Override
	public boolean canConnect(IBlockAccess world, BlockPos pos, ForgeDirection dir) {
		return true;
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, EnumFacing side, float fX, float fY, float fZ, EnumHand hand, ToolType tool){
	
		TileEntityDiode te = (TileEntityDiode)world.getTileEntity(new BlockPos(x, y, z));
		
		if(world.isRemote)
			return true;
		
		if(tool == ToolType.SCREWDRIVER) {
			if(te.level < 17)
				te.level++;
			te.markDirty();
			INBTPacketReceiver.networkPack((TileEntity)te, te.packValues(), 20);
			return true;
		}
		
		if(tool == ToolType.HAND_DRILL) {
			if(te.level > 1)
				te.level--;
			te.markDirty();
			INBTPacketReceiver.networkPack((TileEntity)te, te.packValues(), 20);
			return true;
		}
		
		if(tool == ToolType.DEFUSER) {
			int p = te.priority.ordinal() + 1;
			if(p > 2) p = 0;
			te.priority = ConnectionPriority.values()[p];
			te.markDirty();
			INBTPacketReceiver.networkPack((TileEntity)te, te.packValues(), 20);
			return true;
		}
		
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
        this.addStandardInfo((List)list);
        super.addInformation(stack, worldIn, (List)list, flagIn);
    }

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		if(!(te instanceof TileEntityDiode))
			return;
		
		TileEntityDiode diode = (TileEntityDiode) te;
		
		List<String> text = new ArrayList();
		text.add("Max.: " + Library.getShortNumber(diode.getMaxPower()*20) + "HE/s");
		text.add("Priority: " + diode.priority.name());
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xffff00, 0x404000, text);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityDiode();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	public static class TileEntityDiode extends TileEntityLoadedBase implements ITickable, IEnergyUser, INBTPacketReceiver {

		@Override
		public void networkUnpack(NBTTagCompound nbt){
			level = nbt.getInteger("level");
			priority = ConnectionPriority.values()[nbt.getByte("p")];
		}

		public NBTTagCompound packValues(){
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("level", level);
			nbt.setByte("p", (byte) this.priority.ordinal());
			return nbt;
		}
		
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			level = nbt.getInteger("level");
			priority = ConnectionPriority.values()[nbt.getByte("p")];
		}
		
		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
			super.writeToNBT(nbt);
			nbt.setInteger("level", level);
			nbt.setByte("p", (byte) this.priority.ordinal());
			return nbt;
		}

		@Override
		public SPacketUpdateTileEntity getUpdatePacket(){
			return new SPacketUpdateTileEntity(this.getPos(), 0, this.writeToNBT(new NBTTagCompound()));
		}

		@Override
		public NBTTagCompound getUpdateTag() {
			return this.writeToNBT(new NBTTagCompound());
		}
		
		@Override
		public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
			this.readFromNBT(pkt.getNbtCompound());
		}
		
		int level = 1;
		
		private ForgeDirection getDir() {
			IBlockState state = world.getBlockState(pos);
			return ForgeDirection.getOrientation(state.getBlock().getMetaFromState(state)).getOpposite();
		}

		@Override
		public void update() {
			if(!world.isRemote) {
				this.updateConnectionsExcept(world, pos, getDir());
			}
		}

		@Override
		public boolean canConnect(ForgeDirection dir) {
			return dir != getDir();
		}
		
		private boolean recursionBrake = false;
		private long subBuffer;
		private long contingent = 0;
		private long lastTransfer = 0;
		private int pulses = 0;
		public ConnectionPriority priority = ConnectionPriority.NORMAL;

		@Override
		public long transferPower(long power) {

			if(recursionBrake)
				return power;
			
			pulses++;
			
			if(lastTransfer != world.getTotalWorldTime()) {
				lastTransfer = world.getTotalWorldTime();
				contingent = getMaxPower();
				pulses = 0;
			}
			
			if(contingent <= 0 || pulses > 10)
				return power;
			
			//this part turns "maxPower" from a glorified transfer weight into an actual transfer cap
			long overShoot = Math.max(0, power - contingent);
			power = Math.min(power, contingent);
			
			recursionBrake = true;
			this.subBuffer = power;
			
			ForgeDirection dir = getDir();
			this.sendPower(world, pos.add(dir.offsetX, dir.offsetY, dir.offsetZ), dir);
			long ret = this.subBuffer;
			
			long sent = power - ret;
			contingent -= sent;
			
			this.subBuffer = 0;
			recursionBrake = false;
			
			return ret + overShoot;
		}


		@Override
		public long getMaxPower() {
			return (long) Math.pow(10, level) >> 1;
		}

		@Override
		public long getPower() {
			return subBuffer;
		}
		
		@Override
		public void setPower(long power) {
			this.subBuffer = power;
		}

		@Override
		public ConnectionPriority getPriority() {
			return this.priority;
		}
	}
}
