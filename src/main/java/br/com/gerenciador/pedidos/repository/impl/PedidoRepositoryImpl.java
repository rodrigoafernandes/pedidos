package br.com.gerenciador.pedidos.repository.impl;

import static java.math.BigDecimal.ZERO;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.gerenciador.pedidos.converter.PedidoConverter;
import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.dto.ItemPedidoDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.entity.QCliente;
import br.com.gerenciador.pedidos.entity.QItem;
import br.com.gerenciador.pedidos.entity.QPedido;
import br.com.gerenciador.pedidos.entity.QPedidoItem;
import br.com.gerenciador.pedidos.exception.ItensPedidoNotFoundException;
import br.com.gerenciador.pedidos.exception.PedidosNotFoundException;
import br.com.gerenciador.pedidos.repository.PedidoRepository;

@RequestScoped
public class PedidoRepositoryImpl implements PedidoRepository {

	@Inject
	EntityManager entityManager;

	@Inject
	PedidoConverter converter;

	@Override
	public List<PedidoDTO> findByFilter(FiltroPedidosDTO filtro) {
		JPAQuery<Tuple> query = new JPAQuery<>(entityManager);

		QPedido pedido = QPedido.pedido;
		QCliente cliente = QCliente.cliente;
		QPedidoItem pedidoItem = QPedidoItem.pedidoItem;
		QItem item = QItem.item;

		query.select(pedido.codigo, cliente.nome, pedido.dataCadastro).from(pedido).innerJoin(cliente)
				.on(pedido.codigoCliente.eq(cliente.codigo)).where(buildPredicate(filtro, pedido, cliente));

		List<Tuple> results = query.fetch();

		if (isEmpty(results)) {
			throw new PedidosNotFoundException("Não foram encontrados pedidos para o filtro informado");
		}

		List<PedidoDTO> pedidos = converter.toPedidoDTO(results);

		pedidos.forEach(pedidoDB -> {
			JPAQuery<Tuple> queryItens = new JPAQuery<>(entityManager);

			queryItens.select(item.nome, item.valorUnitario, pedidoItem.qtdItem).from(pedidoItem).innerJoin(item)
					.on(pedidoItem.id.codigoItem.eq(item.codigo))
					.where(pedidoItem.id.codigoPedido.eq(pedidoDB.getNumPedido()));

			List<Tuple> itensResults = queryItens.fetch();

			if (isEmpty(itensResults)) {
				throw new ItensPedidoNotFoundException(
						String.format("Não foram encontrados itens para o pedido: %d", pedidoDB.getNumPedido()));
			}

			List<ItemPedidoDTO> itensPedido = converter.toPedidoItensDTO(itensResults);
			pedidoDB.setItens(itensPedido).setValorTotal(pedidoDB.getValorTotal().add(
					itensPedido.stream().map(ItemPedidoDTO::getValorUnitario).reduce(ZERO, BigDecimal::add).multiply(
							new BigDecimal(itensPedido.stream().mapToLong(ItemPedidoDTO::getQuantidade).sum()))));

		});

		return pedidos;
	}

	private Predicate buildPredicate(FiltroPedidosDTO filtro, QPedido pedido, QCliente cliente) {
		BooleanBuilder predicate = new BooleanBuilder();

		if (filtro.getDataCadastro() != null) {
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(Date.from(filtro.getDataCadastro().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			predicate.and(pedido.dataCadastro.eq(dataCadastro));
		}

		if (filtro.getNumeroPedido() != null) {
			predicate.and(pedido.codigo.eq(filtro.getNumeroPedido()));
		}

		if (isNotBlank(filtro.getNomeCliente())) {
			predicate.and(cliente.nome.equalsIgnoreCase(filtro.getNomeCliente()));
		}

		return predicate;
	}

}
