package ru.chernyshev.restful.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.Animal;
import ru.chernyshev.restful.domain.VisitedLocation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImplVisitedLocRepository implements CustomVisitedLocRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<VisitedLocation> getVisitedLocByFilter(Animal animal,
                                                       LocalDateTime startDateTime,
                                                       LocalDateTime endDateTime,
                                                       Integer from,
                                                       Integer size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<VisitedLocation> query = criteriaBuilder.createQuery(VisitedLocation.class);

        Root<VisitedLocation> root = query.from(VisitedLocation.class);


        Predicate predicateAnimal = criteriaBuilder.equal(root.get("animal"), animal);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(predicateAnimal);
        if (startDateTime != null) {
            Predicate predicateStartDate = criteriaBuilder.greaterThan(root.get("dateTime"), startDateTime);
            predicates.add(predicateStartDate);
        }
        if (endDateTime != null) {
            Predicate predicateEndDate = criteriaBuilder.lessThan(root.get("dateTime"), endDateTime);
            predicates.add(predicateEndDate);
        }

        Predicate globalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        query.where(globalPredicate)
                .orderBy(criteriaBuilder.asc(root.get("dateTime")));


        return entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

    }
}
