# API para gerenciamento de pedidos

Neste repositório está a implementação para o teste de API de consulta de pedidos.

Tecnologias utilizadas:

* [Java 1.8](https://openjdk.java.net/install/)
* [Quarkus](https://quarkus.io/)
* [mariaDB](https://mariadb.org/)
* [Lombok](https://projectlombok.org/)
* [Flyway](https://flywaydb.org/)
* [Swagger](https://swagger.io/)
* [Jacoco](https://www.eclemma.org/jacoco/)
* [Checkstyle](https://checkstyle.sourceforge.io/)
* [PMD](https://pmd.github.io/)
* [Gradle](https://gradle.org/)

### Swagger
Para acessar a documentação Swagger, basta rodar a aplicação e acessar pelo browser: http://hostname:port/swagger-ui.html.

### Build da aplicação
Para buildar a aplicação, basta utilizar o comando: <br/>
<code>./gradlew build</code>

### Executando a aplicação
Para rodar a aplicação em modo <code>dev</code>, executar o comando.<br/>
<code>./gradlew quarkusDev</code><br/>
Para utilizar a aplicação como um container Docker, primeiro build o container e na sequência execute o container.<br/>
<code>docker build -f Dockerfile -t gerenciador/pedidos:0.1.0 .</code><br/>
<code>docker container run -d --name pedidos -p 8080:8080 gerenciador/pedidos:0.1.0</code><br/>

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