package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Slf4j
public class CompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> get(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                    @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                    @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get request to endpoint: GET \"/compilations\" get");
        return compilationService.get(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable @Positive Long compId) {
        log.info("Get request to endpoint: GET \"/compilations/{compId}\" compId = {}", compId);
        return compilationService.getById(compId);
    }

}
