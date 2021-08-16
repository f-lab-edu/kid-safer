package com.flab.kidsafer.domain.enums;

public enum NotificationType implements CodeEnum {
    REQUEST_APPLICATION, REQUEST_SUCCESS, REQUEST_FAIL, BLOCK_STATUE_CHANGE;

    @Override
    public String getCode() {
        return String.valueOf(this.name());
    }
}
