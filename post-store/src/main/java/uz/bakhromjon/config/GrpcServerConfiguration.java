package uz.bakhromjon.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.bakhromjon.post.PostServiceGrpcImpl;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class GrpcServerConfiguration {
    private final PostServiceGrpcImpl postServiceGrpc;
    @Bean
    public Server grpcServer() throws IOException {
        Server server = ServerBuilder.forPort(9000)
                .addService(postServiceGrpc)
                .build();
        server.start();
        return server;
    }
}