package net.fengyu.broker;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.channel.ChannelFutureListener.CLOSE_ON_FAILURE;

/**
 * @author fengyu
 * @date 2023/6/30 17:13
 */
public class DreamIdleTimeoutHandler extends ChannelDuplexHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DreamIdleTimeoutHandler.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState e = ((IdleStateEvent) evt).state();
            if (e == IdleState.READER_IDLE) {
                LOG.info("Firing channel inactive event. MqttClientId = {}.", NettyUtils.clientID(ctx.channel()));
                // fire a close that then fire channelInactive to trigger publish of Will
                ctx.close().addListener(CLOSE_ON_FAILURE);
            }
        } else {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Firing Netty event CId = {}, eventClass = {}", NettyUtils.clientID(ctx.channel()),
                        evt.getClass().getName());
            }
            super.userEventTriggered(ctx, evt);
        }
    }
}
