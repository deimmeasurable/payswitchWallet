package com.example.paytwitch.services;

import com.example.paytwitch.data.dtos.request.UserRequest;
import com.example.paytwitch.data.dtos.request.UserUpdateRequest;
import com.example.paytwitch.data.dtos.request.WalletRequest;
import com.example.paytwitch.data.dtos.response.UserResponse;
import com.example.paytwitch.data.dtos.response.UserUpdateResponse;
import com.example.paytwitch.data.models.Role;
import com.example.paytwitch.data.models.RoleType;
import com.example.paytwitch.data.models.User;
import com.example.paytwitch.data.respository.UserRepository;
import com.example.paytwitch.exception.UserAlreadyExistsException;
import com.example.paytwitch.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletService walletService;


    @Override
    public UserResponse createUser(UserRequest form) {


        User newUser = new User();
        newUser.setEmail(form.getEmail());
        if (checkIfEmailAlreadyExists(form.getEmail())) throw new UserAlreadyExistsException("email already exist");
        newUser.setPassword(form.getPassword());
        newUser.setFirstName(form.getFirstName());
        newUser.setLastName(form.getLastName());
        newUser.getCountry().add("Nigeria");

        WalletRequest newRequest = new WalletRequest();
        newRequest.setUserName(form.getEmail());
        walletService.createWallet(newRequest);
        userRepository.save(newUser);
        System.out.println(newUser);
        System.out.println(newRequest);


        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(newUser.getEmail());
        userResponse.setPassword(newUser.getPassword());
        userResponse.setFirstName(newUser.getFirstName());
        userResponse.setLastName(newUser.getLastName());
        userResponse.setCountry(newUser.getCountry());


        return userResponse;
    }

    @Override
    public UserUpdateResponse userCanUpdateDetails(UserUpdateRequest userUpdate) {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findUserByEmail(userUpdate.getEmail()).orElseThrow(() -> new UserNotFoundException("user don't exist")));


        if (foundUser.isPresent()) {
            UserUpdateResponse response = new UserUpdateResponse();
            response.setNewFirstName(userUpdate.getNewFirstName());
            response.setNewLastName(userUpdate.getNewLastName());
            response.setNewPassword(userUpdate.getNewPassword());
            response.setListOfCountry(foundUser.get().getCountry());
            userRepository.save(foundUser.get());

            return response;


        }
        throw new UserNotFoundException("user don't exist");


    }


    private boolean checkIfEmailAlreadyExists(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new UserNotFoundException("user don't exist"));
        org.springframework.security.core.userdetails.User returnedUser = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user.getRoles()));
        return returnedUser;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {

        Collection<? extends SimpleGrantedAuthority> authorities = roles.stream().map(
                role -> new SimpleGrantedAuthority(role.getRoleType().name())
        ).collect(Collectors.toSet());
        return authorities;
    }
}