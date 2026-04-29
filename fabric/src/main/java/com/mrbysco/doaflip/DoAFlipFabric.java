package com.mrbysco.doaflip;

import com.mrbysco.doaflip.config.FlipConfig;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.v5.ModConfigEvents;
import net.fabricmc.api.ClientModInitializer;
import net.neoforged.fml.config.ModConfig;

public class DoAFlipFabric implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ConfigRegistry.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.CLIENT, FlipConfig.clientSpec);
		ModConfigEvents.loading(Constants.MOD_ID).register((config) -> {
			FlipConfig.refreshCache();
		});
		ModConfigEvents.reloading(Constants.MOD_ID).register((config) -> {
			FlipConfig.refreshCache();
		});
	}
}
