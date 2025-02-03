package com.example.notes.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.notes.entity.Notes;

public interface NotesRepository extends MongoRepository<Notes,ObjectId> {
    
}
