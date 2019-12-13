package br.com.gerenciador.pedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import lombok.Getter;

@Getter
public class PedidosNotFoundException extends HttpClientErrorException {

	private static final long serialVersionUID = -2114440257472766047L;

	private String messageError;
	
	public PedidosNotFoundException(String messageError) {
		super(HttpStatus.NOT_FOUND, messageError);
		this.messageError = messageError;
	}
	
}
