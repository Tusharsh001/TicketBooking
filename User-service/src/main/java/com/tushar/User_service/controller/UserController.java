package com.tushar.User_service.controller;

import com.tushar.User_service.dto.UserDTO;
import com.tushar.User_service.model.User;
import com.tushar.User_service.service.UserService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(
            @RequestHeader("X-user-Email") String email) throws Exception{

        UserDTO user=userService.getUserByEmail(email);
         return ResponseEntity.ok().body(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") int id) throws Exception{
        UserDTO user =userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping()
    public  ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.ok().body(userService.getAllUser());
    }

}
