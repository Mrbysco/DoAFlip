package com.mrbysco.doaflip.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrbysco.doaflip.DoAFlip;
import com.mrbysco.doaflip.client.config.ConfigCache;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FlipHandler {
	private static final String PREVENT_FLIP = "prevent_flip";
	private static final String FLIP_START_TICK_KEY = "flip_start_tick";
	private static final String FLIP_PROGRESS_KEY = "flip_progress";
	private static final String FLIP_DURATION = "flip_duration";
	private static final String FRONT_FLIP = "front_flip";

	/**
	 * Handles the flip animation for a living entity
	 *
	 * @param livingEntity The entity to potentially flip
	 * @param poseStack    The PoseStack to apply the flip rotation to
	 * @param partialTicks The partial ticks for interpolation
	 */
	public static void doFlipping(LivingEntity livingEntity, PoseStack poseStack, float partialTicks) {
		if (!canFlip(livingEntity)) return;
		if (livingEntity instanceof Player player && player.getAbilities().flying) return;

		CompoundTag persistentData = livingEntity.getPersistentData();
		if (livingEntity.onGround() || livingEntity.isInWater() || livingEntity.isVehicle()) {
			//Remove flip data if it exists
			persistentData.remove(PREVENT_FLIP);
			persistentData.remove(FLIP_START_TICK_KEY);
			persistentData.remove(FLIP_PROGRESS_KEY);
			persistentData.remove(FLIP_DURATION);
			persistentData.remove(FRONT_FLIP);
			return;
		}
		if (persistentData.contains(PREVENT_FLIP)) return;

		// Check how many blocks from the ground the entity is
		double distanceFromGround = getDistanceFromGround(livingEntity);
		if (distanceFromGround <= 3) return;

		// Check if the entity is falling and hasn't started flipping yet
		if ((livingEntity.getDeltaMovement().y < 0 || (livingEntity instanceof Player && livingEntity.getDeltaMovement().y == 0.419875D)) && !persistentData.contains(FLIP_START_TICK_KEY)) {
			RandomSource random = livingEntity.getRandom();
			float flipProbability = ConfigCache.flipChance;
			float randomFloat = random.nextFloat();
			System.out.println(randomFloat);

			if (randomFloat < flipProbability) {
				DoAFlip.LOGGER.info("flipping because randomFloat: " + randomFloat + " is lower than flipProbability: " + flipProbability);
				//Calculate total duration of the flip animation based on the distance from the ground
				//The higher the distance, the longer the flip (capped at 1.5 seconds)
				float totalDuration = (float) Mth.clamp(distanceFromGround * 1F, 5F, 30.0F);

				persistentData.putLong(FLIP_START_TICK_KEY, livingEntity.level().getGameTime());
				persistentData.putFloat(FLIP_PROGRESS_KEY, 0.0f);
				persistentData.putFloat(FLIP_DURATION, totalDuration);
				persistentData.putBoolean(FRONT_FLIP, random.nextBoolean());
			} else {
				DoAFlip.LOGGER.info("Not flipping because randomFloat: " + randomFloat + " is higher than flipProbability: " + flipProbability);
				// Mark as flipped to prevent multiple flips
				persistentData.putBoolean(PREVENT_FLIP, true);
			}
		}

		// Handle the flip animation
		if (persistentData.contains(FLIP_START_TICK_KEY)) {
			long startTick = persistentData.getLong(FLIP_START_TICK_KEY);
			float progress = persistentData.getFloat(FLIP_PROGRESS_KEY);
			// Duration of the flip animation in ticks
			float totalDuration = persistentData.contains(FLIP_DURATION) ? persistentData.getFloat(FLIP_DURATION) : 10.0F;

			if (progress < 1.0f) {
				boolean doesFrontFlip = persistentData.getBoolean(FRONT_FLIP);
				// Calculate the rotation angle based on the progress and the partial ticks
				float rotationAngle = 360.0F * progress + 180.0f * partialTicks / totalDuration;

				//Rotate the entity around the X axis
				poseStack.mulPose(Axis.XP.rotationDegrees(doesFrontFlip ? -rotationAngle : rotationAngle));

				//Offset the entity to make it look like it's flipping around its center
				poseStack.translate(0.0, -livingEntity.getBbHeight() / 2F, 0.0);

				// Update progress
				long currentTick = livingEntity.level().getGameTime();
				progress = (currentTick - startTick) / totalDuration;
				persistentData.putFloat(FLIP_PROGRESS_KEY, progress);
			} else {
				// Flip animation is complete, remove the flip data
				persistentData.remove(FLIP_START_TICK_KEY);
				persistentData.remove(FLIP_PROGRESS_KEY);
				persistentData.remove(FLIP_DURATION);
				persistentData.remove(FRONT_FLIP);

				//Mark flipped
				persistentData.putBoolean(PREVENT_FLIP, true);
			}
		}
	}

	private static double getDistanceFromGround(LivingEntity livingEntity) {
		BlockPos pos = livingEntity.blockPosition();
		Level level = livingEntity.level();
		int distance;
		for (distance = 1; distance < 32; ++distance) {
			BlockPos checkPos = pos.below(distance);
			BlockState belowState = level.getBlockState(checkPos);
			if (belowState.entityCanStandOn(level, checkPos, livingEntity)) {
				break;
			}
		}
		return distance;
	}

	private static boolean canFlip(LivingEntity livingEntity) {
		return ConfigCache.invertMobs != ConfigCache.mobs.contains(livingEntity.getType());
	}
}
