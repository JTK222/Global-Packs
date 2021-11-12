package net.dark_roleplay.globaldataandresourcepacks.mixin;

import net.dark_roleplay.globaldataandresourcepacks.GlobalDataAndResourcepacks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.DataPackConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Inject(at=@At("INVOKE"), remap=false, method="Lnet/minecraft/server/MinecraftServer;configurePackRepository(Lnet/minecraft/server/packs/repository/PackRepository;Lnet/minecraft/world/level/DataPackConfig;Z)Lnet/minecraft/world/level/DataPackConfig;")
	private static void configurePackRepository(PackRepository p_240772_0_, DataPackConfig config, boolean p_240772_2_, CallbackInfoReturnable<DataPackConfig> info) {
		GlobalDataAndResourcepacks.addDatapackFinder(p_240772_0_);
	}
}
