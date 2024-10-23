package com.university.Timetable.Management.System.exception;

public class BookRoomCollectionException extends Exception{

    private static final long serialVersionUID = 1L;

    public BookRoomCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String id) {
        return "RoomBook with " + id + " not found";
    }

    public static String AlreadyExists() {
        return "RoomBook with given name already exists";
    }
}
