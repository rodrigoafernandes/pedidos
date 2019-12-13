package br.com.gerenciador.pedidos.validator;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

import org.springframework.stereotype.Component;

import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;

@Component
public class ParametersValidator {

	public void validate(FiltroPedidosDTO filtro) {
		if (filtro.getNumeroPedido().compareTo(LONG_ONE) < INTEGER_ONE) {
			
		}
	}

}
