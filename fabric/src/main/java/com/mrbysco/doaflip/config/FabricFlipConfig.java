package com.mrbysco.doaflip.config;

import com.mrbysco.doaflip.Constants;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.List;

@Config(name = Constants.MOD_ID)
public class FabricFlipConfig implements ConfigData {
	@ConfigEntry.Gui.CollapsibleObject
	public final Client client = new Client();

	public static class Client {
		@Comment("The chance of a flip happening when a mob falls from a high enough distance (MUST BE BETWEEN 0 and 1) [0.0 = 0%, 0.1 = 10%, 1.0 = 100%] (Default: 0.35)")
		public double flipChance = 0.35D;
		@Comment("When enabled turns the flippingMobs option into a blacklist [default: true]")
		public boolean invertMobs = true;
		@Comment("Defines a list of mobs that can flip when falling from a high enough distance [Format: modid:entity]")
		public List<String> flippingMobs = List.of("minecraft:bat", "minecraft:armor_stand", "minecraft:ender_dragon", "minecraft:wither");
	}
}