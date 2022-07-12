package com.example.paytwitch.services;

import com.example.paytwitch.data.dtos.request.UserRequestLogInDto;
import com.example.paytwitch.data.models.AccessToken;
import com.example.paytwitch.data.models.User;
import com.example.paytwitch.exception.UserNotFoundException;
import com.example.paytwitch.security.jwt.TokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsService userDetailsService;

@Autowired
    TokenServices tokenService;

//    public LoginServiceImpl(TokenService tokenService){
//       this.tokenService= tokenService;
//    }
    @Override
    public AccessToken login(UserRequestLogInDto loginRequest) throws UserNotFoundException {
        User user = userService.findUserByEmail(loginRequest.getEmail());

        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = tokenService.generateToken(userDetails);
            return new AccessToken(token);
        }
        throw new UserNotFoundException("invalid Password");
    }
}
