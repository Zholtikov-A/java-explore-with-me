package ru.practicum.statisticservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hit_id")
    private Long id;
    @Column(name = "app")
    private String app;
    @Column(name = "uri")
    private String uri;
    @Column(name = "ip")
    private String ip;
    @Column(name = "created")
    private LocalDateTime created;

}
