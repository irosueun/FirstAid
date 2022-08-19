package com.example.example_mod.utils;

import com.example.example_mod.AlignedBoundingBox;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerSizeHelper {
	private static final Map<EquipmentSlot, AlignedBoundingBox> NORMAL_AlignedBoundingBoxES;
	private static final Map<EquipmentSlot, AlignedBoundingBox> SNEAKING_AlignedBoundingBoxES;

	static {
		Map<EquipmentSlot, AlignedBoundingBox> builder = new LinkedHashMap<>();
		builder.put(EquipmentSlot.FEET, new AlignedBoundingBox(0D, 0D, 0D, 1D, 0.15D, 1D));
		builder.put(EquipmentSlot.LEGS, new AlignedBoundingBox(0D, 0.15D, 0D, 1D, 0.45D, 1D));
		builder.put(EquipmentSlot.CHEST, new AlignedBoundingBox(0D, 0.45D, 0D, 1D, 0.8D, 1D));
		builder.put(EquipmentSlot.HEAD, new AlignedBoundingBox(0D, 0.8D, 0D, 1D, 1D, 1D));
		NORMAL_AlignedBoundingBoxES = Collections.unmodifiableMap(builder);

		builder = new LinkedHashMap<>();
		builder.put(EquipmentSlot.FEET, new AlignedBoundingBox(0D, 0D, 0D, 1D, 0.15D, 1D));
		builder.put(EquipmentSlot.LEGS, new AlignedBoundingBox(0D, 0.15D, 0D, 1D, 0.45D, 1D));
		builder.put(EquipmentSlot.CHEST, new AlignedBoundingBox(0D, 0.45D, 0D, 1D, 0.8D, 1D));
		builder.put(EquipmentSlot.HEAD, new AlignedBoundingBox(0D, 0.8D, 0D, 1D, 1D, 1D));
		SNEAKING_AlignedBoundingBoxES = Collections.unmodifiableMap(builder);
	}

	public static Map<EquipmentSlot, AlignedBoundingBox> getAlignedBoundingBoxes(Entity entity) {
		switch (entity.getPose()) {
			case STANDING:
				return NORMAL_AlignedBoundingBoxES;
			case CROUCHING:
				return SNEAKING_AlignedBoundingBoxES;
			case SPIN_ATTACK: //trident
			case FALL_FLYING: //elytra
				return Collections.emptyMap(); // To be evaluated
			case DYING:
			case SLEEPING:
			case SWIMMING:
			default:
				return Collections.emptyMap();
		}
	}

	public static EquipmentSlot getSlotTypeForProjectileHit(Entity hittingObject, PlayerEntity toTest) {
		Map<EquipmentSlot, AlignedBoundingBox> toUse = getAlignedBoundingBoxes(toTest);
		Vec3d oldPosition = hittingObject.getPos();
		Vec3d newPosition = oldPosition.add(hittingObject.getVelocity());

		// See ProjectileHelper.getEntityHitResult
		float[] inflationSteps = new float[]{0.01F, 0.1F, 0.2F, 0.3F};
		for (float inflation : inflationSteps) {
			EquipmentSlot bestSlot = null;
			double bestValue = Double.MAX_VALUE;
			for (Map.Entry<EquipmentSlot, AlignedBoundingBox> entry : toUse.entrySet()) {
				Box box = entry.getValue().createBox(toTest.getBoundingBox()).expand(inflation);
				Optional<Vec3d> optional = box.raycast(oldPosition, newPosition);
				if (optional.isPresent()) {
					double d1 = oldPosition.squaredDistanceTo(optional.get());
					double d2 = 0D;//newPosition.distanceToSqr(optional.get());
					if ((d1 + d2) < bestValue) {
						bestSlot = entry.getKey();
						bestValue = d1 + d2;
					}
				}
			}
		}
		return null;
	}
}
