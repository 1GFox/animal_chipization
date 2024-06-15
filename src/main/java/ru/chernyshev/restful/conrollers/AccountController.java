package ru.chernyshev.restful.conrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernyshev.restful.dto.AccountDto;
import ru.chernyshev.restful.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public AccountDto accountInfo(@PathVariable Integer accountId) {

        return accountService.getAccountById(accountId);

    }
}
