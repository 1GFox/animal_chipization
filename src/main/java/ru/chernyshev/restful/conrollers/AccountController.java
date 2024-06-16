package ru.chernyshev.restful.conrollers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.AccountDto;
import ru.chernyshev.restful.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public AccountDto accountInfo(@PathVariable @Min(1) Integer accountId) {
        return accountService.getAccountById(accountId);
    }

    @PutMapping("/{accountId}")
    public AccountDto updateAccount(@PathVariable @Min(1) Integer accountId, @RequestBody AccountDto newAccountInfo) {
        return accountService.updateAccountInfo(accountId, newAccountInfo);
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable @Min(1) Integer accountId) {
        accountService.deleteAccount(accountId);
    }
}
