package net.fengyu.protocol.tcp;

import java.io.Serializable;


public class MsgHeader implements Serializable {

    /**
     * 包头定长20byte
     */
    public static final int HEADER_LENGTH = 20;

    private int headLength;
    private int clientVersion;
    private int cmdId;
    private int seq;
    private int bodyLength;

    private MsgHeader() {
    }

    /**
     * 构造函数
     *
     * @return
     */
    public static MsgHeader newMsgHeader() {
        return new MsgHeader();
    }

    public static MsgHeader newMsgHeader(MsgHeader header) {
        MsgHeader _header = new MsgHeader();
        _header.setHeadLength(header.getHeadLength());
        _header.setClientVersion(header.getClientVersion());
        _header.setCmdId(header.getCmdId());
        _header.setSeq(header.getSeq());
        _header.setBodyLength(header.getBodyLength());
        return _header;
    }

    /**
     * 解析消息流，得到消息头内容
     *
     * @param msg
     * @return
     */
    public boolean decode(byte[] msg) {
        if (msg == null || msg.length <= 0) {
            throw new IllegalArgumentException("msg byte array is null");
        }

        if (msg.length < HEADER_LENGTH) {
            throw new IllegalArgumentException("msg length is less than header length : " + msg.length);
        }

        int offset = 0;
        this.headLength = BitConverter.byteArrayToInt(msg, offset);

        offset += 4; // Integer.SIZE / Byte.SIZE;
        this.clientVersion = BitConverter.byteArrayToInt(msg, offset);

        offset += 4; // Integer.SIZE / Byte.SIZE;
        this.cmdId = BitConverter.byteArrayToInt(msg, offset);

        offset += 4; // Integer.SIZE / Byte.SIZE;
        this.seq = BitConverter.byteArrayToInt(msg, offset);

        offset += 4; // Integer.SIZE / Byte.SIZE;
        this.bodyLength = BitConverter.byteArrayToInt(msg, offset);

        return true;
    }

    public byte[] encode() {
        byte[] headLength_bytes = BitConverter.intToByteArray(this.headLength);
        byte[] clientVersion_bytes = BitConverter.intToByteArray(this.clientVersion);
        byte[] cmdId_bytes = BitConverter.intToByteArray(this.cmdId);
        byte[] seq_bytes = BitConverter.intToByteArray(this.seq);
        byte[] bodyLength_bytes = BitConverter.intToByteArray(this.bodyLength);

        return BitConverter.concat(headLength_bytes, clientVersion_bytes, cmdId_bytes, seq_bytes, bodyLength_bytes);
    }

    public int getHeadLength() {
        return headLength;
    }

    public void setHeadLength(int headLength) {
        this.headLength = headLength;
    }

    public int getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(int clientVersion) {
        this.clientVersion = clientVersion;
    }

    public int getCmdId() {
        return cmdId;
    }

    public void setCmdId(int cmdId) {
        this.cmdId = cmdId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MsgHeader that = (MsgHeader) o;
        if (this.headLength == that.headLength && this.clientVersion == that.clientVersion && this.seq == that.seq
                && this.cmdId == that.cmdId && this.bodyLength == that.bodyLength) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MsgHeader[headLength=").append(this.headLength).append(",clientVersion=").append(this.clientVersion);
        sb.append(",cmdId=").append(this.cmdId).append(",seq=").append(this.seq).append(",bodyLength=").append(this.bodyLength).append("]");

        return sb.toString();
    }
}
