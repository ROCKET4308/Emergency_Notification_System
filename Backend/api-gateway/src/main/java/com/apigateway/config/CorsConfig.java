package com.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CorsConfig {

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            ServerHttpResponse response = ctx.getResponse();

            HttpHeaders headers = response.getHeaders();
            headers.add("Access-Control-Allow-Origin", request.getHeaders().getOrigin());
            headers.add("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Authorization, Content-Type");
            headers.add("Access-Control-Max-Age", "3600");

            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }

            return chain.filter(ctx);
        };
    }
}

