package com.tushar.api_gateway.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateTokenResponse {
    private boolean valid;
    private Integer userId;
    private String email;
    private String roles;
    private String message;
}
