package com.brundhavan.foodapi.services;

import com.brundhavan.foodapi.IO.UserRequest;
import com.brundhavan.foodapi.IO.UserResponse;

public interface UserService {
    UserResponse userRegister(UserRequest request);
    String findByUserId();
}
