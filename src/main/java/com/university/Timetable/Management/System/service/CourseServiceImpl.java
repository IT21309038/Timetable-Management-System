package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.CourseCollectionException;
import com.university.Timetable.Management.System.model.Course;
import com.university.Timetable.Management.System.repo.CourseRepo;
import jakarta.validation.ConstraintViolationException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;


    @Override
    public Course createCourse(Course course) throws ConstraintViolationException, CourseCollectionException {
        Optional<Course> courseOptional = courseRepo.findByCourseCode(course.getCourseCode());
        if(courseOptional.isPresent()){
            throw new CourseCollectionException(CourseCollectionException.AlreadyExists());
        }else {
            course.setCreatedAt(new Date(System.currentTimeMillis()));
            courseRepo.save(course);
        }
        return course;
    }

    @Override
    public List<Course> getAllCourse() {
        List<Course> course = courseRepo.findAll();
        if (!course.isEmpty()){
            return course;
        }else {
            return new ArrayList<Course>();
        }
    }

    @Override
    public Course getSingleCourseByCourseCode(String courseCode) throws CourseCollectionException {
        Optional<Course> optCourse = courseRepo.findByCourseCode(courseCode);
        if(!optCourse.isPresent()){
            throw new CourseCollectionException(CourseCollectionException.NotFoundException(courseCode));
        }else {
            return optCourse.get();
        }
    }

    @Override
    public void updateCourse(String id, Course course) throws CourseCollectionException {
        Optional<Course> courseWithId = courseRepo.findById(id);

        Optional<Course> courseWithSameCode = courseRepo.findByCourseCode(course.getCourseCode());

        if(courseWithId.isPresent()){
            if(courseWithSameCode.isPresent() && !courseWithSameCode.get().getId().equals(id)){
                throw new CourseCollectionException(CourseCollectionException.AlreadyExists());
            }

            Course courseToUpdate = courseWithId.get();

            courseToUpdate.setCourseCode(course.getCourseCode());
            courseToUpdate.setCourseName(course.getCourseName());
            courseToUpdate.setCourseCredit(course.getCourseCredit());
            courseToUpdate.setCourseDescription(course.getCourseDescription());
            courseToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            courseRepo.save(courseToUpdate);

        }else {
            throw new CourseCollectionException(CourseCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteCourseById(String id) throws CourseCollectionException {
        Optional<Course> course = courseRepo.findById(id);
        if(!course.isPresent()){
            throw new CourseCollectionException(CourseCollectionException.NotFoundException(id));
        }else {
            courseRepo.deleteById(id);
        }
    }
}
