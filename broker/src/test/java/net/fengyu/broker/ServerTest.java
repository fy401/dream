package net.fengyu.broker;

import net.fengyu.client.nettydemo.DreamClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

/**
 * @author fengyu
 * @date 2023/6/30 14:04
 */
public class ServerTest {
    @Test
    public void testServer() throws IOException, InterruptedException {

        String file = getClass().getResource("/").getPath();
        System.setProperty("dream.path", file);
        Server m_server = new Server();

        Properties plainProps = new Properties();
        plainProps.put(BrokerConstants.PORT_PROPERTY_NAME, "8089");
        plainProps.put(BrokerConstants.HOST_PROPERTY_NAME, "127.0.0.1");
        plainProps.put(BrokerConstants.JKS_PATH_PROPERTY_NAME, "src/test/resources/serverkeystore.jks");
        plainProps.put(BrokerConstants.KEY_STORE_PASSWORD_PROPERTY_NAME, "passw0rdsrv");
        plainProps.put(BrokerConstants.KEY_MANAGER_PASSWORD_PROPERTY_NAME, "passw0rdsrv");
//        plainProps.put(BrokerConstants.DATA_PATH_PROPERTY_NAME, dbPath);
        plainProps.put(BrokerConstants.PERSISTENCE_ENABLED_PROPERTY_NAME, "true");

        m_server.startServer(plainProps);
//
//        System.out.println(plainProps.getProperty(BrokerConstants.HOST_PROPERTY_NAME));
//        String host = plainProps.getProperty(BrokerConstants.HOST_PROPERTY_NAME);
//        int port = Integer.parseInt(plainProps.getProperty(BrokerConstants.PORT_PROPERTY_NAME));
        new DreamClient(plainProps.getProperty(BrokerConstants.HOST_PROPERTY_NAME),
                Integer.parseInt(String.valueOf(plainProps.getProperty(BrokerConstants.PORT_PROPERTY_NAME)))).action();


        Thread.sleep(2000L);
    }
}
