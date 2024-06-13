package com.mrbysco.doaflip.platform;

import com.mrbysco.doaflip.IPersistentData;
import com.mrbysco.doaflip.platform.services.IPlatformHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class FabricPlatformHelper implements IPlatformHelper {
	@Override
	public CompoundTag getPersistentData(LivingEntity livingEntity) {
		return ((IPersistentData) livingEntity).getPersistentData();
	}
}
