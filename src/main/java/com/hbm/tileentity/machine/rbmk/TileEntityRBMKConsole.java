package com.hbm.tileentity.machine.rbmk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual.RBMKColor;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.Optional;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityRBMKConsole extends TileEntityMachineBase implements IControlReceiver, ITickable, SimpleComponent {
	
	private int targetX;
	private int targetY;
	private int targetZ;

	protected static int lookbackLength = 40;
	
	//made this one-dimensional because it's a lot easier to serialize
	public RBMKColumn[] columns = new RBMKColumn[15 * 15];

	public RBMKScreen[] screens = new RBMKScreen[6];

	public RBMKGraph graph;

	public TileEntityRBMKConsole() {
		super(0);
		graph = new RBMKGraph();
		for(int i = 0; i < screens.length; i++) {
			screens[i] = new RBMKScreen();
		}
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void update() {
		
		if(!world.isRemote) {
			
			if(this.world.getTotalWorldTime() % 10 == 0) {
				rescan();
				prepareScreenInfo();
				prepareGraphInfo();
			}
			prepareNetworkPack();
		}
	}
	
	private void rescan() {
		
		for(int i = -7; i <= 7; i++) {
			for(int j = -7; j <= 7; j++) {
				
				TileEntity te = world.getTileEntity(new BlockPos(targetX + i, targetY, targetZ + j));
				int index = (i + 7) + (j + 7) * 15;
				
				if(te instanceof TileEntityRBMKBase) {
					
					TileEntityRBMKBase rbmk = (TileEntityRBMKBase)te;
					
					columns[index] = new RBMKColumn(rbmk.getConsoleType(), rbmk.getNBTForConsole());
					columns[index].data.setDouble("heat", rbmk.heat);
					columns[index].data.setDouble("maxHeat", rbmk.maxHeat());
					if(rbmk.isModerated()) columns[index].data.setBoolean("moderated", true); //false is the default anyway and not setting it when we don't need to reduces cruft
					
				} else {
					columns[index] = null;
				}
			}
		}
		
		
	}

	private void prepareGraphInfo() {
		if(graph.type == ScreenType.NONE) {
			graph.dataBuffer = new int[lookbackLength];
			return;
		}
		
		double value = 0;
		int count = 0;
		
		for(Integer i : graph.columns) {
			
			RBMKColumn col = this.columns[i];
			
			if(col == null)
				continue;
			
			switch(graph.type) {
			case COL_TEMP:
				count++;
				value += col.data.getDouble("heat");
				break;
			case FUEL_DEPLETION:
				if(col.data.hasKey("enrichment")) {
					count++;
					value += (100D - (col.data.getDouble("enrichment") * 100D));
				}
				break;
			case FUEL_POISON:
				if(col.data.hasKey("xenon")) {
					count++;
					value += col.data.getDouble("xenon");
				}
				break;
			case FUEL_TEMP:
				if(col.data.hasKey("c_heat")) {
					count++;
					value += col.data.getDouble("c_heat");
				}
				break;
			case FLUX:
				if(col.data.hasKey("flux")) {
					count++;
					value += col.data.getDouble("flux");
				}
				break;
			case ROD_EXTRACTION:
				if(col.data.hasKey("level")) {
					count++;
					value += col.data.getDouble("level") * 100;
				}
				break;
			}
		}
		
		double result = value / (double) count;
		for(int i = 0; i < graph.dataBuffer.length - 1; i++) {
			graph.dataBuffer[i] = graph.dataBuffer[i + 1];
		}
		
		graph.dataBuffer[graph.dataBuffer.length - 1] = (int) result;
	}

	private void prepareScreenInfo() {
		
		for(RBMKScreen screen : this.screens) {
			
			if(screen.type == ScreenType.NONE) {
				screen.display = null;
				continue;
			}
			
			double value = 0;
			int count = 0;
			
			for(Integer i : screen.columns) {
				
				RBMKColumn col = this.columns[i];
				
				if(col == null)
					continue;
				
				switch(screen.type) {
				case COL_TEMP:
					count++;
					value += col.data.getDouble("heat");
					break;
				case FUEL_DEPLETION:
					if(col.data.hasKey("enrichment")) {
						count++;
						value += (100D - (col.data.getDouble("enrichment") * 100D));
					}
					break;
				case FUEL_POISON:
					if(col.data.hasKey("xenon")) {
						count++;
						value += col.data.getDouble("xenon");
					}
					break;
				case FUEL_TEMP:
					if(col.data.hasKey("c_heat")) {
						count++;
						value += col.data.getDouble("c_heat");
					}
					break;
				case FLUX:
					if(col.data.hasKey("flux")) {
						count++;
						value += col.data.getDouble("flux");
					}
					break;
				case ROD_EXTRACTION:
					if(col.data.hasKey("level")) {
						count++;
						value += col.data.getDouble("level") * 100;
					}
					break;
				}
			}
			
			double result = value / (double) count;
			String text = ((int)(result * 10)) / 10D + "";
			
			switch(screen.type) {
			case COL_TEMP: text = "rbmk.screen.temp=" + text + "°C"; break;
			case FUEL_DEPLETION: text = "rbmk.screen.depletion=" + text + "%"; break;
			case FUEL_POISON: text = "rbmk.screen.xenon=" + text + "%"; break;
			case FUEL_TEMP: text = "rbmk.screen.core=" + text + "°C"; break;
			case FLUX: text = "rbmk.screen.flux=" + text ; break;
			case ROD_EXTRACTION: text = "rbmk.screen.rod=" + text + "%"; break;
			}
			
			screen.display = text;
		}
	}
	
	private void prepareNetworkPack() {
		
		NBTTagCompound data = new NBTTagCompound();

		
		if(this.world.getTotalWorldTime() % 10 == 0) {
			
			data.setBoolean("full", true);
			
			for(int i = 0; i < columns.length; i++) {
				
				if(this.columns[i] != null) {
					data.setTag("column_" + i, this.columns[i].data);
					data.setShort("type_" + i, (short)this.columns[i].type.ordinal());
				}
			}
			
			data.setIntArray("buffer", this.graph.dataBuffer);

			for(int i = 0; i < this.screens.length; i++) {
				RBMKScreen screen = screens[i];
				if(screen.display != null) {
					data.setString("t" + i, screen.display);
				}
			}
		}
		
		for(int i = 0; i < this.screens.length; i++) {
			RBMKScreen screen = screens[i];
			data.setByte("s" + i, (byte) screen.type.ordinal());
		}
		data.setByte("g", (byte) graph.type.ordinal());
		
		this.networkPack(data, 50);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		
		if(data.getBoolean("full")) {
			this.columns = new RBMKColumn[15 * 15];
			
			for(int i = 0; i < columns.length; i++) {
				
				if(data.hasKey("type_" + i)) {
					this.columns[i] = new RBMKColumn(ColumnType.values()[data.getShort("type_" + i)], (NBTTagCompound)data.getTag("column_" + i));
				}
			}
			
			this.graph.dataBuffer = data.getIntArray("buffer");
			
			for(int i = 0; i < this.screens.length; i++) {
				RBMKScreen screen = screens[i];
				screen.display = data.getString("t" + i);
			}
		}
		
		for(int i = 0; i < this.screens.length; i++) {
			RBMKScreen screen = screens[i];
			screen.type = ScreenType.values()[data.getByte("s" + i)];
		}
		graph.type = ScreenType.values()[data.getByte("g")];
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(pos.getX() - player.posX, pos.getY() - player.posY, pos.getZ() - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("level")) {
			
			Set<String> keys = data.getKeySet();
			
			for(String key : keys) {
				
				if(key.startsWith("sel_")) {

					int x = data.getInteger(key) % 15 - 7;
					int z = data.getInteger(key) / 15 - 7;
					
					TileEntity te = world.getTileEntity(new BlockPos(targetX + x, targetY, targetZ + z));
					
					if(te instanceof TileEntityRBMKControlManual) {
						TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
						rod.startingLevel = rod.level;
						rod.setTarget(MathHelper.clamp(data.getDouble("level"), 0, 1));
						te.markDirty();
					}
				}
			}
		}

		if(data.hasKey("toggle")) {
			int slot = data.getByte("toggle");
			if(slot == 99){
				int next = this.graph.type.ordinal() + 1;
				ScreenType type = ScreenType.values()[next % ScreenType.values().length];
				this.graph.type = type;
				this.graph.dataBuffer = new int[lookbackLength];
				Arrays.fill(this.graph.dataBuffer, 0);
			} else {
				int next = this.screens[slot].type.ordinal() + 1;
				ScreenType type = ScreenType.values()[next % ScreenType.values().length];
				this.screens[slot].type = type;
			}
		}
		
		if(data.hasKey("id")) {
			int slot = data.getByte("id");
			List<Integer> list = new ArrayList();
			
			for(int i = 0; i < 15 * 15; i++) {
				if(data.getBoolean("s" + i)) {
					list.add(i);
				}
			}

			Integer[] cols = list.toArray(new Integer[0]);
			if(slot == 99){
				this.graph.columns = cols;
			} else {
				this.screens[slot].columns = cols;
			}
		}
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(pos.getX() - 2, pos.getY(), pos.getZ() - 2, pos.getX() + 3, pos.getY() + 4, pos.getZ() + 3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public void setTarget(int x, int y, int z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.targetX = nbt.getInteger("tX");
		this.targetY = nbt.getInteger("tY");
		this.targetZ = nbt.getInteger("tZ");

		for(int i = 0; i < this.screens.length; i++) {
			this.screens[i].type = ScreenType.values()[nbt.getByte("t" + i)];
			this.screens[i].columns = Arrays.stream(nbt.getIntArray("s" + i)).boxed().toArray(Integer[]::new);
		}
		this.graph.type = ScreenType.values()[nbt.getByte("g")];
		this.graph.columns = Arrays.stream(nbt.getIntArray("gc")).boxed().toArray(Integer[]::new);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("tX", this.targetX);
		nbt.setInteger("tY", this.targetY);
		nbt.setInteger("tZ", this.targetZ);

		for(int i = 0; i < this.screens.length; i++) {
			nbt.setByte("t" + i, (byte) this.screens[i].type.ordinal());
			nbt.setIntArray("s" + i, Arrays.stream(this.screens[i].columns).mapToInt(Integer::intValue).toArray());
		}
		nbt.setByte("g", (byte) this.graph.type.ordinal());
		nbt.setIntArray("gc", Arrays.stream(this.graph.columns).mapToInt(Integer::intValue).toArray());
		
		return nbt;
	}
	
	public static class RBMKColumn {
		
		public ColumnType type;
		public NBTTagCompound data;
		
		public RBMKColumn(ColumnType type) {
			this.type = type;
		}
		
		public RBMKColumn(ColumnType type, NBTTagCompound data) {
			this.type = type;
			
			if(data != null) {
				this.data = data;
			} else {
				this.data = new NBTTagCompound();
			}
		}

		@SuppressWarnings("incomplete-switch")
		@SideOnly(Side.CLIENT)
		public List<String> getFancyStats() {
			
			if(this.data == null)
				return null;
			
			/*
			 * Making a big switch with the values converted based on type by hand might seem "UnPrOfEsSiOnAl" and a major pain in the ass
			 * but my only other solution that would not have me do things in multiple places where they shouldn't be involved passing
			 * classes in the enum and then calling a special method from that class and quite honestly it turned out to be such a crime
			 * against humanity that I threw the towel. It's not fancy, I get that, please fuck off.
			 */
			
			List<String> stats = new ArrayList<>();
			stats.add(TextFormatting.YELLOW + I18nUtil.resolveKey("rbmk.heat", ((int)((this.data.getDouble("heat") * 10D)) / 10D) + "°C"));
			switch(this.type) {

			case FUEL:
			case FUEL_SIM:
				if(this.data.hasKey("rod_name"))
					stats.add("§3" + I18n.format("rbmk.rod.name") + " " + I18n.format(this.data.getString("rod_name")+".name"));
				else
					stats.add("§3" + I18n.format("rbmk.rod.name"));
				stats.add(TextFormatting.GREEN + I18nUtil.resolveKey("rbmk.rod.flux", (int)this.data.getDouble("flux")));
				stats.add(TextFormatting.DARK_GREEN + I18nUtil.resolveKey("rbmk.rod.depletion", ((int)(((1D - this.data.getDouble("enrichment")) * 100000)) / 1000D) + "%"));
				stats.add(TextFormatting.DARK_PURPLE + I18nUtil.resolveKey("rbmk.rod.xenon", ((int)(((this.data.getDouble("xenon")) * 1000D)) / 1000D) + "%"));
				stats.add(TextFormatting.DARK_RED + I18nUtil.resolveKey("rbmk.rod.coreTemp", ((int)((this.data.getDouble("c_coreHeat") * 10D)) / 10D) + "°C"));
				stats.add(TextFormatting.RED + I18nUtil.resolveKey("rbmk.rod.skinTemp", ((int)((this.data.getDouble("c_heat") * 10D)) / 10D) + "°C", ((int)((this.data.getDouble("c_maxHeat") * 10D)) / 10D) + "°C"));
				break;
			case BOILER:
				stats.add(TextFormatting.BLUE + I18nUtil.resolveKey("rbmk.boiler.water", this.data.getInteger("water"), this.data.getInteger("maxWater")));
				stats.add(TextFormatting.WHITE + I18nUtil.resolveKey("rbmk.boiler.steam", this.data.getInteger("steam"), this.data.getInteger("maxSteam")));
				stats.add(TextFormatting.YELLOW + I18nUtil.resolveKey("rbmk.boiler.type", I18nUtil.resolveKey(FluidRegistry.getFluid(this.data.getString("type")).getUnlocalizedName())));
				break;
			case CONTROL:
				
				if(this.data.hasKey("color")) {
					short col = this.data.getShort("color");
					
					if(col >= 0 && col < RBMKColor.values().length)
						stats.add(TextFormatting.YELLOW + I18nUtil.resolveKey("rbmk.control." + RBMKColor.values()[col].name().toLowerCase()));
				}
				
			case CONTROL_AUTO:
				stats.add(TextFormatting.YELLOW + I18nUtil.resolveKey("rbmk.control.level", ((int)((this.data.getDouble("level") * 100D))) + "%"));
				break;
			}
			
			if(data.getBoolean("moderated"))
				stats.add(TextFormatting.YELLOW + I18nUtil.resolveKey("rbmk.moderated"));
			
			return stats;
		}
	}
	
	public static enum ColumnType {
		BLANK(0),
		FUEL(10),
		FUEL_SIM(90),
		CONTROL(20),
		CONTROL_AUTO(30),
		BOILER(40),
		MODERATOR(50),
		ABSORBER(60),
		REFLECTOR(70),
		OUTGASSER(80),
		BREEDER(100),
		STORAGE(110),
		COOLER(120),
		HEATEX(130);
		
		public int offset;
		
		private ColumnType(int offset) {
			this.offset = offset;
		}
	}

	public class RBMKScreen {
		public ScreenType type = ScreenType.NONE;
		public Integer[] columns = new Integer[0];
		public String display = null;
		
		public RBMKScreen() { }
		public RBMKScreen(ScreenType type, Integer[] columns, String display) {
			this.type = type;
			this.columns = columns;
			this.display = display;
		}
	}

	public class RBMKGraph {
		public ScreenType type = ScreenType.NONE;
		public Integer[] columns = new Integer[0];
		public int[] dataBuffer = new int[lookbackLength];
		
		public RBMKGraph() {
			Arrays.fill(this.dataBuffer, 0); 
		}
		public RBMKGraph(ScreenType type, Integer[] columns, int[] dataBuffer) {
			this.type = type;
			this.columns = columns;
			this.dataBuffer = dataBuffer;
		}
	}
	
	public static enum ScreenType {
		NONE(0 * 18),
		COL_TEMP(1 * 18),
		ROD_EXTRACTION(2 * 18),
		FUEL_DEPLETION(3 * 18),
		FUEL_POISON(4 * 18),
		FUEL_TEMP(5 * 18),
		FLUX(6 * 18);
		
		public int offset;
		
		private ScreenType(int offset) {
			this.offset = offset;
		}
	}

	// opencomputers interface 

	@Override
	public String getComponentName() {
		return "rbmk_console";
	}

	@Callback(doc = "func(i:str): retrieves all column data for given index i")
	public Object[] getColumnData(Context context, Arguments args) {
		int i = Integer.parseInt(args.checkString(0));

		int x = i % 15 - 7;
		int z = i / 15 - 7;
		TileEntity te = world.getTileEntity(new BlockPos(targetX + x, targetY, targetZ + z));
		TileEntityRBMKBase column = (TileEntityRBMKBase) te;

		ArrayList<String> column_data = new ArrayList<String>();
		column_data.add(column.getConsoleType().name());
		column_data.add(Double.toString(columns[i].data.getDouble("heat")));
		column_data.add(Double.toString(columns[i].data.getDouble("water")));
		column_data.add(Double.toString(columns[i].data.getDouble("steam")));
		column_data.add(Boolean.toString(columns[i].data.getBoolean("moderated")));
		column_data.add(Double.toString(columns[i].data.getDouble("level")));
		column_data.add(Short.toString(columns[i].data.getShort("color")));
		column_data.add(Double.toString(columns[i].data.getDouble("enrichment")));
		column_data.add(Double.toString(columns[i].data.getDouble("xenon")));
		column_data.add(Double.toString(columns[i].data.getDouble("c_heat")));
		column_data.add(Double.toString(columns[i].data.getDouble("c_coreHeat")));
		column_data.add(Double.toString(columns[i].data.getDouble("c_maxHeat")));

		return new Object[] {column_data}; 
	}

	@Callback(doc = "func(x:str, i:str): sets column at index i to level x given 100>=x>=0")
	public Object[] setLevel(Context context, Arguments args) {
		double new_level = Double.parseDouble(args.checkString(0))/100.0;
		int x = Integer.parseInt(args.checkString(1)) % 15 - 7;
		int z = Integer.parseInt(args.checkString(1)) / 15 - 7;
		
		TileEntity te = world.getTileEntity(new BlockPos(targetX + x, targetY, targetZ + z));
		
		if (te instanceof TileEntityRBMKControlManual) {
			TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
			rod.startingLevel = rod.level;
			if (new_level > 1) { 
				new_level = 1;
			}
			rod.setTarget(new_level);
			te.markDirty();
		}	

		return new Object[] {};
	}

	@Callback(doc = "func(c:str, i:str): set color c of column at index i")
	public Object[] setColor(Context context, Arguments args) {
		int new_color = Integer.parseInt(args.checkString(0));

		int x = Integer.parseInt(args.checkString(1)) % 15 - 7;
		int z = Integer.parseInt(args.checkString(1)) / 15 - 7;
		
		TileEntity te = world.getTileEntity(new BlockPos(targetX + x, targetY, targetZ + z));

		if (te instanceof TileEntityRBMKControlManual) {
			TileEntityRBMKControlManual rod = (TileEntityRBMKControlManual) te;
			rod.setColor(new_color);
			te.markDirty();
		}	

		return new Object[] {};
	}
}