package net.dark_roleplay.globaldataandresourcepacks.mixin;

import net.dark_roleplay.globaldataandresourcepacks.GlobalDataAndResourcepacks;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.datafix.codec.DatapackCodec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Inject(at=@At("INVOKE"), remap = false, method="Lnet/minecraft/server/MinecraftServer;func_240772_a_(Lnet/minecraft/resources/ResourcePackList;Lnet/minecraft/util/datafix/codec/DatapackCodec;Z)Lnet/minecraft/util/datafix/codec/DatapackCodec;")
	private static void func_240772_a_(ResourcePackList p_240772_0_, DatapackCodec p_240772_1_, boolean p_240772_2_, CallbackInfoReturnable<DatapackCodec> info) {
		GlobalDataAndResourcepacks.addDatapackFinder(p_240772_0_);
	}
}
