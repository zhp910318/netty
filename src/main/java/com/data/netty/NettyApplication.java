package com.data.netty;

import com.data.netty.tcp.NettyTcpServer;
import com.data.netty.udp.NettyUdpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@Slf4j
@SpringBootApplication
public class NettyApplication implements CommandLineRunner {
    @Autowired
    NettyTcpServer nettyTcpServer;
    @Autowired
    NettyUdpServer nettyUdpServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyApplication.class, args);
    }

    /**
     * 启动netty 服务
     * tcp和udp同时只能启动一个
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //启动TCP服务
        //InetSocketAddress tcpAddress = new InetSocketAddress(DefaultConstants.SOCKET_IP,DefaultConstants.TCP_SOCKET_PORT);
        //nettyTcpServer.start(tcpAddress);
        //启动udp服务
        InetSocketAddress udpAddress = new InetSocketAddress(DefaultConstants.SOCKET_IP, DefaultConstants.UDP_SOCKET_PORT);
        nettyUdpServer.start(udpAddress);
    }
}
