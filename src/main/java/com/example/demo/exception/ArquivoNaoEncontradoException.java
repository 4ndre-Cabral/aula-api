package com.example.demo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArquivoNaoEncontradoException extends RuntimeException {
	public ArquivoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ArquivoNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
