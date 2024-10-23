package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.StudentCollectionException;
import com.university.Timetable.Management.System.model.Student;
import com.university.Timetable.Management.System.repo.StudentRepo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public Student createStudent(Student student) throws ConstraintViolationException, StudentCollectionException {
        Optional<Student> studentOptional = studentRepo.findByStudentId(student.getStudentId());
        if(studentOptional.isPresent()){
            throw new StudentCollectionException(StudentCollectionException.AlreadyExists());
        }else {
            student.setCreatedAt(new Date(System.currentTimeMillis()));
            studentRepo.save(student);
        }
        return student;
    }

    @Override
    public List<Student> getAllStudent() {
        List<Student> students = studentRepo.findAll();
        if (!students.isEmpty()){
            return students;
        }else {
            return new ArrayList<Student>();
        }
    }

    @Override
    public Student getSingleStudentByStudentId(String studentId) throws StudentCollectionException {
        Optional<Student> optStudent = studentRepo.findByStudentId(studentId);
        if(!optStudent.isPresent()){
            throw new StudentCollectionException(StudentCollectionException.NotFoundException(studentId));
        }else {
            return optStudent.get();
        }
    }

    @Override
    public Student updateStudent(String id, Student student) throws StudentCollectionException {
        Optional<Student> studentWithId = studentRepo.findById(id);

        Optional<Student> studentWithSameStudentId = studentRepo.findByStudentId(student.getStudentId());

        if(studentWithId.isPresent()){
            if(studentWithSameStudentId.isPresent() && !studentWithSameStudentId.get().getId().equals(id)){
                throw new StudentCollectionException(StudentCollectionException.AlreadyExists());
            }
            Student studentToUpdate = studentWithId.get();

            studentToUpdate.setStudentName(student.getStudentName());
            studentToUpdate.setStudentId(student.getStudentId());
            studentToUpdate.setStudentEmail(student.getStudentEmail());
            studentToUpdate.setYear(student.getYear());
            studentToUpdate.setSemester(student.getSemester());
            studentToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            studentRepo.save(studentToUpdate);
        }else {
            throw new StudentCollectionException(StudentCollectionException.NotFoundException(id));
        }
        return student;
    }

    @Override
    public Student deleteStudentById(String id) throws StudentCollectionException {
        Optional<Student> student = studentRepo.findById(id);
        if(!student.isPresent()){
            throw new StudentCollectionException(StudentCollectionException.NotFoundException(id));
        }else {
            studentRepo.deleteById(id);
        }
        return student.get();
    }
}
