package net.dark_roleplay.gdarp.mixin;

import net.dark_roleplay.gdarp.CommonClass;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Minecraft.class)
public class MinecraftMixin {

	@ModifyArg(
			method = "<init>",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"),
			index = 0
	)
	private RepositorySource[] globalpacks_addClientPackFinder(RepositorySource[] arg) {
		return ArrayUtils.addAll(arg, CommonClass.getRepositorySource(PackType.CLIENT_RESOURCES, true), CommonClass.getRepositorySource(PackType.CLIENT_RESOURCES, false));
	}
}
