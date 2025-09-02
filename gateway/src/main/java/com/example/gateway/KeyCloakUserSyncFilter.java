package com.example.gateway;

import com.example.gateway.user.RegisterRequest;
import com.example.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyCloakUserSyncFilter implements WebFilter {
    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain){
        String token = exchange.getRequest().getHeaders().getFirst("Authoriztion");
        String userID = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        RegisterRequest registerRequest = getUserDetails(token);

        if(userID == null){
            userID = registerRequest.getKeyCloakID();
        }

        if(userID != null && token != null){
            String finalUserID = userID;
            return userService.validate(userID)
                    .flatMap(exist ->{
                        if(!exist){
                            // Register user

                            if(registerRequest != null){
                                return userService.registerUser(registerRequest).then(Mono.empty());
                            }
                            else{
                                return Mono.empty();
                            }

                        } else{
                            log.info("User {} already exist", finalUserID);
                            return Mono.empty();
                        }

                    })
                    .then(Mono.defer(()->{
                        ServerHttpRequest request = exchange.getRequest().mutate().header("X-User-ID",userID).build();

                        return chain.filter(exchange.mutate().request(request).build());
                    }));
        }
        return chain.filter(exchange);
    }

    private RegisterRequest getUserDetails(String token) {
        try{
            String tokenWithoutBearer = token.replace("Bearer ", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.setEmail(claims.getStringClaim("email"));
            registerRequest.setKeyCloakID(claims.getStringClaim("sub"));
            registerRequest.setPassword("dummy@123123");
            registerRequest.setFirstName(claims.getStringClaim("given_name"));
            registerRequest.setLastName(claims.getStringClaim("family_name"));
            return registerRequest;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }


}
