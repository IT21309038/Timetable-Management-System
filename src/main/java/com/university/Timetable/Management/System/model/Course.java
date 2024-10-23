package com.university.Timetable.Management.System.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    @NotNull(message = "Course Name cannot be null")
    private String courseName;
    @NotNull(message = "Course Code cannot be null")
    private String courseCode;
    @NotNull(message = "Course Description cannot be null")
    private String courseDescription;
    @NotNull(message = "Course Credit cannot be null")
    private int courseCredit;

    private Date createdAt;
    private Date updatedAt;

    @DBRef
    private Faculty faculty;
    @DBRef
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (!this.students.contains(student)) {
            this.students.add(student);
        }
    }

}
