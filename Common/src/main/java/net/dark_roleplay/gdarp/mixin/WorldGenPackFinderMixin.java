package net.dark_roleplay.gdarp.mixin;

import net.dark_roleplay.gdarp.CommonClass;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreateWorldScreen.class)
public class WorldGenPackFinderMixin {

	@ModifyArg(
			method = "getDataPackSelectionSettings",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>(Lnet/minecraft/server/packs/PackType;[Lnet/minecraft/server/packs/repository/RepositorySource;)V"),
			index = 1
	)
	private RepositorySource[] addClientPackFinder(RepositorySource[] arg) {
		return ArrayUtils.addAll(arg, CommonClass.getRepositorySource(PackType.SERVER_DATA, false), CommonClass.getRepositorySource(PackType.SERVER_DATA, true));
	}
}
