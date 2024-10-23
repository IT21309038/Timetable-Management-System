package com.university.Timetable.Management.System.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnrolmentDTO {

    private String studentId;
    private String courseId;
    private String facultyId;
}
