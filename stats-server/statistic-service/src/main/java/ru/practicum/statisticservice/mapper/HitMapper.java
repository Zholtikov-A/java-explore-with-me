package ru.practicum.statisticservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.statisticdto.EndpointHit;
import ru.practicum.statisticservice.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class HitMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Hit toHit(EndpointHit endpointHit) {

        return Hit.builder()
                .app(endpointHit.getApp())
                .created(LocalDateTime.parse(endpointHit.getTimestamp(), formatter))
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .build();
    }

    public EndpointHit toHitDto(Hit hit) {
        return EndpointHit.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .ip(hit.getIp())
                .uri(hit.getUri())
                .timestamp(hit.getCreated().toString())
                .build();
    }

}
