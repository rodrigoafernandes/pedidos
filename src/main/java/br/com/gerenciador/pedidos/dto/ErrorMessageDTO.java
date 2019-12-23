package br.com.gerenciador.pedidos.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ErrorMessageDTO implements Serializable {

	private static final long serialVersionUID = 1384823612389023364L;

	private String property;

    private String message;

}
