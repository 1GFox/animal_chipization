package ru.chernyshev.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);

}
