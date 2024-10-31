package ru.tonkoshkurov.MySecondTestAppSpringBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.model.*;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.service.ModifyRequestService;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.service.ValidationService;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.util.ResponseUtil;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;


@Slf4j
@RestController
public class MyController {

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                        ModifyRequestService modifyRequestService) {

        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@RequestBody Request request, BindingResult bindingResult) {
        log.info("request: {}", request);
        Response response = ResponseUtil.createSuccessResponse(request);
        log.info("response: {}", response);

        try {
            if (Integer.parseInt(request.getUid()) == 123) {
                throw new UnsupportedCodeException("UID 123 не поддерживается");
            }
            validationService.isValid(bindingResult);
        } catch (ValidationFailedException e) {
            response.setCode(String.valueOf(Codes.FAILED));
            response.setErrorCode(String.valueOf(ErrorCodes.VALIDATION_EXCEPTION));
            response.setErrorMessage(String.valueOf(ErrorMessages.VALIDATION));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            response.setCode(String.valueOf(Codes.FAILED));
            response.setErrorCode(String.valueOf(ErrorCodes.VALIDATION_EXCEPTION));
            response.setErrorMessage(String.valueOf(ErrorMessages.VALIDATION));
            log.error("Error mess:", e);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            response.setCode(String.valueOf(Codes.FAILED));
            response.setErrorCode(String.valueOf(ErrorCodes.UNKNOWN_EXCEPTION));
            response.setErrorMessage(String.valueOf(ErrorMessages.UNKNOWN));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseService.modify(response);
        modifyRequestService.modify(request);
        log.info("response: {}", response);
        return new ResponseEntity<>(modifyResponseService.modify(response), HttpStatus.OK);
    }
}


