package com.university.Timetable.Management.System.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "resources")
public class Resource {

    @Id
    private String id;

    @NotNull(message = "Resource Code cannot be null")
    private String resourceCode;
    @NotNull(message = "Resource Name cannot be null")
    private String resourceName;

    private Date createdAt;
    private Date updatedAt;
}
