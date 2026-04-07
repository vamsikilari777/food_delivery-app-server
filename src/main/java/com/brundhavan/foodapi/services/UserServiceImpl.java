package com.brundhavan.foodapi.services;

import com.brundhavan.foodapi.Entities.UserEntity;
import com.brundhavan.foodapi.IO.UserRequest;
import com.brundhavan.foodapi.IO.UserResponse;
import com.brundhavan.foodapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationFacade authenticationFacade;

    @Override
    public UserResponse userRegister(UserRequest request) {
        UserEntity userEntity = converToUserEntity(request);
        userEntity = userRepository.save(userEntity);
        return converToUserResponse(userEntity);



    }

    @Override
    public String findByUserId() {
        String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
        UserEntity loggedInUser = userRepository.findByEmail(loggedInUserEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return loggedInUser.getId();
    }

    public UserEntity converToUserEntity(UserRequest request){
        return UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    public UserResponse converToUserResponse(UserEntity entity){
        return  UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
