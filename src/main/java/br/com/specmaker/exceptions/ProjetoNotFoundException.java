package br.com.specmaker.exceptions;

public class ProjetoNotFoundException extends RuntimeException {
    public ProjetoNotFoundException() {}

    public ProjetoNotFoundException(String message) {
        super(message);
    }
}
