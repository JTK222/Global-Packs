package net.dark_roleplay.gdarp.mixin.accessors;

import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FolderRepositorySource.class)
public interface FolderRepositorySourceAccessor {
	@Accessor("packSource")
	PackSource globalpacks_getPackSource();
}
