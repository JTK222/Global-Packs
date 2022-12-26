package net.dark_roleplay.gdarp;

import com.google.common.collect.ImmutableCollection;
import net.dark_roleplay.gdarp.pack_finders.MultiFilePackFinder;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class Util {
//	public static void globalPacks_makeRepositorySourcesMutable(Set<RepositorySource> sources, Pack.PackConstructor factory, RepositorySource[] sourcesIn, CallbackInfo info) {
//		if (sources instanceof ImmutableCollection)
//			sources = new HashSet(sources);
//
//		for (RepositorySource provider : sources)
//			if (provider instanceof MultiFilePackFinder)
//				return;
//
//		Field[] declaredFields = factory.getClass().getDeclaredFields();
//		for (Field f : declaredFields) {
//			if(f.getType() == PackType.class){
//				try {
//					f.setAccessible(true);
//					PackType packType = (PackType) f.get(factory);
//
//					if(packType == PackType.SERVER_DATA){
//						sources.add(CommonClass.getRepositorySource(PackType.SERVER_DATA, true));
//						sources.add(CommonClass.getRepositorySource(PackType.SERVER_DATA, false));
//					}
//				} catch (IllegalAccessException e) {
//					throw new RuntimeException(e);
//				}
//			}
//		}
//	}

}
