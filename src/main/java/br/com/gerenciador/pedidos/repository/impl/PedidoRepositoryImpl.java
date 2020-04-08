package br.com.gerenciador.pedidos.repository.impl;

import static java.math.BigDecimal.ZERO;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.gerenciador.pedidos.converter.PedidoConverter;
import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.dto.ItemPedidoDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.entity.Cliente;
import br.com.gerenciador.pedidos.entity.Item;
import br.com.gerenciador.pedidos.entity.Pedido;
import br.com.gerenciador.pedidos.entity.PedidoItem;
import br.com.gerenciador.pedidos.exception.ItensPedidoNotFoundException;
import br.com.gerenciador.pedidos.exception.PedidosNotFoundException;
import br.com.gerenciador.pedidos.repository.PedidoRepository;

@RequestScoped
public class PedidoRepositoryImpl implements PedidoRepository {

	@Inject
	EntityManager entityManager;

	@Inject
	PedidoConverter converter;

	CriteriaBuilder criteriaBuilder;

	@PostConstruct
	public void setUp() {
		criteriaBuilder = entityManager.getCriteriaBuilder();
	}

	@Override
	public List<PedidoDTO> findByFilter(FiltroPedidosDTO filtro) {
		CriteriaQuery<Tuple> query = criteriaBuilder.createQuery(Tuple.class);

		Root<Pedido> pedido = query.from(Pedido.class);
		Path<Cliente> cliente = pedido.get("cliente");

		pedido.join("cliente");

		query.select(criteriaBuilder.tuple(pedido.get("codigo"), cliente.get("nome"), pedido.get("dataCadastro")));

		query.where(buildPredicate(filtro, pedido, cliente));

		List<Tuple> results = entityManager.createQuery(query).getResultList();

		if (isEmpty(results)) {
			throw new PedidosNotFoundException("Não foram encontrados pedidos para o filtro informado");
		}

		List<PedidoDTO> pedidos = converter.toPedidoDTO(results);

		pedidos.forEach(pedidoDB -> {
			CriteriaQuery<Tuple> queryItens = criteriaBuilder.createQuery(Tuple.class);

			Root<PedidoItem> pedidoItem = queryItens.from(PedidoItem.class);
			Path<Item> item = pedidoItem.get("item");

			queryItens.select(criteriaBuilder.tuple(item.get("nome"), item.get("valorUnitario"),
					pedidoItem.get("qtdItem")));

			pedidoItem.join("item").on(
					criteriaBuilder.equal(pedidoItem.get("item").get("codigo"), item.get("codigo")));

			queryItens.where(criteriaBuilder.equal(pedidoItem.get("pedido").get("codigo"), pedidoDB.getNumPedido()));

			List<Tuple> itensResults = entityManager.createQuery(queryItens).getResultList();

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

	private Predicate buildPredicate(FiltroPedidosDTO filtro, Root<Pedido> pedido, Path<Cliente> cliente) {
		List<Predicate> predicates = new ArrayList<>();

		if (filtro.getDataCadastro() != null) {
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(Date.from(filtro.getDataCadastro().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			predicates.add(criteriaBuilder.equal(pedido.get("dataCadastro"), dataCadastro));
		}

		if (filtro.getNumeroPedido() != null) {
			predicates.add(criteriaBuilder.equal(pedido.get("codigo"), filtro.getNumeroPedido()));
		}

		if (isNotBlank(filtro.getNomeCliente())) {
			predicates.add(criteriaBuilder.equal(
					criteriaBuilder.upper(cliente.get("nome")), filtro.getNomeCliente().toUpperCase()));
		}

		return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	}

}
