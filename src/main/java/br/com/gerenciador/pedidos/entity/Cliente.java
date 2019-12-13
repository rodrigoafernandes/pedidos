package br.com.gerenciador.pedidos.entity;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "GER_CLIENTE")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 5976344362337295610L;

	@Id
	@Column(name = "CD_CLIENTE")
	@GeneratedValue(strategy = IDENTITY)
	private Long codigo;
	
	@NotBlank
	@Length(max = 100)
	@Column(name = "NM_CLIENTE")
	private String nome;
	
	@NotNull
	@Enumerated(STRING)
	@Column(name = "TP_DOCUMENTO")
	private TipoDocumentoEnum tipoDocumento;
	
	@NotBlank
	@Length(max = 14)
	@Column(name = "NR_DOCUMENTO")
	private String numeroDocumento;
	
}
