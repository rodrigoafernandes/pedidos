package br.com.gerenciador.pedidos.resource.exception.handler;

import br.com.gerenciador.pedidos.dto.ErrorDTO;
import br.com.gerenciador.pedidos.exception.ItensPedidoNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
public class ItensPedidoNotFoundExceptionHandler implements ExceptionMapper<ItensPedidoNotFoundException> {

    @Override
    public Response toResponse(ItensPedidoNotFoundException e) {
        return Response.status(NOT_FOUND).type(APPLICATION_JSON).entity(ErrorDTO.builder()
                .message(e.getMessage()).build()).build();
    }

}
