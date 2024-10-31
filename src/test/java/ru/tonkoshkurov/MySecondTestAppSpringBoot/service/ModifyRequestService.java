package ru.tonkoshkurov.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.tonkoshkurov.MySecondTestAppSpringBoot.model.Request;

@Service
public interface ModifyRequestService {
    void modify(Request request);
}
