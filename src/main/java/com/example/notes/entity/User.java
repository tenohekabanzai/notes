package com.example.notes.entity;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document("users")
public class User {
    
    @Id @Getter 
    private ObjectId id;

    @Indexed(unique= true)
    @NonNull @Getter 
    private String username;

    @Getter @NonNull
    private String password;

    @DBRef @Getter
    private List<Notes> notes = new ArrayList<>();

    @Getter @Setter
    private List<String> roles = new ArrayList<>();

    public void setUsername(String x){
        this.username = x;
    }

    public void setPassword(String x){
        this.password = x;
    }


}
