package br.com.gerenciador.pedidos.repository.impl;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MariaDBContainer;

import java.util.Arrays;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.math.NumberUtils.createInteger;

public class PedidoRepositoryImplTests {

    @Rule
    private MariaDBContainer mariaDBContainer = new MariaDBContainer();

    @BeforeEach
    void setup() {
        mariaDBContainer.setExposedPorts(asList(createInteger("3307")));
    }

    @Test
    public void given_when_thenShould() {
        
    }

}
