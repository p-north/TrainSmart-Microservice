package com.fitness.userservice;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponse register(RegisterRequest request) {
         if(repository.existsByEmail(request.getEmail())){
             User existingUser = repository.findByEmail((request.getEmail()));
             UserResponse userResponse = new UserResponse();
             userResponse.setId(existingUser.getId());
             userResponse.setKeycloakID(existingUser.getKeycloakID());
             userResponse.setEmail(existingUser.getEmail());
             userResponse.setFirstName(existingUser.getFirstName());
             userResponse.setLastName(existingUser.getLastName());
             userResponse.setPassword(existingUser.getPassword());
             userResponse.setCreatedAt(existingUser.getCreatedAt());
             userResponse.setUpdatedAt(existingUser.getUpdatedAt());

             return userResponse;
         }


        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

       User savedUser = repository.save(user);
       UserResponse userResponse = new UserResponse();
       userResponse.setId(savedUser.getId());
       savedUser.setKeycloakID(userResponse.getKeycloakID());
       userResponse.setEmail(savedUser.getEmail());
       userResponse.setFirstName(savedUser.getFirstName());
       userResponse.setLastName(savedUser.getLastName());
       userResponse.setPassword(savedUser.getPassword());
       userResponse.setCreatedAt(savedUser.getCreatedAt());
       userResponse.setUpdatedAt(savedUser.getUpdatedAt());

       return userResponse;

    }

    public UserResponse getUserProfile(String userID) {
        User user = repository.findById(userID)
                .orElseThrow(()->new RuntimeException("user not found"));

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPassword(user.getPassword());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;


    }

//    public Boolean existByUserID(String userID) {
//        return repository.existsByKeyCloakID(userID);
//    }


    public Boolean existsByKeyCloakID(String userID) {
        return  repository.existsByKeyCloakID(userID);
    }
}
