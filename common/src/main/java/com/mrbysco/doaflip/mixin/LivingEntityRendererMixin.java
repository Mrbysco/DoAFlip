package com.mrbysco.doaflip.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.doaflip.client.FlipHandler;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {


	@Inject(
			method = "setupRotations(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;FFFF)V",
			at = @At("RETURN")
	)
	public void doaflip$setupRotations(T livingEntity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale, CallbackInfo ci) {
		FlipHandler.doFlipping(livingEntity, poseStack, partialTicks);
	}
}