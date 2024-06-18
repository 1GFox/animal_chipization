package ru.chernyshev.restful.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.Account;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ImplAccountRepository implements CustomAccountRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Account> findAccountByFilter(String firstName,
                                             String lastName,
                                             String email,
                                             Integer from,
                                             Integer size) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);

        Root<Account> root = query.from(Account.class);

        List<Predicate> predicates = new ArrayList<>();

        if (firstName != null) {
            Predicate predicateFirstName = criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")), "%" + firstName.toUpperCase() + "%");
            predicates.add(predicateFirstName);
        }
        if (lastName != null) {
            Predicate predicateLastName = criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")), "%" + lastName.toUpperCase() + "%");
            predicates.add(predicateLastName);
        }
        if (email != null) {
            Predicate predicateEmail = criteriaBuilder.like(criteriaBuilder.upper(root.get("email")), "%" + email.toUpperCase() + "%");
            predicates.add(predicateEmail);
        }

        Predicate globalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        query.where(globalPredicate)
                .orderBy(criteriaBuilder.asc(root.get("id")));


        return entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
    }
}
