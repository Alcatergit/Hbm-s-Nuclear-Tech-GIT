package com.hbm.inventory.fluid.trait;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.util.I18nUtil;
import com.mojang.realmsclient.gui.ChatFormatting;

import java.io.IOException;
import java.util.List;

public class FT_PWRModerator extends FluidTrait {

	private double multiplier;
	
	public FT_PWRModerator(double mulitplier) {
		this.multiplier = mulitplier;
	}
	
	public double getMultiplier() {
		return multiplier;
	}
	
	@Override
	public void addInfo(List<String> info) {
		info.add(ChatFormatting.BLUE + "[" + I18nUtil.resolveKey("trait.pwrflux") + "]");
	}

	@Override
	public void addInfoHidden(List<String> info) {
		int mult = (int) (multiplier * 100 - 100);
		info.add(ChatFormatting.BLUE + I18nUtil.resolveKey("trait.pwrflux.desc") + " " + (mult >= 0 ? "+" : "") + mult + "%");
	}

	@Override
	public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("multiplier").value(multiplier);
	}
	
	@Override
	public void deserializeJSON(JsonObject obj) {
		this.multiplier = obj.get("multiplier").getAsDouble();
	}
}
