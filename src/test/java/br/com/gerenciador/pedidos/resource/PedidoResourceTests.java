package br.com.gerenciador.pedidos.resource;

import br.com.gerenciador.pedidos.exception.InvalidParametersException;
import br.com.gerenciador.pedidos.exception.ItensPedidoNotFoundException;
import br.com.gerenciador.pedidos.exception.PedidosNotFoundException;
import br.com.gerenciador.pedidos.service.PedidoServices;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PedidoResource.class)
public class PedidoResourceTests {

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private PedidoServices services;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void givenValidRequestParameters_whenSearchPedidos_thenShouldReturnsHttpStatusOk() throws Exception {
        String numeroPedido = EMPTY;
        String dataCadastro = LocalDate.now().toString();
        String nomeCliente = EMPTY;

        when(services.findPedidos(numeroPedido, dataCadastro, nomeCliente)).thenReturn(new ArrayList<>());

        mvc.perform(get("/pedidos?numeroPedido={numeroPedido}&dataCadastro={dataCadastro}&nomeCliente={nomeCliente}",
                numeroPedido, dataCadastro, nomeCliente)).andExpect(status().isOk());
    }

    @Test
    public void givenInvalidRequestParameters_whenSearchPedidos_thenShouldReturnsHttpStatusPreconditionFailed() throws Exception {
        String numeroPedido = "-1";
        String dataCadastro = LocalDate.now().toString();
        String nomeCliente = EMPTY;

        InvalidParametersException exception = new InvalidParametersException("Parâmetros inválidos",
                asList(new ObjectError("numeroPedido", "O número do pedido não pode ser negativo")));
        Mockito.doThrow(exception).when(services).findPedidos(numeroPedido, dataCadastro, nomeCliente);

        mvc.perform(get("/pedidos?numeroPedido={numeroPedido}&dataCadastro={dataCadastro}&nomeCliente={nomeCliente}",
                numeroPedido, dataCadastro, nomeCliente)).andExpect(status().isPreconditionFailed());
    }

    @Test
    public void givenNoResultsFound_whenSearchPedidos_thenShouldReturnsHttpStatusNotFound() throws Exception {
        String numeroPedido = "2";
        String dataCadastro = LocalDate.now().toString();
        String nomeCliente = EMPTY;

        PedidosNotFoundException exception = new PedidosNotFoundException("Pedidos não localizado");

        Mockito.doThrow(exception).when(services).findPedidos(numeroPedido, dataCadastro, nomeCliente);

        mvc.perform(get("/pedidos?numeroPedido={numeroPedido}&dataCadastro={dataCadastro}&nomeCliente={nomeCliente}",
                numeroPedido, dataCadastro, nomeCliente)).andExpect(status().isNotFound());

    }

    @Test
    public void givenNoItensFound_whenSearchPedidos_thenShouldReturnsHttpStatusNotFound() throws Exception {
        String numeroPedido = "3";
        String dataCadastro = EMPTY;
        String nomeCliente = EMPTY;

        ItensPedidoNotFoundException exception = new ItensPedidoNotFoundException("Itens não localizados");

        Mockito.doThrow(exception).when(services).findPedidos(numeroPedido, dataCadastro, nomeCliente);

        mvc.perform(get("/pedidos?numeroPedido={numeroPedido}&dataCadastro={dataCadastro}&nomeCliente={nomeCliente}",
                numeroPedido, dataCadastro, nomeCliente)).andExpect(status().isNotFound());

    }

}
