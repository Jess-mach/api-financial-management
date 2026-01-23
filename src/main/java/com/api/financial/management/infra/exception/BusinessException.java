package com.api.financial.management.infra.exception;

class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}