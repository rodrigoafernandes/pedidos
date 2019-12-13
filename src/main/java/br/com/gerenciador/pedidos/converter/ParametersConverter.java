package br.com.gerenciador.pedidos.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;

@Component
public class ParametersConverter {

	private static final String PATTERN = "yyyy-MM-dd";

	public FiltroPedidosDTO convert(String numeroPedidoRequest, String dataCadastroRequest, String nomeClienteRequest,
			BindingResult bindingResult) {

		Long numeroPedido = null;
		LocalDate dataCadastro = null;

		if (StringUtils.isNotBlank(numeroPedidoRequest)) {
			try {
				numeroPedido = Long.valueOf(numeroPedidoRequest);
			} catch (NumberFormatException e) {
				bindingResult.addError(new ObjectError("numeroPedido", "O campo deve ser possuir apenas números"));
			}
		}

		if (StringUtils.isNotBlank(dataCadastroRequest)) {
			try {
				dataCadastro = LocalDate.parse(dataCadastroRequest, DateTimeFormatter.ofPattern(PATTERN));
			} catch (DateTimeParseException e) {
				bindingResult.addError(new ObjectError("dataCadastro", "O campo deve possuir o formato: yyyy-MM-dd"));
			}
		}

		return FiltroPedidosDTO.builder().numeroPedido(numeroPedido).dataCadastro(dataCadastro)
				.nomeCliente(nomeClienteRequest).build();
	}

}
