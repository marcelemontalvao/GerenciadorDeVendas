package com.vrsoftware.gerenciadorDeVendas.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {
    public HttpStatus status;
    public String message;
}
