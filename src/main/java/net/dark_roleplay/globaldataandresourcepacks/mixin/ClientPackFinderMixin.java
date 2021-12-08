package net.dark_roleplay.globaldataandresourcepacks.mixin;

import net.dark_roleplay.globaldataandresourcepacks.GlobalDataAndResourcepacks;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Minecraft.class)
public class ClientPackFinderMixin {

	@ModifyArg(
			method = "<init>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>(Lnet/minecraft/server/packs/repository/Pack$PackConstructor;[Lnet/minecraft/server/packs/repository/RepositorySource;)V"),
			index = 1,
			remap = false
	)
	private RepositorySource[] addClientPackFinder(RepositorySource[] arg) {
		return ArrayUtils.addAll(arg, GlobalDataAndResourcepacks.getRepositorySource(PackType.CLIENT_RESOURCES, true));
	}

	@ModifyArg(
			method = "makeServerStem",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>(Lnet/minecraft/server/packs/PackType;[Lnet/minecraft/server/packs/repository/RepositorySource;)V"),
			index = 1,
			remap = false
	)
	private RepositorySource[] addClientPackFinder2(RepositorySource[] arg) {
		return ArrayUtils.addAll(arg, GlobalDataAndResourcepacks.getRepositorySource(PackType.SERVER_DATA, true), GlobalDataAndResourcepacks.getRepositorySource(PackType.SERVER_DATA, false));
	}
}
