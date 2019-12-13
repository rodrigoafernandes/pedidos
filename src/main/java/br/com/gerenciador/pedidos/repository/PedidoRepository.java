package br.com.gerenciador.pedidos.repository;

import java.util.List;

import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.entity.Pedido;

public interface PedidoRepository {

	List<Pedido> findByFilter(FiltroPedidosDTO filtro);

}
