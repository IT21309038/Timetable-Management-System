package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.BookResourceCollectionException;
import com.university.Timetable.Management.System.model.BookResource;
import com.university.Timetable.Management.System.model.Resource;
import com.university.Timetable.Management.System.repo.BookResourceRepo;
import com.university.Timetable.Management.System.repo.ResourceRepo;
import com.university.Timetable.Management.System.repo.UserRepo;
import com.university.Timetable.Management.System.security.CustomUserDetailsService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class BookResourceServiceImpl implements BookResourceService{

    @Autowired
    private BookResourceRepo bookResourceRepo;

    @Autowired
    private ResourceRepo resourceRepo;

    @Override
    public void createBookResource(String resourceId, BookResource bookResource) throws ConstraintViolationException, BookResourceCollectionException {
        Optional<Resource> resourceOptional = resourceRepo.findById(resourceId);

        if (resourceOptional.isEmpty()) {
            throw new BookResourceCollectionException("Resource is not available or does not exist");
        }

        Resource resource = resourceOptional.get();

        // Check for conflicting bookings
        DayOfWeek dayOfWeek = bookResource.getDayOfWeek();
        LocalTime startTime = bookResource.getStartTime();
        LocalTime endTime = bookResource.getEndTime();
        String bookedBy = bookResource.getBookedBy();

        // Find existing bookings for the same resource
        List<BookResource> existingBookings = bookResourceRepo.findByResource(resource);

        // Check if the new booking time overlaps with any existing booking
        boolean isOverlapping = existingBookings.stream()
                .anyMatch(existingBooking -> {
                    LocalTime existingStartTime = existingBooking.getStartTime();
                    LocalTime existingEndTime = existingBooking.getEndTime();

                    // Check if the new booking starts or ends during an existing booking
                    boolean startsDuringExisting = !startTime.isBefore(existingStartTime) && startTime.isBefore(existingEndTime);
                    boolean endsDuringExisting = !endTime.isAfter(existingEndTime) && endTime.isAfter(existingStartTime);

                    // Check if the new booking entirely contains an existing booking
                    boolean containsExisting = startTime.isBefore(existingStartTime) && endTime.isAfter(existingEndTime);

                    return startsDuringExisting || endsDuringExisting || containsExisting;
                });

        // If there's no overlap, proceed to save the new booking
        if (!isOverlapping) {
            bookResource.setResource(resource);
            bookResource.setIsBooked(true); // Mark as booked
            bookResource.setBookedBy(bookedBy);
            bookResource.setCreatedAt(new Date(System.currentTimeMillis()));

            bookResourceRepo.save(bookResource);
        } else {
            throw new BookResourceCollectionException("Conflicting booking exists for the given time slot");
        }
    }


    @Override
    public List<BookResource> getAllBookResource() {
        List<BookResource> bookResources = bookResourceRepo.findAll();
        if (!bookResources.isEmpty()){
            return bookResources;
        }else {
            return new ArrayList<BookResource>();
        }
    }

    @Override
    public BookResource getSingleBookResourceById(String id) throws BookResourceCollectionException {
        Optional<BookResource> bookResourceOptional = bookResourceRepo.findById(id);
        if(!bookResourceOptional.isPresent()){
            throw new BookResourceCollectionException(BookResourceCollectionException.NotFoundException(id));
        }else {
            return bookResourceOptional.get();
        }
    }

    @Override
    public void deleteBookResourceById(String id) throws BookResourceCollectionException {
        Optional<BookResource> bookResource = bookResourceRepo.findById(id);
        if(!bookResource.isPresent()){
            throw new BookResourceCollectionException(BookResourceCollectionException.NotFoundException(id));
        }else {
            bookResourceRepo.deleteById(id);
        }
    }
}
