package com.pfa.userservice.user.controller;

import com.pfa.userservice.user.dto.request.UserRequest;
import com.pfa.userservice.user.dto.response.UserResponse;
import com.pfa.userservice.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }


    @PostMapping("/cree")
    public ResponseEntity<Long> createUser(@RequestBody @Valid UserRequest request){
        return ResponseEntity.ok(service.createUser(request));
    }
    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody @Valid UserRequest request){
        service.updateUser(request);
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> findAll(){
        return ResponseEntity.ok(service.findAllUsers());
    }
    @GetMapping("/{user-id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("user-id") Long userId) {
        return ResponseEntity.ok(service.findById(userId));
    }
    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> deleteById(@PathVariable("user-id") Long userId) {
        this.service.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }

}
