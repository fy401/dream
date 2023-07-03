package net.fengyu.protocol.tcp;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1380398756418802143L;
    /**
     * 消息头
     */
    private MsgHeader header;
    /**
     * 消息体
     */
    private byte[] payLoad;

    public static Message create(MsgHeader header, byte[] body) {
        return new Message(header, body);
    }

    private Message() {
    }

    private Message(MsgHeader header, byte[] payLoad) {
        this.header = header;
        this.payLoad = payLoad;

        if (payLoad != null) {
            this.header.setBodyLength(payLoad.length);
        } else {
            this.header.setBodyLength(0);
        }
    }

    /**
     * 从byte数组中，解析出消息内容
     *
     * @param msg
     * @return
     */
    public static Message streamToMessage(byte[] msg) {

        if (msg == null || msg.length <= 0) {
            throw new IllegalArgumentException("msg byte array is null");
        }

        MsgHeader _header = MsgHeader.newMsgHeader();
        _header.decode(msg);

        if (_header.getBodyLength() + MsgHeader.HEADER_LENGTH != msg.length) {
            throw new IllegalArgumentException("wrong body length");
        }

        byte[] _body = new byte[_header.getBodyLength()];

        System.arraycopy(msg, MsgHeader.HEADER_LENGTH, _body, 0, _header.getBodyLength());

        return new Message(_header, _body);
    }


    /**
     * 得到消息的byte[]格式数据
     *
     * @return
     */
    public byte[] toByteArray() {
        return BitConverter.concat(header.encode(), this.payLoad);
    }

    public MsgHeader getHeader() {
        return header;
    }

    public void setHeader(MsgHeader header) {
        this.header = header;
    }

    public byte[] getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(byte[] payLoad) {
        this.payLoad = payLoad;
        if (payLoad != null) {//修正header中bodyLength的值
            this.header.setBodyLength(payLoad.length);
        } else {
            this.header.setBodyLength(0);
        }
    }

}
