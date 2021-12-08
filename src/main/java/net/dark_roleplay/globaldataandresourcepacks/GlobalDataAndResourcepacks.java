package net.dark_roleplay.globaldataandresourcepacks;

import net.dark_roleplay.globaldataandresourcepacks.config.PackConfig;
import net.dark_roleplay.globaldataandresourcepacks.pack_finders.MultiFilePackFinder;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mod("globaldataandresourcepacks")
public final class GlobalDataAndResourcepacks {

	public GlobalDataAndResourcepacks() {
	}

	public static IPackFinder getRepositorySource(ResourcePackType type, boolean force) {
		Set<File> files = new HashSet<>();

		Optional<List<String>> packFolders = type == ResourcePackType.CLIENT_RESOURCES ? PackConfig.getRequiredResourceacks():
				force ? PackConfig.getRequiredDatapacks() : !force ? PackConfig.getOptionalDatapacks() : Optional.empty();

		packFolders.ifPresent(list -> list.stream()
						.map(str -> new File("./" + str))
						.filter(fl -> fl.exists())
						.forEach(files::add));

		return new MultiFilePackFinder(force, type, nameComp -> nameComp, files);
	}
}