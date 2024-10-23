package com.university.Timetable.Management.System.exception;

public class EnrolmentCollectionException extends Exception{

    private static final long serialVersionUID = 1L;

    public EnrolmentCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String enrolmentId, String studentId) {
        return "Enrolment with " + enrolmentId + " not found";
    }

    public static String AlreadyExists() {
        return "Enrolment with given name already exists";
    }
}
