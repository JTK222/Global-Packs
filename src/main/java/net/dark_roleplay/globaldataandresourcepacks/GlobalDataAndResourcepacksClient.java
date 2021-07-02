package net.dark_roleplay.globaldataandresourcepacks;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.IPackFinder;

import java.io.File;
import java.nio.file.Files;

public class GlobalDataAndResourcepacksClient {

	private static final IPackFinder PACK_FINDER_IMPL = new ForcedFolderPackFinder(
			new File("./global_resource_packs"),
			nameComp -> nameComp
	);

	public static void setupClientResourcePackFinder() {
		Minecraft.getInstance().getResourcePackList().addPackFinder(PACK_FINDER_IMPL);
	}
}
