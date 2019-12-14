package br.com.gerenciador.pedidos.resource;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gerenciador.pedidos.dto.ErrorDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.service.PedidoServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pedidos")
@Api(value = "Serviços referente aos Pedidos", tags = "Pedidos")
public class PedidoResource {

	private final PedidoServices services;

	@GetMapping
	@ApiOperation(value = "Busca Pedidos pelo filtro informado", tags = "Pedidos")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK", response = PedidoDTO[].class),
			@ApiResponse(code = 404, message = "Not found", response = ErrorDTO.class),
			@ApiResponse(code = 412, message = "Precondition Failed", response = ObjectError[].class) })
	public ResponseEntity<List<PedidoDTO>> findPedidos(
			@RequestParam(value = "numeroPedido", required = false) String numeroPedido,
			@ApiParam(format = "yyyy-MM-dd", type = "date") @RequestParam(value = "dataCadastro", required = false) String dataCadastro,
			@RequestParam(value = "nomeCliente", required = false) String nomeCliente) {
		return ResponseEntity.ok(services.findPedidos(numeroPedido, dataCadastro, nomeCliente));
	}

}
