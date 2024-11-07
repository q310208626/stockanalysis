package com.person.lsj.stock.constant;

public interface ErrorReason {
    String CSRF_ACCESS_DENINED = "csrf-access-denied";
    String CSRF_MISSING_DENINED = "csrf-missing-denied";

    String AUTH_NEED_LOGIN = "auth-need-login";
    String AUTH_USER_DENINED = "auth-user-denied";
    String AUTH_INTERNAL_ERROR_DENINED = "auth-internal-error-denied";
}
