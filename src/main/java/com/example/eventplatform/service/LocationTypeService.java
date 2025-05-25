package com.example.eventplatform.service;

import com.example.eventplatform.repository.LocationType;
import com.example.eventplatform.repository.LocationTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationTypeService {

    private final LocationTypeRepository locationTypeRepository;

    public LocationTypeService(LocationTypeRepository locationTypeRepository) {
        this.locationTypeRepository = locationTypeRepository;
    }

    public List<LocationType> findAll() {
        return locationTypeRepository.findAll();
    }
}