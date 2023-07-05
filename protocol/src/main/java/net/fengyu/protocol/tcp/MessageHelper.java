package net.fengyu.protocol.tcp;

import net.fengyu.protocol.protobuf.MessageCmd;

/**
 * @author fengyu
 * @date 2023/7/5 17:35
 */
public class MessageHelper {


    public static MsgHeader generateMsgHeader(int seq, MessageCmd.CmdID cmdID) {
        return generateMsgHeader(0, seq, cmdID);
    }

    public static MsgHeader generateMsgHeader(int payLoadLength, int seq, MessageCmd.CmdID cmdID) {
        int clientVersion = 1;
        MsgHeader msgHeader = MsgHeader.newMsgHeader();
        msgHeader.setHeadLength(MsgHeader.HEADER_LENGTH);
        msgHeader.setClientVersion(clientVersion);
        msgHeader.setCmdId(cmdID.getNumber());
        msgHeader.setSeq(seq);
        msgHeader.setBodyLength(payLoadLength);
        return msgHeader;
    }

    public static Message generateMessage(MsgHeader header, byte[] payLoad) {
        return Message.create(header, payLoad);
    }
}
