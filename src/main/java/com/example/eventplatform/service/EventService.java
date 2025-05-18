package com.example.eventplatform.service;

import com.example.eventplatform.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EventService {

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationTypeRepository locationTypeRepository;

    public EventService(EventRepository eventRepository,
                        UserRepository userRepository,
                        CategoryRepository categoryRepository,
                        LocationTypeRepository locationTypeRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.locationTypeRepository = locationTypeRepository;
    }

    @Transactional
    public Event createEvent(String title,
                             String description, String address,
                             String startTime, String endTime,
                             MultipartFile photo, Long categoryId,
                             Long locationTypeId, Long organizerId) {
        logger.info("Received photo: {}", photo != null ? "not null" : "null"); // НЕ ЗАБЫТЬ УБРАТЬ ЛОГ
        Optional<User> organizer = userRepository.findById(organizerId);
        Optional<Category> category = categoryRepository.findById(categoryId);
        Optional<LocationType> locationType = locationTypeRepository.findById(locationTypeId);

        if (organizer.isEmpty()) throw new IllegalStateException("Организатор не найден");
        if (category.isEmpty()) throw new IllegalStateException("Категория не найдена");
        if (locationType.isEmpty()) throw new IllegalStateException("Тип места не найден");

        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setAddress(address);
        event.setStartTime(startTime);
        event.setEndTime(endTime);

        if (photo != null) {
            try {
                event.setPhoto(photo.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при загрузке фото", e);
            }
        } else {
            event.setPhoto(null); // Устанавливаем null, если фото не передано
        }

        event.setOrganizer(organizer.get());
        event.setCategory(category.get());
        event.setLocationType(locationType.get());

        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(Long id, String title, String description,
                             String address, String startTime, String endTime,
                             MultipartFile photo, Long categoryId, Long locationTypeId, Long organizerId) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) throw new IllegalStateException("Мероприятие не найдено");

        Event event = optionalEvent.get();
        event.setTitle(title);
        event.setDescription(description);
        event.setAddress(address);
        event.setStartTime(startTime);
        event.setEndTime(endTime);

        if (photo != null) {
            try {
                event.setPhoto(photo.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при обновлении фото", e);
            }
        }

        Optional<User> organizer = userRepository.findById(organizerId);
        Optional<Category> category = categoryRepository.findById(categoryId);
        Optional<LocationType> locationType = locationTypeRepository.findById(locationTypeId);

        if (organizer.isEmpty()) throw new IllegalStateException("Организатор не найден");
        if (category.isEmpty()) throw new IllegalStateException("Категория не найдена");
        if (locationType.isEmpty()) throw new IllegalStateException("Тип места не найден");

        event.setOrganizer(organizer.get());
        event.setCategory(category.get());
        event.setLocationType(locationType.get());

        return eventRepository.save(event);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Transactional
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
