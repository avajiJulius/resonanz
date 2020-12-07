package com.resonanz.client;

import com.resonanz.client.handler.SimpleHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class SslChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;
    private Callback onMessageReceived;

    public SslChannelInitializer(SslContext sslCtx, Callback onMessageReceived) {
        this.sslCtx = sslCtx;
        this.onMessageReceived = onMessageReceived;
    }

    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {

        socketChannel.pipeline().addLast(
                sslCtx.newHandler(socketChannel.alloc(), Network.HOST, Network.PORT),
                new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()),
                new StringDecoder(),
                new StringEncoder(),
                new SimpleHandler(onMessageReceived));

    }
}

