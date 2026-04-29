package com.mrbysco.doaflip;

import com.mrbysco.doaflip.config.FlipConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class DoAFlipNeoForge {

	public DoAFlipNeoForge(IEventBus eventBus, ModContainer container) {
		container.registerConfig(ModConfig.Type.CLIENT, FlipConfig.clientSpec);
		container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
		eventBus.addListener(this::onLoad);
		eventBus.addListener(this::onFileChange);
	}

	private void onLoad(final ModConfigEvent.Loading configEvent) {
		if (configEvent.getConfig().getModId().equals(Constants.MOD_ID)) {
			Constants.LOGGER.debug("Loaded Do A Flip's config file {}", configEvent.getConfig().getFileName());
			FlipConfig.refreshCache();
		}
	}

	private void onFileChange(final ModConfigEvent.Reloading configEvent) {
		if (configEvent.getConfig().getModId().equals(Constants.MOD_ID)) {
			Constants.LOGGER.warn("Do A Flip's config just got changed on the file system!");
			FlipConfig.refreshCache();
		}
	}

}