package ru.chernyshev.restful.conrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.AccountDto;
import ru.chernyshev.restful.service.AccountService;


@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private AccountService accountService;


    @PostMapping
    public AccountDto registration(@RequestBody AccountDto accountDto) {
        return accountService.create(accountDto);
    }
}
