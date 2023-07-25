package ru.practicum.statisticservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statisticdto.EndpointHit;
import ru.practicum.statisticdto.ViewStats;
import ru.practicum.statisticservice.service.StatService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatService statService;

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                    @RequestParam(defaultValue = "false") boolean unique,
                                    @RequestParam(required = false) String[] uris
    ) {
        log.info("Get request to endpoint: GET \"/stats\" getStats");
        return statService.getStats(start, end, unique, uris);
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHit createInfo(@RequestBody @Valid EndpointHit endpointHit) {
        log.info("Get request to endpoint: POST \"/hit\" create");
        return statService.create(endpointHit);
    }

}