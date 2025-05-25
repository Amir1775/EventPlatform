package com.example.eventplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e JOIN FETCH e.organizer JOIN FETCH e.category JOIN FETCH e.locationType WHERE e.id = :id")
    Optional<Event> findByIdWithDetails(@Param("id") Long id);

    List<Event> findByOrganizerId(Integer organizerId);
}
