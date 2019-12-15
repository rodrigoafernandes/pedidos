package br.com.gerenciador.pedidos.validator;

import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.enterprise.context.RequestScoped;
import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

@RequestScoped
public class ParametersValidator {

	public void validate(FiltroPedidosDTO filtro, Set<ConstraintViolation<FiltroPedidosDTO>> constraintViolations) {
		if (filtro.getNumeroPedido() != null && filtro.getNumeroPedido().compareTo(LONG_ONE) < INTEGER_ZERO) {
			constraintViolations.add(ConstraintViolationImpl.forBeanValidation("Parâmetros inválidos", null, null,
					String.format("O campo %s não pode ser negativo", "numeroPedido"), null, null,
					null, null, PathImpl.createPathFromString("numeroPedido"), null, null));
		}
	}

}
