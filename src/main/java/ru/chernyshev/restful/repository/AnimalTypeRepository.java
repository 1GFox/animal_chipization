package ru.chernyshev.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.AnimalType;

@Repository
public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

}
