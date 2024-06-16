package ru.chernyshev.restful.mapper;

import org.springframework.stereotype.Component;
import ru.chernyshev.restful.domain.Account;
import ru.chernyshev.restful.dto.AccountDto;

@Component
public class AccountMapper implements Mapper<Account, AccountDto> {

    @Override
    public AccountDto toDto(Account account) {
        return new AccountDto(account.getId(), account.getFirstName(), account.getLastName(), account.getEmail(), null);
    }

}