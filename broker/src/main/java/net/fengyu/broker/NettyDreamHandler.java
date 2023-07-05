package net.fengyu.broker;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import net.fengyu.protocol.tcp.Message;
import net.fengyu.protocol.tcp.MsgHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author fengyu
 * @date 2023/6/30 19:06
 */
@ChannelHandler.Sharable
public class NettyDreamHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(NettyDreamHandler.class);

    private static final String ATTR_CONNECTION = "connection";
    private static final AttributeKey<Object> ATTR_KEY_CONNECTION = AttributeKey.valueOf(ATTR_CONNECTION);

    private MarsConnectionFactory connectionFactory;

    NettyDreamHandler(MarsConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        Message m = (Message) message;
        System.out.println("服务端收到消息：" + new String(m.getPayLoad(), Charset.defaultCharset()));

        MsgHeader msgHeader = MsgHeader.copy(m.getHeader());
        Message res = Message.generateMessage(msgHeader,"服务端发出的消息".getBytes(Charset.defaultCharset()));
        ctx.channel().writeAndFlush(res);
    }
}
