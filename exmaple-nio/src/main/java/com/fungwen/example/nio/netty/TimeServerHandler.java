package com.fungwen.example.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {


    private final static String BAD_ORDER = "BAD ORDER";
    private final static String ORDER = "QUERY TIME ORDER";
    private final static String CHARSET = "UTF-8";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] request = new byte[buf.readableBytes()];
        buf.readBytes(request);
        String body = new String(request, CHARSET);
        System.out.println("Time server receive the order:" + body);
        String responeString = ORDER.equalsIgnoreCase(body)? new Date().toString() : BAD_ORDER;
        ByteBuf response = Unpooled.copiedBuffer(responeString.getBytes());
        ctx.write(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        ctx.close();
    }

}
