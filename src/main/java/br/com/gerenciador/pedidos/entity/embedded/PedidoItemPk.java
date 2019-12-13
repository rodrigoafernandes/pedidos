package br.com.gerenciador.pedidos.entity.embedded;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PedidoItemPk implements Serializable {

	private static final long serialVersionUID = 2982844952008417229L;

	@NotNull
	@Column(name = "CD_PEDIDO")
	private Long codigoPedido;

	@NotNull
	@Column(name = "CD_ITEM")
	private Long codigoItem;
	
}
