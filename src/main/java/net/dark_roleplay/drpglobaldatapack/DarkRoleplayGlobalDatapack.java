package net.dark_roleplay.drpglobaldatapack;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import java.util.Collection;

@Mod("globaldatapack")
public final class DarkRoleplayGlobalDatapack {

    public DarkRoleplayGlobalDatapack() {
        MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> DarkRoleplayGlobalDatapackClient::setupClientResourcePackFinder);
    }


    public void serverStarting(FMLServerAboutToStartEvent event) {

        event.getServer().getResourcePacks().addPackFinder(new ForcedFolderPackFinder(
                event.getServer().getFile("global_data_pack"),
                nameComp -> nameComp
        ));

        //Util to load Datapacks
        ResourcePackList<?> packs = event.getServer().getResourcePacks();
        packs.reloadPacksFromFinders();

        IServerConfiguration serverConf = event.getServer().func_240793_aU_();

        Collection<String> collection = packs.func_232621_d_();
        Collection<String> collection1 = func_241058_a_(packs, serverConf, collection);

        event.getServer().func_240780_a_(collection1).exceptionally(exception -> null);
    }

    private static Collection<String> func_241058_a_(ResourcePackList<?> packs, IServerConfiguration serverConf, Collection<String> existingPacks) {
        Collection<String> collection = Lists.newArrayList(existingPacks);
        Collection<String> collection1 = serverConf.func_230403_C_().func_234887_b_(); //Get active packs

        for (String s : packs.func_232616_b_()) {
            if (!collection1.contains(s) && !collection.contains(s)) {
                collection.add(s);
            }
        }

        return collection;
    }
}