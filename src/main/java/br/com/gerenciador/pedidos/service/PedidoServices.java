package br.com.gerenciador.pedidos.service;

import br.com.gerenciador.pedidos.converter.ParametersConverter;
import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.exception.InvalidParametersException;
import br.com.gerenciador.pedidos.repository.PedidoRepository;
import br.com.gerenciador.pedidos.validator.ParametersValidator;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@RequestScoped
public class PedidoServices {

	@Inject
	PedidoRepository pedidoRepository;

	@Inject
	ParametersConverter parametersConverter;

	@Inject
	ParametersValidator validator;

	public List<PedidoDTO> findPedidos(String numeroPedido, String dataCadastro, String nomeCliente) {
		Set<ConstraintViolation<FiltroPedidosDTO>> constraintViolations = new HashSet<>();

		FiltroPedidosDTO filtro = parametersConverter.convert(numeroPedido, dataCadastro, nomeCliente,
				constraintViolations);

		if (isNotEmpty(constraintViolations)) {
			throw new InvalidParametersException("Parâmetros de request inválidos", constraintViolations);
		}

		validator.validate(filtro, constraintViolations);

		if (isNotEmpty(constraintViolations)) {
			throw new InvalidParametersException("Parâmetros de request inválidos", constraintViolations);
		}

		return pedidoRepository.findByFilter(filtro);
	}

}
