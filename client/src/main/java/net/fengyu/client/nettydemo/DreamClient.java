package net.fengyu.client.nettydemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.fengyu.protocol.protobuf.MessageCmd;
import net.fengyu.protocol.tcp.Message;
import net.fengyu.protocol.tcp.MessageHelper;
import net.fengyu.protocol.tcp.MsgHeader;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @author fengyu
 * @date 2023/7/4 17:56
 */
public class DreamClient {

    // 要请求的服务器的ip地址
    private String ip;
    // 服务器的端口
    private int port;

    public DreamClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    // 请求端主题
    public void action() throws InterruptedException, UnsupportedEncodingException {

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        Bootstrap bs = new Bootstrap();

        bs.group(bossGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // marshalling 序列化对象的解码
//                  socketChannel.pipeline().addLast(MarshallingCodefactory.buildDecoder());
                        // marshalling 序列化对象的编码
//                  socketChannel.pipeline().addLast(MarshallingCodefactory.buildEncoder());

                        // 处理来自服务端的响应信息
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });

        // 客户端开启
        ChannelFuture cf = bs.connect(ip, port).sync();

        String reqStr = "我是客户端请求1$_";
        MsgHeader msgHeader = MessageHelper.generateMsgHeader(123, MessageCmd.CmdID.CMD_ID_AUTH);
        Message message = MessageHelper.generateMessage(msgHeader,reqStr.getBytes(Charset.defaultCharset()));





        // 发送客户端的请求
        cf.channel().writeAndFlush(Unpooled.copiedBuffer(message.toByteArray()));
//      Thread.sleep(300);
//      cf.channel().writeAndFlush(Unpooled.copiedBuffer("我是客户端请求2$_---".getBytes(Constant.charset)));
//      Thread.sleep(300);
//      cf.channel().writeAndFlush(Unpooled.copiedBuffer("我是客户端请求3$_".getBytes(Constant.charset)));

//      Student student = new Student();
//      student.setId(3);
//      student.setName("张三");
//      cf.channel().writeAndFlush(student);

        // 等待直到连接中断
        cf.channel().closeFuture().sync();
    }

//    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
//        new DreamClient("127.0.0.1", Constant.serverSocketPort).action();
//    }
}
