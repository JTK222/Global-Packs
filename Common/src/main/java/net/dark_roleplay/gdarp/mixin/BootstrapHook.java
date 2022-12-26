package net.dark_roleplay.gdarp.mixin;

import net.dark_roleplay.gdarp.CommonClass;
import net.dark_roleplay.gdarp.config.PackConfig;
import net.dark_roleplay.gdarp.mixin.accessors.DataPackConfigAccessor;
import net.dark_roleplay.gdarp.pack_finders.GlobalPackFinder;
import net.dark_roleplay.gdarp.pack_finders.MultiFilePackFinder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.DataPackConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(BuiltInRegistries.class)
public class BootstrapHook {

	@Inject(
			method = "bootStrap()V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/core/registries/BuiltInRegistries;freeze()V", ordinal = 0)
	)
	private static void globalpacks_bootstrap(CallbackInfo callback) {
//		PackConfig.createFolders();
//
//		List<String> enabledMutable = new ArrayList<>(DataPackConfig.DEFAULT.getEnabled());
//
//		GlobalPackFinder packFinder = CommonClass.getRepositorySource(PackType.SERVER_DATA, true);
//		packFinder.updatePacks();
//		enabledMutable.addAll(packFinder.getAvailablePacks());
//
//		DataPackConfigAccessor.globalPacks_setForceEnabledPacks(new DataPackConfig(enabledMutable, DataPackConfig.DEFAULT.getDisabled()));
	}
}

