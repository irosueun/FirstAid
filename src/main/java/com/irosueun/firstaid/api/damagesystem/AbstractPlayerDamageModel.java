package com.irosueun.firstaid.api.damagesystem;

import com.irosueun.firstaid.api.enums.EnumPlayerPart;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class AbstractPlayerDamageModel implements Iterable<AbstractDamageablePart> {
	public final AbstractDamageablePart HEAD;
	public final AbstractDamageablePart LEFT_ARM;
	public final AbstractDamageablePart LEFT_LEG;
	public final AbstractDamageablePart LEFT_FOOT;
	public final AbstractDamageablePart BODY;
	public final AbstractDamageablePart RIGHT_ARM;
	public final AbstractDamageablePart RIGHT_LEG;
	public final AbstractDamageablePart RIGHT_FOOT;
	public boolean hasTutorial;

	public AbstractPlayerDamageModel(AbstractDamageablePart head, AbstractDamageablePart leftArm, AbstractDamageablePart leftLeg, AbstractDamageablePart leftFoot, AbstractDamageablePart body, AbstractDamageablePart rightArm, AbstractDamageablePart rightLeg, AbstractDamageablePart rightFoot) {
		this.HEAD = head;
		this.LEFT_ARM = leftArm;
		this.LEFT_LEG = leftLeg;
		this.LEFT_FOOT = leftFoot;
		this.BODY = body;
		this.RIGHT_ARM = rightArm;
		this.RIGHT_LEG = rightLeg;
		this.RIGHT_FOOT = rightFoot;
	}

	public AbstractDamageablePart getFromEnum(EnumPlayerPart part) {
		switch (part) {
			case HEAD:
				return HEAD;
			case LEFT_ARM:
				return LEFT_ARM;
			case LEFT_LEG:
				return LEFT_LEG;
			case BODY:
				return BODY;
			case RIGHT_ARM:
				return RIGHT_ARM;
			case RIGHT_LEG:
				return RIGHT_LEG;
			case LEFT_FOOT:
				return LEFT_FOOT;
			case RIGHT_FOOT:
				return RIGHT_FOOT;
			default:
				throw new RuntimeException("Unknown enum " + part);
		}
	}

	/**
	 * Updates the part.
	 * Should not be called by other mods!
	 */
	public abstract void tick(World world, PlayerEntity player);

	/**
	 * @deprecated Migrated to a potion effect, pass it in to directly apply
	 */
	@Deprecated
	public abstract void applyMorphine();

	public abstract void applyMorphine(PlayerEntity player);

	/**
	 * @deprecated Migrated to a potion effect
	 */
	@Deprecated
	public abstract int getMorphineTicks();

	/**
	 * Checks if the player is dead.
	 * This does not mean that the player cannot be revived.
	 *
	 * @param player The player to check. If null, it will not be checked if the player can be revived (Using PlayerRevival)
	 * @return true if dead, false otherwise
	 */
	public abstract boolean isDead(@Nullable PlayerEntity player);

	public abstract Float getAbsorption();

	public abstract void setAbsorption(float absorption);

	public abstract int getCurrentMaxHealth();

	@Environment(EnvType.CLIENT)
	public abstract int getMaxRenderSize();

	public abstract void sleepHeal(PlayerEntity player);

	public abstract void revivePlayer(PlayerEntity player);

	public abstract void runScaleLogic(PlayerEntity player);

	public abstract void scheduleResync();

	public abstract boolean hasNoCritical();
}
