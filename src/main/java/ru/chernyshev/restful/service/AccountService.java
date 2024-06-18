package ru.chernyshev.restful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chernyshev.restful.domain.Account;
import ru.chernyshev.restful.dto.AccountDto;
import ru.chernyshev.restful.exception.DataConflictException;
import ru.chernyshev.restful.exception.InaccessibleEntityException;
import ru.chernyshev.restful.exception.NotFoundException;
import ru.chernyshev.restful.mapper.Mapper;
import ru.chernyshev.restful.repository.AccountRepository;
import ru.chernyshev.restful.repository.CustomAccountRepository;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private Mapper<Account, AccountDto> accountMapper;
    @Autowired
    private CustomAccountRepository customAccountRepository;

    public AccountDto create(AccountDto dto) {

        boolean check = accountRepository.existsByEmail(dto.getEmail());
        if (check){
            throw new DataConflictException("Account with this email already exists");
        }

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

    public AccountDto updateAccountInfo(Integer id, AccountDto dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new InaccessibleEntityException("Account with this id has not found: " + id));

        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setEmail(dto.getEmail());
        account.setPassword(dto.getPassword());


        accountRepository.save(account);

        return accountMapper.toDto(account);
    }

    public void deleteAccount(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new InaccessibleEntityException("Account with this id has not found: " + id));

        accountRepository.delete(account);
    }

    public List<AccountDto> searchAccounts(String firstName, String lastName, String email, Integer from, Integer size) {
        return customAccountRepository.findAccountByFilter(firstName, lastName, email, from, size)
                .stream()
                .map(accountMapper::toDto).toList();
    }
}
