package ru.chernyshev.restful.conrollers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.chernyshev.restful.dto.AccountDto;
import ru.chernyshev.restful.service.AccountService;

@Validated
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private AccountService accountService;


    @PostMapping
    public ResponseEntity<AccountDto> registration(@RequestBody @Valid AccountDto accountDto) {

        AccountDto dto = accountService.create(accountDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
