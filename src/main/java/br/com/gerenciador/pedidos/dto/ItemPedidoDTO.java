package br.com.gerenciador.pedidos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemPedidoDTO implements Serializable {

	private static final long serialVersionUID = 609056282888321076L;

	private String nomeItem;
	
	private BigDecimal valorUnitario;
	
	private Long quantidade;
	
}
