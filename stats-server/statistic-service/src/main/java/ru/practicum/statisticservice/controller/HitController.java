package ru.practicum.statisticservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statisticdto.EndpointHit;
import ru.practicum.statisticservice.service.StatService;

import javax.validation.Valid;

@RestController
@RequestMapping("/hit")
@Slf4j
@RequiredArgsConstructor
public class HitController {

    private final StatService statService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHit createInfo(@RequestBody @Valid EndpointHit endpointHit) {
        log.info("Получен запрос к эндпоинту \"/hits\" create");
        return statService.create(endpointHit);
    }
}
