package ru.chernyshev.restful.repository;


import org.springframework.stereotype.Repository;
import ru.chernyshev.restful.domain.Account;

import java.util.List;

@Repository
public interface CustomAccountRepository {

    List<Account> findAccountByFilter(String firstName,
                                      String lastName,
                                      String email,
                                      Integer from,
                                      Integer size);



}
