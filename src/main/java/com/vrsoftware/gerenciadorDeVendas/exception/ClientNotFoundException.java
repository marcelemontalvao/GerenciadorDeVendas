package com.vrsoftware.gerenciadorDeVendas.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() { super("Cliente não encontrado."); }
    public ClientNotFoundException(String message) { super(message); }
}
