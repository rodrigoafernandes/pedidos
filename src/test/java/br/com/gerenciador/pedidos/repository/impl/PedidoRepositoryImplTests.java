package br.com.gerenciador.pedidos.repository.impl;

import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.entity.*;
import br.com.gerenciador.pedidos.entity.embedded.PedidoItemPk;
import br.com.gerenciador.pedidos.exception.ItensPedidoNotFoundException;
import br.com.gerenciador.pedidos.exception.PedidosNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Calendar;

import static br.com.gerenciador.pedidos.entity.TipoDocumentoEnum.PJ;
import static java.time.LocalDate.now;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.getInstance;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.math.NumberUtils.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class PedidoRepositoryImplTests {

    @Inject
    PedidoRepositoryImpl pedidoRepository;

    @Inject
    EntityManager entityManager;

    @BeforeEach
    @Transactional
    void setup() {
        Calendar dataCadastro = getInstance();
        dataCadastro.add(DAY_OF_MONTH, createInteger("3"));

        for (int i = 0; i < 10; i++) {
            Item item = entityManager.merge(Item.builder().nome("Item " + i).valorUnitario(createBigDecimal("50.0"))
                    .build());
            Cliente cliente = entityManager.merge(Cliente.builder().nome("Cliente " + i).tipoDocumento(PJ)
                    .numeroDocumento("00000000000000").build());
            Pedido pedido = entityManager.merge(Pedido.builder().cliente(cliente)
                    .dataCadastro(getInstance()).build());

            entityManager
                    .createNativeQuery("INSERT INTO GER_PEDIDO_ITEM(CD_ITEM, CD_PEDIDO, QT_ITEM) " +
                            "VALUES (:codItem, :codPedido, :qtItem)")
                    .setParameter("codItem", item.getCodigo())
                    .setParameter("codPedido", pedido.getCodigo())
                    .setParameter("qtItem", LONG_ONE)
                    .executeUpdate();

        }

        entityManager.merge(Pedido.builder().cliente(entityManager.find(Cliente.class, LONG_ONE))
                .dataCadastro(dataCadastro).build());
    }

    @Test
    public void givenValidFiltroPedidos_whenBuscaPedidos_thenShouldReturnsListOfPedidosDTO() {
        assertTrue(isNotEmpty(pedidoRepository.findByFilter(FiltroPedidosDTO.builder().dataCadastro(now()).build())));
    }

    @Test
    public void givenNoResults_whenBuscaPedidos_thenShouldThrowsPedidosNotFoundException() {
        assertThrows(PedidosNotFoundException.class, () -> {
           pedidoRepository.findByFilter(FiltroPedidosDTO.builder().dataCadastro(now().plusDays(LONG_ONE))
                   .build());
        });
    }

    @Test
    public void givenNoItensFound_whenBuscaPedidos_thenShouldThrowsItensPedidoNotFoundException() {
        assertThrows(ItensPedidoNotFoundException.class, () -> {
           pedidoRepository.findByFilter(FiltroPedidosDTO.builder().dataCadastro(now().plusDays(createInteger("3")))
                   .build());
        });
    }

    @Test
    public void givenValidNumeroPedido_whenBuscaPedidos_thenShouldReturnsListOfPedidosDTO() {
        assertTrue(isNotEmpty(pedidoRepository.findByFilter(FiltroPedidosDTO.builder().numeroPedido(LONG_ONE)
                .build())));
    }

    @Test
    public void givenValidNomeCliente_whenBuscaPedidos_thenShouldReturnsListOfPedidosDTO() {
        assertTrue(isNotEmpty(pedidoRepository.findByFilter(FiltroPedidosDTO.builder().nomeCliente("Cliente 1")
                .build())));
    }

}
