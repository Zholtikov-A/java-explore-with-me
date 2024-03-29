package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.*;
import ru.practicum.ewmservice.enums.StateAction;
import ru.practicum.ewmservice.enums.StatusParticipation;
import ru.practicum.ewmservice.service.CategoryService;
import ru.practicum.ewmservice.service.CompilationService;
import ru.practicum.ewmservice.service.EventService;
import ru.practicum.ewmservice.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Validated
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid CategoryCreateDto category) {
        log.info("Get request to endpoint POST \"admin/categories\" createCategory {}", category);
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @Positive Long catId) {
        log.info("Get request to endpoint: DELETE \"admin/categories/{catId}\" deleteCategory, id = {}", catId);
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/categories/{catId}")
    public CategoryDto updateCategory(@PathVariable @Positive Long catId,
                                      @RequestBody @Valid CategoryCreateDto categoryDto) {
        log.info("Get request to endpoint: PATCH \"admin/categories/{catId}\" updateCategory, id = {}", catId);
        return categoryService.updateCategory(categoryDto, catId);
    }

    @GetMapping("/events")
    public List<EventFullDto> getEventsAdmin(@RequestParam(name = "users", required = false) List<Long> users,
                                             @RequestParam(name = "states", required = false) StatusParticipation status,
                                             @RequestParam(name = "categories", required = false) List<Long> categories,
                                             @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                             @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                             @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get request to endpoint: GET \"admin/events\" getEventsAdmin");
        return eventService.getEventsAdmin(users, status, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto updateEventAndStatus(@PathVariable @Positive Long eventId,
                                             @RequestBody @Validated EventAdminUpdateRequestDto adminRequest) {
        log.info("Get request to endpoint: PATCH \"admin/events/{eventId}\" updateEventAndStatus, id = {}", eventId);
        return eventService.updateEventAndStatus(eventId, adminRequest);
    }

    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Long> ids,
                                  @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                  @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get request to endpoint: GET \"admin/users\" getUsers");
        return userService.getUsers(ids, from, size);
    }


    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid UserCreateRequestDto userRequest) {
        log.info("Get request to endpoint: POST \"admin/users\" createUser");
        return userService.createUser(userRequest);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable @Positive Long userId) {
        log.info("Get request to endpoint: DELETE \"admin/users\" deleteUser, id = {}", userId);
        userService.deleteUser(userId);
    }

    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid CompilationCreateDto compilationCreateDto) {
        log.info("Get request to endpoint: POST \"admin/compilations\" createCompilation");
        return compilationService.create(compilationCreateDto);
    }

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable @Positive Long compId) {
        log.info("Get request to endpoint: DELETE \"admin/compilations/{compId}\" deleteCompilation, id = {}", compId);
        compilationService.delete(compId);
    }

    @PatchMapping("/compilations/{compId}")
    public CompilationDto updateCompilation(@PathVariable @Positive Long compId,
                                            @RequestBody @Valid CompilationUpdateRequestDto compilationUpdateRequestDto) {
        log.info("Get request to endpoint: PATCH \"admin/compilations/{compId}\" updateCompilation id = {}", compId);
        return compilationService.update(compId, compilationUpdateRequestDto);
    }

    @PostMapping("/{eventId}/moderation")
    public EventFullDto addModeration(@PathVariable Long eventId,
                                      @RequestBody @Valid ModerationDto moderationDto,
                                      @RequestParam(value = "state") StateAction state) {
        log.info("Get request to endpoint: POST \"admin/{eventId}/moderation\"  getModerationById c eventId = {}", eventId);
        return eventService.addAnswerModeration(eventId, moderationDto, state);
    }

    @GetMapping("/moderation")
    public List<EventShortDto> getEventWaitModeration() {
        log.info("Get request to endpoint: GET \"admin/moderation\" getEventWaitModeration");
        return eventService.getWaitModeration();
    }

    @GetMapping("/moderation/{modId}")
    public ModerationDto getModerationById(@PathVariable @Positive Long modId) {
        log.info("Get request to endpoint: GET \"admin/moderation/{modId}\" getModerationById c modId = {}", modId);
        return eventService.getModerationById(modId);
    }


}
