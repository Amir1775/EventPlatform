package com.example.eventplatform.repository;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String address;
    private String startTime;
    private String endTime;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] photo;

    @ManyToOne
    private User organizer;

    @ManyToOne
    private Category category;

    @ManyToOne
    private LocationType locationType;
}