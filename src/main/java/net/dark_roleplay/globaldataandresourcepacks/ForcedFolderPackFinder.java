package net.dark_roleplay.globaldataandresourcepacks;

import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;

import java.io.File;
import java.util.function.Consumer;

public class ForcedFolderPackFinder extends FolderRepositorySource {
    public ForcedFolderPackFinder(File file, PackSource decorator) {
        super(file, decorator);

        if(!file.exists())
            file.mkdirs();
    }

    @Override
    public void loadPacks(Consumer<Pack> consumer, Pack.PackConstructor factory) {
        super.loadPacks(packInfo -> {
            packInfo.required = true;
            consumer.accept(packInfo);
        }, factory);
    }
}