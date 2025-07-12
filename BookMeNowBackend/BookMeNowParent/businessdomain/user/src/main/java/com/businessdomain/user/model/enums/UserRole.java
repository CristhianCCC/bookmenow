package com.businessdomain.user.model.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;

public enum UserRole {

    CLIENT ("1"),

    PROVIDER("2");

    private final String code;

    UserRole(String code){
        this.code = code;
    }

    @JsonIgnore
    public String code () {
        return code;
    }
}
