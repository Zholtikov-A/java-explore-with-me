package ru.practicum.ewmservice.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewmservice.enums.StatusParticipation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    Long id;

    String annotation;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "category_id")
    Category category;

    @JoinColumn(name = "confirmed_requests")
    Integer confirmedRequests;

    @JoinColumn(name = "created_on")
    LocalDateTime createdOn;

    String description;

    @JoinColumn(name = "event_date")
    LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator", referencedColumnName = "user_id")
    User initiator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "location_id")
    Location location;

    Boolean paid;

    @JoinColumn(name = "participant_limit")
    Integer participantLimit;

    @JoinColumn(name = "published_on")
    LocalDateTime publishedOn;

    @JoinColumn(name = "request_moderation")
    Boolean requestModeration;

    @Enumerated(value = EnumType.STRING)
    StatusParticipation state;

    String title;

    Integer views;
}