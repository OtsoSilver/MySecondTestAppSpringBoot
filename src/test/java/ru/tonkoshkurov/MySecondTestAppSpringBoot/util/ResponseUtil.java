package ru.tonkoshkurov.MySecondTestAppSpringBoot.util;

import ru.tonkoshkurov.MySecondTestAppSpringBoot.model.*;

import java.util.Date;

public class ResponseUtil {

    public static Response createSuccessResponse(Request request) {

        return Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(String.valueOf(Codes.SUCCESS))
                .errorCode(String.valueOf(ErrorCodes.EMPTY))
                .errorMessage(String.valueOf(ErrorMessages.EMPTY))
                .build();
    }
}
