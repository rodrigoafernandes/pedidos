package br.com.gerenciador.pedidos.service;

import br.com.gerenciador.pedidos.converter.ParametersConverter;
import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.exception.InvalidParametersException;
import br.com.gerenciador.pedidos.repository.PedidoRepository;
import br.com.gerenciador.pedidos.validator.ParametersValidator;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.math.NumberUtils.LONG_MINUS_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTests {

    @Mock
    PedidoRepository pedidoRepository;

    @Mock
    ParametersConverter parametersConverter;

    @Mock
    ParametersValidator validator;

    @InjectMocks
    private PedidoServices pedidoServices;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void givenInvalidRequestParameters_whenFindPedidos_thenShouldThrowsInvalidParametersException() {
        when(parametersConverter.convert(anyString(), anyString(), anyString(),any(Set.class))).thenCallRealMethod();

        assertThrows(InvalidParametersException.class, () -> {
           pedidoServices.findPedidos("AAA", "15/12/2019", EMPTY);
        });

    }

    @Test
    public void givenInvalidConvertedParameters_whenFindPedidos_thenShouldThrowsInvalidParametersException() {
        when(parametersConverter.convert(anyString(), anyString(), anyString(), any(Set.class)))
                .thenReturn(FiltroPedidosDTO.builder().numeroPedido(LONG_MINUS_ONE).build());
        Mockito.doCallRealMethod().when(validator).validate(any(FiltroPedidosDTO.class), any(Set.class));
        assertThrows(InvalidParametersException.class, () -> {
            pedidoServices.findPedidos("-1", EMPTY, EMPTY);
        });
    }

    @Test
    public void givenValidParameters_whenFindPedidos_thenShouldReturnsListOfPedidosDTO() {
        when(parametersConverter.convert(anyString(), anyString(), anyString(), any(Set.class)))
                .thenReturn(FiltroPedidosDTO.builder().numeroPedido(LONG_ONE).build());
        doNothing().when(validator).validate(any(FiltroPedidosDTO.class), any(Set.class));
        when(pedidoRepository.findByFilter(any(FiltroPedidosDTO.class)))
                .thenReturn(asList(PedidoDTO.builder().build()));
        assertTrue(isNotEmpty(pedidoServices.findPedidos("1", EMPTY, EMPTY)));
    }

}
