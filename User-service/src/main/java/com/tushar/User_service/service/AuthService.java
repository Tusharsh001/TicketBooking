package com.tushar.User_service.service;

import com.tushar.User_service.dto.AuthResponse;
import com.tushar.User_service.dto.LoginRequest;
import com.tushar.User_service.dto.UserDTO;
import com.tushar.User_service.mapper.UserMapper;
import com.tushar.User_service.model.User;
import com.tushar.User_service.model.UserRole;
import com.tushar.User_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private  final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JWTProvider jwtProvider;
    private final CustomUserDetailService userDetailService;

    /*
    1. check if the email already exists or not
    2.Encode the password using the BCrypt
    3.save user in db
    4.Generate the JWT Token
    5.Return token and user information
     */
    public AuthResponse signp(UserDTO request) throws  Exception{
        User existingUser=userRepository.findByEmail(request.getEmail());
        if(existingUser!=null){
            throw new Exception("User already exists");
        }
        if(request.getRole()== UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("You cannot singup as system admin");
        }

        User user=User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(request.getRole())
                .fullName(request.getFullName())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        User savedUser =userRepository.save(user);

        Authentication authentication= new UsernamePasswordAuthenticationToken(savedUser.getEmail()
        ,savedUser.getPassword());
        String jwt=jwtProvider.generateToken(authentication,savedUser.getId());
        AuthResponse response=new AuthResponse();
        response.setJwt(jwt);
        response.setTitle("SIGNUP");
        response.setUser(UserMapper.toDTO(savedUser));
        response.setMessage("Registered SuccessFully");

        return response;

    }

    /*
    1.load user by email
    2.compare password with BCrypt
    3.update last login
    4.Generate JWT Token
    5.Return token and user information
     */
    public AuthResponse login(LoginRequest request) throws Exception{
         String email=request.getEmail();
         String password=request.getPassword();
        Authentication authentication =authenticate(email,password);

        User user=userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        String jwt=jwtProvider.generateToken(authentication,user.getId());
        AuthResponse response=new AuthResponse();
        response.setJwt(jwt);
        response.setTitle("Login! ");
        response.setUser(UserMapper.toDTO(user));
        response.setMessage("login SuccessFully");
        return  response;
    }

    private Authentication authenticate(String email,String password) throws Exception {
        UserDetails userDetails =userDetailService.loadUserByUsername(email);

        if(!encoder.matches(password,userDetails.getPassword())){
            throw new Exception("invalid password or username");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,
                null ,
                userDetails.getAuthorities());
    }


}
