package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.Account;
import ru.chernyshev.restful.dto.AccountDto;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.AccountMapper;
import ru.chernyshev.restful.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;

    public AccountDto create(AccountDto dto) {

        Account account = new Account();


        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setEmail(dto.getEmail());
        account.setPassword(dto.getPassword());

        accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    public AccountDto getAccountById(Integer id) {
        return accountRepository.findById(id)
                .map(accountMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Account with this id has not found: " + id));
    }
}
