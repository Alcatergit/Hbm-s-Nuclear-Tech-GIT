package com.hbm.inventory.control_panel.nodes.leafia;

import com.hbm.inventory.control_panel.*;
import com.hbm.inventory.control_panel.DataValue.DataType;
import com.hbm.inventory.control_panel.nodes.NodeOutput;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.tileentity.machine.TileEntityControlPanel;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class NodeSounder extends NodeOutput {
	NodeSnd snd = NodeSnd.BUTTON_ACCEPT;
	TileEntityControlPanel panel;
	enum NodeSnd {
		BUTTON_ACCEPT(HBMSoundEvents.buttonYes),
		BUTTON_DENY(HBMSoundEvents.buttonNo),
		CLICK_ACCEPT(SoundEvents.UI_BUTTON_CLICK),
		CLICK_INVALID(HBMSoundEvents.UI_BUTTON_INVALID),
		ARMED(HBMSoundEvents.fstbmbStart),
		TIMER(HBMSoundEvents.fstbmbPing),
		BLEEP(HBMSoundEvents.techBleep),
		POP(HBMSoundEvents.techBoop),
		SHUTDOWN(HBMSoundEvents.shutdown),
		;
		final SoundEvent evt;
		NodeSnd(SoundEvent evt) {
			this.evt = evt;
		}
		static NodeSnd getByName(String s) {
			for (NodeSnd value : NodeSnd.values()) {
				if (value.name().equals(s)) return value;
			}
			return null;
		}
	}
	public NodeSounder(float x,float y) {
		super(x,y);
		NodeDropdown sounds = new NodeDropdown(this,otherElements.size(),s->{
			NodeSnd snd = NodeSnd.getByName(s);
			if (snd != null)
				this.snd = snd;
			return null;
		},()->snd.name());
		String[] list = new String[NodeSnd.values().length];
		for (int i = 0; i < list.length; i++)
			list[i] = NodeSnd.values()[i].name();
		Arrays.sort(list);
		sounds.list.addItems(list);
		otherElements.add(sounds);
		this.inputs.add(new NodeConnection("Volume",this,inputs.size(),true,DataType.NUMBER,new DataValueFloat(0.5f)));
		this.inputs.add(new NodeConnection("Pitch",this,inputs.size(),true,DataType.NUMBER,new DataValueFloat(1)));
		recalcSize();
	}

	@Override
	public void readFromNBT(NBTTagCompound tag,NodeSystem sys) {
		NodeSnd snd = NodeSnd.getByName(tag.getString("sound"));
		if (snd != null)
			this.snd = snd;
		super.readFromNBT(tag,sys);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag,NodeSystem sys) {
		tag.setString("nodeType","sounder");
		tag.setString("sound",snd.name());
		return super.writeToNBT(tag,sys);
	}

	@Override
	public DataValue evaluate(int idx) {
		return null;
	}

	@Override
	public boolean doOutput(IControllable from,Map<String,NodeSystem> sendNodeMap,List<BlockPos> positions) {
		World world = from.getControlWorld();
		ControlEventSystem.get(world).playSound(world,from.getControlPos(),snd.evt,inputs.get(0).evaluate().getNumber(),inputs.get(1).evaluate().getNumber());
		return false;
	}

	@Override
	public NodeType getType() {
		return NodeType.OUTPUT;
	}

	@Override
	public String getDisplayName() {
		return "Play Sound";
	}
}
