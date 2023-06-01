package net.dark_roleplay.globaldataandresourcepacks.mixin;

import net.dark_roleplay.globaldataandresourcepacks.GlobalDataAndResourcepacks;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackType;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Minecraft.class)
public class ClientPackFinderMixin {

	@ModifyArg(
			method = "<init>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourcePackList;<init>(Lnet/minecraft/resources/ResourcePackInfo$IFactory;[Lnet/minecraft/resources/IPackFinder;)V"),
			index = 1
	)
	private IPackFinder[] addClientPackFinder(IPackFinder[] arg) {
		return ArrayUtils.addAll(arg, GlobalDataAndResourcepacks.getRepositorySource(ResourcePackType.CLIENT_RESOURCES, true));
	}

	@ModifyArg(
			method = "Lnet/minecraft/client/Minecraft;makeServerStem(Lnet/minecraft/util/registry/DynamicRegistries$Impl;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/world/storage/SaveFormat$LevelSave;)Lnet/minecraft/client/Minecraft$PackManager;",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourcePackList;<init>([Lnet/minecraft/resources/IPackFinder;)V"),
			index = 0
	)
	private IPackFinder[] addClientPackFinder2(IPackFinder[] arg) {
		return ArrayUtils.addAll(arg, GlobalDataAndResourcepacks.getRepositorySource(ResourcePackType.SERVER_DATA, true), GlobalDataAndResourcepacks.getRepositorySource(ResourcePackType.SERVER_DATA, false));
	}
}
