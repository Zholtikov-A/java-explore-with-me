package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.Moderation;

public interface ModerationRepository extends JpaRepository<Moderation, Long> {
}
