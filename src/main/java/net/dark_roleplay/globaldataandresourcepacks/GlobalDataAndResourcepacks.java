package net.dark_roleplay.globaldataandresourcepacks;

import net.dark_roleplay.globaldataandresourcepacks.config.PackConfig;
import net.dark_roleplay.globaldataandresourcepacks.pack_finders.MultiFilePackFinder;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
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

	public static RepositorySource getRepositorySource(PackType type, boolean force) {
		Set<File> files = new HashSet<>();

		Optional<List<String>> packFolders = switch (type){
			case CLIENT_RESOURCES -> PackConfig.getRequiredResourceacks();
			case SERVER_DATA -> force ? PackConfig.getRequiredDatapacks() : PackConfig.getOptionalDatapacks();
			default -> Optional.empty();
		};

		packFolders.ifPresentOrElse(list -> list.stream()
						.map(str -> new File("./" + str))
						.filter(fl -> fl.exists())
						.forEach(files::add),
				() -> System.out.println("No Required Resourcepacks have been specified"));

		return new MultiFilePackFinder(force, type, nameComp -> nameComp, files);
	}
}