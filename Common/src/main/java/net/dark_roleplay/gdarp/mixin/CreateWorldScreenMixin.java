package net.dark_roleplay.gdarp.mixin;

import net.dark_roleplay.gdarp.CommonClass;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {



//	boolean wasInitialized = false;
//
//	@Shadow protected abstract void tryApplyNewDataPacks(PackRepository packRepository);
//
//	@Shadow protected abstract Pair<File, PackRepository> getDataPackSelectionSettings();
//
////	@Accessor
////	public abstract DataPackConfig getDataPacks();
//
	@ModifyArg(
			method = "openFresh",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V")
	)
	private static RepositorySource[] globalpacks_addServerPackFinder(RepositorySource[] arg) {
		return ArrayUtils.addAll(arg, CommonClass.getRepositorySource(PackType.SERVER_DATA, true), CommonClass.getRepositorySource(PackType.SERVER_DATA, false));
	}
}