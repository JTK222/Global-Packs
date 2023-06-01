package net.dark_roleplay.globaldataandresourcepacks.config;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.ConfigParser;
import com.electronwill.nightconfig.core.io.ParsingMode;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class PackConfig {
	private static boolean hasLoadedConfig = false;

	private static Optional<List<String>> REQUIRED_DATAPACKS;
	private static Optional<List<String>> OPTIONAL_DATAPACKS;
	private static Optional<List<String>> REQUIRED_RESOURCEACKS;

	public static void loadConfigs() {
		if(hasLoadedConfig) return;

		//Legacy system, only do this, to create default configs
		CommentedFileConfig config = CommentedFileConfig
				.builder("./config/global_data_and_resourcepacks.toml")
				.defaultData(PackConfig.class.getClassLoader().getResource("default_config.toml"))
				.preserveInsertionOrder()
				.backingMapCreator(LinkedHashMap::new)
				.build();
		config.load();

//		//Hopefully ordered config?
//		TomlParser tomlParser = TomlFormat.instance().createParser();
//
//		Config c =  TomlFormat.instance().createConfig(LinkedHashMap::new);
//
//		try(BufferedReader reader = Files.newBufferedReader(Paths.get("./config/global_data_and_resourcepacks.toml"))){
//			tomlParser.parse(reader, c, ParsingMode.REPLACE);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}

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
