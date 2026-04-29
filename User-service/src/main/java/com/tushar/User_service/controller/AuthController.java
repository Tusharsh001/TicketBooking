package com.tushar.User_service.controller;

import com.tushar.User_service.dto.AuthResponse;
import com.tushar.User_service.dto.LoginRequest;
import com.tushar.User_service.dto.UserDTO;
import com.tushar.User_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody @Valid UserDTO request) throws  Exception{
        return  new ResponseEntity<>(authService.signp(request), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    throws Exception{
            return ResponseEntity.ok().body(authService.login(request));
    }
}
