package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewmservice.enums.StatusParticipation;
import ru.practicum.ewmservice.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByInitiatorId(Long userId, Pageable pageable);

    Event findByIdAndInitiatorId(Long eventId, Long userId);

    Event findTopByCategoryId(Long catId);

    @Query("SELECT e FROM Event e " +
            "WHERE e.state = :status " +
            "AND e.eventDate >= :start " +
            "AND e.eventDate <= :end " +
            "AND e.initiator.id IN :users " +
            "AND e.category.id IN :categories ")
    Page<Event> findEvents(@Param("status") StatusParticipation status,
                           @Param("start") LocalDateTime rangeStart,
                           @Param("end") LocalDateTime rangeEnd,
                           @Param("users") List<Long> users,
                           @Param("categories") List<Long> categories,
                           Pageable pageable);

    Event findByIdAndState(Long eventId, StatusParticipation statusParticipation);

    Page<Event> findAll(Specification<Event> spec, Pageable pageable);

    List<Event> findAllByIdIn(Set<Long> events);

    List<Event> findByState(StatusParticipation statusParticipation);

}
