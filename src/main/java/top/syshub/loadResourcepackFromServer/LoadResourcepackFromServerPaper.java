package top.syshub.loadResourcepackFromServer;

import io.papermc.paper.network.ChannelInitializeListenerHolder;
import net.kyori.adventure.key.Key;
import org.bukkit.plugin.java.JavaPlugin;
import top.offsetmonkey538.meshlib.api.HttpHandlerRegistry;
import top.offsetmonkey538.meshlib.impl.ProtocolHandler;

public final class LoadResourcepackFromServerPaper extends JavaPlugin {
    @Override
    public void onEnable() {
        ChannelInitializeListenerHolder.addListener(Key.key("meshlib", "meshlib"), (channel) -> channel.pipeline().addFirst("mesh-lib", new ProtocolHandler()));
        HttpHandlerRegistry.INSTANCE.register("resourcepack.zip", new ResourcePackHandler());
    }
    @Override
    public void onDisable() {}
}

