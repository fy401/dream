package net.fengyu.broker.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.fengyu.protocol.tcp.Message;

import java.util.List;

/**
 * @author fengyu
 * @date 2023/7/3 10:50
 */
@ChannelHandler.Sharable
public class MarsMessageEncoder extends MessageToMessageEncoder<Message> {
    public static final MarsMessageEncoder INSTANCE = new MarsMessageEncoder();
    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, List<Object> out) throws Exception {
        try {
            byte[] msg = message.toByteArray();
            ByteBuf buf = ctx.alloc().buffer(msg.length);
            buf.writeBytes(msg);
        } catch (Exception e) {
            throw e;
        }
    }
}
