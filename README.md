# Microservices with Spring Boot

Esse projeto está dividido da seguinte maneira: um gatewayZull, um servidor Eureka e dois microservices. 

## spring-eureka-sales - port: 8300

Esse é o microservice responsável por observar continuamente o diretório data/in e gerar reporter sempre que o mesmo receber um no arquivo. Seu funcionamento e muito simples, ao ler um arquivo .TXT no formato especificado abaixo, o mesmo gera um arquivo .JSON com os dados necessários apra o report pedido e insere na pasta data/out

## spring-eureka-gallery - port: 8100

Esse microservice é o dahsboard do usuário, sendo sua url /reports mostra em tela todos os relatórios dispóníveis


## spring-eureka-server - port: 8761
Eureka server

## spring-eureka-zull (gateway) - port: 8762

Todas as chamadas externas são feitas via gateway zull. Para acessar os reports: localhost:8762/gallery/reports

## TODO
* Melhorar visualização de dados no dashboard
* Testes unitários
* Commom Service to handle path variables
* Database connection


## Descrição:
Criar um sistema de análise de dados de venda que irá importar lotes de arquivos e produzir
um relatório baseado em informações presentes no mesmo.

Existem 3 tipos de dados dentro dos arquivos e eles podem ser distinguidos pelo seu
identificador que estará presente na primeira coluna de cada linha, onde o separador de
colunas é o caractere “ç”.

### Dados do vendedor
Os dados do vendedor possuem o identificador 001 e seguem o seguinte formato:
001çCPFçNameçSalary

### Dados do cliente
Os dados do cliente possuem o identificador 002 e seguem o seguinte formato:
002çCNPJçNameçBusiness Area

### Dados de venda
Os dados de venda possuem o identificador 003 e seguem o seguinte formato:

003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name

Exemplo de conteúdo total do arquivo:

001ç1234567891234çPedroç50000

001ç3245678865434çPauloç40000.99

002ç2345675434544345çJose da SilvaçRural

002ç2345675433444345çEduardo PereiraçRural

003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro

003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo

## O sistema deverá ler continuamente todos os arquivos dentro do diretório padrão
HOMEPATH/data/in e colocar o arquivo de saída em HOMEPATH/data/out.

## No arquivo de saída o sistema deverá possuir os seguintes dados:
• Quantidade de clientes no arquivo de entrada
• Quantidade de vendedores no arquivo de entrada
• ID da venda mais cara
• O pior vendedor
