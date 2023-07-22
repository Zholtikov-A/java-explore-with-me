package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * Подборка событий
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private Set<Long> events;
    private Boolean pinned = false;
    @Length(min = 1, max = 50, message = "Заголовок подборки не должен быть пустым и более 50 символов")
    @NotBlank(message = "Заголовок не должен быть пустым")
    private String title;
}
