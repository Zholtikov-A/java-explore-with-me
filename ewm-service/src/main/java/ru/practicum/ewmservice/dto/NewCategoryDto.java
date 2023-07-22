package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Данные для добавления новой категории
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {

    @Size(min = 1, max = 50, message = "Имя категории не должно быть пустым и более 50 символов")
    @NotBlank(message = "Name не может быть пустым")
    private String name;
}
