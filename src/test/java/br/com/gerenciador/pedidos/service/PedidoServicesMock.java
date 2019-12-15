package br.com.gerenciador.pedidos.service;

import br.com.gerenciador.pedidos.converter.ParametersConverter;
import br.com.gerenciador.pedidos.dto.FiltroPedidosDTO;
import br.com.gerenciador.pedidos.dto.PedidoDTO;
import br.com.gerenciador.pedidos.exception.InvalidParametersException;
import br.com.gerenciador.pedidos.repository.impl.PedidoRepositoryTestImpl;
import br.com.gerenciador.pedidos.validator.ParametersValidator;
import io.quarkus.test.Mock;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Mock
@ApplicationScoped
public class PedidoServicesMock extends PedidoServices{

    PedidoRepositoryTestImpl pedidoRepository = new PedidoRepositoryTestImpl();

    ParametersConverter parametersConverter = new ParametersConverter();

    ParametersValidator validator = new ParametersValidator();

    @Override
    public List<PedidoDTO> findPedidos(String numeroPedido, String dataCadastro, String nomeCliente) {
        Set<ConstraintViolation<FiltroPedidosDTO>> constraintViolations = new HashSet<>();

        FiltroPedidosDTO filtro = parametersConverter.convert(numeroPedido, dataCadastro, nomeCliente,
                constraintViolations);

        if (isNotEmpty(constraintViolations)) {
            throw new InvalidParametersException("Parâmetros de request inválidos", constraintViolations);
        }

        validator.validate(filtro, constraintViolations);

        if (isNotEmpty(constraintViolations)) {
            throw new InvalidParametersException("Parâmetros de request inválidos", constraintViolations);
        }

        return pedidoRepository.findByFilter(filtro);
    }
}
