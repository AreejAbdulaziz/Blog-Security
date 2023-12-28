package com.example.blogsecurity.Controller;

import com.example.blogsecurity.Model.User;
import com.example.blogsecurity.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    //All
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid User user){
        authService.register(user);
        return ResponseEntity.status(200).body("user register");
    }
    //all
    @PostMapping("/login")
    public ResponseEntity login(){
        return ResponseEntity.status(200).body("login");
    }
    //all
    @PostMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.status(200).body("logout");
    }
    //Admin
    @GetMapping("/getUsers")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.status(200).body(authService.getAllUsers());
    }
    // Admin
    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(authService.getUser(id));
    }
    // All
    @GetMapping("/my-user")
    public ResponseEntity getMyUser(@AuthenticationPrincipal User auth){
        return ResponseEntity.status(200).body(authService.getUser(auth.getId()));
    }
    // User - No one can update another user
    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestBody @Valid User newUser, @AuthenticationPrincipal User auth){
        authService.updateUser(newUser , auth.getId());
        return ResponseEntity.status(200).body("User Updated");
    }

    // Admin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){
        authService.deleteUser(id);
        return ResponseEntity.status(200).body("User Deleted");
    }
}
