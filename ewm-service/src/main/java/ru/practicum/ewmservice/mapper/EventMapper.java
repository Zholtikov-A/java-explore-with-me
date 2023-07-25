package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.dto.*;
import ru.practicum.ewmservice.enums.StatusParticipation;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.Location;
import ru.practicum.ewmservice.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {
    public static Event toEvent(NewEventDto newEventDto, Category category, Location location, User initiator, LocalDateTime created) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .confirmedRequests(0)
                .createdOn(created)
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .initiator(initiator)
                .location(newEventDto.getLocation())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .state(StatusParticipation.PENDING)
                .publishedOn(LocalDateTime.now())
                .views(0)
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .paid(event.getPaid())
                .views(event.getViews())
                .title(event.getTitle())
                .confirmedRequests(event.getConfirmedRequests())
                .build();
    }

    public static void toEventDto() {
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryDto.builder()
                        .id(event.getCategory().getId())
                        .name(event.getCategory().getName())
                        .build())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(UserShortDto.builder()
                        .id(event.getInitiator().getId())
                        .name(event.getInitiator().getName())
                        .build())
                .location(LocationDto.builder()
                        .lat(event.getLocation().getLat())
                        .lon(event.getLocation().getLon())
                        .build())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEventFromFullDto(EventFullDto eventFullDto) {
        return Event.builder()
                .annotation(eventFullDto.getAnnotation())
                .category(Category.builder()
                        .id(eventFullDto.getCategory().getId())
                        .name(eventFullDto.getCategory().getName())
                        .build())
                .confirmedRequests(eventFullDto.getConfirmedRequests())
                .createdOn(eventFullDto.getCreatedOn())
                .description(eventFullDto.getDescription())
                .eventDate(eventFullDto.getEventDate())
                .initiator(User.builder()
                        .id(eventFullDto.getInitiator().getId())
                        .name(eventFullDto.getInitiator().getName())
                        .build())
                .location(Location.builder()
                        .lat(eventFullDto.getLocation().getLat())
                        .lon(eventFullDto.getLocation().getLon())
                        .build())
                .paid(eventFullDto.getPaid())
                .participantLimit(eventFullDto.getParticipantLimit())
                .requestModeration(eventFullDto.getRequestModeration())
                .title(eventFullDto.getTitle())
                .state(StatusParticipation.PENDING)
                .publishedOn(LocalDateTime.now())
                .views(0)
                .build();
    }
}
