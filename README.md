# API para gerenciamento de pedidos

Neste repositório está a implementação para o teste de API de consulta de pedidos.

Tecnologias utilizadas:

* [Java 1.8](https://openjdk.java.net/install/)
* [Spring Boot 2.2.2.RELEASE](https://spring.io/projects/spring-boot)
* [mariaDB](https://mariadb.org/)
* [QueryDSL](http://www.querydsl.com/)
* [Lombok](https://projectlombok.org/)
* [Flyway](https://flywaydb.org/)
* [Swagger](https://swagger.io/)
* [Jacoco](https://www.eclemma.org/jacoco/)
* [Checkstyle](https://checkstyle.sourceforge.io/)
* [PMD](https://pmd.github.io/)

### Swagger
Para acessar a documentação Swagger, basta rodar a aplicação e acessar pelo browser: http://hostname:port/swagger-ui.html.

### Build da aplicação
Para buildar a aplicação, basta utilizar o comando: <br/>
<code>./mvnw clean package</code>

### Executando a aplicação
Após compilar e gerar o artefato, a aplicação poderá ser executada utilizando o seguinte comando: 
<code>java -Dspring.profiles.active=ti -Dserver.port=8080 -jar target/pedidos-0.0.1-SNAPSHOT.jar</code><br/>
Este comando inicializará a aplicação utilizando o hostname: localhost, inicializando também uma instancia do banco de dados MariaDB de forma "embeddable".<br/>
Para executar a aplicação utilizando outro profile, deverá ser informado os seguintes parâmetros para inicialização da aplicação:<br/> <code>-Dspring.profiles.active={profile desejado} -Dspring.datasource.username={usuário do banco de dados} -Dspring.datasource.password={password do usuário de banco de dados} -Dspring.datasource.url={url de conexão jdbc com o banco de dados}</code><br/>
O usuário de banco de dados deve possuir permissão para criar, alterar e deletar objetos do banco de dados, assim como permissão para leitura e escrita de registros.<br/>

### Testando a aplicação
Para testar a aplicação, poderá ser utilizado a própria documentação Swagger, utilizar o comando <code>curl</code> ou ferramentas para testes de serviços web, como o [Postman](https://www.getpostman.com/) por exemplo.

#### Exemplos de request e response
Request sem utilizar nenhum dos filtros:<br/>
<code>curl -X GET "http://localhost:8080/pedidos" -H "accept: */*"</code><br/>
Resultado esperado:<br/>
<code>Http Status Code 200 e resposta json com todos os pedidos cadastrados.</code><br/>
Response:<br/>
<code>[
        {
          "numPedido": 1,
          "cliente": "CLIENTE 1",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "MOUSE",
              "valorUnitario": 60.35,
              "quantidade": 2
            }
          ],
          "valorTotal": 120.7
        },
        {
          "numPedido": 2,
          "cliente": "CLIENTE 2",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "MOUSE",
              "valorUnitario": 60.35,
              "quantidade": 1
            }
          ],
          "valorTotal": 60.35
        },
        {
          "numPedido": 3,
          "cliente": "CLIENTE 3",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "TECLADO",
              "valorUnitario": 75.5,
              "quantidade": 1
            }
          ],
          "valorTotal": 75.5
        },
        {
          "numPedido": 4,
          "cliente": "CLIENTE 4",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "TECLADO",
              "valorUnitario": 75.5,
              "quantidade": 5
            }
          ],
          "valorTotal": 377.5
        },
        {
          "numPedido": 5,
          "cliente": "CLIENTE 5",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "MOUSE",
              "valorUnitario": 60.35,
              "quantidade": 1
            }
          ],
          "valorTotal": 60.35
        },
        {
          "numPedido": 6,
          "cliente": "CLIENTE 6",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "TECLADO",
              "valorUnitario": 75.5,
              "quantidade": 3
            }
          ],
          "valorTotal": 226.5
        },
        {
          "numPedido": 7,
          "cliente": "CLIENTE 7",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "MOUSE",
              "valorUnitario": 60.35,
              "quantidade": 1
            }
          ],
          "valorTotal": 60.35
        },
        {
          "numPedido": 8,
          "cliente": "CLIENTE 8",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "TECLADO",
              "valorUnitario": 75.5,
              "quantidade": 3
            }
          ],
          "valorTotal": 226.5
        },
        {
          "numPedido": 9,
          "cliente": "CLIENTE 9",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "MOUSE",
              "valorUnitario": 60.35,
              "quantidade": 1
            }
          ],
          "valorTotal": 60.35
        },
        {
          "numPedido": 10,
          "cliente": "CLIENTE 10",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "TECLADO",
              "valorUnitario": 75.5,
              "quantidade": 5
            }
          ],
          "valorTotal": 377.5
        }
      ]</code><br/>

Request utilizando os filtros:<br/>
<code>curl -X GET "http://localhost:8080/pedidos?nomeCliente=cliente%201" -H "accept: */*"</code><br/>
Resultado esperado:<br/>
<code>Http Status Code 200 e resposta json com os pedidos cadastrados para o filtro informado.</code><br/>
Response:<br/>
<code>[
        {
          "numPedido": 1,
          "cliente": "CLIENTE 1",
          "dataCadastro": "2019-11-14",
          "itens": [
            {
              "nomeItem": "MOUSE",
              "valorUnitario": 60.35,
              "quantidade": 2
            }
          ],
          "valorTotal": 120.7
        }
      ]</code><br/>
      
Request utilizando os filtros:<br/>
<code>curl -X GET "http://localhost:8080/pedidos?nomeCliente=cliente%2015" -H "accept: */*"</code><br/>
Resultado esperado:<br/>
<code>Http Status Code 404 e mensagem informando o resultado</code><br/>
Response:<br/>
<code>{
        "message": "Não foram encontrados pedidos para o filtro informado"
      }</code><br/>

Request utilizando os filtros:<br/>
<code>curl -X GET "http://localhost:8080/pedidos?dataCadastro=13122019&numeroPedido=AAA" -H "accept: */*"</code><br/>
Resultado esperado:<br/>
<code>Http Status Code 412 e resposta json informando os parâmetros inválidos</code><br/>
Response:<br/>
<code>[
        {
          "codes": null,
          "arguments": null,
          "defaultMessage": "O campo deve ser possuir apenas números",
          "objectName": "numeroPedido",
          "code": null
        },
        {
          "codes": null,
          "arguments": null,
          "defaultMessage": "O campo deve possuir o formato: yyyy-MM-dd",
          "objectName": "dataCadastro",
          "code": null
        }
      ]</code><br/>
      
Request utilizando os filtros:<br/>
<code>curl -X GET "http://localhost:8080/pedidos?nomeCliente=cliente%2015" -H "accept: */*"</code><br/>
Resultado esperado:<br/>
<code>Http Status Code 404 e mensagem informando o resultado</code><br/>
Response:<br/>
<code>{
        "message": "Não foram encontrados pedidos para o filtro informado"
      }</code><br/>

Request utilizando os filtros:<br/>
<code>curl -X GET "http://localhost:8080/pedidos?numeroPedido=-1" -H "accept: */*"</code><br/>
Resultado esperado:<br/>
<code>Http Status Code 412 e resposta json informando os parâmetros inválidos</code><br/>
Response:<br/>
<code>[
        {
          "codes": null,
          "arguments": null,
          "defaultMessage": "O número do pedido não pode ser negativo",
          "objectName": "numeroPedido",
          "code": null
        }
      ]</code><br/>