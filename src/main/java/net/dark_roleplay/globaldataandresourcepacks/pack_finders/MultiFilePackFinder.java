package net.dark_roleplay.globaldataandresourcepacks.pack_finders;

import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MultiFilePackFinder implements RepositorySource {

	private static final FileFilter RESOURCEPACK_FILTER = (pack) ->
			(pack.isFile() && pack.getName().endsWith(".zip")) ||
			(pack.isDirectory() && (new File(pack, "pack.mcmeta")).isFile());

	private final boolean shouldForcePacks;
	private final Map<File, FilePackType> packs;
	private final PackSource packSource;

	public MultiFilePackFinder(boolean shouldForcePacks, PackSource packSource, Set<File> files) {
		this.shouldForcePacks = shouldForcePacks;
		this.packSource = packSource;
		this.packs = new HashMap<>();
		for (File file : files)
			this.packs.put(file, FilePackType.MISSING);
	}

	private void updatePacks() {
		for (File file : this.packs.keySet()) {
			if (file.isFile() && file.getPath().endsWith(".zip"))
				packs.put(file, FilePackType.ZIPED_PACK);
			else if (file.isDirectory() && new File(file, "pack.mcmeta").exists())
				packs.put(file, FilePackType.UNZIPED_PACK);
			else {
				if (!file.exists())
					file.mkdirs();
				packs.put(file, FilePackType.PACK_FOLDER);
			}
		}
	}

	@Override
	public void loadPacks(Consumer<Pack> packConsumer, Pack.PackConstructor packBuilder) {
		updatePacks();

		for (File file : this.packs.keySet()) {
			FilePackType type = this.packs.get(file);

			Pack pack = null;

			switch (type) {
				case UNZIPED_PACK:
				case ZIPED_PACK:
					pack = Pack.create(
							"file/" + file.getName(), this.shouldForcePacks,
							createSupplier(file),
							packBuilder, Pack.Position.TOP, this.packSource
					);
					if (pack != null)
						packConsumer.accept(pack);
					break;
				case PACK_FOLDER:
					File[] afile = file.listFiles(RESOURCEPACK_FILTER);
					if (afile != null)
						for (File packFile : afile)
							pack = Pack.create(
									"file/" + packFile.getName(), this.shouldForcePacks,
									createSupplier(packFile),
									packBuilder, Pack.Position.TOP, this.packSource);
					if (pack != null)
						packConsumer.accept(pack);
			}
			break;
		}
	}

	private Supplier<PackResources> createSupplier(File pack) {
		return pack.isDirectory() ? () -> new FolderPackResources(pack) : () -> new FilePackResources(pack);
	}

	private enum FilePackType {
		MISSING,
		ZIPED_PACK,
		UNZIPED_PACK,
		PACK_FOLDER
	}
}