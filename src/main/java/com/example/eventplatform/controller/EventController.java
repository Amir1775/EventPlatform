package com.example.eventplatform.controller;

import com.example.eventplatform.repository.Event;
import com.example.eventplatform.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.findAll();
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("address") String address,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam(value = "photo", required = false) MultipartFile photo, //ПОТОМ РЕШИТЬ ПРОБЛЕМУ
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("locationTypeId") Long locationTypeId,
            @RequestParam("organizerId") Long organizerId) {
        Event event = eventService.createEvent(title, description, address, startTime, endTime, photo, categoryId, locationTypeId, organizerId);
        return ResponseEntity.ok(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("address") String address,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("locationTypeId") Long locationTypeId,
            @RequestParam("organizerId") Long organizerId) {
        Event event = eventService.updateEvent(id, title, description, address, startTime, endTime, photo, categoryId, locationTypeId, organizerId);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}