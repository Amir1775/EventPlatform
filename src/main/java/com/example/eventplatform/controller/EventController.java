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
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "locationTypeId", required = false) Long locationTypeId,
            @RequestParam(value = "organizerId", required = false) Long organizerId) {
        Event updatedEvent = eventService.updateEvent(id, title, description, address, startTime, endTime, photo, categoryId, locationTypeId, organizerId);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}