package net.dark_roleplay.gdarp.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.dark_roleplay.gdarp.CommonClass;
import net.dark_roleplay.gdarp.mixin.accessors.PackRepositoryAccessor;
import net.dark_roleplay.gdarp.pack_finders.MultiFilePackFinder;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.level.DataPackConfig;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Collection;
import java.util.List;

@Mixin(CreateWorldScreen.class)
public abstract class WorldGenPackFinderMixin {

	boolean wasInitialized = false;

	@Shadow protected abstract void tryApplyNewDataPacks(PackRepository packRepository);

	@Shadow protected abstract Pair<File, PackRepository> getDataPackSelectionSettings();

//	@Accessor
//	public abstract DataPackConfig getDataPacks();

	@Inject(
			method = "init",
			at = @At(value = "RETURN")
	)
	private void globalpacks_constructorInject(CallbackInfo clb){
		if(!wasInitialized){
			Pair<File, PackRepository> packFolderRepo = this.getDataPackSelectionSettings();
			PackRepository packRepo = packFolderRepo.getSecond();

			this.tryApplyNewDataPacks(packRepo);
		}
	}
}