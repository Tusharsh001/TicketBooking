package com.tushar.User_service.dto;

import com.tushar.User_service.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private UserRole role;
    private LocalDateTime lastLogin;

}
