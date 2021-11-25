package net.dark_roleplay.globaldataandresourcepacks;

import net.dark_roleplay.globaldataandresourcepacks.pack_finders.MultiFilePackFinder;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Mod("globaldataandresourcepacks")
public final class GlobalDataAndResourcepacks {

	public GlobalDataAndResourcepacks() {}

	public static RepositorySource getRepositorySource(PackType type, boolean force) {
		Set<File> files = new HashSet<>();
		//TODO Search external folders specified in json file

		switch (type) {
			case CLIENT_RESOURCES:
				if(force)
					files.add(new File("./global_resource_packs/"));
				break;
			case SERVER_DATA:
				if(force)
					files.add(new File("./global_data_packs/"));
				else
					files.add(new File("./optional_data_packs/"));
				break;
		}

		return new MultiFilePackFinder(force, nameComp -> nameComp, files);
	}
}