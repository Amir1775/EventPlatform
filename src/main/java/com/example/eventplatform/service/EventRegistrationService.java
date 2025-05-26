package com.example.eventplatform.service;

import com.example.eventplatform.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventRegistrationService {

    private final EventRegistrationRepository eventRegistrationRepository;
    private final EventRepository eventRepository;
    private final UserService userService;

    public EventRegistrationService(EventRegistrationRepository eventRegistrationRepository,
                                    EventRepository eventRepository,
                                    UserService userService) {
        this.eventRegistrationRepository = eventRegistrationRepository;
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public EventRegistration registerForEvent(Long eventId) {
        User currentUser = userService.getCurrentUser();
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("Event with id " + eventId + " does not exist");
        }

        // Проверяем, не зарегистрирован ли пользователь уже
        List<EventRegistration> existingRegistrations = eventRegistrationRepository.findByUser(currentUser);
        if (existingRegistrations.stream().anyMatch(reg -> reg.getEvent().getId().equals(eventId))) {
            throw new IllegalStateException("You are already registered for this event");
        }

        EventRegistration registration = new EventRegistration();
        registration.setEvent(optionalEvent.get());
        registration.setUser(currentUser);
        return eventRegistrationRepository.save(registration);
    }

    public List<EventRegistration> getUserRegistrations() {
        User currentUser = userService.getCurrentUser();
        return eventRegistrationRepository.findByUser(currentUser);
    }

    public List<EventRegistration> getEventParticipants(Long eventId) {
        User currentUser = userService.getCurrentUser();
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isEmpty()) {
            throw new IllegalStateException("Event with id " + eventId + " does not exist");
        }
        Event event = optionalEvent.get();
        if (event.getOrganizer().getId() != currentUser.getId()) {
            throw new IllegalStateException("You can only view participants of your own events");
        }
        return eventRegistrationRepository.findByEventId(eventId);
    }
}