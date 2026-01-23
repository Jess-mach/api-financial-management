package br.com.alura.codechella.domain.exception;

class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}