package com.example.example_mod.api.damagesystem;

import net.minecraft.item.ItemStack;

import java.util.function.IntSupplier;

public abstract class AbstractPartHealer {
	public final IntSupplier maxHeal;
	public final ItemStack stack;
	public final IntSupplier ticksPerHeal;

	public AbstractPartHealer(IntSupplier maxHeal, ItemStack stack, IntSupplier ticksPerHeal) {
		this.maxHeal = maxHeal;
		this.stack = stack;
		this.ticksPerHeal = ticksPerHeal;
	}

	/**
	 * Called when the part is loaded with saved data.
	 *
	 * @return this
	 */
	public abstract AbstractPartHealer loadNBT(int ticksPassed, int heals);

	/**
	 * Returns true if the healer is finished healing the body part.
	 * The healer will be removed from the part at the next tick
	 *
	 * @return True if the healer is finished, otherwise false
	 */
	public abstract boolean hasFinished();

	/**
	 * Updates the healer.
	 * Should not be called by other mods!
	 */
	public abstract boolean tick();

	/**
	 * Gets the time that passed since the
	 */
	public abstract int getTicksPassed();

	/**
	 * Gets the heals that this healer did
	 */
	public abstract int getHealsDone();
}
