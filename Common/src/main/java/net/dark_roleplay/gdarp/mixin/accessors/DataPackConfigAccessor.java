package net.dark_roleplay.gdarp.mixin.accessors;

import net.minecraft.world.level.DataPackConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DataPackConfig.class)
public interface DataPackConfigAccessor {

	@Mutable
	@Accessor("DEFAULT")
	public static void globalPacks_setForceEnabledPacks(DataPackConfig config){
		throw new AssertionError();
	}
}
