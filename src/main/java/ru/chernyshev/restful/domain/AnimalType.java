package ru.chernyshev.restful.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal_type")
@SequenceGenerator(name = "animalTypeSeqGen", sequenceName = "animalType_seq", allocationSize = 1)
public class AnimalType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animalTypeSeqGen")
    @Column
    private Long id;
    @Column
    private String type;
}
