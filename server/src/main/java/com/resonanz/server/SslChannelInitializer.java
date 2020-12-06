package com.resonanz.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;

    public SslChannelInitializer(SslContext context) {
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        SSLEngine engine = context.newEngine(ch.alloc());
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                new SslHandler(engine, true),
                new StringDecoder(),
                new StringEncoder());
    }
}
