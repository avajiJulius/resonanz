package com.resonanz.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class Server {

    private static final int SERVER_PORT = Integer.parseInt(System.getProperty("port", "8181"));
    private static Server server;
    private static SslContext context;
    private static EventLoopGroup bossGroup;
    private static EventLoopGroup workerGroup;
    private static ServerBootstrap bootstrap;

    public static void main(String[] args) {
        init();
        run();
    }

    public static Server init() {
        if(server == null) {
            server = new Server();
        }
        return server;
    }

    private Server() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        try {
            SelfSignedCertificate certificate = new SelfSignedCertificate();
            context = SslContextBuilder.forServer(certificate.certificate(), certificate.privateKey()).build();
        } catch (Exception e) {

        }
        setServerBootstrap();
    }

    private static void setServerBootstrap(){

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new SslChannelInitializer(context));
    }

    private static void run(){
        try {
            bootstrap.bind(SERVER_PORT).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
