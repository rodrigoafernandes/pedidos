package br.com.gerenciador.pedidos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;

import br.com.gerenciador.pedidos.converter.ParametersConverter;
import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.exception.InvalidParametersException;
import br.com.gerenciador.pedidos.repository.PedidoRepository;
import br.com.gerenciador.pedidos.validator.ParametersValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServices {

	private final PedidoRepository pedidoRepository;
	private final ParametersConverter parametersConverter;
	private final ParametersValidator validator;

	public List<PedidoDTO> findPedidos(String numeroPedido, String dataCadastro, String nomeCliente) {
		BindingResult bindingResult = new WebDataBinder(numeroPedido).getBindingResult();

		FiltroPedidosDTO filtro = parametersConverter.convert(numeroPedido, dataCadastro, nomeCliente, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new InvalidParametersException("Parâmetros de request inválidos", bindingResult.getAllErrors());
		}

		validator.validate(filtro, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new InvalidParametersException("Parâmetros de request inválidos", bindingResult.getAllErrors());
		}

		return pedidoRepository.findByFilter(filtro);
	}

}
