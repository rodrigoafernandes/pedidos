package br.com.gerenciador.pedidos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
		"mariaDB4j.port=3307",
		"spring.datasource.url=jdbc:mariadb://localhost:3307/"
})
@ActiveProfiles("test-db")
class PedidosApplicationTests {

	@Test
	void contextLoads() {
	}

}
