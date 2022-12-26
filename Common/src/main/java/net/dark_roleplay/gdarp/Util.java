package net.dark_roleplay.gdarp;

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
