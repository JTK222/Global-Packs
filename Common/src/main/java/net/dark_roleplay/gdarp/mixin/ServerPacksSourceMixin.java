package net.dark_roleplay.gdarp.mixin;

import net.dark_roleplay.gdarp.CommonClass;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ServerPacksSource.class)
public class ServerPacksSourceMixin {

	@ModifyArg(
			method = "createPackRepository",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V")
	)
	private static RepositorySource[] globalpacks_createPackRepository(RepositorySource[] arg) {
		return ArrayUtils.addAll(arg, CommonClass.getRepositorySource(PackType.SERVER_DATA, true), CommonClass.getRepositorySource(PackType.SERVER_DATA, false));
	}
}
