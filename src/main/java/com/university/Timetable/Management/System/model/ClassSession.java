package com.university.Timetable.Management.System.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "classSessions")
public class ClassSession {

    @Id
    private String id;

    @NotNull(message = "Course Code cannot be null")
    private String courseCode;
    @NotNull(message = "Day of Week cannot be null")
    private DayOfWeek dayOfWeek;
    @NotNull(message = "Start Time cannot be null")
    private LocalTime startTime;
    @NotNull(message = "End Time cannot be null")
    private LocalTime endTime;
    @NotNull(message = "Faculty Name cannot be null")
    private String facultyName;
    @NotNull(message = "Location cannot be null")
    @DBRef
    private Room location;

    private Date CreatedAt;
    private Date UpdatedAt;
}
