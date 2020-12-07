package com.resonanz.client.handler;

import com.resonanz.client.Callback;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleHandler extends SimpleChannelInboundHandler<String> {
    private Callback onMessageReceived;

    public SimpleHandler(Callback onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        if(onMessageReceived != null) {
            onMessageReceived.callback(s);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
