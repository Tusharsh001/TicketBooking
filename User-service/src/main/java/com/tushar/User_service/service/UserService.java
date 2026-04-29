package com.tushar.User_service.service;

import com.tushar.User_service.dto.UserDTO;
import com.tushar.User_service.mapper.UserMapper;
import com.tushar.User_service.model.User;
import com.tushar.User_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO getUserByEmail(String email) throws  Exception{
        User user=userRepository.findByEmail(email);
        if(user== null){
            throw new Exception("User not found with the email ");
        }
        return UserMapper.toDTO(user);
    }

    public UserDTO getUserById(int id ) throws Exception{
        User user=userRepository.findById(id).orElseThrow(()->
           new Exception("user not found with the Id")
        );

        return UserMapper.toDTO(user);
    }

    public List<UserDTO> getAllUser(){
        List<UserDTO> users=userRepository.findAll().stream()
                .map(UserMapper:: toDTO)
                .toList();
        return users;
    }
}
