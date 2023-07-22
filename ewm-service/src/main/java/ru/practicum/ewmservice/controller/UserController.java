package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.*;
import ru.practicum.ewmservice.service.EventService;
import ru.practicum.ewmservice.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final EventService eventService;
    private final RequestService requestService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getEventForUser(@PathVariable @Positive Long userId,
                                               @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                               @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Получен запрос к эндпоинту: users/{userId}/events getEventByUserId userId={}", userId);

        return eventService.getEventForUser(userId, from, size);
    }

    @PostMapping("/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable @Positive Long userId,
                                    @RequestBody @Valid NewEventDto eventDto) {
        log.info("Получен запрос к эндпоинту: users/{userId}/events createEvent userId={}", userId);
        return eventService.createEvent(userId, eventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventFullDto getEventByUserAndEventId(@PathVariable @Positive Long userId,
                                                 @PathVariable @Positive Long eventId) {
        log.info("Получен запрос к эндпоинту: users/{userId}/events/{eventId} getEventByUserAndEventId userId={}, eventId={}", userId, eventId);
        return eventService.getEventByUserAndEventId(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventFullDto updateEvent(@PathVariable @Positive Long userId,
                                    @PathVariable @Positive Long eventId,
                                    @RequestBody @Validated UpdateEventUserRequest eventUserRequest) {
        log.info("Получен запрос к эндпоинту: users/{userId}/events/{eventId} updateEvent userId={}, eventId={}", userId, eventId);
        return eventService.updateEvent(userId, eventId, eventUserRequest);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestEventByUser(@PathVariable @Positive Long userId,
                                                               @PathVariable @Positive Long eventId) {
        log.info("Получен запрос к эндпоинту: users/{userId}/events/{eventId}/requests getRequestEventByUser userId={}, eventId={}", userId, eventId);
        return requestService.getRequestEventByUser(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestEventByUser(@PathVariable @Positive Long userId,
                                                                   @PathVariable @Positive Long eventId,
                                                                   @RequestBody @Valid EventRequestStatusUpdateRequest statusUpdateRequest) {
        log.info("Получен запрос к эндпоинту: users/{userId}/events/{eventId}/requests updateRequestEventByUser userId={}, eventId={}", userId, eventId);

        return requestService.updateRequestStatus(userId, eventId, statusUpdateRequest);
    }

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     */
    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getAllRequest(@PathVariable @Positive Long userId) {
        log.info("Получен запрос к эндпоинту: users/{userId}/requests getAllRequest userId={}", userId);
        return requestService.getRequest(userId);
    }

    @PostMapping("/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addRequest(@PathVariable @Positive Long userId,
                                              @RequestParam(name = "eventId") @Positive Long eventId) {
        log.info("Получен запрос к эндпоинту: users/{userId}/requests addRequest userId={}, eventId={}", userId, eventId);
        return requestService.addRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelledRequest(@PathVariable @Positive Long userId,
                                                    @PathVariable @Positive Long requestId) {
        log.info("Получен запрос к эндпоинту: users/{userId}/requests/{requestId}/cancel cancelledRequest userId={}, requestId={}", userId, requestId);
        return requestService.updateRequest(userId, requestId);
    }

}