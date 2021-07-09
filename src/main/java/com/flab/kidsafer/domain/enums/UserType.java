package com.flab.kidsafer.domain.enums;

import com.flab.kidsafer.mybatis.handler.CodeEnumTypeHandler;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

public enum UserType implements CodeEnum {
  PARENT("PARENT"),
  SAFER("SAFER"),
  ADMIN("ADMIN");

  private final String value;

  UserType(String value) {
    this.value = value;
  }

  @Override
  public String getCode() {
    return String.valueOf(value);
  }

  @Component
  @MappedTypes(UserType.class)
  public static class TypeHandler extends CodeEnumTypeHandler<UserType> {

    public TypeHandler() {
      super(UserType.class);
    }
  }
}
