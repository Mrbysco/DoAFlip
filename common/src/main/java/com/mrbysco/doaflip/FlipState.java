package com.mrbysco.doaflip;

import net.minecraft.nbt.CompoundTag;

public interface FlipState {
	boolean doAFlip$canFlip();

	void doAFlip$setCanFlip(boolean canFlip);

	boolean doAFlip$isFlying();

	void doAFlip$setFlying(boolean isFlying);

	boolean doAFlip$shouldResetData();

	void doAFlip$setResetData(boolean isGrounded);

	boolean doAFlip$isFalling();

	void doAFlip$setIsFalling(boolean falling);

	double doAFlip$distanceFromGround();

	void doAFlip$setDistanceFromGround(double distance);

	CompoundTag doAFlip$persistentData();

	void doAFlip$setPersistentData(CompoundTag tag);
}
