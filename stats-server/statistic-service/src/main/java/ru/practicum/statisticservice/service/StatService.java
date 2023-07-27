package ru.practicum.statisticservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.statisticdto.EndpointHit;
import ru.practicum.statisticdto.ViewStats;
import ru.practicum.statisticservice.exceptions.BadRequestException;
import ru.practicum.statisticservice.mapper.HitMapper;
import ru.practicum.statisticservice.model.Hit;
import ru.practicum.statisticservice.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRepository statRepository;

    public EndpointHit create(EndpointHit endpointHit) {
        Hit hit = statRepository.save(HitMapper.toHit(endpointHit));
        return HitMapper.toHitDto(hit);
    }

    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, Boolean unique, String[] uris) {
        List<ViewStats> viewStatsList;
        if (start.isAfter(end)) {
            throw new BadRequestException("Неверные датами начала и конца диапазона");
        }

        if (uris == null || uris.length == 0) {
            if (unique) {
                viewStatsList = statRepository.findViewStatsWithoutUrisUnique(start, end, PageRequest.of(0, 10));
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
