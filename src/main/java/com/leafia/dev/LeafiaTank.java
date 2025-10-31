package com.leafia.dev;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

// wip unused shit (im too tired rn to code this shit)
public class LeafiaTank extends FluidTank {
	public Fluid type = null;
	protected double buffer = 0;
	public LeafiaTank(int capacity) {
		super(capacity);
	}
	public double fillDouble(double amount,boolean doFill) {
		int amt = (int)Math.floor(amount);
		double buf = buffer+(amount-amt);
		int add = (int)Math.floor(buf);

		int initial = this.getFluidAmount();
		double initialAmt = initial+buffer;
		int filled = this.fill(new FluidStack(type,amt+add),doFill);
		double finalAmt = Math.min(initial+filled+(buf-add),this.getCapacity());

		if (doFill)
			buffer = finalAmt-Math.floor(finalAmt);

		return finalAmt-initialAmt;
	}
	public double drainDouble(double amount,boolean doDrain) {
		int initial = this.getFluidAmount();
		double initialAmt = initial+buffer;

		double base = amount-buffer;
		int drain = (int)Math.ceil(base);
		double buf = drain-base;
		FluidStack stack = this.drain(drain,doDrain);
		if (stack == null)
			return 0;

		if (stack.amount < drain)
			buf = 0;
		if (doDrain)
			buffer = buf;

		double finalAmt = initial-stack.amount+buf;

		return initialAmt-finalAmt;
	}
}
