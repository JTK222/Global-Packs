package net.dark_roleplay.globaldataandresourcepacks.mixin;

import net.dark_roleplay.globaldataandresourcepacks.GlobalDataAndResourcepacks;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.server.Main;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Main.class)
public class DedicatedServerPackFinderMixin {

	@ModifyArg(
			method = "main",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/resources/ResourcePackList;<init>([Lnet/minecraft/resources/IPackFinder;)V"),
			index = 0
	)
	private static IPackFinder[] addServerPackFinders(IPackFinder[] arg) {
		return ArrayUtils.addAll(arg, GlobalDataAndResourcepacks.getRepositorySource(ResourcePackType.SERVER_DATA, true), GlobalDataAndResourcepacks.getRepositorySource(ResourcePackType.SERVER_DATA, false));
	}
}
