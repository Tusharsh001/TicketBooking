package com.tushar.User_service.mapper;

import com.tushar.User_service.dto.UserDTO;
import com.tushar.User_service.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user){
        if(user==null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole())
                .lastLogin(user.getLastLogin())
                .phone(user.getPhone())
                .build();
    }


}
