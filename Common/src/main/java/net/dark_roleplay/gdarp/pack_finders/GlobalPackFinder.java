package net.dark_roleplay.gdarp.pack_finders;

import com.google.common.collect.ImmutableSet;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.linkfs.LinkFileSystem;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class GlobalPackFinder implements RepositorySource {

	private static PackSource GLOBAL = PackSource.create(name -> Component.translatable("globalpacks.source.global", name).withStyle(ChatFormatting.GRAY), true);
	private static PackSource GLOBAL_OPT = PackSource.create(name -> Component.translatable("globalpacks.source.global_opt", name).withStyle(ChatFormatting.GRAY), true);

	private final PackType packType;
	private final boolean forcedPacks;
	private final ImmutableSet<Path> packLocations;


	public GlobalPackFinder(PackType packType, boolean required, Set<Path> packLocations) {
		this.packType = packType;
		this.forcedPacks = required;
		this.packLocations = ImmutableSet.copyOf(packLocations);
	}

	@Override
	public void loadPacks(Consumer<Pack> packRegistrar) {
		discoverResourcePacks(path -> {
			Pack.ResourcesSupplier resourceSupplier = null;
			if(Files.isRegularFile(path) && path.endsWith(".zip"))
				resourceSupplier = createFilePack(path);
			else resourceSupplier = createFolderPack(path);

			if(resourceSupplier == null) return;

			Pack pack =  Pack.readMetaAndCreate(
					(this.forcedPacks ? "global/" : "globalOpt/") + path.getFileName(),
					Component.literal(path.getFileName().toString()),
					this.forcedPacks, resourceSupplier,
					this.packType,
					Pack.Position.TOP,
					this.forcedPacks ? GLOBAL : GLOBAL_OPT
			);

			if(pack != null) packRegistrar.accept(pack);
		});
	}

	private Pack.ResourcesSupplier createFilePack(Path path){
		Pack.ResourcesSupplier supplier = null;
		FileSystem fs = path.getFileSystem();
		if(fs == FileSystems.getDefault() || fs instanceof LinkFileSystem)
			return (needle) -> new FilePackResources(needle, path.toFile(), false);

		return null;
	}

	private Pack.ResourcesSupplier createFolderPack(Path path){
		return (needle) -> new PathPackResources(needle, path, false);
	}

	private void discoverResourcePacks(Consumer<Path> packCallback){
		for(Path path : this.packLocations){
			if(Files.isDirectory(path)){
				if(Files.isRegularFile(path.resolve("pack.mcmeta"))){
					packCallback.accept(path);
					continue;
				}

				try {
					if(Files.notExists(path)) Files.createDirectories(path);
				} catch (IOException e) {
					e.printStackTrace();
				}

				try(Stream<Path> fileStream = Files.list(path)) {
					fileStream.forEach(packCallback);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				packCallback.accept(path);
			}
		}
	}
}