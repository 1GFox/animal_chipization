package ru.chernyshev.restful.repository;

import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.Animal;
import ru.chernyshev.restful.domain.VisitedLocation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomVisitedLocRepository {

    List<VisitedLocation> getVisitedLocByFilter(Animal animal,
                                                LocalDateTime startDateTime,
                                                LocalDateTime endDateTime,
                                                Integer from,
                                                Integer size);

}
