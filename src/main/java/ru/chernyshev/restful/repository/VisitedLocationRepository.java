package ru.chernyshev.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chernyshev.restful.domain.VisitedLocation;

public interface VisitedLocationRepository extends JpaRepository<VisitedLocation, Long> {

}
