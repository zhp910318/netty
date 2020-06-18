package com.data.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Slf4j
@Component
public class NettyUdpServer {

    private final Bootstrap bootstrap;

    private final NioEventLoopGroup group;

    private Channel channel;

    public void start(InetSocketAddress address) throws InterruptedException {
        try {
            channel = bootstrap.bind(address).sync().channel();
            log.info("UdpServer start success");
            channel.closeFuture().await();
        } finally {
            group.shutdownGracefully();
        }
    }

    private static final class NettyUdpServerHolder {
        static final NettyUdpServer INSTANCE = new NettyUdpServer();
    }

    public static NettyUdpServer getInstance() {
        return NettyUdpServerHolder.INSTANCE;
    }

    private NettyUdpServer() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .option(ChannelOption.SO_RCVBUF, 1024 * 1024 * 100)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new NettyUdpServerHandler());
                    }
                });
    }

}
