package com.fitness.userservice;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository repository;

    public UserResponse register(RegisterRequest request) {
         if(repository.existsByEmail(request.getEmail())){
             throw new RuntimeException("email already exists");
         }


        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

       User savedUser = repository.save(user);
       UserResponse userResponse = new UserResponse();
       userResponse.setId(savedUser.getId());
       userResponse.setEmail(savedUser.getEmail());
       userResponse.setFirstName(savedUser.getFirstName());
       userResponse.setLastName(savedUser.getLastName());
       userResponse.setPassword(savedUser.getPassword());
       userResponse.setCreatedAt(savedUser.getCreatedAt());
       userResponse.setUpdatedAt(savedUser.getUpdatedAt());

       return userResponse;

    }
}
