package com.university.Timetable.Management.System;

import com.university.Timetable.Management.System.exception.BookResourceCollectionException;
import com.university.Timetable.Management.System.model.BookResource;
import com.university.Timetable.Management.System.model.Resource;
import com.university.Timetable.Management.System.repo.BookResourceRepo;
import com.university.Timetable.Management.System.repo.ResourceRepo;
import com.university.Timetable.Management.System.service.BookResourceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookResourceServiceImplTest {

    @Mock
    private BookResourceRepo bookResourceRepo;

    @Mock
    private ResourceRepo resourceRepo;

    @InjectMocks
    private BookResourceServiceImpl bookResourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBookResource_Success() {
        Resource resource = new Resource();
        resource.setId("resourceId");

        BookResource bookResource = new BookResource();
        bookResource.setDayOfWeek(DayOfWeek.MONDAY);
        bookResource.setStartTime(LocalTime.of(10, 0));
        bookResource.setEndTime(LocalTime.of(12, 0));
        bookResource.setBookedBy("user");

        when(resourceRepo.findById("resourceId")).thenReturn(Optional.of(resource));
        when(bookResourceRepo.findByResource(resource)).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> bookResourceService.createBookResource("resourceId", bookResource));
        verify(bookResourceRepo, times(1)).save(any(BookResource.class));
    }

    @Test
    void testCreateBookResource_ResourceNotAvailable() {
        when(resourceRepo.findById("resourceId")).thenReturn(Optional.empty());

        BookResource bookResource = new BookResource();
        assertThrows(BookResourceCollectionException.class,
                () -> bookResourceService.createBookResource("resourceId", bookResource));
        verify(bookResourceRepo, never()).save(any());
    }

}

