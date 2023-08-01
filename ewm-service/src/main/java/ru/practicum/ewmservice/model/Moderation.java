package ru.practicum.ewmservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "moderation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Moderation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "moderation_id")
    Long id;

    @Column(name = "event_id")
    Long eventId;

    String comment;

    LocalDateTime created;
}
