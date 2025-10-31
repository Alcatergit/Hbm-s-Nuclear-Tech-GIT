package com.leafia.contents.network.computers.cable;

import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.Optional;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class ComputerCableTE extends TileEntity implements SimpleComponent {
	@Override
	public String getComponentName() {
		return "dummy_enclosed_cable";
	}
}