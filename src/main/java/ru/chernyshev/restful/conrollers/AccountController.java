package ru.chernyshev.restful.conrollers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.AccountDto;
import ru.chernyshev.restful.service.AccountService;

import java.util.List;

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

    @GetMapping("/search")
    public List<AccountDto> searchAccounts(@RequestParam(required = false) String firstName,
                                           @RequestParam(required = false) String lastName,
                                           @RequestParam(required = false) String email,
                                           @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                           @RequestParam(defaultValue = "10") @Min(1) Integer size) {

        return accountService.searchAccounts(firstName, lastName, email, from, size);

    }
}
