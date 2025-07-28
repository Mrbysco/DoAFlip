package com.mrbysco.doaflip.platform;

import com.mrbysco.doaflip.DoAFlipFabric;
import com.mrbysco.doaflip.IPersistentData;
import com.mrbysco.doaflip.config.FabricFlipConfig;
import com.mrbysco.doaflip.platform.services.IPlatformHelper;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public class FabricPlatformHelper implements IPlatformHelper {
	@Override
	public CompoundTag getPersistentData(LivingEntity livingEntity) {
		return ((IPersistentData) livingEntity).doaflip$getPersistentData();
	}

	@Override
	public int getMinimumFallDistance() {
		if (DoAFlipFabric.config == null)
			DoAFlipFabric.config = AutoConfig.getConfigHolder(FabricFlipConfig.class).getConfig();
		return DoAFlipFabric.config.client.minimumFallDistance;
	}
}
