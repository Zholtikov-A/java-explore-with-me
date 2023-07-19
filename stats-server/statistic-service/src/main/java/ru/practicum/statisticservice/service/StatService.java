package ru.practicum.statisticservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.statisticdto.EndpointHit;
import ru.practicum.statisticdto.ViewStats;
import ru.practicum.statisticservice.mapper.HitMapper;
import ru.practicum.statisticservice.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;

    public void create(EndpointHit endpointHit) {
        statRepository.save(HitMapper.toHit(endpointHit));
    }

    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, Boolean unique, List<String> uris) {
        List<ViewStats> viewStatsList;

        if (uris == null || uris.isEmpty()) {
            if (unique) {
                viewStatsList = statRepository.findViewStatsWithoutUrisUnigue(start, end, PageRequest.of(0, 10));
            } else {
                viewStatsList = statRepository.findViewStatsWithoutUris(start, end, PageRequest.of(0, 10));
            }
        } else {
            if (unique) {
                viewStatsList = statRepository.findViewStatsUniqueIp(start, end, uris);
            } else {
                viewStatsList = statRepository.findViewStats(start, end, uris);
            }
        }


        return viewStatsList;
    }
}
