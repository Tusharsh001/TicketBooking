package com.tushar.api_gateway.client;


import com.tushar.api_gateway.dto.ValidateTokenRequest;
import com.tushar.api_gateway.dto.ValidateTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserServiceClient {
    @PostMapping("/auth/validate")
    ValidateTokenResponse validateToken(@RequestBody ValidateTokenRequest request);
}
