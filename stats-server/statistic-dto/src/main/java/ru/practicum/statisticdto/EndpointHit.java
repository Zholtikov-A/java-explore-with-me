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

    @NotBlank(message = "app can't be empty")
    String app;

    @NotBlank(message = "uri can't be empty")
    String uri;

    @NotBlank(message = "ip can't be empty")
    String ip;

    @NotBlank(message = "timestamp can't be empty")
    String timestamp;
}
