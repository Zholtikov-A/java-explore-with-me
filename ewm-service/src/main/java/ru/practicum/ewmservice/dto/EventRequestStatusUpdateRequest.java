package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmservice.enums.StatusEventRequest;


import java.util.Set;

/**
 * Изменение статуса запроса на участие в событии текущего пользователя
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateRequest {
    private Set<Long> requestIds;
    private StatusEventRequest status;
}
