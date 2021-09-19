package net.dark_roleplay.globaldataandresourcepacks;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.File;

public class GlobalDataAndResourcepacksClient {

	private static final RepositorySource PACK_FINDER_IMPL = new ForcedFolderPackFinder(
			new File("./global_resource_packs"),
			nameComp -> nameComp
	);

	public static void setupClientResourcePackFinder() {
		Minecraft.getInstance().getResourcePackRepository().addPackFinder(PACK_FINDER_IMPL);
	}
}
