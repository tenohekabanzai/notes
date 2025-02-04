package com.example.notes.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes.entity.User;
import com.example.notes.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/Healthcheck")
    public String Greet(){
        return new String("All Ok");
    }    

    @Autowired
    private UserService us;

    @PostMapping
    public void createUser(@RequestBody User user) {
        if(user.getUsername()!= null && user.getPassword() != null)
        {
            ArrayList<String> userList = new ArrayList<>();
            userList.add("USER");
            if(user.getRoles().isEmpty())
            user.setRoles(userList);
            us.saveNewUser(user);
        }
    }

}
