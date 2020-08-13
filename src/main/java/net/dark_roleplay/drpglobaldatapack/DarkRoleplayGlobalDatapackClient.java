package net.dark_roleplay.drpglobaldatapack;

import net.minecraft.client.Minecraft;

import java.io.File;

public class DarkRoleplayGlobalDatapackClient {

    public static void setupClientResourcePackFinder(){
        Minecraft.getInstance().getResourcePackList().addPackFinder(new ForcedFolderPackFinder(
                new File("./global_resource_packs"),
                nameComp -> nameComp
        ));
    }
}
