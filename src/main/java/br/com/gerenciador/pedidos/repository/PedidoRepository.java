package br.com.gerenciador.pedidos.repository;

import java.util.List;

import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;

public interface PedidoRepository {

	List<PedidoDTO> findByFilter(FiltroPedidosDTO filtro);

}
