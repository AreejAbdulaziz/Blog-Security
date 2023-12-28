package com.example.blogsecurity.Service;

import com.example.blogsecurity.Exception.ApiException;
import com.example.blogsecurity.Model.User;
import com.example.blogsecurity.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<User> getAllUsers(){
        return authRepository.findAll();
    }
    public void register(User user){

        user.setRole("USER");
        String hash=new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);
    }
    public User getUser(Integer id){
        User myUser=authRepository.findUserById(id);
        if (myUser==null){
            throw new ApiException("User Not Found!");
        }
        return myUser;
    }

    public void deleteUser(Integer id){
        User myUser=authRepository.findUserById(id);
        if(myUser==null){
            throw new ApiException("User Not Found");
        }
        authRepository.delete(myUser);
    }


    public void updateUser(User user, Integer id){
        User oldUser=authRepository.findUserById(id);

        user.setId(id);
        user.setRole(oldUser.getRole());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        authRepository.save(user);
    }
}
