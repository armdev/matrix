package io.project.app.producer;

import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class RSocketClientConfiguration {
//
//    @Bean
//    public RSocket rSocket() {
//        
//
//        RSocket socket
//                = RSocketConnector.connectWith(TcpClientTransport.create("producer", 2050)).block();
//
//        return socket;
//    }

//    @Bean
//    RSocketRequester internalRequester(RSocketRequester.Builder builder) {
//       return builder
//                .dataMimeType(MediaType.APPLICATION_CBOR)
//                .tcp("producer", 2050);
//    
//    }

}
//https://github.com/rsocket/rsocket-java/blob/master/rsocket-examples/src/main/java/io/rsocket/examples/transport/tcp/channel/ChannelEchoClient.java\
//https://www.codota.com/code/java/classes/io.rsocket.RSocketFactory
//https://docs.spring.io/spring-integration/docs/current/reference/html/rsocket.html#rsocket
