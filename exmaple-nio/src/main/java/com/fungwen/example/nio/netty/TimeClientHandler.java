package com.fungwen.example.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter{

    private final static String ORDER = "QUERY TIME ORDER";
    private final static String CHARSET = "UTF-8";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] order = ORDER.getBytes();
        ByteBuf request = Unpooled.buffer(order.length);
        request.writeBytes(order);
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] response = new byte[buf.readableBytes()];
        buf.readBytes(response);
        String body = new String(response, CHARSET);
        System.out.println("Now is :" + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
