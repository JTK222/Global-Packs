package net.dark_roleplay.gdarp.mixin;

import net.dark_roleplay.gdarp.CommonClass;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowFinderMixin {

//	@ModifyArg(
//			method = "createPackRepository",
//			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>(Lnet/minecraft/server/packs/PackType;[Lnet/minecraft/server/packs/repository/RepositorySource;)V"),
//			index = 1
//	)
//	private static RepositorySource[] globalpacks_addClientPackFinder2(RepositorySource[] arg) {
//		return ArrayUtils.addAll(arg, CommonClass.getRepositorySource(PackType.SERVER_DATA, true), CommonClass.getRepositorySource(PackType.SERVER_DATA, false));
//	}
}
