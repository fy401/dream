/*
 * Copyright (c) 2012-2018 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package net.fengyu.broker.metrics;


import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.fengyu.broker.NettyUtils;
import net.fengyu.protocol.tcp.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static io.netty.channel.ChannelFutureListener.CLOSE_ON_FAILURE;

/**
 * 输出日志
 */
@Sharable
public class MarsMessageLogger extends ChannelDuplexHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MarsMessageLogger.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        updateFishtags(ctx);
        logMarsMessageRead(ctx, message);
        ctx.fireChannelRead(message);
        MDC.clear();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        updateFishtags(ctx);
        ctx.fireChannelReadComplete();
        MDC.clear();
    }

    private void updateFishtags(ChannelHandlerContext ctx) {
        MDC.put("channel", ctx.channel().toString());
        final String clientId = NettyUtils.clientID(ctx.channel());
        if (clientId != null && !clientId.isEmpty()) {
            MDC.put("client.id", "[" + clientId + "]");
        }
    }

    private void logMarsMessageRead(ChannelHandlerContext ctx, Object message) throws Exception {
        logMarsMessage(ctx, message, "C->B");
    }

    private void logMarsMessageWrite(ChannelHandlerContext ctx, Object message) throws Exception {
        logMarsMessage(ctx, message, "C<-B");
    }

    private void logMarsMessage(ChannelHandlerContext ctx, Object message, String direction) throws Exception {
        if (!(message instanceof Message)) {
            return;
        }
        Message msg = NettyUtils.validateMessage(message);
        String clientID = NettyUtils.clientID(ctx.channel());
        int messageType = msg.getHeader().getCmdId();
        MDC.put("msg.type", "[" + messageType + "]");
//        switch (messageType) {
//            case CONNACK:
//            case PINGREQ:
//            case PINGRESP:
//                LOG.debug("{} {} <{}>", direction, messageType, clientID);
//                break;
//            case CONNECT:
//            case DISCONNECT:
//                LOG.info("{} {} <{}>", direction, messageType, clientID);
//                break;
//            case SUBSCRIBE:
//                MqttSubscribeMessage subscribe = (MqttSubscribeMessage) msg;
//                LOG.info("{} SUBSCRIBE <{}> to topics {}", direction, clientID,
//                    subscribe.payload().topicSubscriptions());
//                break;
//            case UNSUBSCRIBE:
//                MqttUnsubscribeMessage unsubscribe = (MqttUnsubscribeMessage) msg;
//                LOG.info("{} UNSUBSCRIBE <{}> to topics <{}>", direction, clientID, unsubscribe.payload().topics());
//                break;
//            case PUBLISH:
//                MqttPublishMessage publish = (MqttPublishMessage) msg;
//                LOG.debug("{} PUBLISH <{}> to topics <{}>", direction, clientID, publish.variableHeader().topicName());
//                break;
//            case PUBREC:
//            case PUBCOMP:
//            case PUBREL:
//            case PUBACK:
//            case UNSUBACK:
//                LOG.info("{} {} <{}> packetID <{}>", direction, messageType, clientID, messageId(msg));
//                break;
//            case SUBACK:
//                MqttSubAckMessage suback = (MqttSubAckMessage) msg;
//                final List<Integer> grantedQoSLevels = suback.payload().grantedQoSLevels();
//                LOG.info("{} SUBACK <{}> packetID <{}>, grantedQoses {}", direction, clientID, messageId(msg),
//                    grantedQoSLevels);
//                break;
//        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        updateFishtags(ctx);
        LOG.info("Channel Inactive");
        ctx.fireChannelInactive();
        MDC.clear();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        updateFishtags(ctx);
        logMarsMessageWrite(ctx, msg);
        ctx.write(msg, promise).addListener(CLOSE_ON_FAILURE);
        MDC.clear();
    }
}
