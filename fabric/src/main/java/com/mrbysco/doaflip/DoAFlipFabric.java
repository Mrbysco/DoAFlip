package com.mrbysco.doaflip;

import com.mrbysco.doaflip.client.ConfigCache;
import com.mrbysco.doaflip.config.FabricFlipConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.world.InteractionResult;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

public class DoAFlipFabric implements ClientModInitializer {
	public static FabricFlipConfig config;

	@Override
	public void onInitializeClient() {
		ConfigHolder<FabricFlipConfig> configHolder = AutoConfig.register(FabricFlipConfig.class, Toml4jConfigSerializer::new);
		configHolder.registerLoadListener((holder, config) -> {
			ConfigCache.setFlipChance((float) config.client.flipChance);
			ConfigCache.setInvertMobs(config.client.invertMobs);
			ConfigCache.generateEntityList(config.client.flippingMobs);
			return InteractionResult.PASS;
		});
		configHolder.registerSaveListener((holder, config) -> {
			ConfigCache.setFlipChance((float) config.client.flipChance);
			ConfigCache.setInvertMobs(config.client.invertMobs);
			ConfigCache.generateEntityList(config.client.flippingMobs);
			return InteractionResult.PASS;
		});

		config = configHolder.getConfig();

		try {
			var watchService = FileSystems.getDefault().newWatchService();
			Paths.get("config").register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			Thread watchThread = new Thread(() -> {
				WatchKey key;
				try {
					while ((key = watchService.take()) != null) {
						if (Thread.currentThread().isInterrupted()) {
							watchService.close();
							break;
						}
						for (WatchEvent<?> event : key.pollEvents()) {
							if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
								continue;
							}
							if (((Path) event.context()).endsWith("doaflip.toml")) {
								Constants.LOGGER.info("Reloading DoAFlip's Client config");
								if (configHolder.load()) {
									config = configHolder.getConfig();
								}
							}
						}
						key.reset();
					}
				} catch (InterruptedException ignored) {
				} catch (IOException e) {
					Constants.LOGGER.error("Failed to close filesystem watcher", e);
				}
			}, "DoAFlip's Client Config Watcher");
			watchThread.start();
		} catch (IOException e) {
			Constants.LOGGER.error("Failed to create filesystem watcher for configs", e);
		}
	}
}
