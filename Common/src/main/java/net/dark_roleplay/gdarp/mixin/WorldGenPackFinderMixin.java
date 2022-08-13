package net.dark_roleplay.gdarp.mixin;

import com.mojang.datafixers.util.Pair;
import net.dark_roleplay.gdarp.CommonClass;
import net.dark_roleplay.gdarp.pack_finders.MultiFilePackFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldGenSettingsComponent;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.level.DataPackConfig;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mixin(CreateWorldScreen.class)
public abstract class WorldGenPackFinderMixin {

	boolean wasInitialized = false;

	@Shadow protected abstract void tryApplyNewDataPacks(PackRepository packRepository);

	@Shadow protected abstract Pair<File, PackRepository> getDataPackSelectionSettings();

	@Accessor
	public abstract DataPackConfig getDataPacks();

	@ModifyArg(
			method = "getDataPackSelectionSettings",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>(Lnet/minecraft/server/packs/PackType;[Lnet/minecraft/server/packs/repository/RepositorySource;)V"),
			index = 1
	)
	private RepositorySource[] addClientPackFinder(RepositorySource[] arg) {
		MultiFilePackFinder finder = CommonClass.getRepositorySource(PackType.SERVER_DATA, true);
		return ArrayUtils.addAll(arg, CommonClass.getRepositorySource(PackType.SERVER_DATA, false), finder);
	}

	@Inject(
			method = "init",
			at = @At(value = "RETURN")
	)
	private void constructorInject(CallbackInfo clb){
		if(!wasInitialized)
			this.tryApplyNewDataPacks(this.getDataPackSelectionSettings().getSecond());
		wasInitialized = true;
	}


	@ModifyArg(
			method = "openFresh",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>(Lnet/minecraft/server/packs/PackType;[Lnet/minecraft/server/packs/repository/RepositorySource;)V"),
			index = 1
	)
	private static RepositorySource[] addDefaultPackRepo(RepositorySource[] arg) {
		MultiFilePackFinder finder = CommonClass.getRepositorySource(PackType.SERVER_DATA, true);
		return ArrayUtils.addAll(arg, CommonClass.getRepositorySource(PackType.SERVER_DATA, false), finder);
	}
}