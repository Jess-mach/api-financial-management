package br.com.ntt.transacao.producer.domain.exception;

class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}