package ru.chernyshev.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
