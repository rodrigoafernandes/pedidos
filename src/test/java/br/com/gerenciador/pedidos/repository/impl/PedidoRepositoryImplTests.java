package br.com.gerenciador.pedidos.repository.impl;

import br.com.gerenciador.pedidos.config.MariaDBTestConfig;
import br.com.gerenciador.pedidos.converter.PedidoConverter;
import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.entity.Pedido;
import br.com.gerenciador.pedidos.exception.ItensPedidoNotFoundException;
import br.com.gerenciador.pedidos.exception.PedidosNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import static java.math.BigDecimal.TEN;
import static java.util.Calendar.DAY_OF_MONTH;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_TWO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest(properties = {
        "mariaDB4j.port=3308",
        "spring.datasource.url=jdbc:mariadb://localhost:3308/"
})
@ActiveProfiles("test-db")
@Import(MariaDBTestConfig.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = NONE)
public class PedidoRepositoryImplTests {

    @TestConfiguration
    static class PedidoRepositoryImplTestsConfig {

        @Bean
        public PedidoConverter converter() {
            return new PedidoConverter();
        }

    }

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PedidoConverter converter;

    private PedidoRepositoryImpl repository;

    @BeforeEach
    void setup() {
        repository = new PedidoRepositoryImpl(entityManager, converter);
        Calendar dataCadastro = Calendar.getInstance();

        dataCadastro.add(DAY_OF_MONTH, INTEGER_TWO);

        entityManager.merge(Pedido.builder().codigoCliente(TEN.longValue()).dataCadastro(dataCadastro).build());
    }

    @Test
    public void givenSearchByDataCadastro_whenSearchPedidos_thenShouldReturnsLisOfPedidoDTO() {
        List<PedidoDTO> pedidos = repository.findByFilter(FiltroPedidosDTO.builder().dataCadastro(LocalDate.now())
                .build());
        assertTrue(isNotEmpty(pedidos));
    }

    @Test
    public void givenSearchByNumeroPedido_whenSearchPedidos_thenShouldReturnsLisOfPedidoDTO() {
        List<PedidoDTO> pedidos = repository.findByFilter(FiltroPedidosDTO.builder().numeroPedido(LONG_ONE)
                .build());
        assertTrue(isNotEmpty(pedidos));
    }

    @Test
    public void givenSearchByNomeCliente_whenSearchPedidos_thenShouldReturnsLisOfPedidoDTO() {
        List<PedidoDTO> pedidos = repository.findByFilter(FiltroPedidosDTO.builder().nomeCliente("Cliente 1")
                .build());
        assertTrue(isNotEmpty(pedidos));
    }

    @Test
    public void givenNoResultsFound_whenSearchPedidos_thenShouldThrowsPedidosNotFoundException() {
        Assertions.assertThrows(PedidosNotFoundException.class, () -> {
            repository.findByFilter(FiltroPedidosDTO.builder().dataCadastro(LocalDate.now().plusDays(LONG_ONE))
                    .build());
        });
    }

    @Test
    public void givenNoItensFound_whenSearchPedidos_thenShouldThrowsItensPedidoNotFoundException() {
        Assertions.assertThrows(ItensPedidoNotFoundException.class, () -> {
            repository.findByFilter(FiltroPedidosDTO.builder().dataCadastro(LocalDate.now().plusDays(LONG_ONE)
                .plusDays(LONG_ONE)).build());
        });
    }

}
