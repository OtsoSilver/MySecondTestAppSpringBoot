package ru.tonkoshkurov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {
    Response modify(Response response);
}
