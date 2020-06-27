package net.dark_roleplay.drpglobaldatapack;

import net.minecraft.resources.FilePack;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import java.io.File;
import java.util.function.Consumer;

@Mod("globaldatapack")
@Mod.EventBusSubscriber(modid="globaldatapack")
public final class DarkRoleplayGlobalDatapack {

	@SubscribeEvent
	public static void serverStarting(FMLServerAboutToStartEvent event) {

		event.getServer().getResourcePacks().addPackFinder(new IPackFinder() {
			@Override
			public <T extends ResourcePackInfo> void func_230230_a_(Consumer<T> consumer, ResourcePackInfo.IFactory<T> iFactory) {
				File globalDatapacks = event.getServer().getFile("global_data_pack");

				globalDatapacks.mkdirs();
				if(globalDatapacks.exists() && globalDatapacks.isDirectory()) {
					for (File file : globalDatapacks.listFiles()) {
						if (!file.isDirectory() && !file.getName().endsWith(".zip")) continue;
						T t = ResourcePackInfo.createResourcePack(file.getName(), false, () -> file.isDirectory() ? new FolderPack(file) : new FilePack(file), iFactory, ResourcePackInfo.Priority.TOP, text -> new TranslationTextComponent("global_datapack.pack", text));
						if (t == null) continue;
						consumer.accept(t);
					}
				}
			}
		});
	}
}
