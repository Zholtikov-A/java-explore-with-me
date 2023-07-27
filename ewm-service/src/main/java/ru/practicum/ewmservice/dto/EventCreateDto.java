package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventCreateDto {
    @Size(min = 20, max = 2000, message = "Annotation length must be between 20 and 2000 symbols")
    @NotBlank(message = "Annotation can't be empty")
    String annotation;

    Long category;

    @Size(min = 20, max = 7000, message = "Description length must be between 20 and 7000 symbols")
    @NotBlank(message = "Description can't be empty")
    String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    Location location;

    Boolean paid = false;

    @PositiveOrZero
    Integer participantLimit = 0;

    Boolean requestModeration = true;

    StateAction stateAction;

    @Size(min = 3, max = 120, message = "Title length must be between 3 and 120 symbols")
    @NotBlank(message = "Title can't be empty")
    String title;

}
