package com.mrbysco.doaflip.mixin;

import com.mrbysco.doaflip.IPersistentData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntity.class)
public class LivingEntityMixin implements IPersistentData {
	@Unique
	public final CompoundTag persistentData = new CompoundTag();

	@Override
	public CompoundTag doaflip$getPersistentData() {
		return this.persistentData;
	}
}