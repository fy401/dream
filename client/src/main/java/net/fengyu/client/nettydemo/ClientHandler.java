package net.fengyu.client.nettydemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import net.fengyu.protocol.tcp.Message;

import java.nio.charset.Charset;

/**
 * @author fengyu
 * @date 2023/7/4 18:50
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.err.println("客户端读取数据");
        Message m = (Message) msg;
        try {
//            ByteBuf bb = (ByteBuf)msg;
//            byte[] respByte = new byte[bb.readableBytes()];
//            bb.readBytes(respByte);
//            String respStr = new String(respByte, Charset.defaultCharset());
            System.err.println("client--收到响应：" + new String(m.getPayLoad(),Charset.defaultCharset()));

            // 直接转成对象
//          handlerObject(ctx, msg);

        } finally{
            // 必须释放msg数据
            ReferenceCountUtil.release(msg);

        }

    }

    private void handlerObject(ChannelHandlerContext ctx, Object msg) {


//        System.err.println("server 获取信息："+student.getId()+student.getName());
    }


    // 数据读取完毕的处理
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.err.println("客户端读取数据完毕");
    }

    // 出现异常的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println("client 读取数据出现异常");
        ctx.close();
    }
}
