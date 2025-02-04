package com.example.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import com.example.notes.entity.User;
import com.example.notes.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService us;
    
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all = us.getAll();
        if(!(all == null) && !all.isEmpty())
        return new ResponseEntity<> (all,HttpStatus.ACCEPTED);
        else
        return new ResponseEntity<> (HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user) {
        us.saveAdmin(user);
    }
    
}
