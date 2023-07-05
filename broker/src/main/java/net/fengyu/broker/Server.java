package net.fengyu.broker;


import net.fengyu.broker.config.IConfig;
import net.fengyu.broker.config.MemoryConfig;
import net.fengyu.broker.interception.InterceptHandler;
import net.fengyu.broker.security.IAuthenticator;
import net.fengyu.broker.security.IAuthorizatorPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static net.fengyu.broker.logging.LoggingUtils.getInterceptorIds;


/**
 * @author fengyu
 * @date 2023/6/30 13:59
 */
//@SpringBootApplication
public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final static String BANNER =
            "这是一个需要替换的banner";

    public static final String DREAM_VERSION = "0.1-SNAPSHOT";

    private NewNettyAcceptor acceptor;

    private volatile boolean initialized;

    /**
     * 是否单节点模式，false：是，true:否（集群模式）
     */
    private boolean standalone = false;

    static {
        System.out.println(BANNER);
    }


    public static void main(String[] args) throws IOException {

        final Server server = new Server();
        try {
            server.startStandaloneServer();
        } catch (RuntimeException e) {
            System.exit(1);
        }
        System.out.println("Server started, version " + DREAM_VERSION);
        //Bind a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(server::stopServer));
    }

    private void startStandaloneServer() throws IOException {
        this.standalone = true;
        startServer();
    }


    public void startServer() throws IOException {
//        File defaultConfigurationFile = defaultConfigFile();
//
//        acceptor = new NewNettyAcceptor();
//        //acceptor.initialize();
//        initialized = true;
    }

    public void startServer(Properties configProps) throws IOException {
        LOG.debug("Starting dream integration using properties object");
        final IConfig config = new MemoryConfig(configProps);
        startServer(config);
    }

    public void startServer(IConfig config) throws IOException {
        LOG.debug("Starting dream integration using IConfig instance");
        startServer(config, null);
    }

    public void startServer(IConfig config, List<? extends InterceptHandler> handlers) throws IOException {
        LOG.debug("Starting moquette integration using IConfig instance and intercept handlers");
        startServer(config, handlers, null, null, null);
    }

    public void startServer(IConfig config, List<? extends InterceptHandler> handlers, ISslContextCreator sslCtxCreator,
                            IAuthenticator authenticator, IAuthorizatorPolicy authorizatorPolicy) throws IOException {
        final long start = System.currentTimeMillis();
        if (handlers == null) {
            handlers = Collections.emptyList();
        }
        LOG.trace("Starting Dream Server. Mars message interceptors={}", getInterceptorIds(handlers));

        final BrokerConfiguration brokerConfig = new BrokerConfiguration(config);
        MarsConnectionFactory connectionFactory = new MarsConnectionFactory(brokerConfig, authenticator, null,
                null);

        final NettyDreamHandler dreamHandler = new NettyDreamHandler(connectionFactory);

        acceptor = new NewNettyAcceptor();
        acceptor.initialize(dreamHandler, config, sslCtxCreator, brokerConfig);

        final long startTime = System.currentTimeMillis() - start;
        LOG.info("Dream integration has been started successfully in {} ms", startTime);
    }

    private static File defaultConfigFile() {
        String configPath = System.getProperty("dream.path", null);
        return new File(configPath, IConfig.DEFAULT_CONFIG);
    }

    public void stopServer() {


        initialized = false;
    }
}
