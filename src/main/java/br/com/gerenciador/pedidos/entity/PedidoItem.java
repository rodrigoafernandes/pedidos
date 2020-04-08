package br.com.gerenciador.pedidos.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "GER_PEDIDO_ITEM")
public class PedidoItem implements Serializable {

	private static final long serialVersionUID = 1787141136355075712L;
	
	@NotNull
	@Column(name = "QT_ITEM")
	private Long qtdItem;

	@Id
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "CD_ITEM", referencedColumnName = "CD_ITEM")
	private Item item;

	@Id
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "CD_PEDIDO", referencedColumnName = "CD_PEDIDO")
	private Pedido pedido;

}
