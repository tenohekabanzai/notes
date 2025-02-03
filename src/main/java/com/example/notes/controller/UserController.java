package com.example.notes.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes.entity.User;
import com.example.notes.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService us;

  

   

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        User x = us.findByUsername(username);
        if(x!= null)
        {
            if(user.getUsername() != null)
            x.setUsername(user.getUsername());
            if(user.getPassword() != null)
            x.setPassword(user.getPassword());
            if(user.getRoles()!= null)
            x.setRoles(user.getRoles());

            us.saveUser(x);
        }   
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserById(){
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        us.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

   

}
