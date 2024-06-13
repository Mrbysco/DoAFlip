package com.mrbysco.doaflip.client;

import net.minecraft.world.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class ConfigCache {
	public static float flipChance = 0.2F;
	public static boolean invertMobs = true;
	public static final List<EntityType<?>> mobs = new ArrayList<>();

	public static void setFlipChance(float value) {
		flipChance = value;
	}

	public static void setInvertMobs(boolean value) {
		invertMobs = value;
	}

	public static void generateEntityList(List<? extends String> configValues) {
		mobs.clear();

		for (String configValue : configValues) {
			EntityType.byString(configValue).ifPresent(mobs::add);
		}
	}
}
