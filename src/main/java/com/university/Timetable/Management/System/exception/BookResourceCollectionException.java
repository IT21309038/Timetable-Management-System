package com.university.Timetable.Management.System.exception;

public class BookResourceCollectionException extends Exception{

        private static final long serialVersionUID = 1L;

        public BookResourceCollectionException(String message) {
            super(message);
        }

        public static String NotFoundException(String resourceId) {
            return "Resource with " + resourceId + " not found";
        }

        public static String AlreadyExists() {
            return "Resource with given name already exists";
        }
}
