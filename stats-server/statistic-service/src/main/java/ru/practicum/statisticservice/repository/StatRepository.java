package ru.practicum.statisticservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statisticdto.ViewStats;
import ru.practicum.statisticservice.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Hit, Long> {

    @Query("SELECT new ru.practicum.statisticdto.ViewStats(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.created BETWEEN ?1 AND ?2 AND h.uri IN (?3) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC"
    )
    List<ViewStats> findViewStatsUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.statisticdto.ViewStats(h.app, h.uri, COUNT(h)) " +
            "FROM Hit h " +
            "WHERE h.created BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h) DESC")
    List<ViewStats> findViewStatsWithoutUris(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT new ru.practicum.statisticdto.ViewStats(h.app, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.created BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<ViewStats> findViewStatsWithoutUrisUnigue(LocalDateTime start, LocalDateTime end, Pageable pageable);

    @Query("SELECT new ru.practicum.statisticdto.ViewStats(h.app, h.uri, COUNT(h)) " +
            "FROM Hit h " +
            "WHERE h.created BETWEEN ?1 AND ?2 AND h.uri IN (?3) " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h) DESC"
    )
    List<ViewStats> findViewStats(LocalDateTime start, LocalDateTime end, List<String> uris);

}
