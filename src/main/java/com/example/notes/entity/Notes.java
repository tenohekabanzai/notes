package com.example.notes.entity;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;


@Document(collection="notes")
public class Notes {
    
    @Getter 
    @Setter
    @Id
    private ObjectId id;
    @Getter @NonNull
    private String title;
    @Getter @Setter
    private String content;

    @Getter @Setter
    private Date date;


    public void setTitle(String x){
        this.title = x;
    }

}
