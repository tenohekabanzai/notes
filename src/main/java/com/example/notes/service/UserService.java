package com.example.notes.service;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.notes.entity.Notes;
import com.example.notes.entity.User;
import com.example.notes.repository.NotesRepository;
import com.example.notes.repository.UserRepository;

@Component
public class UserService {
    
    @Autowired
    private UserRepository ur;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private NotesRepository nr;

    public List<User> getAll(){
        List<User> x = ur.findAll();
        return new ArrayList<>(x);
    }

    public void saveUser(User user){
        ur.save(user);
    }

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ur.save(user);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<String> l = new ArrayList<>();
        l.add("ADMIN");
        l.add("USER");
        user.setRoles(l);  
        ur.save(user);
    }

    public User findByUsername(String u_name){
        User x = ur.findByUsername(u_name);
        return x;
    }

    public void deleteUser(String username){
        
        User user = ur.findByUsername(username);
        List<Notes> l = user.getNotes();
        l.forEach(x -> { if(x!= null) nr.deleteById(x.getId());});
        ur.deleteById(user.getId());
    }

}
