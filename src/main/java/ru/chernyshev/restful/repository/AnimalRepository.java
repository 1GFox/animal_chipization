package ru.chernyshev.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernyshev.restful.domain.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
