package net.dark_roleplay.drpglobaldatapack;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(DarkRoleplayGlobalDatapack.MODID)
public class DarkRoleplayGlobalDatapack {
	public static final String MODID = "drpglobaldatapack";
	
	public static Object INSTANCE;
	
	private static final String DEFAULT_PACK_META = "{\r\n" + 
			"	\"pack\":{\r\n" + 
			"		\"pack_format\":1,\r\n" + 
			"		\"description\":\"Global Datapack - using Dark Roleplays, Global Datapack mod\"\r\n" + 
			"	}\r\n" + 
			"}";
	
	public DarkRoleplayGlobalDatapack() {
		DarkRoleplayGlobalDatapack.INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(new ServerLaunchListener());
        
		File globalDatapack = new File("./global_data_pack/");
		globalDatapack.mkdirs();
		
		File packMeta = new File(globalDatapack, "pack.mcmeta");
		if(!packMeta.exists()) {
			try {
				packMeta.createNewFile();
				try(Writer writer = new FileWriter(packMeta)){
					writer.write(DEFAULT_PACK_META);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
