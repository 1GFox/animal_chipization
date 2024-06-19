package ru.chernyshev.restful.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visited_locations")
@SequenceGenerator(name = "visitedLocSeqGen", sequenceName = "visited_loc_seq", allocationSize = 1)
public class VisitedLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visitedLocSeqGen")
    @Column(name = "id")
    private Long id;
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "animal_id")
    private Animal animal;


}
