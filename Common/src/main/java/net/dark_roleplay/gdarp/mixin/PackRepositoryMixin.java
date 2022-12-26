package net.dark_roleplay.gdarp.mixin;

import net.dark_roleplay.gdarp.Util;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(value = PackRepository.class, priority = 2000)
public class PackRepositoryMixin {

//	@Shadow
//	@Final
//	@Mutable
//	private Set<RepositorySource> sources;
//
//	@Inject(
//			method = "Lnet/minecraft/server/packs/repository/PackRepository;<init>(Lnet/minecraft/server/packs/repository/Pack$PackConstructor;[Lnet/minecraft/server/packs/repository/RepositorySource;)V",
//			at = @At("RETURN")
//	)
//	public void globalPacks_makeRepositorySourcesMutable(Pack.PackConstructor factory, RepositorySource[] sourcesIn, CallbackInfo info) {
//		Util.globalPacks_makeRepositorySourcesMutable(this.sources, factory, sourcesIn, info);
//	}
//
//	@ModifyArg(
//			method = "rebuildSelected",
//			at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;copyOf(Ljava/util/Collection;)Lcom/google/common/collect/ImmutableList;")
//	)
//	public Collection<? extends Pack> globalPacks_potato(Collection<? extends Pack> elements) {
//		return elements.stream().sorted((o1, o2) -> (o1.getId().startsWith("global:") ? 2 : 0) +
//				(o2.getId().startsWith("global:") ? -2 : 0) + (o1.getId().startsWith("globalOpt:") ? 1 : 0) +
//				(o2.getId().startsWith("globalOpt:") ? -1 : 0)
//		).collect(Collectors.toList());
//	}
}