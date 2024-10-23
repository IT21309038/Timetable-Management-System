package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.ResourceCollectionException;
import com.university.Timetable.Management.System.model.Resource;
import com.university.Timetable.Management.System.repo.ResourceRepo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    private ResourceRepo resourceRepo;

    @Override
    public void createResource(Resource resource) throws ConstraintViolationException, ResourceCollectionException {
        Optional<Resource> resourceOptional = resourceRepo.findByResourceCode(resource.getResourceCode());
        if(resourceOptional.isPresent()){
            throw new ResourceCollectionException(ResourceCollectionException.AlreadyExists());
        }else {
            resource.setCreatedAt(new Date(System.currentTimeMillis()));
            resourceRepo.save(resource);
        }
    }

    @Override
    public List<Resource> getAllResource() {
        List<Resource> resource = resourceRepo.findAll();
        if (!resource.isEmpty()){
            return resource;
        }else {
            return new ArrayList<Resource>();
        }
    }

    @Override
    public Resource getSingleResourceByCode(String resourceCode) throws ResourceCollectionException {
        Optional<Resource> optResource = resourceRepo.findByResourceCode(resourceCode);
        if(!optResource.isPresent()){
            throw new ResourceCollectionException(ResourceCollectionException.NotFoundException(resourceCode));
        }else {
            return optResource.get();
        }
    }

    @Override
    public void updateResource(String id, Resource resource) throws ResourceCollectionException {
        Optional<Resource> resourceWithId = resourceRepo.findById(id);

        Optional<Resource> resourceWithSameCode = resourceRepo.findByResourceCode(resource.getResourceCode());

        if(resourceWithId.isPresent()){
            if(resourceWithSameCode.isPresent() && !resourceWithSameCode.get().getId().equals(id)){
                throw new ResourceCollectionException(ResourceCollectionException.AlreadyExists());
            }

            Resource resourceToUpdate = resourceWithId.get();

            resourceToUpdate.setResourceCode(resource.getResourceCode());
            resourceToUpdate.setResourceName(resource.getResourceName());
            resourceToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));

            resourceRepo.save(resourceToUpdate);
        }else {
            throw new ResourceCollectionException(ResourceCollectionException.NotFoundException(id));
        }
    }

    @Override
    public void deleteResourceById(String id) throws ResourceCollectionException {
        Optional<Resource> resource = resourceRepo.findById(id);
        if(!resource.isPresent()){
            throw new ResourceCollectionException(ResourceCollectionException.NotFoundException(id));
        }else {
            resourceRepo.deleteById(id);
        }
    }
}
