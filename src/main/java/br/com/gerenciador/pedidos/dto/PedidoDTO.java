package br.com.gerenciador.pedidos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PedidoDTO implements Serializable {

	private static final long serialVersionUID = 5577023023005098041L;

	private Long numPedido;
	
	private String cliente;
	
	private LocalDate dataCadastro;
	
	private List<ItemPedidoDTO> itens;
	
	private BigDecimal valorTotal;
	
}
