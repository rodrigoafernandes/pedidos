package br.com.gerenciador.pedidos.service;

import br.com.gerenciador.pedidos.converter.ParametersConverter;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.exception.InvalidParametersException;
import br.com.gerenciador.pedidos.repository.impl.PedidoRepositoryTestImpl;
import br.com.gerenciador.pedidos.validator.ParametersValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PedidoServicesTests {

    private PedidoServices pedidoServices;

    @BeforeEach
    void setup() {
        pedidoServices = new PedidoServices(new PedidoRepositoryTestImpl(), new ParametersConverter(),
                new ParametersValidator());
    }

    @Test
    public void givenValidRequestParameters_whenSearchPedidos_thenShouldReturnsListOfPedidoDTO() {
        List<PedidoDTO> pedidos = pedidoServices.findPedidos("1", LocalDate.now().toString(), "");
        assertTrue(isNotEmpty(pedidos));
    }

    @Test
    public void givenInvalidRequestParameters_whenSearchPedidos_thenShouldThrowsInvalidParametersException() {
        Assertions.assertThrows(InvalidParametersException.class, () -> {
           pedidoServices.findPedidos("AAA", "13/12/2019", "");
        });
    }

    @Test
    public void givenInvalidConvertedParameters_whenSearchPedidos_thenShouldThrowsInvalidParametersException() {
        Assertions.assertThrows(InvalidParametersException.class, () -> {
           pedidoServices.findPedidos("-1", "", "");
        });
    }

}
