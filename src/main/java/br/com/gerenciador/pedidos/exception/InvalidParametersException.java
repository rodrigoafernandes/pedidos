package br.com.gerenciador.pedidos.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.HttpClientErrorException;

import lombok.Getter;

@Getter
public class InvalidParametersException extends HttpClientErrorException {

	private static final long serialVersionUID = 2392538978966606203L;

	private List<ObjectError> errors;

	public InvalidParametersException(String errorMessage, List<ObjectError> errors) {
		super(HttpStatus.PRECONDITION_FAILED, errorMessage);
		this.errors = errors;
	}

}
