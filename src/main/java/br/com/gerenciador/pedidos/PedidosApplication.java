package br.com.gerenciador.pedidos;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "API Pedidos", version = "0.1.0",
		description = "Documentação da API de consulta de pedidos gerados"))
public class PedidosApplication extends Application {

}
