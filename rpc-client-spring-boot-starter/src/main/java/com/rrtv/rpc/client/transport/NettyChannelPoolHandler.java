package com.rrtv.rpc.client.transport;

import com.rrtv.rpc.client.handler.RpcResponseHandler;
import com.rrtv.rpc.core.codec.RpcDecoder;
import com.rrtv.rpc.core.codec.RpcEncoder;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.pool.AbstractChannelPoolHandler;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class NettyChannelPoolHandler extends AbstractChannelPoolHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void channelReleased(Channel ch) {
        ch.writeAndFlush(Unpooled.EMPTY_BUFFER);
        logger.info("Released channel.Channel ID:"+ ch.id());
    }

    @Override
    public void channelCreated(Channel ch) {
        logger.info("Create Channel. Channel ID: " + ch.id() +"Channel REAL HASH: " + System.identityHashCode(ch));
        SocketChannel channel = (SocketChannel) ch;
        channel.config().setKeepAlive(true);
        channel.config().setTcpNoDelay(true);
        channel.pipeline()
                // 解码 是入站操作 将二进制解码成消息
                .addLast(new RpcDecoder())
                // 接收响应 入站操作
                .addLast(new RpcResponseHandler())
                // 编码 是出站操作 将消息编写二进制
                .addLast(new RpcEncoder<>());
    }
}
