package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> get(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                 @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get request to endpoint: GET \"/categories\" get");
        return categoryService.get(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable @Positive Long catId) {
        log.info("Get request to endpoint: GET \"/categories/{catId}\" getById id = {}", catId);
        return categoryService.getById(catId);
    }
}
