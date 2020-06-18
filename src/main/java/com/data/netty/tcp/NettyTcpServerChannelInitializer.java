package com.data.netty.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class NettyTcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast("decoder", new MyDecoder());
        socketChannel.pipeline().addLast("encoder", new MyDecoder());
        socketChannel.pipeline().addLast(new NettyTcpServerHandler());
    }
}
