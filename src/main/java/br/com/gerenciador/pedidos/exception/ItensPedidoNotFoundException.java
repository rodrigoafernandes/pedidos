package br.com.gerenciador.pedidos.exception;

public class ItensPedidoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -475698427675010983L;
	
	public ItensPedidoNotFoundException(String messageError) {
		super(messageError);
	}

}
