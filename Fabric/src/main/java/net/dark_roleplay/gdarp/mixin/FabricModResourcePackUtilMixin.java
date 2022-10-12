package net.dark_roleplay.gdarp.mixin;

import net.fabricmc.fabric.impl.resource.loader.ModResourcePackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.List;

@Mixin(ModResourcePackUtil.class)
public class FabricModResourcePackUtilMixin {

	@ModifyArgs(
			method="Lnet/fabricmc/fabric/impl/resource/loader/ModResourcePackUtil;createDefaultDataPackSettings()Lnet/minecraft/world/level/DataPackConfig;",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/world/level/DataPackConfig;<init>(Ljava/util/List;Ljava/util/List;)V"
			)
	)
	private static void fixDataPackConfig(Args args){
		List<String> enabled = args.get(0);
		List<String> disabled = args.get(1);

		for(String str : disabled){
			if(str.startsWith("global:")) {
				enabled.add(str);
				disabled.remove(str);
			}
		}
	}
}
