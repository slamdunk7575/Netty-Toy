package me.toy.netty;

import me.toy.netty.telnet.config.TelnetServerConfig;
import me.toy.netty.telnet.server.TelnetServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        AbstractApplicationContext springContext = null;

        try {
            springContext = new AnnotationConfigApplicationContext(TelnetServerConfig.class);
            springContext.registerShutdownHook();

            TelnetServer telnetServer = springContext.getBean(TelnetServer.class);
            telnetServer.start();
        } finally {
            springContext.close();
        }
    }

}
