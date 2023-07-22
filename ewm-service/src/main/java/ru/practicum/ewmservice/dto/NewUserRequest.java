package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Данные нового пользователя
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {

    @Size(min = 2, max = 250, message = "Имя должно быть минимум 2 символа и не более 250 символов")
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Email(message = "Электронная почта не может быть пустой и должна содержать символ @")
    @Size(min = 6, max = 254, message = "Email должен быть минимум 6 символов и не более 254 символов")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

}
