package com.finance.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.dto.LoginRequest;
import com.finance.dto.RefreshTokenRequest;
import com.finance.dto.RefreshTokenResponse;
import com.finance.dto.RegisterRequest;
import com.finance.model.RefreshToken;
import com.finance.model.User;
import com.finance.repository.UserRepository;
import com.finance.service.RefreshTokenService;
import com.finance.serviceImpl.UserServiceImpl;
import com.finance.utils.JwtUtils;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private  UserServiceImpl userServiceImpl;  
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest user) {

        return ResponseEntity.ok().body(userServiceImpl.registerUser(user));
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userServiceImpl.login(loginRequest);
        
        String token = jwtUtils.generateToken(user.getEmail(),user.getRole());
        
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
        
        return ResponseEntity.ok(Map.of(
        		                     "token",token,
        		                     "refreshToken",refreshToken,
        		                     "role",user.getRole()));
    }
   
    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request){
    	 RefreshToken refreshToken = refreshTokenService.verifyExpiration(request.getRefreshToken());
    	 
    	 User user = userRepository.findByEmail(refreshToken.getUserEmail())
    			 .orElseThrow(() -> new RuntimeException("User not found "));
    	 
    	 String newAccessToken = jwtUtils.generateToken(user.getEmail(), user.getRole());
    	 
    	 RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getEmail());
    	 
    	 return new RefreshTokenResponse(newAccessToken, newRefreshToken.getToken());
    	 
    	
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshToken){
    	RefreshToken verifyExpiration = refreshTokenService.verifyExpiration(refreshToken.getRefreshToken());
    	refreshTokenService.deleteByUserEmail(verifyExpiration.getUserEmail());
    	return ResponseEntity.ok("Logged Out Successfully!!");
    		
    }
    

}
