package ru.chernyshev.restful.repository;

import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.*;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomAnimalRepository {

    List<Animal> findAnimalByFilter(LocalDateTime startDateTime,
                                    LocalDateTime endDateTime,
                                    Gender gender,
                                    Account account,
                                    Location location,
                                    LifeStatus lifeStatus,
                                    Integer from,
                                    Integer size);
}
