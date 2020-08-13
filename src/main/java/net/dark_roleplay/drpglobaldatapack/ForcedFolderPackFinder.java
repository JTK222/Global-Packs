package net.dark_roleplay.drpglobaldatapack;

import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;

import java.io.File;
import java.util.function.Consumer;

public class ForcedFolderPackFinder extends FolderPackFinder {
    public ForcedFolderPackFinder(File file, IPackNameDecorator decorator) {
        super(file, decorator);
    }

    @Override
    public void func_230230_a_(Consumer consumer, ResourcePackInfo.IFactory factory) {
        super.func_230230_a_(packInfo -> {
            packInfo.alwaysEnabled = true;
            consumer.accept(packInfo);
        }, factory);
    }
}