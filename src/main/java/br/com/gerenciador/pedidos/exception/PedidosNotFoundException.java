package br.com.gerenciador.pedidos.exception;

public class PedidosNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2114440257472766047L;

	public PedidosNotFoundException(String messageError) {
		super(messageError);
	}
	
}
