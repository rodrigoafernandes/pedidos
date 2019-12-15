package br.com.gerenciador.pedidos.repository.impl;

import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.dto.ItemPedidoDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.exception.ItensPedidoNotFoundException;
import br.com.gerenciador.pedidos.exception.PedidosNotFoundException;
import br.com.gerenciador.pedidos.repository.PedidoRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNoneBlank;
import static org.apache.commons.lang3.math.NumberUtils.*;

public class PedidoRepositoryTestImpl implements PedidoRepository {

    private List<PedidoDTO> pedidos = new ArrayList<>();

    public PedidoRepositoryTestImpl() {
        pedidos.addAll(asList(PedidoDTO.builder().cliente("Cliente Teste").dataCadastro(LocalDate.now())
                .numPedido(LONG_ONE).valorTotal(createBigDecimal("100.37"))
                .itens(asList(ItemPedidoDTO.builder().nomeItem("Item 1").quantidade(LONG_ONE)
                        .valorUnitario(createBigDecimal("100.37")).build())).build(),
                PedidoDTO.builder().cliente("Cliente Teste 2")
                        .dataCadastro(LocalDate.now().plusDays(createLong("3"))).numPedido(createLong("3")).build()));
    }

    @Override
    public List<PedidoDTO> findByFilter(FiltroPedidosDTO filtro) {
        List<PedidoDTO> results = pedidos;
        if (filtro.getNumeroPedido() != null && filtro.getNumeroPedido().compareTo(LONG_ZERO) > INTEGER_MINUS_ONE) {
            results = results.stream().filter(pedido -> pedido.getNumPedido().equals(filtro.getNumeroPedido()))
                    .collect(toList());
        }

        if (filtro.getDataCadastro() != null) {
            results = results.stream().filter(pedido -> pedido.getDataCadastro().equals(filtro.getDataCadastro()))
                    .collect(toList());
        }

        if (isNoneBlank(filtro.getNomeCliente())) {
            results = results.stream().filter(pedido -> pedido.getCliente().equalsIgnoreCase(filtro.getNomeCliente()))
                    .collect(toList());
        }

        if (isEmpty(results)) {
            throw new PedidosNotFoundException("Pedidos nao localizados para o filtro informado");
        }

        results.forEach(pedido -> {
            if (isEmpty(pedido.getItens())) {
                throw new ItensPedidoNotFoundException("Itens não encontrados para o pedido");
            }
        });

        return results;
    }
}
