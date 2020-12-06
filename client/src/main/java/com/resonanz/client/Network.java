package com.resonanz.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

public class Network {
    private SocketChannel channel;
    static final String HOST = System.getProperty("localhost", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8181"));

    public Network(){
        Thread thread = new Thread(() -> {
            EventLoopGroup group = new NioEventLoopGroup(1);
            try {

                final SslContext sslCtx = SslContextBuilder.forClient()
                        .trustManager(InsecureTrustManagerFactory.INSTANCE).build();

                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new SslChannelInitializer(sslCtx));
                ChannelFuture future = b.connect(HOST, PORT).sync();
                future.channel().closeFuture().sync();

            } catch (Exception e) {

            } finally {
                group.shutdownGracefully();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
