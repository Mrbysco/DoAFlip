package com.mrbysco.doaflip.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.doaflip.FlipState;
import com.mrbysco.doaflip.client.FlipHandler;
import com.mrbysco.doaflip.platform.Services;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState> {

	@Inject(
			method = "setupRotations(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V",
			at = @At("RETURN")
	)
	public void doaflip$setupRotations(S renderState, PoseStack poseStack, float $$2, float $$3, CallbackInfo ci) {
		FlipHandler.doFlipping(renderState, poseStack, renderState.ageInTicks);
	}

	@Inject(
			method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
			at = @At("RETURN")
	)
	public void doaflip$extractRenderState(T livingEntity, S renderState, float partialTick, CallbackInfo ci) {
		if (renderState instanceof FlipState flipState) {
			flipState.doAFlip$setCanFlip(FlipHandler.canFlip(livingEntity));
			flipState.doAFlip$setFlying(livingEntity instanceof Player player && player.getAbilities().flying);
			flipState.doAFlip$setResetData(livingEntity.onGround() || livingEntity.isInWater() || livingEntity.isVehicle());
			flipState.doAFlip$setIsFalling(livingEntity.getDeltaMovement().y < 0 || (livingEntity instanceof Player && livingEntity.getDeltaMovement().y == 0.419875D));
			flipState.doAFlip$setDistanceFromGround(FlipHandler.getDistanceFromGround(livingEntity));
			flipState.doAFlip$setPersistentData(Services.PLATFORM.getPersistentData(livingEntity));
		}
	}
}