package io.github.ygsama.echo_demo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * ChannelHandler 负责接收并响应事件通知
 * 一个ChannelHandler可以被多个Channel共享
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * write()操作是异步的，直到 channelRead()方法返回后可能仍然没有完成
     * EchoServerHandler扩展了 ChannelInboundHandlerAdapter，其在这个时间点上不会释放消息。
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        // 将接收到的消息 写给发送者
        ctx.write(in);
    }


    /**
     * 消息在 writeAndFlush()方法被调用时被释放
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 重写 exceptionCaught()方法,对 Throwable 的任何子类型做出反应
     * 每个 Channel 都拥有一个关联的 ChannelPipeline，内部有一个 ChannelHandler 的实例链
     * 在默认的情况下，ChannelHandler 会把对它的方法的调用转发给链中的下一个 Channel-Handler。
     * 如果 exceptionCaught()方法没有被该链中的某处实现，那么所接收的异常将会被
     * 传递到 ChannelPipeline 的尾端并被记录。
     * 所以，你的应用程序应该提供至少有一个实现了exceptionCaught()方法的 ChannelHandler
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
