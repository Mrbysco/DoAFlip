package com.mrbysco.doaflip.mixin;

import com.mrbysco.doaflip.FlipState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements FlipState {
	@Unique
	private boolean doAFlip$canFlip = true;
	@Unique
	private boolean doAFlip$isFlying = false;
	@Unique
	private boolean doAFlip$shouldResetdata = false;
	@Unique
	private boolean doAFlip$isFalling = false;
	@Unique
	private double doAFlip$distanceFromGround = 0.0d;
	@Unique
	private CompoundTag doAFlip$persistentData = new CompoundTag();

	@Override
	public boolean doAFlip$canFlip() {
		return this.doAFlip$canFlip;
	}

	@Override
	public void doAFlip$setCanFlip(boolean canFlip) {
		this.doAFlip$canFlip = canFlip;
	}

	@Override
	public boolean doAFlip$isFlying() {
		return this.doAFlip$isFlying;
	}

	@Override
	public void doAFlip$setFlying(boolean isFlying) {
		this.doAFlip$isFlying = isFlying;
	}

	@Override
	public boolean doAFlip$shouldResetData() {
		return this.doAFlip$shouldResetdata;
	}

	@Override
	public void doAFlip$setResetData(boolean isGrounded) {
		this.doAFlip$shouldResetdata = isGrounded;
	}

	@Override
	public boolean doAFlip$isFalling() {
		return this.doAFlip$isFalling;
	}

	@Override
	public void doAFlip$setIsFalling(boolean falling) {
		this.doAFlip$isFalling = falling;
	}

	@Override
	public double doAFlip$distanceFromGround() {
		return this.doAFlip$distanceFromGround;
	}

	@Override
	public void doAFlip$setDistanceFromGround(double distance) {
		this.doAFlip$distanceFromGround = distance;
	}

	@Override
	public CompoundTag doAFlip$persistentData() {
		return this.doAFlip$persistentData;
	}

	@Override
	public void doAFlip$setPersistentData(CompoundTag tag) {
		this.doAFlip$persistentData = tag;
	}

}
