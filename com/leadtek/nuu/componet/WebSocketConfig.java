package com.leadtek.nuu.componet;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 註冊一個Stomp 協議的endpoint指定URL為myWebSocket,並用.withSockJS()指定
		// SockJS協議。.setAllowedOrigins("*")設定跨域
		registry.addEndpoint("/api/etlcontroller").setAllowedOrigins("*").withSockJS();
		registry.addEndpoint("/api/etlcontroller/dataclean/schoolsynoym").setAllowedOrigins("*").withSockJS();
		registry.addEndpoint("/api/etlcontroller/dataclean/StudentScore").setAllowedOrigins("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 配置訊息代理(message broker)
		// 將訊息傳回給以‘/topic’開頭的客戶端
		config.enableSimpleBroker("/topic");
	}
}
