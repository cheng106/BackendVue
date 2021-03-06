package com.mark.cheng;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.InetAddress;

@Slf4j
@EnableWebMvc
@SpringBootApplication
public class ChengApplication implements ApplicationListener<WebServerInitializedEvent> {

    public static String contextFullPath = null;

    public static void main(String[] args) {
        SpringApplication.run(ChengApplication.class, args);
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        WebServer server = event.getWebServer();
        WebServerApplicationContext context = event.getApplicationContext();
        Environment env = context.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        int port = server.getPort();
        String contextPath = env.getProperty("server.servlet.context-path");
        if (contextPath == null) {
            contextPath = "";
        }
        contextFullPath = String.format("http://%s:%s%s", ip, port, contextPath);
        log.info("\n---------------------------------------------------------\n" +
                "\tApplication is running! Access address:\n" +
                "\tLocal:\t\thttp://localhost:{}" +
                "\n\tExternal:\t{}" +
                "\n---------------------------------------------------------\n", port, contextFullPath);
    }
}
