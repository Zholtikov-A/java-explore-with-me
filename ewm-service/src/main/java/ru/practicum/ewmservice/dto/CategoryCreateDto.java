package ru.practicum.ewmservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCreateDto {

    @Size(min = 1, max = 50, message = "Name length must be between 1 and 50 symbols")
    @NotBlank(message = "Name can't be empty")
    String name;
}
