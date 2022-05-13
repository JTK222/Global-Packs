package net.dark_roleplay.gdarp.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.dark_roleplay.gdarp.CommonClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

public class PackConfig {
	private static boolean hasLoadedConfig = false;

	private static Optional<List<String>> REQUIRED_DATAPACKS;
	private static Optional<List<String>> OPTIONAL_DATAPACKS;
	private static Optional<List<String>> REQUIRED_RESOURCEACKS;

	public static void loadConfigs() {
		if (hasLoadedConfig) return;
		try {
			Files.createDirectories(new File(CommonClass.getGameDir().toFile(), "/config/").toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		CommentedFileConfig config = CommentedFileConfig
				.builder(new File(CommonClass.getGameDir().toFile(), "/config/global_data_and_resourcepacks.toml"))
				.defaultData(PackConfig.class.getClassLoader().getResource("default_config.toml"))
				.build();

		config.load();

		REQUIRED_DATAPACKS = config.getOptional("datapacks.required");
		OPTIONAL_DATAPACKS = config.getOptional("datapacks.optional");
		REQUIRED_RESOURCEACKS = config.getOptional("resourcepacks.required");

		config.close();
		hasLoadedConfig = true;
	}

	public static Optional<List<String>> getRequiredDatapacks() {
		loadConfigs();
		return REQUIRED_DATAPACKS;
	}

	public static Optional<List<String>> getOptionalDatapacks() {
		loadConfigs();
		return OPTIONAL_DATAPACKS;
	}

	public static Optional<List<String>> getRequiredResourceacks() {
		loadConfigs();
		return REQUIRED_RESOURCEACKS;
	}
}
