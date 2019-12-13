package br.com.gerenciador.pedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import lombok.Getter;

@Getter
public class ItensPedidoNotFoundException extends HttpClientErrorException {

	private static final long serialVersionUID = -475698427675010983L;

	private String messageError;
	
	public ItensPedidoNotFoundException(String messageError) {
		super(HttpStatus.NOT_FOUND, messageError);
		this.messageError = messageError;
	}

}
