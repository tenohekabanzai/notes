package com.example.notes.repository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.notes.entity.User;
public interface UserRepository extends MongoRepository<User,ObjectId> {
    User findByUsername(String username);
}
