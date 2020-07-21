package com.example.demo;

import java.time.Duration;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Flux;
import reactor.netty.udp.UdpServer;

@SpringBootApplication
public class PockerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PockerServerApplication.class, args);
	}
	// 在Spring BOOT启动类中添加UDP Server，
	// CommandLineRunner是Spring BOOT项目启动时会执行的任务，
	// 及在Spring BOOT启动时创建UDP Server
	@Bean
	CommandLineRunner serverRunner(UdpDecoderHandler udpDecoderHandler, 
			UdpEncoderHandler udpEncoderHandler,
			UdpHandler udpHandler) {
		return strings -> {
			createUdpServer(udpDecoderHandler, udpEncoderHandler, udpHandler);
		};
	}
	private void createUdpServer(UdpDecoderHandler udpDecoderHandler, 
			UdpEncoderHandler udpEncoderHandler,
			UdpHandler udpHandler) {
		UdpServer.create().handle((in, out) ->{
			in.receive().asByteArray().subscribe();
			return Flux.never();
		})
		.port(3789)// udpserver 端口
		.doOnBound(conn -> conn.addHandler("decoder", udpDecoderHandler)
				.addHandler("encoder", udpEncoderHandler)
				.addHandler("handler", udpHandler))
		.bindNow(Duration.ofSeconds(30));
	}

}
