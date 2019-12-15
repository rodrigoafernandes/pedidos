package br.com.gerenciador.pedidos.resource;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.gerenciador.pedidos.dto.ErrorDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.service.PedidoServices;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path(value = "/pedidos")
public class PedidoResource {

	@Inject
	PedidoServices services;

	@GET
	@Tag(name = "Pedidos")
	@Produces(APPLICATION_JSON)
	@Operation(description = "Busca Pedidos pelo filtro informado")
	@APIResponses({ @APIResponse(responseCode = "200", description = "OK",
			content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = PedidoDTO[].class))),
			@APIResponse(responseCode = "404", description = "Not found",
					content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = ErrorDTO.class))),
			@APIResponse(responseCode = "412", description = "Precondition Failed",
					content = @Content(mediaType = APPLICATION_JSON,
							schema = @Schema(implementation = ConstraintViolation[].class))) })
	public List<PedidoDTO> findPedidos(@QueryParam("numeroPedido") String numeroPedido,
									   @QueryParam("dataCadastro") String dataCadastro,
									   @QueryParam("nomeCliente") String nomeCliente) {
		return services.findPedidos(numeroPedido, dataCadastro, nomeCliente);
	}

}
