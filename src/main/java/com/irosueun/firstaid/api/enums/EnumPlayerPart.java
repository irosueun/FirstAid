package com.irosueun.firstaid.api.enums;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EquipmentSlot;

import javax.annotation.Nonnull;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public enum EnumPlayerPart {
	HEAD(EquipmentSlot.HEAD), LEFT_ARM(EquipmentSlot.CHEST), LEFT_LEG(EquipmentSlot.LEGS), LEFT_FOOT(EquipmentSlot.FEET),
	BODY(EquipmentSlot.CHEST), RIGHT_ARM(EquipmentSlot.CHEST), RIGHT_LEG(EquipmentSlot.LEGS), RIGHT_FOOT(EquipmentSlot.FEET);

	public static final EnumPlayerPart[] VALUES = values();

	static {
		for (EnumPlayerPart value : VALUES) {
			List<EnumPlayerPart> neighbours = value.getNeighbours();
			if (neighbours.contains(value))
				throw new RuntimeException(value + " contains itself as a neighbour!");
			if (neighbours.isEmpty())
				throw new RuntimeException(value + " does not have any neighbours!");
			if (EnumSet.copyOf(neighbours).size() != neighbours.size())
				throw new RuntimeException(value + " neighbours contain the same part multiple times!");

			// Check that the parts can be reached by calling neighbours recursively
			Set<EnumPlayerPart> hopefullyAllParts = EnumSet.copyOf(neighbours);
			int oldSize = -1;
			while (oldSize != hopefullyAllParts.size()) {
				oldSize = hopefullyAllParts.size();
				Set<EnumPlayerPart> neighboursOfNeighbours = EnumSet.noneOf(EnumPlayerPart.class);
				for (EnumPlayerPart part : hopefullyAllParts) {
					neighboursOfNeighbours.addAll(part.getNeighbours());
				}
				hopefullyAllParts.addAll(neighboursOfNeighbours);
			}
			if (hopefullyAllParts.size() != VALUES.length) {
				throw new RuntimeException(value + " could not read all player parts " + Arrays.toString(hopefullyAllParts.toArray(new EnumPlayerPart[0])));
			}
		}
	}

	private ImmutableList<EnumPlayerPart> neighbours;
	public final EquipmentSlot slot;

	EnumPlayerPart(EquipmentSlot slot) {
		this.slot = slot;
	}

	public ImmutableList<EnumPlayerPart> getNeighbours() {
		if (neighbours == null) { // Need to do lazy init to avoid crashes when initializing class
			synchronized (this) {
				if (neighbours == null) {
					ImmutableList.Builder<EnumPlayerPart> builder = ImmutableList.builder();
					builder.addAll(getNeighboursDown());
					builder.addAll(getNeighboursUp());
					builder.addAll(getNeighboursLeft());
					builder.addAll(getNeighboursRight());
					neighbours = builder.build();
				}
			}
		}
		return neighbours;
	}

	@Nonnull
	private List<EnumPlayerPart> getNeighboursUp() {
		switch (this) {
			case BODY:
				return singletonList(HEAD);
			case LEFT_LEG:
			case RIGHT_LEG:
				return singletonList(BODY);
			case LEFT_FOOT:
				return singletonList(LEFT_LEG);
			case RIGHT_FOOT:
				return singletonList(RIGHT_LEG);
			default:
				return emptyList();
		}
	}

	@Nonnull
	private List<EnumPlayerPart> getNeighboursDown() {
		switch (this) {
			case HEAD:
				return singletonList(BODY);
			case BODY:
				return Arrays.asList(LEFT_LEG, RIGHT_LEG);
			case LEFT_LEG:
				return singletonList(LEFT_FOOT);
			case RIGHT_LEG:
				return singletonList(RIGHT_FOOT);
			default:
				return emptyList();
		}
	}

	@Nonnull
	private List<EnumPlayerPart> getNeighboursLeft() {
		switch (this) {
			case RIGHT_ARM:
				return singletonList(BODY);
			case RIGHT_LEG:
				return singletonList(LEFT_LEG);
			case RIGHT_FOOT:
				return singletonList(LEFT_FOOT);
			case BODY:
				return singletonList(LEFT_ARM);
			default:
				return emptyList();
		}
	}

	@Nonnull
	private List<EnumPlayerPart> getNeighboursRight() {
		switch (this) {
			case LEFT_ARM:
				return singletonList(BODY);
			case LEFT_LEG:
				return singletonList(RIGHT_LEG);
			case LEFT_FOOT:
				return singletonList(RIGHT_FOOT);
			case BODY:
				return singletonList(RIGHT_ARM);
			default:
				return emptyList();
		}
	}
}
