package com.university.Timetable.Management.System.exception;

public class StudentCollectionException extends Exception{

    private static final long serialVersionUID = 1L;

    public StudentCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String studentId) {
        return "Student with " + studentId + " not found";
    }

    public static String AlreadyExists() {
        return "Student with given name already exists";
    }

}
