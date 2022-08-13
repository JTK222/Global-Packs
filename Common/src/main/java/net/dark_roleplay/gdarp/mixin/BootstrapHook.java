package net.dark_roleplay.gdarp.mixin;

import net.dark_roleplay.gdarp.config.PackConfig;
import net.minecraft.server.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapHook {

	@Inject(
			method = "bootStrap()V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;freezeBuiltins()V", ordinal = 0)
	)
	private static void bootstrap(CallbackInfo callback) {
		PackConfig.createFolders();
	}
}

