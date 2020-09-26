package net.dark_roleplay.globaldataandresourcepacks;

import net.minecraft.resources.ResourcePackList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

@Mod("globaldataandresourcepacks")
public final class GlobalDataAndResourcepacks {

	public GlobalDataAndResourcepacks() {
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> GlobalDataAndResourcepacksClient::setupClientResourcePackFinder);
	}

	public static void addDatapackFinder(ResourcePackList packList){
		File dataPacks = new File("global_data_pack");
		if (!dataPacks.exists())
			dataPacks = new File("global_data_packs");

		packList.addPackFinder(new ForcedFolderPackFinder(
				dataPacks,
				nameComp -> nameComp
		));
	}
}