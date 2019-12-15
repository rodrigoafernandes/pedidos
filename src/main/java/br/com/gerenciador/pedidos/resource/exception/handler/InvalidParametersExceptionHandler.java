package br.com.gerenciador.pedidos.resource.exception.handler;

import br.com.gerenciador.pedidos.exception.InvalidParametersException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.PRECONDITION_FAILED;

@Provider
public class InvalidParametersExceptionHandler implements ExceptionMapper<InvalidParametersException> {

	@Override
	public Response toResponse(InvalidParametersException e) {
		return Response.status(PRECONDITION_FAILED).type(APPLICATION_JSON).entity(e.getErrors()).build();
	}

}
