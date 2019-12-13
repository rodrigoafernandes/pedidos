package br.com.gerenciador.pedidos.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

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
@Table(name = "GER_ITEM")
public class Item implements Serializable {

	private static final long serialVersionUID = 3816258090884000247L;

	@Id
	@Column(name = "CD_ITEM")
	@GeneratedValue(strategy = IDENTITY)
	private Long codigo;
	
	@NotBlank
	@Length(max = 60)
	@Column(name = "NM_ITEM")
	private String nome;
	
	@NotNull
	@Column(name = "VL_UNITARIO")
	@Digits(integer = 18, fraction = 2)
	private BigDecimal valorUnitario;
	
}
