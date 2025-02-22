package top.syshub.loadResourcepackFromServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.meshlib.api.HttpHandler;
import top.offsetmonkey538.meshlib.api.HttpHandlerRegistry;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class LoadResourcepackFromServer implements ModInitializer {

    @Override
    public void onInitialize() {
        HttpHandlerRegistry.INSTANCE.register("resourcepack.zip", new ResourcePackHandler());
    }

    public static class ResourcePackHandler implements HttpHandler {
        @Override
        public void handleRequest(@NotNull ChannelHandlerContext ctx, @NotNull FullHttpRequest request) {
            final File resourcePackFile = new File("./resourcepack.zip");
            if (!resourcePackFile.exists())
                sendError(ctx, HttpResponseStatus.NOT_FOUND, "File not found");
            try {
                byte[] fileBytes = Files.readAllBytes(resourcePackFile.toPath());
                ByteBuf content = Unpooled.copiedBuffer(fileBytes);
                FullHttpResponse response = new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK,
                        content
                );
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/zip");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            } catch (IOException e) {
                sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR, "Internal server error");
            }
        }
        private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status, String message) {
            ByteBuf content = Unpooled.copiedBuffer(message, StandardCharsets.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    status,
                    content
            );
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }
}