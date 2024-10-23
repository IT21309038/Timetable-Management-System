package com.university.Timetable.Management.System.exception;

public class RoomCollectionException extends Exception{

    private static final long serialVersionUID = 1L;

    public RoomCollectionException(String message) {
        super(message);
    }

    public static String NotFoundException(String roomCode) {
        return "Room with " + roomCode + " not found";
    }

    public static String AlreadyExists() {
        return "Room with given name already exists";
    }
}
