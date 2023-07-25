package ru.practicum.ewmservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationUpdateRequestDto {

    Set<Long> events;

    Boolean pinned;

    @Length(min = 1, max = 50, message = "Title length must be between 1 and 50 symbols")
    String title;

}
