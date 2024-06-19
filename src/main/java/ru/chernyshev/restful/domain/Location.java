package ru.chernyshev.restful.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location")
@SequenceGenerator(name = "locationSeqGen", sequenceName = "location_seq", allocationSize = 1)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locationSeqGen")
    @Column(name = "id")
    private Long id;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Animal> animalsChippedHere = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<VisitedLocation> visitedLocationsWithThisLoc = new ArrayList<>();


}
