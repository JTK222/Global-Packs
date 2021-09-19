package net.dark_roleplay.globaldataandresourcepacks;

import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

@Mod("globaldataandresourcepacks")
public final class GlobalDataAndResourcepacks {

	private static final RepositorySource PACK_FINDER_IMPL = new ForcedFolderPackFinder(
			new File("./global_data_packs"),
			nameComp -> nameComp
	);

	public GlobalDataAndResourcepacks() {
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> GlobalDataAndResourcepacksClient::setupClientResourcePackFinder);
	}

	public static void addDatapackFinder(PackRepository packList){
		packList.addPackFinder(PACK_FINDER_IMPL);
	}
}