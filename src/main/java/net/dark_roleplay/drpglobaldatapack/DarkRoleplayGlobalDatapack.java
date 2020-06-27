package net.dark_roleplay.drpglobaldatapack;

import java.io.File;
import java.util.Map;

import net.minecraft.resources.FilePack;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@Mod("globaldatapack")
@Mod.EventBusSubscriber(modid="globaldatapack")
public final class DarkRoleplayGlobalDatapack {

	@SubscribeEvent
	public static void serverStarting(FMLServerAboutToStartEvent event) {

		event.getServer().getResourcePacks().addPackFinder(new IPackFinder() {
			@Override
			public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, ResourcePackInfo.IFactory<T> packInfoFactory) {
				File globalDatapacks = event.getServer().getFile("global_data_pack");

				globalDatapacks.mkdirs();
				if(globalDatapacks.exists() && globalDatapacks.isDirectory()) {
					for (File file : globalDatapacks.listFiles()) {
						if (!file.isDirectory() && !file.getName().endsWith(".zip")) continue;
						T t = ResourcePackInfo.createResourcePack("modpack:" + file.getName(), false, () -> file.isDirectory() ? new FolderPack(file) : new FilePack(file), packInfoFactory, ResourcePackInfo.Priority.TOP);
						if (t == null) continue;
						nameToPackMap.put("modpack:" + file.getName(), t);
					}
				}
			}
		});
	}
}
