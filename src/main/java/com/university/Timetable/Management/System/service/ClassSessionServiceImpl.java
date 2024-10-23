package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.ClassSessionCollectionException;
import com.university.Timetable.Management.System.model.ClassSession;
import com.university.Timetable.Management.System.model.Course;
import com.university.Timetable.Management.System.model.Room;
import com.university.Timetable.Management.System.model.Student;
import com.university.Timetable.Management.System.repo.ClassSessionRepo;
import com.university.Timetable.Management.System.repo.CourseRepo;
import com.university.Timetable.Management.System.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

@Service
public class ClassSessionServiceImpl implements ClassSessionService{

    @Autowired
    private ClassSessionRepo classSessionRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public ClassSession createClassSession(ClassSession classSession) throws ClassSessionCollectionException {
        DayOfWeek dayOfWeek = classSession.getDayOfWeek();
        LocalTime startTime = classSession.getStartTime();
        LocalTime endTime = classSession.getEndTime();
        String courseCode = classSession.getCourseCode();

        Optional<Course> courseOptional = courseRepo.findByCourseCode(courseCode);

        // Check if location data is null, if so, throw an exception
        if (classSession.getLocation() == null) {
            throw new ClassSessionCollectionException(ClassSessionCollectionException.NotFoundException("Location"));
        }

        String location = classSession.getLocation().getRoomCode();

        // Check for existing class session with the same day, start time, end time, and location
        List<ClassSession> existingClassSessions = classSessionRepo.findByDayOfWeekAndStartTimeAndEndTimeAndLocation(
                dayOfWeek, startTime, endTime, location);

        if (!existingClassSessions.isEmpty()) {
            // If an existing session with the same day, start time, end time, and location is found, throw an exception
            throw new ClassSessionCollectionException(ClassSessionCollectionException.AlreadyExists());
        }

        // Check for conflicting sessions with the same day, start time, end time, and course code
        List<ClassSession> conflictingSessions = classSessionRepo.findByDayOfWeekAndStartTimeAndEndTimeAndCourseCode(
                dayOfWeek, startTime, endTime, courseCode);

        for (ClassSession conflictingSession : conflictingSessions) {
            // If a conflicting session is found (same day, start time, end time, and different location), throw an exception
            if (!conflictingSession.getLocation().equals(location)) {
                throw new ClassSessionCollectionException(ClassSessionCollectionException.ConflictingSessionExists());
            }
        }

        if (courseOptional.isEmpty()) {
            // If the course is not found, throw an exception
            throw new ClassSessionCollectionException(ClassSessionCollectionException.NotFoundException(courseCode));
        }

        // Save the new class session
        classSession.setCreatedAt(new Date(System.currentTimeMillis()));
        classSessionRepo.save(classSession);

        return classSession;
    }




    @Override
    public List<ClassSession> getAllClassSession() {
        List<ClassSession> classSession = classSessionRepo.findAll();
        if (!classSession.isEmpty()){
            classSession.sort(Comparator.comparing(ClassSession::getDayOfWeek).thenComparing(ClassSession::getStartTime));
            return classSession;
        }else {
            return new ArrayList<ClassSession>();
        }
    }

    @Override
    public List<ClassSession> getClassSessionByDayOfWeek(DayOfWeek day) {
        List<ClassSession> classSession = classSessionRepo.findByDayOfWeek(day);
        if (!classSession.isEmpty()){
            classSession.sort(Comparator.comparing(ClassSession::getStartTime));
            return classSession;
        }else {
            return new ArrayList<ClassSession>();
        }
    }

    @Override
    public ClassSession getSingleClassSessionById(String id) throws ClassSessionCollectionException {
        Optional<ClassSession> classSession = classSessionRepo.findById(id);
        if (!classSession.isPresent()){
            throw new ClassSessionCollectionException(ClassSessionCollectionException.NotFoundException(id));
        }else {
            return classSession.get();
        }
    }

