package br.com.gerenciador.pedidos.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
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
	
	@NotNull
	@Column(name = "CD_CLIENTE")
	private Long codigoCliente;
	
	@NotNull
	@Temporal(DATE)
	@Column(name = "DT_CADASTRO")
	private Calendar dataCadastro;
}
