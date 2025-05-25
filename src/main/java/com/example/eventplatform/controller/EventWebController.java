package com.example.eventplatform.controller;

import com.example.eventplatform.repository.Event;
import com.example.eventplatform.repository.EventRepository;
import com.example.eventplatform.repository.User;
import com.example.eventplatform.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class EventWebController {

    private final EventRepository eventRepository;
    private final UserService userService;

    public EventWebController(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    @GetMapping("/events/new")
    public String showCreateForm(Model model) {
        model.addAttribute("event", new Event());
        return "new-event";
    }

    @PostMapping("/events")
    public String createEvent(
            @ModelAttribute Event event,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            event.setOrganizer(currentUser);
            if (photo != null && !photo.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                    String uploadDir = "uploads/";
                    File uploadDirFile = new File(uploadDir);
                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdirs();
                    }
                    File destinationFile = new File(uploadDir + fileName);
                    photo.transferTo(destinationFile);
                    event.setPhotoPath(fileName); // Сохраняем только имя файла
                    System.out.println("Photo saved at: " + destinationFile.getAbsolutePath()); // Отладка
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Failed to save photo: " + e.getMessage());
                }
            } else {
                System.out.println("No photo uploaded for event: " + event.getTitle());
            }
            eventRepository.save(event);
        }
        return "redirect:/home";
    }

    @GetMapping("/events")
    public String viewEvent(@RequestParam("id") Long id, Model model) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
        model.addAttribute("event", event);
        System.out.println("Event loaded: " + event.getTitle()); // Для отладки
        return "event-details";
    }

}