    @Override
    public void updateClassSession(String id, ClassSession classSession) throws ClassSessionCollectionException {
        Optional<ClassSession> classSessionWithId = classSessionRepo.findById(id);

        if (classSessionWithId.isPresent()) {
            ClassSession existingSession = classSessionWithId.get();

            // Check if there's an existing session with the same course ID, day, start time, and end time in different locations
            List<ClassSession> existingSessionsWithSameCourse = classSessionRepo.
                    findByDayOfWeekAndStartTimeAndEndTimeAndCourseCode(
                    classSession.getDayOfWeek(), classSession.getStartTime(),
                            classSession.getEndTime(), classSession.getCourseCode());

            // If an existing session is found in a different location, throw an exception
            for (ClassSession session : existingSessionsWithSameCourse) {
                if (!session.getId().equals(id) && !session.getLocation().equals(classSession.getLocation())) {
                    throw new ClassSessionCollectionException(ClassSessionCollectionException.
                            ConflictingSessionExists());
                }
            }

            // Check if there's an existing session with conflicting time in the same location
            List<ClassSession> conflictingSessionsInSameLocation = classSessionRepo.
                    findByDayOfWeekAndStartTimeAndEndTimeAndLocation(
                    classSession.getDayOfWeek(), classSession.getStartTime(),
                            classSession.getEndTime(), classSession.getLocation().getRoomCode());

            // If a conflicting session is found, throw an exception
            for (ClassSession session : conflictingSessionsInSameLocation) {
                if (!session.getId().equals(id)) {
                    throw new ClassSessionCollectionException(ClassSessionCollectionException.
                            ConflictingSessionExists());
                }
            }

            // Update the session
            existingSession.setCourseCode(classSession.getCourseCode());
            existingSession.setDayOfWeek(classSession.getDayOfWeek());
            existingSession.setStartTime(classSession.getStartTime());
            existingSession.setEndTime(classSession.getEndTime());
            existingSession.setFacultyName(classSession.getFacultyName());
            existingSession.setLocation(classSession.getLocation());
            existingSession.setUpdatedAt(new Date(System.currentTimeMillis()));

            classSessionRepo.save(existingSession);
        } else {
            // If the session with the given id is not found, throw an exception
            throw new ClassSessionCollectionException(ClassSessionCollectionException.NotFoundException(id));
        }
    }



    @Override
    public void deleteClassSessionById(String id) throws ClassSessionCollectionException {
        Optional<ClassSession> classSession = classSessionRepo.findById(id);
        if (!classSession.isPresent()){
            throw new ClassSessionCollectionException(ClassSessionCollectionException.NotFoundException(id));
        }else {
            classSessionRepo.deleteById(id);
        }
    }

    @Override
    public ArrayList<ClassSession> getClassSessionByCourseCodeAndDayOfWeek(String courseCode, DayOfWeek day) {
        List<ClassSession> classSession = classSessionRepo.findByCourseCodeAndDayOfWeek(courseCode, day);
        if (!classSession.isEmpty()){
            classSession.sort(Comparator.comparing(ClassSession::getStartTime));
            return (ArrayList<ClassSession>) classSession;
        }else {
            return new ArrayList<ClassSession>();
        }
    }

    @Override
    public ArrayList<ClassSession> getClassSessionByCourseCode(String courseCode) {
        List<ClassSession> classSession = classSessionRepo.findByCourseCode(courseCode);
        if (!classSession.isEmpty()) {
            classSession.sort(Comparator.comparing(ClassSession::getDayOfWeek).thenComparing(ClassSession::getStartTime));
            return (ArrayList<ClassSession>) classSession;
        } else {
            return new ArrayList<ClassSession>();
        }
    }

    @Override
    public ArrayList<ClassSession> getAllClassSessionForStudent(String studentId) {
        Optional<Student> student = studentRepo.findByStudentId(studentId);

        if(student.isEmpty()){
            return new ArrayList<ClassSession>();
        }

        List<ClassSession> allClassSessions = new ArrayList<>();

        for(Course course : student.get().getCourses()){
            List<ClassSession> classSessions = classSessionRepo.findByCourseCode(course.getCourseCode());
            allClassSessions.addAll(classSessions);
        }

        allClassSessions.sort(Comparator.comparing(ClassSession::getDayOfWeek).thenComparing(ClassSession::getStartTime));

        return new ArrayList<>(allClassSessions);
    }
}
