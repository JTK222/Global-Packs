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
			index = 1,
			remap = false
	)
	private IPackFinder[] addClientPackFinder(IPackFinder[] arg) {
		return ArrayUtils.addAll(arg, GlobalDataAndResourcepacks.getRepositorySource(ResourcePackType.CLIENT_RESOURCES, true));
	}

	@ModifyArg(
			method = "makeServerStem",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourcePackList;<init>([Lnet/minecraft/resources/IPackFinder;)V"),
			index = 0,
			remap = false
	)
	private IPackFinder[] addClientPackFinder2(IPackFinder[] arg) {
		return ArrayUtils.addAll(arg, GlobalDataAndResourcepacks.getRepositorySource(ResourcePackType.SERVER_DATA, true), GlobalDataAndResourcepacks.getRepositorySource(ResourcePackType.SERVER_DATA, false));
	}
}
