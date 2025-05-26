package com.example.eventplatform.controller;

import com.example.eventplatform.repository.EventRegistration;
import com.example.eventplatform.service.EventRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/registrations")
public class EventRegistrationController {

    private final EventRegistrationService eventRegistrationService;

    public EventRegistrationController(EventRegistrationService eventRegistrationService) {
        this.eventRegistrationService = eventRegistrationService;
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<EventRegistration> registerForEvent(@PathVariable Long eventId) {
        EventRegistration registration = eventRegistrationService.registerForEvent(eventId);
        return ResponseEntity.ok(registration);
    }

    @GetMapping("/my")
    public List<EventRegistration> getMyRegistrations() {
        return eventRegistrationService.getUserRegistrations();
    }

    @GetMapping("/event/{eventId}")
    public List<EventRegistration> getEventParticipants(@PathVariable Long eventId) {
        return eventRegistrationService.getEventParticipants(eventId);
    }
}