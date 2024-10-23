package com.university.Timetable.Management.System.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookRoom")
public class BookRoom {

    @Id
    private String id;

    @DBRef
    private Room room;
    @DBRef
    private ClassSession classSession;

    private Boolean isInUse;
    private Date createdAt;
    private Date updatedAt;
}
