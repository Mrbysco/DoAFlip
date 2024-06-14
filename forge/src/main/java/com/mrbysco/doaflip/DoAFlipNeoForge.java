package com.mrbysco.doaflip;

import com.mrbysco.doaflip.config.NeoForgeFlipConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class DoAFlipNeoForge {

	public DoAFlipNeoForge(IEventBus eventBus) {
		if (FMLEnvironment.dist.isClient()) {
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, NeoForgeFlipConfig.clientSpec);
			eventBus.register(NeoForgeFlipConfig.class);
		}
	}
}