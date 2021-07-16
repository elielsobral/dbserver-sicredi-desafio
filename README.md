# DESAFIO SICREDI - Sobre o Projeto

## Frameworks utilizados

* [Java 11](#)
* [Slf4j](#)
* [OpenFeign](#)
* [Lombok](#)
* [Spring Cloud Gateway](#)
* [Eureka](#)
* [H2 Database](#)

## Ordem de inicialização dos Microserviços

1. sc-eureka-server
2. sc-cloud-gateway-server
3. sc-associado
4. sc-pauta
5. sc-sessao
6. sc-voto

Após a inicialização dos microserviços para teste seguir o cenario abaixo:

## Cadastrar Associado  

<http://localhost:8765/sc-associado/associados/v1>  

REQUEST BODY
{  
    "nome": "João da Cooperativa",  
    "cpf": "39143296530"  
}  

## Buscar Associado  

<http://localhost:8765/sc-associado//associados/v1/1>  

RESPONSE BODY
{  
    "id": 1,  
    "nome": "João da Cooperativa",  
    "cpf": "39143296530"  
}  
