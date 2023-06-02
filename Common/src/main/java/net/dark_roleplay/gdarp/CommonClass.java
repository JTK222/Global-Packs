package net.dark_roleplay.gdarp;

import net.dark_roleplay.gdarp.config.PackConfig;
import net.dark_roleplay.gdarp.pack_finders.MultiFilePackFinder;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonClass {

	private static Path GAME_DIR;

	static{
		GAME_DIR = Path.of(".");

		String launchArgument = System.getProperty("sun.java.command");

		System.out.println(launchArgument);

		if(launchArgument == null){
			Constants.LOG.warn("Unable to find launch arguments, the mod might not function as expected.");
		}else if(launchArgument.contains("gameDir")){
			Pattern pattern = Pattern.compile("gameDir\\s(.+?)(?:\\s--|$)");
			Matcher matcher = pattern.matcher(launchArgument);
			if(!matcher.find()){
				Constants.LOG.error("Unable to find gameDir in launch arguments '{}' even though it was specified", launchArgument);
			}else{
				String gameDirParam = matcher.group(1);
				GAME_DIR = Path.of(gameDirParam);
			}
		}
	}

	public static Path getGameDir() {
		return GAME_DIR;
	}

	public static MultiFilePackFinder getRepositorySource(PackType type, boolean force) {
		List<Path> files = new ArrayList<>();

		Optional<List<String>> packFolders = switch (type){
			case CLIENT_RESOURCES -> force ? PackConfig.getRequiredResourceacks() : PackConfig.getOptionalResourceacks();
			case SERVER_DATA -> force ? PackConfig.getRequiredDatapacks() : PackConfig.getOptionalDatapacks();
			default -> Optional.empty();
		};

		packFolders.ifPresent(list -> list.stream()
				.map(str -> Path.of(str))
				.map(str -> PackConfig.isSystemGlobalPath(str) ? str : GAME_DIR.resolve(str))
				.forEach(files::add));

		return new MultiFilePackFinder(force, type, nameComp -> nameComp, files);
	}
}
