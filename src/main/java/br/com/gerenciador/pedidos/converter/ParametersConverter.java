package br.com.gerenciador.pedidos.converter;

import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintViolation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

@RequestScoped
public class ParametersConverter {

	private static final String PATTERN = "yyyy-MM-dd";

	public FiltroPedidosDTO convert(String numeroPedidoRequest, String dataCadastroRequest, String nomeClienteRequest,
									Set<ConstraintViolation<FiltroPedidosDTO>> constraintViolations) {

		Long numeroPedido = null;
		LocalDate dataCadastro = null;

		if (StringUtils.isNotBlank(numeroPedidoRequest)) {
			try {
				numeroPedido = Long.valueOf(numeroPedidoRequest);
			} catch (NumberFormatException e) {
				constraintViolations.add(ConstraintViolationImpl.forBeanValidation("Parâmetros inválidos", null, null,
						String.format("O campo %s deve possuir valor númerico e inteiro", "numeroPedido"), null, null,
						null, null, PathImpl.createPathFromString("numeroPedido"), null, null));
			}
		}

		if (StringUtils.isNotBlank(dataCadastroRequest)) {
			try {
				dataCadastro = LocalDate.parse(dataCadastroRequest, DateTimeFormatter.ofPattern(PATTERN));
			} catch (DateTimeParseException e) {
				constraintViolations.add(ConstraintViolationImpl.forBeanValidation("Parâmetros inválidos", null, null,
						String.format("O campo  %s deve possuir o formato: yyyy-MM-dd", "dataCadastro"), null, null,
						null, null, PathImpl.createPathFromString("dataCadastro"), null, null));
			}
		}

		return FiltroPedidosDTO.builder().numeroPedido(numeroPedido).dataCadastro(dataCadastro)
				.nomeCliente(nomeClienteRequest).build();
	}

}
