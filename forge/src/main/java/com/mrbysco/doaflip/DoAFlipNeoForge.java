package com.mrbysco.doaflip;

import com.mrbysco.doaflip.config.NeoForgeFlipConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(Constants.MOD_ID)
public class DoAFlipNeoForge {

	public DoAFlipNeoForge(IEventBus eventBus, ModContainer container, Dist dist) {
		if (dist.isClient()) {
			container.registerConfig(ModConfig.Type.CLIENT, NeoForgeFlipConfig.clientSpec);
			eventBus.register(NeoForgeFlipConfig.class);
		}
	}
}