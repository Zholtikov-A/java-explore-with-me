package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmservice.enums.StateAction;
import ru.practicum.ewmservice.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @Size(min = 20, max = 2000, message = "Короткое описание должно быть минимум 20 символов и не более не более 2000 символов")
    @NotBlank(message = "Короткое описание не должно быть пустым")
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000, message = "Описание должно быть минимум 20 символов и не более не более 7000 символов")
    @NotBlank(message = "Описание не должно быть пустым")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid = false;

    @PositiveOrZero
    private Integer participantLimit = 0;

    private Boolean requestModeration = true;

    private StateAction stateAction;

    @Size(min = 3, max = 120, message = "Новый заголовок должен быть минимум 3 символа и не более 120 символов")
    @NotBlank(message = "title не должно быть пустым")
    private String title;

}
