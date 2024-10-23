package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.BookResourceCollectionException;
import com.university.Timetable.Management.System.model.BookResource;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface BookResourceService {

    public void createBookResource(String resourceId, BookResource bookResource) throws
            ConstraintViolationException, BookResourceCollectionException;

    public List<BookResource> getAllBookResource();

    public BookResource getSingleBookResourceById(String id) throws BookResourceCollectionException;

    public void deleteBookResourceById(String id) throws BookResourceCollectionException;
}
