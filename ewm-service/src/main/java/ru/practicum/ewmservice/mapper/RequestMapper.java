package ru.practicum.ewmservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmservice.model.Request;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;

import java.time.LocalDateTime;

@UtilityClass
public class RequestMapper {
    public static Request toRequest(Long userId, Long eventId) {
        return Request.builder()
                .created(LocalDateTime.now())
                .event(eventId)
                .requester(userId)
                .status(null)
                .build();
    }

    public static ParticipationRequestDto toParticipationRequestDto(Request save) {
        return ParticipationRequestDto.builder()
                .id(save.getId())
                .created(save.getCreated())
                .event(save.getEvent())
                .requester(save.getRequester())
                .status(save.getStatus())
                .build();
    }
}
