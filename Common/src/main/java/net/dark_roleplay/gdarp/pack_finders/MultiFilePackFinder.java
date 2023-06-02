package net.dark_roleplay.gdarp.pack_finders;

import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MultiFilePackFinder implements RepositorySource {

	private static final FileFilter RESOURCEPACK_FILTER = (pack) -> {
		if (pack.isFile() && pack.getName().endsWith(".zip")) {
			try {
				FileSystem pfs = FileSystems.newFileSystem(pack.toPath());
				Path pth = pfs.getPath("assets/");
				return Files.exists(pth);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return pack.isDirectory() && (new File(pack, "pack.mcmeta")).isFile() && (new File(pack, "assets/")).exists();
	};

	private static final FileFilter DATAPACK_FILTER = (pack) -> {
		if (pack.isFile() && pack.getName().endsWith(".zip")) {
			try {
				FileSystem pfs = FileSystems.newFileSystem(pack.toPath());
				Path pth = pfs.getPath("data/");
				return Files.exists(pth);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		return pack.isDirectory() && (new File(pack, "pack.mcmeta")).isFile() && (new File(pack, "data/")).exists();
	};

	private final boolean shouldForcePacks;
	private final List<Path> packsOrder;
	private final Map<Path, FilePackType> packs;
	private final PackSource packSource;
	private final PackType packType;

	public MultiFilePackFinder(boolean shouldForcePacks, PackType packType, PackSource packSource, List<Path> files) {
		this.shouldForcePacks = shouldForcePacks;
		this.packSource = packSource;
		this.packs = new HashMap<>();
		this.packType = packType;
		for (Path file : files)
			this.packs.put(file, FilePackType.MISSING);
		this.packsOrder = files;
	}

	private void updatePacks() {
		for (Path file : this.packsOrder) {
			if (Files.isRegularFile(file) && file.endsWith(".zip"))
				packs.put(file, FilePackType.ZIPED_PACK);
			else if (Files.isDirectory(file) && Files.exists(file.resolve("pack.mcmeta")))
				packs.put(file, FilePackType.UNZIPED_PACK);
			else {
				try {
					if (Files.notExists(file))
						Files.createDirectories(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			packs.put(file, FilePackType.PACK_FOLDER);
		}
	}

	@Override
	public void loadPacks(Consumer<Pack> packConsumer, Pack.PackConstructor packBuilder) {
		updatePacks();

		for (Path file : this.packsOrder) {
			FilePackType type = this.packs.get(file);

			Pack pack = null;

			switch (type) {
				case UNZIPED_PACK:
				case ZIPED_PACK:
					pack = Pack.create(
							this.shouldForcePacks ? "global:" : "globalOpt:" + file.getFileName(), this.shouldForcePacks,
							createSupplier(file),
							packBuilder, Pack.Position.TOP, this.packSource
					);
					if (pack != null)
						packConsumer.accept(pack);
					break;
				case PACK_FOLDER:
					File[] afile = file.toFile().listFiles(this.packType == PackType.SERVER_DATA ? DATAPACK_FILTER : RESOURCEPACK_FILTER);
					if (afile != null)
						for (File packFile : afile) {
							pack = Pack.create(
									(this.shouldForcePacks ? "global:" : "globalOpt:") + packFile.getName(), this.shouldForcePacks,
									createSupplier(packFile.toPath()),
									packBuilder, Pack.Position.TOP, this.packSource);
							if (pack != null) {
								packConsumer.accept(pack);
								pack = null;
							}
						}
					break;
				default:
					break;
			}
		}
	}

	public List<String> getProvidedPacks(){
		return this.packs.keySet().stream().map(fl -> (this.shouldForcePacks ? "global:" : "globalOpt:") + fl.getFileName()).collect(Collectors.toList());
	}

	private Supplier<PackResources> createSupplier(Path pack) {
		return Files.isDirectory(pack) ? () -> new FolderPackResources(pack.toFile()) : () -> new FilePackResources(pack.toFile());
	}

private enum FilePackType {
	MISSING,
	ZIPED_PACK,
	UNZIPED_PACK,
	PACK_FOLDER
}
}