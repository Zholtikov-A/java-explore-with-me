package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.Request;

import java.util.List;
import java.util.Set;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequester(Long userId);

    Request findByIdAndRequester(Long requestId, Long userId);

    List<Request> findAllByEvent(Long eventId);

    List<Request> findAllByEventAndIdIn(Long eventId, Set<Long> requestIds);
}
