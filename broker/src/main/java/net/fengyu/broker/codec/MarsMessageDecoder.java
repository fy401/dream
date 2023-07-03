package net.fengyu.broker.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.fengyu.protocol.tcp.Message;

import java.util.List;

/**
 * @author fengyu
 * @date 2023/7/3 10:36
 */
@ChannelHandler.Sharable
public class MarsMessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    public static final MarsMessageDecoder INSTANCE = new MarsMessageDecoder();
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        try {
            Message message = Message.streamToMessage(byteBuf.array());
            out.add(message);
        } catch (Exception e) {
            throw e;
        }

    }
}
