package ru.tonkoshkurov.MySecondTestAppSpringBoot.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.model.Request;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.model.Response;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.service.ValidationService;

@RestController
public class MyController {

    private final ValidationService validationService;

    public MyController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request, BindingResult bindingResult) {

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(request.getSystemTime())
                .code("success")
                .errorCode("")
                .errorMessage("")
                .build();
        try {
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e){
            response.setCode("Failed");
            response.setErrorMessage("ValidationException");
            response.setErrorMessage("Ошибка валидации");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
