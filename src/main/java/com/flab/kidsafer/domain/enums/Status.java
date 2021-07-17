package com.flab.kidsafer.domain.enums;

import com.flab.kidsafer.mybatis.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

public enum Status implements CodeEnum {
    BLOCKED(-1),
    NOT_CONFIRMED(0),
    DEFAULT(1);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public static Status valueOf(int value) {
        switch (value) {
            case -1:
                return BLOCKED;
            case 0:
                return NOT_CONFIRMED;
            case 1:
                return DEFAULT;
            default:
                throw new AssertionError("알수 없는 값입니다 : " + value);
        }
    }

    @Override
    public String getCode() {
        return String.valueOf(value);
    }

    @Component
    @MappedTypes(Status.class)
    public static class TypeHandler extends CodeEnumTypeHandler<Status> {

        public TypeHandler() {
            super(Status.class);
        }
    }
}
