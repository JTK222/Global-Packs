package net.dark_roleplay.gdarp.pack_finders;

import com.google.common.collect.ImmutableList;
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
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class GlobalPackFinder implements RepositorySource {

	private static final Predicate<Path> IS_VALID_RESOURCE_PACK = (pack) -> {
		boolean flag = true;
		if (Files.isRegularFile(pack) && pack.toString().endsWith(".zip")) {
			try (FileSystem fs = FileSystems.newFileSystem(pack)){
				flag &= Files.isDirectory(fs.getPath("assets/"));
				flag &= Files.isRegularFile(fs.getPath("pack.mcmeta"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			flag &= Files.isDirectory(pack.resolve("assets/"));
			flag &= Files.isRegularFile(pack.resolve("pack.mcmeta"));
		}
		return flag;
	};

	private static final Predicate<Path> IS_VALID_DATA_PACK = (pack) -> {
		boolean flag = true;
		if (Files.isRegularFile(pack) && pack.toString().endsWith(".zip")) {
			try (FileSystem fs = FileSystems.newFileSystem(pack)){
				flag &= Files.isDirectory(fs.getPath("data/"));
				flag &= Files.isRegularFile(fs.getPath("pack.mcmeta"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			flag &= Files.isDirectory(pack.resolve("data"));
			flag &= Files.isRegularFile(pack.resolve("pack.mcmeta"));
		}
		return flag;
	};

	private static PackSource GLOBAL = PackSource.create(name -> name.copy().append(" (Global)").withStyle(ChatFormatting.AQUA), true);
	private static PackSource GLOBAL_OPT = PackSource.create(name -> name.copy().append(" (Global Optional)").withStyle(ChatFormatting.DARK_AQUA), false);

	private final PackType packType;
	private final boolean forcedPacks;
	private final ImmutableList<Path> packLocations;


	public GlobalPackFinder(PackType packType, boolean required, List<Path> packLocations) {
		this.packType = packType;
		this.forcedPacks = required;
		this.packLocations = ImmutableList.<Path>builder().addAll(packLocations).build();
	}

	@Override
	public void loadPacks(Consumer<Pack> packRegistrar) {
		discoverResourcePacks(path -> {
			Pack.ResourcesSupplier resourceSupplier = null;
			if (Files.isRegularFile(path) && path.toString().endsWith(".zip"))
				resourceSupplier = createFilePack(path);
			else resourceSupplier = createFolderPack(path);

			if (resourceSupplier == null) return;

			Pack pack = Pack.readMetaAndCreate(
					path.getFileName().toString(),
					Component.literal(path.getFileName().toString()),
					this.forcedPacks, resourceSupplier,
					this.packType,
					Pack.Position.TOP,
					this.forcedPacks ? GLOBAL : GLOBAL_OPT
			);

			if (pack != null) packRegistrar.accept(pack);
		});
	}

	private Pack.ResourcesSupplier createFilePack(Path path) {
		Pack.ResourcesSupplier supplier = null;
		FileSystem fs = path.getFileSystem();

		if (this.packType == PackType.CLIENT_RESOURCES && !IS_VALID_RESOURCE_PACK.test(path)) return null;
		if (this.packType == PackType.SERVER_DATA && !IS_VALID_DATA_PACK.test(path)) return null;


		if (fs == FileSystems.getDefault() || fs instanceof LinkFileSystem)
			return (needle) -> new FilePackResources(needle, path.toFile(), false);

		return null;
}

	private Pack.ResourcesSupplier createFolderPack(Path path) {
		if (this.packType == PackType.CLIENT_RESOURCES && !IS_VALID_RESOURCE_PACK.test(path)) return null;
		if (this.packType == PackType.SERVER_DATA && !IS_VALID_DATA_PACK.test(path)) return null;

		return (needle) -> new PathPackResources(needle, path, false);
	}

	private void discoverResourcePacks(Consumer<Path> packCallback) {
		for (Path path : this.packLocations) {
			if (Files.isDirectory(path)) {
				if (Files.isRegularFile(path.resolve("pack.mcmeta"))) {
					packCallback.accept(path);
					continue;
				}

				try {
					if (Files.notExists(path)) Files.createDirectories(path);
				} catch (IOException e) {
					e.printStackTrace();
				}

				try (Stream<Path> fileStream = Files.list(path)) {
					fileStream.forEach(filePath -> {
						if (Files.isRegularFile(filePath) || (Files.isDirectory(filePath) && Files.isRegularFile(filePath.resolve("pack.mcmeta"))))
							packCallback.accept(filePath);
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				packCallback.accept(path);
			}
		}
	}
}