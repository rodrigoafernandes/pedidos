package br.com.gerenciador.pedidos.dto;

import java.io.Serializable;
import java.time.LocalDate;

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
public class FiltroPedidosDTO implements Serializable {

	private static final long serialVersionUID = 9198053884825863872L;

	private Long numeroPedido;

	private LocalDate dataCadastro;

	private String nomeCliente;

}
