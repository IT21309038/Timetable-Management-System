package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.EnrolmentCollectionException;
import com.university.Timetable.Management.System.model.Course;
import com.university.Timetable.Management.System.model.Faculty;
import com.university.Timetable.Management.System.model.Student;
import com.university.Timetable.Management.System.repo.CourseRepo;
import com.university.Timetable.Management.System.repo.FacultyRepo;
import com.university.Timetable.Management.System.repo.StudentRepo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EnrolmentServiceImpl implements EnrolmentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private FacultyRepo facultyRepo;


    @Override
    public void enrollStudentToCourse(String studentId, String courseId) throws ConstraintViolationException, EnrolmentCollectionException {

        Student student = studentRepo.findById(studentId).orElseThrow(() ->
                new EnrolmentCollectionException(EnrolmentCollectionException.NotFoundException
                        ("Student", studentId)));

        Course course = courseRepo.findById(courseId).orElseThrow(() ->
                new EnrolmentCollectionException(EnrolmentCollectionException.NotFoundException
                        ("Course", courseId)));

        if(student != null && course != null) {

            course.addStudent(student);
            courseRepo.save(course);

            student.addCourse(course);
            studentRepo.save(student);
        }
    }

    @Override
    public ArrayList<Student> getStudentsByCourseCode(String courseCode) {
        ArrayList<Student> students = new ArrayList<>();

        for (Course course : courseRepo.findAll()) {
            if (course.getCourseCode().equals(courseCode)) {
                students.addAll(course.getStudents());
            }
        }
        return students;
    }

    @Override
    public void deleteStudentFromCourse(String studentId, String courseId) throws ConstraintViolationException, EnrolmentCollectionException {
        // Find the course
        Course course = courseRepo.findById(courseId).orElseThrow(() ->
                new EnrolmentCollectionException(EnrolmentCollectionException.NotFoundException("Course", courseId)));

        // Remove the student from the course
        course.getStudents().removeIf(student -> student.getId().equals(studentId));

        // Update the course
        courseRepo.save(course);

        // Find the student
        Student student = studentRepo.findById(studentId).orElseThrow(() ->
                new EnrolmentCollectionException(EnrolmentCollectionException.NotFoundException("Student", studentId)));

        // Remove the course from the student's enrollments
        student.getCourses().removeIf(c -> c.getId().equals(courseId));

        // Update the student
        studentRepo.save(student);
    }

    @Override
    public void addFacultyToCourse(String facultyId, String courseId) throws ConstraintViolationException, EnrolmentCollectionException {
        // Find the course
        Course course = courseRepo.findById(courseId).orElseThrow(() ->
                new EnrolmentCollectionException(EnrolmentCollectionException.NotFoundException("Course", courseId)));

        // Find the faculty
        Faculty faculty = facultyRepo.findById(facultyId).orElseThrow(() ->
                new EnrolmentCollectionException(EnrolmentCollectionException.NotFoundException("Faculty", facultyId)));

        // Add the faculty to the course
        course.setFaculty(faculty);

        // Update the course
        courseRepo.save(course);
    }


}
