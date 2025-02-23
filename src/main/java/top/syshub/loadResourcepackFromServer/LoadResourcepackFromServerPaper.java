package top.syshub.loadResourcepackFromServer;

import org.bukkit.plugin.java.JavaPlugin;
import top.offsetmonkey538.meshlib.api.HttpHandlerRegistry;

public final class LoadResourcepackFromServerPaper extends JavaPlugin {
    @Override
    public void onEnable() {
        HttpHandlerRegistry.INSTANCE.register("resourcepack.zip", new ResourcePackHandler());
    }
    @Override
    public void onDisable() {
    }
}

