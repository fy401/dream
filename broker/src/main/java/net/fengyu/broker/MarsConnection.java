package net.fengyu.broker;

import io.netty.channel.Channel;

import net.fengyu.broker.security.IAuthenticator;
import net.fengyu.protocol.tcp.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author fengyu
 * @date 2023/7/3 20:20
 */
public class MarsConnection {

    private static final Logger LOG = LoggerFactory.getLogger(MarsConnection.class);

    static final boolean sessionLoopDebug = Boolean.parseBoolean(System.getProperty("moquette.session_loop.debug", "false"));

    final Channel channel;
    private final BrokerConfiguration brokerConfig;
    private final IAuthenticator authenticator;
    private final SessionRegistry sessionRegistry;
    private final PostOffice postOffice;
    private volatile boolean connected;
    private final AtomicInteger lastPacketId = new AtomicInteger(0);
    private Session bindedSession;

    MarsConnection(Channel channel, BrokerConfiguration brokerConfig, IAuthenticator authenticator,
                   SessionRegistry sessionRegistry, PostOffice postOffice) {
        this.channel = channel;
        this.brokerConfig = brokerConfig;
        this.authenticator = authenticator;
        this.sessionRegistry = sessionRegistry;
        this.postOffice = postOffice;
        this.connected = false;
    }

    void handleMessage(Message msg) {
        // TODO 写处理逻辑
        System.out.println(msg.toString());
        //MqttMessageType messageType = msg.getHeader().getCmdId();
//        LOG.debug("Received MQTT message, type: {}", messageType);
//        switch (messageType) {
//            case CONNECT:
//                processConnect((MqttConnectMessage) msg);
//                break;
//            case SUBSCRIBE:
//                processSubscribe((MqttSubscribeMessage) msg);
//                break;
//            case UNSUBSCRIBE:
//                processUnsubscribe((MqttUnsubscribeMessage) msg);
//                break;
//            case PUBLISH:
//                processPublish((MqttPublishMessage) msg);
//                break;
//            case PUBREC:
//                processPubRec(msg);
//                break;
//            case PUBCOMP:
//                processPubComp(msg);
//                break;
//            case PUBREL:
//                processPubRel(msg);
//                break;
//            case DISCONNECT:
//                processDisconnect(msg);
//                break;
//            case PUBACK:
//                processPubAck(msg);
//                break;
//            case PINGREQ:
//                MqttFixedHeader pingHeader = new MqttFixedHeader(MqttMessageType.PINGRESP, false, AT_MOST_ONCE,
//                        false, 0);
//                MqttMessage pingResp = new MqttMessage(pingHeader);
//                channel.writeAndFlush(pingResp).addListener(CLOSE_ON_FAILURE);
//                break;
//            default:
//                LOG.error("Unknown MessageType: {}", messageType);
//                break;
//        }
    }
}
