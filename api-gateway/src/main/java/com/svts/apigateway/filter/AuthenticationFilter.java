package com.svts.apigateway.filter;

import com.svts.apigateway.exception.UnauthorizedException;
import com.svts.apigateway.util.JwtUtil;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;

    private final MultivaluedMap<String, String> requestPathControlMap;

    public AuthenticationFilter() {
        super(Config.class);
        this.requestPathControlMap = new MultivaluedHashMap<>();

        requestPathControlMap.addAll("/appointment/appointmentMakingProcess","ROLE_PATIENT","ROLE_ADMIN");
        requestPathControlMap.addAll("/patient/getAllActiveAppointments","ROLE_PATIENT","ROLE_ADMIN");
        requestPathControlMap.addAll("/appointment/appointmentRemove","ROLE_PATIENT","ROLE_ADMIN");

    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                String methodName = new Object() {}.getClass().getEnclosingMethod().getName();

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return Mono.error(new UnauthorizedException("Missing authorization header"));
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authHeader);
                } catch (Exception exception) {
                    return Mono.error(new UnauthorizedException("Unauthorized Access to the Application"));
                }

                var identityNumber = jwtUtil.getIdentityNumberFromToken(authHeader);
                List<String> roles = jwtUtil.getRolesFromToken(authHeader);

                boolean matchStatus = false;

                for (String role : roles) {
                    List<String> key = requestPathControlMap.get(exchange.getRequest().getPath().toString());
                    if (key.contains(role)) {
                        matchStatus = true;
                        break;
                    }
                }

                if (!matchStatus) {
                    return Mono.error(new UnauthorizedException("Role authorization error"));
                }

                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("identityNumber", identityNumber)
                        .build();

                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(modifiedRequest)
                        .build();

                return chain.filter(modifiedExchange);

            }

            return chain.filter(exchange);
        };


    }


    public static class Config {

    }


}




