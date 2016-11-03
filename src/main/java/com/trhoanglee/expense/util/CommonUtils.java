package com.trhoanglee.expense.util;

import javax.ws.rs.BadRequestException;

public class CommonUtils {
    public static Long parsePathVariableId(String id) {
        try {
             return Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new BadRequestException(String.format("'%s' is not a valid id", id));
        }
    }
}
