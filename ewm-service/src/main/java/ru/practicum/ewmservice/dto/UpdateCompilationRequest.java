package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

/**
 * Изменение информации о подборке событий.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRequest {

    /**
     * Список id событий подборки для полной замены текущего списка
     * */
    private Set<Long> events;

    private Boolean pinned;

    @Length(min = 1, max = 50, message = "Заголовок подборки не должен быть пустым и более 50 символов")
    private String title;

}
