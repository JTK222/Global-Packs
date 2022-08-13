package net.dark_roleplay.gdarp.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.dark_roleplay.gdarp.CommonClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PackConfig {
	private static boolean hasLoadedConfig = false;

	private static Optional<List<String>> REQUIRED_DATAPACKS;
	private static Optional<List<String>> OPTIONAL_DATAPACKS;
	private static Optional<List<String>> REQUIRED_RESOURCEACKS;
	private static Optional<List<String>> OPTIONAL_RESOURCEPACKS;

	private static Optional<Boolean> ENABLE_SYSTEM_GLOBAL;
	private static Path SYSTEM_GLOBAL_PATH;

	public static boolean isSystemGlobalPath(Path other){
		return SYSTEM_GLOBAL_PATH != null && other.startsWith(SYSTEM_GLOBAL_PATH);
	}

	public static void loadConfigs() {
		if (hasLoadedConfig) return;
		if(Files.notExists(CommonClass.getGameDir().resolve("config").resolve("global_data_and_resourcepacks.toml"))){
			try {
				Files.createDirectories(new File(CommonClass.getGameDir().toFile(), "/config/").toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}

			Path forgeDefaultCfgPath = CommonClass.getGameDir().resolve("defaultconfigs").resolve("global_data_and_resourcepacks.toml");
			if(Files.exists(forgeDefaultCfgPath)){
				try {
					Files.copy(forgeDefaultCfgPath, CommonClass.getGameDir().resolve("config/global_data_and_resourcepacks.toml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		CommentedFileConfig config = CommentedFileConfig
				.builder(new File(CommonClass.getGameDir().toFile(), "/config/global_data_and_resourcepacks.toml"))
				.defaultData(PackConfig.class.getClassLoader().getResource("default_config.toml"))
				.build();

		config.load();


		ENABLE_SYSTEM_GLOBAL = config.getOptional("enable_system_global_packs");
		boolean enableSystemGlobal = ENABLE_SYSTEM_GLOBAL.orElse(false);

		REQUIRED_DATAPACKS = config.getOptional("datapacks.required");
		OPTIONAL_DATAPACKS = config.getOptional("datapacks.optional");
		REQUIRED_RESOURCEACKS = config.getOptional("resourcepacks.required");

		if(enableSystemGlobal){
			String userHome = System.getProperty("user.home");
			SYSTEM_GLOBAL_PATH = Path.of(userHome, ".minecraft_global_packs");

			REQUIRED_DATAPACKS.ifPresent(packs -> packs.add(SYSTEM_GLOBAL_PATH.resolve("required_datapacks").toFile().getPath()));
			OPTIONAL_DATAPACKS.ifPresent(packs -> packs.add(SYSTEM_GLOBAL_PATH.resolve("optional_datapacks").toFile().getPath()));
			REQUIRED_RESOURCEACKS.ifPresent(packs -> packs.add(SYSTEM_GLOBAL_PATH.resolve("required_resourcepacks").toFile().getPath()));
			OPTIONAL_RESOURCEPACKS = Optional.of(Arrays.asList(SYSTEM_GLOBAL_PATH.resolve("optional_resourcepacks").toFile().getPath()));
		}else{
			OPTIONAL_RESOURCEPACKS = Optional.empty();
		}

		config.close();
		hasLoadedConfig = true;
	}

	public static void createFolders(){
		loadConfigs();

		if(ENABLE_SYSTEM_GLOBAL.orElse(false)){
			String userHome = System.getProperty("user.home");

			try {
				Files.createDirectories(Paths.get(userHome, ".minecraft_global_packs", "required_datapacks"));
				Files.createDirectories(Paths.get(userHome, ".minecraft_global_packs", "optional_datapacks"));
				Files.createDirectories(Paths.get(userHome, ".minecraft_global_packs", "required_resourcepacks"));
				Files.createDirectories(Paths.get(userHome, ".minecraft_global_packs", "optional_resourcepacks"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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

	public static Optional<List<String>> getOptionalResourceacks() {
		loadConfigs();
		return OPTIONAL_RESOURCEPACKS;
	}
}
