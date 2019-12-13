package br.com.gerenciador.pedidos.validator;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;

@Component
public class ParametersValidator {

	public void validate(FiltroPedidosDTO filtro, BindingResult bindingResult) {
		if (filtro.getNumeroPedido() != null && filtro.getNumeroPedido().compareTo(LONG_ONE) < INTEGER_ZERO) {
			bindingResult.addError(new ObjectError("numeroPedido", "O número do pedido não pode ser negativo"));
		}
	}

}
