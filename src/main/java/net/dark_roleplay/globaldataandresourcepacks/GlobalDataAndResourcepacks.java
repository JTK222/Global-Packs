package net.dark_roleplay.globaldataandresourcepacks;

import net.dark_roleplay.globaldataandresourcepacks.pack_finders.MultiFilePackFinder;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Mod("globaldataandresourcepacks")
public final class GlobalDataAndResourcepacks {

	public GlobalDataAndResourcepacks() {
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> GlobalDataAndResourcepacksClient::setupClientResourcePackFinder);
	}

	public static void addDatapackFinder(PackRepository packList){
		Set<File> files = new HashSet<>();
		files.add(new File("./global_data_packs/"));
		//TODO Search external folders specified in json file

		packList.addPackFinder(new MultiFilePackFinder(false, nameComp -> nameComp, files));
	}
}