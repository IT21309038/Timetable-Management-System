package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.ClassSessionCollectionException;
import com.university.Timetable.Management.System.model.ClassSession;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public interface ClassSessionService {

    public ClassSession createClassSession(ClassSession classSession) throws ClassSessionCollectionException;

    public List<ClassSession> getAllClassSession();

    public List<ClassSession> getClassSessionByDayOfWeek(DayOfWeek day);

    public ClassSession getSingleClassSessionById(String id) throws ClassSessionCollectionException;

    public void updateClassSession(String id, ClassSession classSession) throws ClassSessionCollectionException;

    public void deleteClassSessionById(String id) throws ClassSessionCollectionException;

    public ArrayList<ClassSession> getClassSessionByCourseCodeAndDayOfWeek(String courseCode, DayOfWeek day);

    public ArrayList<ClassSession> getClassSessionByCourseCode(String courseCode);

    public ArrayList<ClassSession> getAllClassSessionForStudent(String studentId);
}
