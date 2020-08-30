<h1 align="center">Cep Api</h1>

<p align="center">
 <b>Aplicação backend que faz busca de Cep</b></br>
  <span>Leandro Silva Yamaniha </sub>
</p>

# Visão Geral da Arquitetura

![alt text](https://github.com/leandro-yamaniha/cep/blob/tunning/image/diagrama.png)

# Fluxograma

![alt text](https://github.com/leandro-yamaniha/cep/blob/tunning/image/fluxo.png)

# Resumo

Neste projeto foi escolhido utilizar a linguagem Java com o framework Spring Boot, por já usar no dia a dia e pelo grande número de bibliotecas e ferramentas que o Spring proporcia para facilitar o desenvolvimento, manutenção e evolução do microserviço. Além do Spring Boot, já ter um servidor web embarcado, o servidor web padrão TomCat.

O acesso ao banco de dados foi escolhido o uso do Spring Data JPA por simplificar, acelerar o desenvolvimento e considerando com um microserviço REST que pode evoluir, as construção de novas implementações de verbos tais como POST, PUT, DELETE, por exemplo, ficam muito fáceis, pois nesta implementação já foi criada a infraestrutura de modelo e repositório que para inclusão destes verbos ou outras funcionalidades já aproveita parte do que foi implementado.

Além disso, foi considerado para efeito de oportunidade e melhora de performance o uso de cache, foi abstraido neste desenvolvimento que os serviços que consumem o cep, em muitos momentos podem consultar ceps repetidos, desta forma minimizamos o custo de acesso ao banco de dados.

No fluxograma também foi considerado caso cep informado não seja encontrado, para melhor uso de processamento do banco de dados vs multiplos acessos ao banco, foi considerado que na sugestão de cep, já gerasse uma lista de possiveis cep  sugeridos, para fazer apenas uma busca no banco de dados baseado na lista e pegasse o primeiro registro baseado na query ordenado de forma decrescente. Desta forma se evita muitas consultas ao banco para verificar cada item da lista de sugestão existe ou não no banco de dados.

Para auxiliar o monitoramento da aplicação foi feito o uso das seguintes ferramentas:
- Spring Boot Admin Server, responsável para mostrar as metricas da aplicação , de uso de memória, processamento, número de threads, saúde da aplicação.
- LogStash, ElasticSearch e Kibana - para processar e monitorar os logs
- MySQL - banco de dados para armazenar e consultar os dados
- Cep Api - o próprio microserviço

| Serviço                 | Portas                 |
|-------------------------|------------------------|
|Spring Boot Admin Server | 8081                   |
|LogStash                 | 5000, 5044, 8080, 9600 | 
|ElasticSearch            | 9200, 9300             |
|Kibana                   | 5601                   |
|MySQLServer              | 3306                   |
|Cep Api                  | 9100                   |

Se o microserviço estivesse hospedado na nuvem o recomendado para monitoria seria o uso de Prometheus, Micrometer e  Grafana, para simplificar usei o spring boot admin.

A aplicação Spring Boot Admin Server foi feita uma implementação bem simples para uso no repósitorio, `https://github.com/leandro-yamaniha/spring-boot-admin`. 

Os serviços LogStash, ElasticSearch, Kibana e MySQL serão iniciado via docker, que será descrito a frente.

# Requisitos

[Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

[Maven](https://maven.apache.org/)

[Docker](https://www.docker.com/)

[Spring Tools 4 for Eclipse](https://spring.io/tools) 

[IntelliJ](https://www.jetbrains.com/pt-br/idea/)

[Lombok](https://projectlombok.org/)

[Eclipse - Java Code Coverage](https://www.eclemma.org/)

[Spring Boot Admin Server](https://github.com/leandro-yamaniha/spring-boot-admin/)


# Instalação OS X & Linux:

**Java 11 - SDKMAN:**

```sh
[Instalação SDKMAN](https://sdkman.io/install)
sdk i java 11.0.2-open
```

**Lombok plugin:**

```sh
[Instalação IntelliJ](https://projectlombok.org/setup/intellij)
[Instalação Eclipse](https://projectlombok.org/setup/eclipse)
```

**Docker**

```sh
[Instalação Docker](https://docs.docker.com/engine/install/)
```

**Maven**

```sh
[Instalação Maven](https://maven.apache.org/install.html)
```

**Spring Boot Admin**

Fazer o download em https://github.com/leandro-yamaniha/spring-boot-admin/releases/download/v0.0.1-SNAPSOT/admin-0.0.1-SNAPSHOT.jar.

Em seguida rodar o comando abaixo para iniciar spring boot admin server.

```sh
java -jar admin-0.0.1-SNAPSHOT.jar
```

Quando a inicialização estiver completa, basta acessar a url http://localhost:8081.

# Configuração para Desenvolvimento

**Compilação e geração do artefato jar**

Acessar a pasta raiz do projeto:

```sh
sdk use java 11.0.2-open
mvn clean package
```

**Executar o coverage:**

```sh
sdk use java 11.0.2-open
mvn clean install jacoco:report
```

**Executar o projeto com configuração local:**

Executar o comando abaixo para iniciar o imagen docker do mysql, elasticsearch, logstash, kibana. 

```sh
docker-compose up -d
```

Foi incluido a imagem docker do sonar, mas não é requisito para inicialização do microserviço.

Execute o comando abaixo para inicialização do microservico.

O requisito mínimo para execução do microserviço é a inicialização do mysql, mas como efeito colateral não terá armazenado disponível no kibana, apenas de tela.

```sh
sdk use java 11.0.2-open
mvn clean spring-boot:run -Dspring-boot.run.profiles=loc
```

Considerando a execução local, basta usar a url, abaixo:
http://localhost:9100/cep/swagger-ui.html

Também na inicialização é retornado em tela, todos os ips possíveis com a url, para acesso ao swagger.

Sugestão de token para acesso abaixo:

```sh
Token Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJjZXAtbWFuYWdlciIsIm5hbWUiOiJsZWFuZHJvIiwiaWF0IjoxNjAxMjU5MDAwfQ.PNqDRP76lm4IAUbw4RQfbYt7rJ64rinZimA1e7x4C2A
``` 

Para ser aceito o token pelo microserviço, deve ser autenticado com o atributo "sub" como "cep-manager", payload que é gerado no site do jwt, e JWT_SECRET ser compativel com a variavel setada no application.yml.

Não estão na página do swagger, mas estão disponíveis os seguintes endpoints:
 - actuator/health - endpoint para ver a saúde do microserviço
 - actuator - mostra todos os endpoints de monitoria
Estes endpoints acima não precisam de token para acesso e são utilizados pelo Spring Boot Admin Server.

Para acompanhar o log pelo kibana, deve se acessar a url http://localhost:5601, usuário - elastic , senha - changeme.

Dentro kibana, na criação do indice pattern deve estar disponível o indice com o nome `leandro-cep-api`, basta criar usando o nome, dar next step, escolher @timestamp e clicar em create index pattern.

Desta forma já estará disponível o indice para filtrar log do aplicativo, na opção discover.

Para fazer chamadas a api utilizei o JMeter para fazer várias requisições ao endpoint para fins de testes.


# Referências

[Spring Boot](https://spring.io/projects/spring-boot)

[Spring Data](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference)

[JWT](https://jwt.io/)

[MySQL](https://www.mysql.com/)

[H2](https://www.h2database.com/html/main.html)

[JUnit5](https://junit.org/junit5/docs/current/user-guide/)

[Swagger](https://swagger.io/tools/swagger-editor/)

[Sleuth](https://spring.io/projects/spring-cloud-sleuth)

[LogStash](https://www.elastic.co/pt/logstash)

[ElasticSearch](https://www.elastic.co/pt/what-is/elasticsearch)

[Kibana](https://www.elastic.co/pt/kibana)

[PostMan](https://www.postman.com/)

[JMeter](https://jmeter.apache.org/)

[Sonar](https://docs.sonarqube.org/latest/)



