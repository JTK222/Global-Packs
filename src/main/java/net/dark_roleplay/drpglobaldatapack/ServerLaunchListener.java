package net.dark_roleplay.drpglobaldatapack;

import java.io.File;
import java.util.Map;

import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.IFactory;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

public class ServerLaunchListener {
	
	@SubscribeEvent
	public void serverStarting(FMLServerAboutToStartEvent event) {
		File globalDatapack = new File("./global_data_pack/");
		
		((ResourcePackList<?>)ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, event.getServer(), "field_195577_ad")).addPackFinder(new GlobalDataPackFinder(globalDatapack));
	}
	
	private static class GlobalDataPackFinder implements IPackFinder {

		private File folder;
		
		public GlobalDataPackFinder(File file) {
			this.folder = file;
		}
		
		@Override
		public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, IFactory<T> packInfoFactory) {
			T t = ResourcePackInfo.func_195793_a("global_data_pack", false, () -> { return new FolderPack(this.folder);}, packInfoFactory, ResourcePackInfo.Priority.TOP);
	        if (t != null) {
	           nameToPackMap.put("global_data_pack", t);
	        }
		}
	}
}
