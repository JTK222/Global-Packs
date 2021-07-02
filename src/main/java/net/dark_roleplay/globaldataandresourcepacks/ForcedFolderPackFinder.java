package net.dark_roleplay.globaldataandresourcepacks;

import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class ForcedFolderPackFinder extends FolderPackFinder {
    public ForcedFolderPackFinder(File file, IPackNameDecorator decorator) {
        super(file, decorator);

        if(!file.exists())
            file.mkdirs();
    }

    @Override
    public void findPacks(Consumer consumer, ResourcePackInfo.IFactory factory) {
        super.findPacks(packInfo -> {
            packInfo.alwaysEnabled = true;
            consumer.accept(packInfo);
        }, factory);
    }
}