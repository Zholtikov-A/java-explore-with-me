package ru.practicum.statisticdto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointHit {
    Long id;

    @NotBlank(message = "app не может быть пустым")
    String app;

    @NotBlank(message = "uri не может быть пустым")
    String uri;

    @NotBlank(message = "ip не может быть пустым")
    String ip;

    @NotBlank(message = "timestamp не может быть пустым")
    String timestamp;
}
