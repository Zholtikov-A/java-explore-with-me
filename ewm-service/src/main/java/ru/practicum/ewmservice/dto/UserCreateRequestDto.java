package ru.practicum.ewmservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequestDto {

    @Size(min = 2, max = 250, message = "Name length must be between 2 and 250 symbols")
    @NotBlank(message = "Name can't be empty")
    private String name;

    @Email(message = "Email can't be empty and must contain \"@\" symbol")
    @Size(min = 6, max = 254, message = "Email length must be between 6 and 254 symbols")
    @NotBlank(message = "Email can't be empty")
    private String email;

}
