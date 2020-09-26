package net.dark_roleplay.globaldataandresourcepacks;

import net.minecraft.client.Minecraft;

import java.io.File;

public class GlobalDataAndResourcepacksClient {

	public static void setupClientResourcePackFinder() {
		Minecraft.getInstance().getResourcePackList().addPackFinder(new ForcedFolderPackFinder(
				new File("./global_resource_packs"),
				nameComp -> nameComp
		));
	}
}
