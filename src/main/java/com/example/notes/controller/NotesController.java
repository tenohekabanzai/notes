package com.example.notes.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.notes.entity.Notes;
import com.example.notes.entity.User;
import com.example.notes.service.NotesService;
import com.example.notes.service.UserService;

import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class NotesController {

    

    @Autowired
    private NotesService ne;
    @Autowired
    private UserService us;

    @GetMapping("/public/notes/user/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){        
        User user = us.findByUsername(username);
        List<Notes> x = user.getNotes();
        if(x != null && !x.isEmpty()){
            return new ResponseEntity<>(x,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/public/notes/id/{myId}")
    public ResponseEntity<?> getNotesById(@PathVariable ObjectId myId){
        Optional<Notes> note = ne.findById(myId);
        if(note.isPresent()) 
        return new ResponseEntity<>(note.get(),HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/public/notes/id/{username}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String username,@PathVariable ObjectId id){

        ne.deleteById(id,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/public/notes/id/{username}/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable String username,@PathVariable ObjectId id, @RequestBody Notes Notes) {
        
        Notes old = ne.findById(id).orElse(null);

        if(old != null){
            if(Notes.getTitle() != null)
            old.setTitle(Notes.getTitle());
            if(Notes.getContent() != null)
            old.setContent(Notes.getContent());
        }
        User user = us.findByUsername(username);
        ne.updateNode(old);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/public/notes/{username}")
    public ResponseEntity<?> addNote(@RequestBody Notes Notes, @PathVariable String username) {
        User user = us.findByUsername(username);
        ne.saveNote(Notes,user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/public/notes/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id) {
        
        Optional<Notes> nx = ne.getById(id);
        if(nx.isPresent())
        return ResponseEntity.ok(nx);
        else
        return ResponseEntity.notFound().build();

    }
    
}
