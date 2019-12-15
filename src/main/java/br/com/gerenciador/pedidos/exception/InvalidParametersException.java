package br.com.gerenciador.pedidos.exception;

import br.com.gerenciador.pedidos.dto.ErrorMessageDTO;
import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class InvalidParametersException extends RuntimeException {

	private static final long serialVersionUID = 2392538978966606203L;

	private Set<ConstraintViolation<FiltroPedidosDTO>> constraintViolations;

	public InvalidParametersException(String errorMessage, Set<ConstraintViolation<FiltroPedidosDTO>> errors) {
		super(errorMessage);
		this.constraintViolations = errors;
	}

	public List<ErrorMessageDTO> getErrors() {
		return constraintViolations.stream().map(this::convert).collect(toList());
	}

	private ErrorMessageDTO convert(ConstraintViolation<FiltroPedidosDTO> violation) {
		return ErrorMessageDTO.builder().property(violation.getPropertyPath().toString())
				.message(violation.getMessage()).build();
	}

}
