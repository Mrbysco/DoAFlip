package com.mrbysco.doaflip.platform.services;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public interface IPlatformHelper {
	/**
	 * Get the persistent data for a living entity.
	 *
	 * @return The persistent data for the living entity.
	 */
	CompoundTag getPersistentData(LivingEntity livingEntity);

	/**
	 * Gets the minimum fall distance required for a mob to flip.
	 * @return the configured minimum fall distance.
	 */
	int getMinimumFallDistance();
}
