package net.dark_roleplay.drpglobaldatapack;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandSource;
import net.minecraft.resources.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import java.io.File;
import java.util.Collection;
import java.util.function.Consumer;

@Mod("globaldatapack")
@Mod.EventBusSubscriber(modid="globaldatapack")
public final class DarkRoleplayGlobalDatapack {

	@SubscribeEvent
	public static void serverStarting(FMLServerAboutToStartEvent event) {

		event.getServer().getResourcePacks().addPackFinder(new IPackFinder() {
			@Override
			public void func_230230_a_(Consumer<ResourcePackInfo> consumer, ResourcePackInfo.IFactory iFactory) {
				File globalDatapacks = event.getServer().getFile("global_data_pack");

				globalDatapacks.mkdirs();
				if(globalDatapacks.exists() && globalDatapacks.isDirectory()) {
					for (File file : globalDatapacks.listFiles()) {
						if (!file.isDirectory() && !file.getName().endsWith(".zip")) continue;
						ResourcePackInfo t = ResourcePackInfo.createResourcePack(file.getName(), true, () -> file.isDirectory() ? new FolderPack(file) : new FilePack(file), iFactory, ResourcePackInfo.Priority.TOP, text -> new TranslationTextComponent("global_datapack.pack", text));
						if (t == null) continue;
						consumer.accept(t);
					}
				}
			}
		});

		//Util to load Datapacks
		ResourcePackList packs = event.getServer().getResourcePacks();
		packs.reloadPacksFromFinders();

		IServerConfiguration serverConf = event.getServer().func_240793_aU_();

		Collection<String> collection = packs.func_232621_d_();
		Collection<String> collection1 = func_241058_a_(packs, serverConf, collection);

		event.getServer().func_240780_a_(collection1).exceptionally(exception -> null);
	}

	private static Collection<String> func_241058_a_(ResourcePackList packs, IServerConfiguration serverConf, Collection<String> existingPacks) {
		Collection<String> collection = Lists.newArrayList(existingPacks);
		Collection<String> collection1 = serverConf.func_230403_C_().func_234887_b_(); //Get active packs

		for(String s : packs.func_232616_b_()) {
			if (!collection1.contains(s) && !collection.contains(s)) {
				collection.add(s);
			}
		}

		return collection;
	}
}