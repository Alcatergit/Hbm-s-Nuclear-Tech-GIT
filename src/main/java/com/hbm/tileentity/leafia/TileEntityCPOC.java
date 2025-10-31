package com.hbm.tileentity.leafia;

import com.hbm.inventory.control_panel.ControlEventSystem;
import com.hbm.inventory.control_panel.DataValue;
import com.hbm.inventory.control_panel.IControllable;
import com.hbm.tileentity.TileEntityMachineBase;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraftforge.fml.common.Optional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public abstract class TileEntityCPOC extends TileEntityMachineBase implements IControllable, SimpleComponent {
    public TileEntityCPOC(int scount) {
        super(scount);
    }
    public TileEntityCPOC(int scount, int slotlimit) {
        super(scount, slotlimit);
    }


    // Yeah fuck this thing.
    @Override
    public void validate() {
        super.validate();
        ControlEventSystem.get(world).addControllable(this);
    }
    @Override
    public void invalidate() {
        super.invalidate();
        ControlEventSystem.get(world).removeControllable(this);
    }
    @Override
    public String getComponentName() {
        String[] spl = this.getName().split("\\.");
        return spl[spl.length-1];
    }
    @Override
    public Map<String, DataValue> getQueryData() {
        Map<String, DataValue> data = new HashMap<>();

        return data;
    }
    abstract void __listBroadcastable(List<String> broadcastable);
    @Override
    public List<String> getInEvents() {
        List<String> events = new ArrayList<>();
        __listBroadcastable(events);
        return events;
    }
}
