package br.com.gerenciador.pedidos.resource.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.gerenciador.pedidos.dto.ErrorDTO;
import br.com.gerenciador.pedidos.exception.InvalidParametersException;
import br.com.gerenciador.pedidos.exception.ItensPedidoNotFoundException;
import br.com.gerenciador.pedidos.exception.PedidosNotFoundException;

@RestControllerAdvice
public class PedidoResourceExceptionHandler {

	@ExceptionHandler(InvalidParametersException.class)
	public ResponseEntity<?> invalidParameters(InvalidParametersException exception) {
		return ResponseEntity.status(exception.getStatusCode()).body(exception.getErrors());
	}

	@ExceptionHandler(PedidosNotFoundException.class)
	public ResponseEntity<?> pedidosNotFound(PedidosNotFoundException exception) {
		return ResponseEntity.status(exception.getStatusCode())
				.body(ErrorDTO.builder().message(exception.getMessageError()).build());
	}
	
	@ExceptionHandler(ItensPedidoNotFoundException.class)
	public ResponseEntity<?> itensPedidoNotFound(ItensPedidoNotFoundException exception) {
		return ResponseEntity.status(exception.getStatusCode())
				.body(ErrorDTO.builder().message(exception.getMessageError()).build());
	}

}
