package com.mrbysco.doaflip;

import com.mrbysco.doaflip.config.NeoForgeFlipConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class DoAFlipNeoForge {

	public DoAFlipNeoForge(IEventBus eventBus, ModContainer container) {
		container.registerConfig(ModConfig.Type.CLIENT, NeoForgeFlipConfig.clientSpec);
		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		eventBus.register(NeoForgeFlipConfig.class);
	}
}