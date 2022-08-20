package com.irosueun.firstaid.api.damagesystem;

import com.irosueun.firstaid.api.enums.EnumPlayerPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;


//Will implement serialization later LOL
public abstract class AbstractDamageablePart {
	public final int initialMaxHealth;
	public final boolean canCauseDeath;
	public final EnumPlayerPart part;
	public AbstractPartHealer activeHealer;
	public float currentHealth;

	public AbstractDamageablePart(int maxHealth, boolean canCauseDeath, EnumPlayerPart playerPart) {
		this.initialMaxHealth = maxHealth;
		this.canCauseDeath = canCauseDeath;
		this.part = playerPart;
	}

	/**
	 * Heals the part for the specified amount.
	 *
	 * @param amount      The amount the part should be healed, clamped to max health
	 * @param player      The entity that this part belongs to. May be null if applyDebuff is false, otherwise this is required nonnull
	 * @param applyDebuff If all registered debuffs should be notified of the healing done
	 * @return The amount of health that could not be added
	 */
	public abstract float heal(float amount, @Nullable PlayerEntity player, boolean applyDebuff);

	/**
	 * Damages the part for the specified amount.
	 *
	 * @param amount      The amount the part should be damaged, clamped to 0
	 * @param player      The entity that this part belongs to. May be null if applyDebuff is false, otherwise this is required nonnull
	 * @param applyDebuff If all registered debuffs should be notified of the damage taken
	 * @return The amount of damage that could not be done
	 */
	public abstract float damage(float amount, @Nullable PlayerEntity player, boolean applyDebuff);

	/**
	 * Damages the part for the specified amount.
	 *
	 * @param amount      The amount the part should be damaged, clamped to minHealth
	 * @param player      The entity that this part belongs to. May be null if applyDebuff is false, otherwise this is required nonnull
	 * @param applyDebuff If all registered debuffs should be notified of the damage taken
	 * @param minHealth   The minimum health the part should drop to
	 * @return The amount of damage that could not be done
	 */
	public abstract float damage(float amount, @Nullable PlayerEntity player, boolean applyDebuff, float minHealth);

	/**
	 * Updates the part.
	 * Should not be called by other mods!
	 */
	public abstract void tick(World world, PlayerEntity player, boolean tickDebuffs);

	public abstract void setAbsorption(float absorption);

	public abstract float getAbsorption();

	public abstract void setMaxHealth(int maxHealth);

	public abstract int getMaxHealth();
}
