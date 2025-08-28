package com.fitness.userservice.controller;

import com.fitness.userservice.UserService;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/{userID}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userID){
        return ResponseEntity.ok(userService.getUserProfile(userID));
    }

    @GetMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid@RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.register(request));
    }
}
