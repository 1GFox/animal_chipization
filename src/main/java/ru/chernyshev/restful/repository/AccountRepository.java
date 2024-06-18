package ru.chernyshev.restful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByEmail(String email);

}
