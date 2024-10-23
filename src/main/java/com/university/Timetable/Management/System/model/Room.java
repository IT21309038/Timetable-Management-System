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
@Document(collection = "rooms")
public class Room {

    @Id
    private String id;

    @NotNull(message = "Room Code cannot be null")
    private String roomCode;
    @NotNull(message = "Room Capacity cannot be null")
    private int roomCapacity;
    @NotNull(message = "Room Floor cannot be null")
    private int floor;

    private Date createdAt;
    private Date updatedAt;

}
