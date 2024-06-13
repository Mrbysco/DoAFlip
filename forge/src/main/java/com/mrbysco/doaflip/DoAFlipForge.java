package com.mrbysco.doaflip;

import com.mrbysco.doaflip.config.ForgeFlipConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class DoAFlipForge {

	public DoAFlipForge() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		if (FMLEnvironment.dist.isClient()) {
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ForgeFlipConfig.clientSpec);
			eventBus.register(ForgeFlipConfig.class);
		}
	}
}