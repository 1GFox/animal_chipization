package ru.chernyshev.restful.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal")
@SequenceGenerator(name = "animalSeqGen", sequenceName = "animal_seq", allocationSize = 1)
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animalSeqGen")
    @Column(name = "id")
    private Long id;
    @Column(name = "weight")
    private Float weight;
    @Column(name = "length")
    private Float length;
    @Column(name = "height")
    private Float height;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "life_status")
    private LifeStatus lifeStatus;
    @Column(name = "chipping_date_time")
    private LocalDateTime chippingDateTime;
    @Column(name = "death_date_time")
    private LocalDateTime deathDateTime;

    @ManyToOne
    @JoinColumn(name = "chipping_location_id")
    private Location chippingLocation;

    @ManyToOne
    @JoinColumn(name = "chipper_id")
    private Account chipper;

    @ManyToMany
    @JoinTable(name = "animal_types_rel",
            joinColumns = @JoinColumn(name = "animal_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private List<AnimalType> animalTypes = new ArrayList<>();


    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE)
    private List<VisitedLocation> visitedLocations = new ArrayList<>();

}
