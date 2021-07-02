package net.dark_roleplay.globaldataandresourcepacks;

import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Mod("globaldataandresourcepacks")
public final class GlobalDataAndResourcepacks {

	private static final IPackFinder PACK_FINDER_IMPL = new ForcedFolderPackFinder(
			new File("./global_data_packs"),
			nameComp -> nameComp
	);

	public GlobalDataAndResourcepacks() {
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> GlobalDataAndResourcepacksClient::setupClientResourcePackFinder);
	}

	public static void addDatapackFinder(ResourcePackList packList){
		packList.addPackFinder(PACK_FINDER_IMPL);
	}
}