package com.university.Timetable.Management.System;

import com.university.Timetable.Management.System.exception.ResourceCollectionException;
import com.university.Timetable.Management.System.model.Resource;
import com.university.Timetable.Management.System.repo.ResourceRepo;
import com.university.Timetable.Management.System.service.ResourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceServiceImplTest {

    @Mock
    private ResourceRepo resourceRepo;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateResource_Success() {
        Resource resource = new Resource();
        resource.setResourceCode("101");
        when(resourceRepo.findByResourceCode("101")).thenReturn(Optional.empty());

        try {
            resourceService.createResource(resource);
            verify(resourceRepo, times(1)).save(resource);
        } catch (ResourceCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testGetAllResource_NotEmpty() {
        List<Resource> resources = new ArrayList<>();
        resources.add(new Resource());
        when(resourceRepo.findAll()).thenReturn(resources);

        List<Resource> result = resourceService.getAllResource();

        assertFalse(result.isEmpty());
        assertEquals(resources, result);
    }

    @Test
    void testGetSingleResourceByCode_Success() {
        Resource resource = new Resource();
        resource.setResourceCode("101");
        when(resourceRepo.findByResourceCode("101")).thenReturn(Optional.of(resource));

        try {
            Resource result = resourceService.getSingleResourceByCode("101");
            assertEquals(resource, result);
        } catch (ResourceCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testUpdateResource_Success() {
        Resource resource = new Resource();
        resource.setId("1");
        resource.setResourceCode("101");
        when(resourceRepo.findById("1")).thenReturn(Optional.of(resource));
        when(resourceRepo.findByResourceCode("101")).thenReturn(Optional.empty());

        try {
            resourceService.updateResource("1", resource);
            verify(resourceRepo, times(1)).save(resource);
        } catch (ResourceCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testDeleteResourceById_Success() {
        Resource resource = new Resource();
        resource.setId("1");
        when(resourceRepo.findById("1")).thenReturn(Optional.of(resource));

        try {
            resourceService.deleteResourceById("1");
            verify(resourceRepo, times(1)).deleteById("1");
        } catch (ResourceCollectionException e) {
            fail("Exception not expected here");
        }
    }
}

