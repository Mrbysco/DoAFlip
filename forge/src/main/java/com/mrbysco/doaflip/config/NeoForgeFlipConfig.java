package com.mrbysco.doaflip.config;

import com.mrbysco.doaflip.Constants;
import com.mrbysco.doaflip.client.ConfigCache;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class NeoForgeFlipConfig {
	public static class Client {
		public final ModConfigSpec.DoubleValue flipChance;
		public final ModConfigSpec.BooleanValue invertMobs;
		public final ModConfigSpec.ConfigValue<List<? extends String>> flippingMobs;

		Client(ModConfigSpec.Builder builder) {
			builder.comment("Flip settings")
					.push("Flip");

			flipChance = builder
					.comment("The chance of a flip happening when a mob falls from a high enough distance [0.0 = 0%, 0.1 = 10%, 1.0 = 100%] (Default: 0.35)")
					.defineInRange("flipChance", 0.35D, 0.0D, 1.0D);

			invertMobs = builder
					.comment("When enabled turns the flippingMobs option into a blacklist [default: true]")
					.define("invertMobs", true);

			flippingMobs = builder
					.comment("Defines a list of mobs that can flip when falling from a high enough distance [Format: modid:entity]")
					.defineListAllowEmpty(List.of("flippingMobs"), () ->
									List.of("minecraft:bat", "minecraft:armor_stand", "minecraft:ender_dragon", "minecraft:wither"),
							o -> (o instanceof String string && ResourceLocation.isValidResourceLocation(string)));

			builder.pop();
		}
	}


	public static final ModConfigSpec clientSpec;
	public static final Client CLIENT;

	static {
		final Pair<Client, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		Constants.LOGGER.debug("Loaded Do A Flip's config file {}", configEvent.getConfig().getFileName());
		refreshCache();
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		Constants.LOGGER.warn("Do A Flip's config just got changed on the file system!");
		refreshCache();
	}

	private static void refreshCache() {
		ConfigCache.setFlipChance(CLIENT.flipChance.get().floatValue());
		ConfigCache.setInvertMobs(CLIENT.invertMobs.get());
		ConfigCache.generateEntityList(CLIENT.flippingMobs.get());
	}
}
