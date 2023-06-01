package net.dark_roleplay.globaldataandresourcepacks;

import net.dark_roleplay.globaldataandresourcepacks.config.PackConfig;
import net.dark_roleplay.globaldataandresourcepacks.pack_finders.MultiFilePackFinder;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.util.*;

@Mod("globaldataandresourcepacks")
public final class GlobalDataAndResourcepacks {

	public GlobalDataAndResourcepacks() {
	}

	public static IPackFinder getRepositorySource(ResourcePackType type, boolean force) {
		List<File> files = new ArrayList<>();

		Optional<List<String>> packFolders = type == ResourcePackType.CLIENT_RESOURCES ? PackConfig.getRequiredResourceacks() :
				(force ? PackConfig.getRequiredDatapacks() : PackConfig.getOptionalDatapacks());

		packFolders.ifPresent(list -> list.stream()
						.map(str -> new File(FMLPaths.GAMEDIR.get().toFile(), "/" + str))
						.filter(File::exists)
						.forEach(files::add));

		return new MultiFilePackFinder(force, type, nameComp -> nameComp, files);
	}
}