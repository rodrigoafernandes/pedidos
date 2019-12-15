package br.com.gerenciador.pedidos.converter;

import br.com.gerenciador.pedidos.dto.ItemPedidoDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import com.querydsl.core.Tuple;

import javax.enterprise.context.RequestScoped;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_TWO;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@RequestScoped
public class PedidoConverter {

	public List<PedidoDTO> toPedidoDTO(List<Tuple> results) {
		List<PedidoDTO> pedidos = new ArrayList<>();

		results.forEach(result -> {
			Calendar dataCadastroDB = result.get(INTEGER_TWO, Calendar.class);
			pedidos.add(PedidoDTO.builder().numPedido(result.get(INTEGER_ZERO, Long.class))
					.cliente(result.get(INTEGER_ONE, String.class)).dataCadastro(LocalDate.of(dataCadastroDB.get(YEAR),
							dataCadastroDB.get(MONTH), dataCadastroDB.get(DAY_OF_MONTH)))
					.valorTotal(ZERO).build());
		});

		return pedidos;
	}

	public List<ItemPedidoDTO> toPedidoItensDTO(List<Tuple> itensResults) {
		List<ItemPedidoDTO> itensPedido = new ArrayList<>();

		itensResults.forEach(itemResult -> itensPedido
				.add(ItemPedidoDTO.builder().nomeItem(itemResult.get(INTEGER_ZERO, String.class))
						.valorUnitario(itemResult.get(INTEGER_ONE, BigDecimal.class))
						.quantidade(itemResult.get(INTEGER_TWO, Long.class)).build()));

		return itensPedido;
	}

}
