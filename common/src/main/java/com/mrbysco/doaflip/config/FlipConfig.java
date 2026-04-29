package com.mrbysco.doaflip.config;

import com.mrbysco.doaflip.client.ConfigCache;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class FlipConfig {
	public static class Client {
		public final ModConfigSpec.DoubleValue flipChance;
		public final ModConfigSpec.IntValue minimumFallDistance;
		public final ModConfigSpec.BooleanValue invertMobs;
		public final ModConfigSpec.ConfigValue<List<? extends String>> flippingMobs;

		Client(ModConfigSpec.Builder builder) {
			builder.comment("Flip settings")
					.push("flip");

			flipChance = builder
					.comment("The chance of a flip happening when a mob falls from a high enough distance [0.0 = 0%, 0.1 = 10%, 1.0 = 100%] (Default: 0.35)")
					.defineInRange("flipChance", 0.35D, 0.0D, 1.0D);

			minimumFallDistance = builder
					.comment("The minimum fall distance required for a mob to flip (Between 1 and 32) [default: 3]")
					.defineInRange("minimumFallDistance", 3, 1, 32);

			invertMobs = builder
					.comment("When enabled turns the flippingMobs option into a blacklist [default: true]")
					.define("invertMobs", true);

			flippingMobs = builder
					.comment("Defines a list of mobs that can flip when falling from a high enough distance [Format: modid:entity]")
					.defineListAllowEmpty("flippingMobs", () ->
									List.of("minecraft:bat", "minecraft:armor_stand", "minecraft:ender_dragon", "minecraft:wither"),
							String::new, o -> (o instanceof String string && Identifier.tryParse(string) != null));

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

	public static void refreshCache() {
		ConfigCache.setFlipChance(CLIENT.flipChance.get().floatValue());
		ConfigCache.setInvertMobs(CLIENT.invertMobs.get());
		ConfigCache.generateEntityList(CLIENT.flippingMobs.get());
		ConfigCache.setMinimumFallDistance(CLIENT.minimumFallDistance.get());
	}
}
