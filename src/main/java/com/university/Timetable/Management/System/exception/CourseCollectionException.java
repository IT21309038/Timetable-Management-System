package com.university.Timetable.Management.System.exception;

public class CourseCollectionException extends Exception{

    private static final long serialVersionUID = 1L;

    public CourseCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String courseCode) {
        return "Course with " + courseCode + " not found";
    }

    public static String AlreadyExists() {
        return "Course with given name already exists";
    }
}
