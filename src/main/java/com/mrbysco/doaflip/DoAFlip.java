package com.mrbysco.doaflip;

import com.mojang.logging.LogUtils;
import com.mrbysco.doaflip.client.config.FlipConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(DoAFlip.MOD_ID)
public class DoAFlip {
	public static final String MOD_ID = "doaflip";
	public static final Logger LOGGER = LogUtils.getLogger();

	public DoAFlip() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		if (FMLEnvironment.dist.isClient()) {
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FlipConfig.clientSpec);
			eventBus.register(FlipConfig.class);
		}
	}
}
