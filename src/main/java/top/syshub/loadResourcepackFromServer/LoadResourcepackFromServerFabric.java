package top.syshub.loadResourcepackFromServer;

import net.fabricmc.api.ModInitializer;
import top.offsetmonkey538.meshlib.api.HttpHandlerRegistry;

public class LoadResourcepackFromServerFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        HttpHandlerRegistry.INSTANCE.register("resourcepack.zip", new ResourcePackHandler());
    }
}
