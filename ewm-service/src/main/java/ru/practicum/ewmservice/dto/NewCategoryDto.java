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
public class NewCategoryDto {

    @Size(min = 1, max = 50, message = "Имя категории не должно быть пустым и более 50 символов")
    @NotBlank(message = "Name не может быть пустым")
    String name;
}
