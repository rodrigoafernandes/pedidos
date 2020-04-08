package br.com.gerenciador.pedidos.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
@Table(name = "GER_PEDIDO")
public class Pedido implements Serializable {

	private static final long serialVersionUID = -216401458644677331L;

	@Id
	@Column(name = "CD_PEDIDO")
	@GeneratedValue(strategy = IDENTITY)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "CD_CLIENTE", referencedColumnName = "CD_CLIENTE", nullable = false)
	private Cliente cliente;
	
	@NotNull
	@Temporal(DATE)
	@Column(name = "DT_CADASTRO")
	private Calendar dataCadastro;

	@OneToMany(mappedBy = "pedido")
	private List<PedidoItem> pedidosItem;

}
