package com.example.eventplatform.controller;

import com.example.eventplatform.repository.LocationType;
import com.example.eventplatform.service.LocationTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location-types")
public class LocationTypeController {

    private final LocationTypeService locationTypeService;

    public LocationTypeController(LocationTypeService locationTypeService) {
        this.locationTypeService = locationTypeService;
    }

    @GetMapping
    public List<LocationType> getAllLocationTypes() {
        return locationTypeService.findAll();
    }
}