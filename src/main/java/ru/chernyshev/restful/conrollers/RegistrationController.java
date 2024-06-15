package ru.chernyshev.restful.conrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
