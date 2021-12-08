package net.dark_roleplay.globaldataandresourcepacks.pack_finders;

import net.minecraft.resources.*;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MultiFilePackFinder implements IPackFinder {

	private static final FileFilter RESOURCEPACK_FILTER = (pack) ->
			(pack.isFile() && pack.getName().endsWith(".zip")) ||
			(pack.isDirectory() && (new File(pack, "pack.mcmeta")).isFile());

	private static final FileFilter DATAPACK_FILTER = (pack) ->
			(pack.isFile() && pack.getName().endsWith(".zip")) ||
					(pack.isDirectory() && (new File(pack, "pack.mcmeta")).isFile() && (new File(pack, "data/")).exists());

	private final boolean shouldForcePacks;
	private final Map<File, FilePackType> packs;
	private final IPackNameDecorator packSource;
	private final ResourcePackType packType;

	public MultiFilePackFinder(boolean shouldForcePacks, ResourcePackType packType, IPackNameDecorator packSource, Set<File> files) {
		this.shouldForcePacks = shouldForcePacks;
		this.packSource = packSource;
		this.packs = new HashMap<>();
		this.packType = packType;
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
	public void loadPacks(Consumer<ResourcePackInfo> packConsumer, ResourcePackInfo.IFactory packBuilder) {
		updatePacks();

		for (File file : this.packs.keySet()) {
			FilePackType type = this.packs.get(file);

			ResourcePackInfo pack = null;

			switch (type) {
				case UNZIPED_PACK:
				case ZIPED_PACK:
					pack = ResourcePackInfo.create(
							"global:" + file.getName(), this.shouldForcePacks,
							createSupplier(file),
							packBuilder, ResourcePackInfo.Priority.TOP, this.packSource
					);
					if (pack != null)
						packConsumer.accept(pack);
					break;
				case PACK_FOLDER:
					File[] afile = file.listFiles(this.packType == ResourcePackType.SERVER_DATA ? DATAPACK_FILTER : RESOURCEPACK_FILTER);
					if (afile != null)
						for (File packFile : afile) {
							pack = ResourcePackInfo.create(
									"global:" + packFile.getName(), this.shouldForcePacks,
									createSupplier(packFile),
									packBuilder, ResourcePackInfo.Priority.TOP, this.packSource);
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

	private Supplier<IResourcePack> createSupplier(File pack) {
		return pack.isDirectory() ? () -> new FolderPack(pack) : () -> new FilePack(pack);
	}

	private enum FilePackType {
		MISSING,
		ZIPED_PACK,
		UNZIPED_PACK,
		PACK_FOLDER
	}
}