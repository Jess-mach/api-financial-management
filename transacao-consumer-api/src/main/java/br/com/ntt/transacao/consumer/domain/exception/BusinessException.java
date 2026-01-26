package br.com.ntt.transacao.consumer.domain.exception;

class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}