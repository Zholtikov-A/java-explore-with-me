package ru.practicum.statisticservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<ViewStats> getStats(@RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                    @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                    @RequestParam(name = "unique", defaultValue = "false") Boolean unique,
                                    @RequestParam(name = "uris", required = false) List<String> uris
    ) {
        log.info("Получен запрос к эндпоинту \"/stats\" getStats");
        return statService.getStats(start, end, unique, uris);
    }

    @PostMapping("/hit")
    public ResponseEntity<String> createInfo(@RequestBody @Valid EndpointHit endpointHit) {
        log.info("Получен запрос к эндпоинту \"/hit\" create");
        statService.create(endpointHit);
        return new ResponseEntity<>("Информация сохранена", HttpStatus.CREATED);
    }


}
