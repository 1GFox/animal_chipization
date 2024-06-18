package ru.chernyshev.restful.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImplAnimalRepository implements CustomAnimalRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Animal> findAnimalByFilter(LocalDateTime startDateTime,
                                           LocalDateTime endDateTime,
                                           Gender gender,
                                           Account account,
                                           Location location,
                                           LifeStatus lifeStatus,
                                           Integer from,
                                           Integer size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Animal> query = criteriaBuilder.createQuery(Animal.class);

        Root<Animal> root = query.from(Animal.class);

        List<Predicate> predicates = new ArrayList<>();

        if (startDateTime != null) {
            Predicate predicateStartDateTime = criteriaBuilder.greaterThan(root.get("chippingDateTime"), startDateTime);
            predicates.add(predicateStartDateTime);
        }
        if (endDateTime != null) {
            Predicate predicateEndDateTime = criteriaBuilder.lessThan(root.get("chippingDateTime"), endDateTime);
            predicates.add(predicateEndDateTime);
        }
        if (gender != null) {
            Predicate predicateGender = criteriaBuilder.equal(root.get("gender"), gender);
            predicates.add(predicateGender);
        }
        if (account != null) {
            Predicate predicateChipperId = criteriaBuilder.equal(root.get("chipper"), account);
            predicates.add(predicateChipperId);
        }
        if (location != null) {
            Predicate predicateLocation = criteriaBuilder.equal(root.get("chippingLocation"), location);
            predicates.add(predicateLocation);
        }
        if (lifeStatus != null) {
            Predicate predicateLifeStatus = criteriaBuilder.equal(root.get("lifeStatus"), lifeStatus);
            predicates.add(predicateLifeStatus);
        }

        Predicate globalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        query.where(globalPredicate)
                .orderBy(criteriaBuilder.asc(root.get("id")));


        return entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size).getResultList();
    }
}
