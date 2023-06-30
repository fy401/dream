package net.fengyu.broker;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.IOException;


/**
 * @author fengyu
 * @date 2023/6/30 13:59
 */
public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final static String BANNER =
            "这是一个需要替换的banner";


    private volatile boolean initialized;

    static {
        System.out.println(BANNER);
    }

    public void startServer() throws IOException {

        initialized = true;
    }


    public void stopServer() {


        initialized = false;
    }
}
