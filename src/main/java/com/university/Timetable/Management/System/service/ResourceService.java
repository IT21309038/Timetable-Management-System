package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.ResourceCollectionException;
import com.university.Timetable.Management.System.model.Resource;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface ResourceService {

    public void createResource(Resource resource) throws ConstraintViolationException, ResourceCollectionException;

    public List<Resource> getAllResource();

    public Resource getSingleResourceByCode(String resourceCode) throws ResourceCollectionException;

    public void updateResource(String id, Resource resource) throws ResourceCollectionException;

    public void deleteResourceById(String id) throws ResourceCollectionException;
}
