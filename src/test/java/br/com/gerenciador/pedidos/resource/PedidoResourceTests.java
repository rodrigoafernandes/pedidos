package br.com.gerenciador.pedidos.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.testcontainers.vault.VaultContainer;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.http.HttpStatus.*;

@QuarkusTest
public class PedidoResourceTests {

    @ClassRule
    public static VaultContainer vaultContainer = new VaultContainer<>()
            .withVaultToken("my-root-token")
            .withVaultPort(8200)
            .withSecretInVault("secret/pedidos/datasource", "password=gerenciadorsvcped2019");

    @Test
    public void givenValidRequestParameters_whenSearchPedidos_thenShouldReturnsHttpStatusOk() {
        String dataCadastro = LocalDate.now().toString();

        given().queryParam("numeroPedido", EMPTY).queryParam("dataCadastro", dataCadastro)
                .queryParam("nomeCliente", EMPTY).when().get("/pedidos").then().statusCode(SC_OK);
    }

    @Test
    public void givenInvalidRequestParameters_whenSearchPedidos_thenShouldReturnsHttpStatusPreconditionFailed() {
        String numeroPedido = "-1";
        String dataCadastro = LocalDate.now().toString();

        given().queryParam("numeroPedido", numeroPedido).queryParam("dataCadastro", dataCadastro)
                .queryParam("nomeCliente", EMPTY).when().get("/pedidos").then()
                .statusCode(SC_PRECONDITION_FAILED);
    }

    @Test
    public void givenNoResultsFound_whenSearchPedidos_thenShouldReturnsHttpStatusNotFound() {
        String numeroPedido = "2";
        String dataCadastro = LocalDate.now().toString();

        given().queryParam("numeroPedido", numeroPedido).queryParam("dataCadastro", dataCadastro)
                .queryParam("nomeCliente", EMPTY).when().get("/pedidos").then().statusCode(SC_NOT_FOUND);

    }

    @Test
    public void givenNoItensFound_whenSearchPedidos_thenShouldReturnsHttpStatusNotFound() {
        String numeroPedido = "3";

        given().queryParam("numeroPedido", numeroPedido).queryParam("dataCadastro", EMPTY)
                .queryParam("nomeCliente", EMPTY).when().get("/pedidos").then().statusCode(SC_NOT_FOUND);

    }

}
