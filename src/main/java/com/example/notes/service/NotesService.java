package com.example.notes.service;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.notes.entity.Notes;
import com.example.notes.entity.User;
import com.example.notes.repository.NotesRepository;

@Component
public class NotesService {
    
    @Autowired
    private NotesRepository nr;

    @Autowired
    private UserService us;

    @Transactional
    public void saveNote(Notes note,User user){
        Date d = new Date();
        note.setDate(d);
        Notes saved = nr.save(note);
        
        if (!user.getNotes().contains(saved)) {
            user.getNotes().add(saved);
        }
        us.saveUser(user);
    }

    public void updateNode(Notes note){
        nr.save(note);
    }

    public Optional<Notes> getById(ObjectId id){
        return nr.findById(id);
    }

     public ArrayList<Notes> getAllNotes() {
        List<Notes> notesList = nr.findAll();
        return new ArrayList<>(notesList); 
    }

    public boolean deleteById(ObjectId id,String username){
        User user = us.findByUsername(username);
        user.getNotes().removeIf(x -> x.getId().equals(id));
        // Iterator<Notes> it = user.getNotes().iterator();
        // while(it.hasNext())
        // {
        //     Notes n = it.next();
        //     if(n.getId() == id)
        //     it.remove();
        // }
        us.saveUser(user);
        nr.deleteById(id);
        return true;
    }

    public void deleteByIdOnly(ObjectId id){
        nr.deleteById(id);
    }

    public Optional<Notes> findById(ObjectId id){
        return nr.findById(id);
    }

}
