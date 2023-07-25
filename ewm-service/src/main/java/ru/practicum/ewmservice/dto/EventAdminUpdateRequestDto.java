package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewmservice.enums.StateAction;
import ru.practicum.ewmservice.model.Location;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventAdminUpdateRequestDto {
    @Size(min = 20, max = 2000, message = "Annotation length must be between 20 and 2000 symbols")
    String annotation;

    Integer category;

    @Size(min = 20, max = 7000, message = "Description length must be between 20 and 7000 symbols")
    String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    StateAction stateAction;

    @Size(min = 3, max = 120, message = "Title length must be between 3 and 120 symbols")
    String title;
}
