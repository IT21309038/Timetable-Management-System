package com.university.Timetable.Management.System.exception;

public class ClassSessionCollectionException extends Exception{

    private static final long serialVersionUID = 1L;

    public ClassSessionCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String classSessionId) {
        return "ClassSession with " + classSessionId + " not found";
    }

    public static String AlreadyExists() {
        return "ClassSession with given time slot already exists";
    }

    public static String ConflictingSessionExists() {
        return "Conflicting session exists";
    }
}
