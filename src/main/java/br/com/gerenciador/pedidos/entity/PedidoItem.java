package br.com.gerenciador.pedidos.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.gerenciador.pedidos.entity.embedded.PedidoItemPk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "GER_PEDIDO_ITEM")
public class PedidoItem implements Serializable {

	private static final long serialVersionUID = 1787141136355075712L;
	
	@EmbeddedId
	private PedidoItemPk id;
	
	@NotNull
	@Column(name = "QT_ITEM")
	private Long qtdItem;

}
