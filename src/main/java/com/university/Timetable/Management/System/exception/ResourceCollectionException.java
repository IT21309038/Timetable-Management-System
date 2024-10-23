package com.university.Timetable.Management.System.exception;

public class ResourceCollectionException extends Exception{

        private static final long serialVersionUID = 1L;

        public ResourceCollectionException(String message) {
            super(message);
        }

        public static String NotFoundException(String resourceCode) {
            return "Resource with " + resourceCode + " not found";
        }

        public static String AlreadyExists() {
            return "Resource with given name already exists";
        }
}
