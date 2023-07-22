package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Подборка событий
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private List<EventShortDto> events;
    private Long id;

    private Boolean pinned;

    private String title;

}
