package net.dark_roleplay.globaldataandresourcepacks;

import net.dark_roleplay.globaldataandresourcepacks.pack_finders.MultiFilePackFinder;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class GlobalDataAndResourcepacksClient {

	public static void setupClientResourcePackFinder() {
		Set<File> files = new HashSet<>();
		files.add(new File("./global_resource_packs/"));
		//TODO Search external folders specified in json file

		Minecraft.getInstance().getResourcePackRepository().addPackFinder(new MultiFilePackFinder(true, nameComp -> nameComp, files));
	}
}
