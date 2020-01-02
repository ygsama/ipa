package io.github.ygsama.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * 引导服务器
 * 绑定到服务器将在其上监听并接受传入连接请求的端口；
 * 配置 Channel ，以将有关的入站消息通知给 EchoServerHandler 实例
 */
public class EchoServer {

    private final static int PORT = 8787;


    public static void main(String[] args) throws Exception {
        new EchoServer().start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(PORT))
                    // 当一个新的连接被接受时，一个新的子Channel 将会被创建，
                    // ChannelInitializer 将会把一个EchoServerHandler 的实例添加到该 Channel 的 ChannelPipeline 中。
                    // 这个 ChannelHandler 将会收到有关入站消息的通知。
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            // 对 sync()方法的调用将导致当前 Thread阻塞，一直到绑定操作完成为止
            ChannelFuture f = b.bind().sync();
            // 阻塞等待直到服务器的 Channel 关闭
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}

