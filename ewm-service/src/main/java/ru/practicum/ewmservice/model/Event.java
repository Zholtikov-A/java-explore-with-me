package ru.practicum.ewmservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmservice.enums.StatusParticipation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "category_id")
    private Category category;

    @JoinColumn(name = "confirmed_requests")
    private Integer confirmedRequests;

    @JoinColumn(name = "created_on")
    private LocalDateTime createdOn;

    private String description;

    @JoinColumn(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator", referencedColumnName = "user_id")
    private User initiator;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "location_id")
    private Location location;

    private Boolean paid;

    @JoinColumn(name = "participant_limit")
    private Integer participantLimit;

    @JoinColumn(name = "published_on")
    private LocalDateTime publishedOn;

    @JoinColumn(name = "request_moderation")
    private Boolean requestModeration;

    @Enumerated(value = EnumType.STRING)
    private StatusParticipation state;

    private String title;

    private Integer views;
}