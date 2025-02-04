package com.example.notes.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes.entity.Notes;
import com.example.notes.entity.User;
import com.example.notes.service.NotesService;
import com.example.notes.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/notes")
public class NotesController {

    @Autowired
    private NotesService ne;
    @Autowired
    private UserService us;

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getMethodName(@PathVariable ObjectId myId) 
    {    
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        User user = us.findByUsername(username);
        List<Notes> list1 = user.getNotes();
        
        for (Notes it : list1) {
            if (it.getId().equals(myId)) {
                Optional<Notes> n = ne.getById(myId);
                return new ResponseEntity<>(n.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser()
    {        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = us.findByUsername(username);
        List<Notes> x = user.getNotes();

        if(x != null && !x.isEmpty()){
            return new ResponseEntity<>(x,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    

    @PostMapping
    public ResponseEntity<?> addNote(@RequestBody Notes Notes) 
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        User user = us.findByUsername(username);
        ne.saveNote(Notes,user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ne.deleteById(id,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable ObjectId id, @RequestBody Notes Notes) 
    {   
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Notes old = ne.findById(id).orElse(null);

        if(old != null){
            if(Notes.getTitle() != null)
            old.setTitle(Notes.getTitle());
            if(Notes.getContent() != null)
            old.setContent(Notes.getContent());
        }
        else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // User user = us.findByUsername(username);
        ne.updateNode(old);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/public/notes/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id) 
    {    
        Optional<Notes> nx = ne.getById(id);
        if(nx.isPresent())
        return ResponseEntity.ok(nx);
        else
        return ResponseEntity.notFound().build();
    }
    
}
