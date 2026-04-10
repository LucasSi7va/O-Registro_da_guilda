
# Nome: Lucas Silva De Souza

# Bem Vindos ao Guilda Dos Aventureiros
## aqui mostrarei como funciona o registro da guilda


![Contém uma imagem de:](https://i.pinimg.com/236x/c2/f7/b1/c2f7b1a75b87b9f1003ad85b406c9e7b.jpg)



um projeto mostra como funciona os Registro dos aventureiro onde o usuario podera fazer buscas , adicionar aventureiro ou companheiro , buscar missoes e participacao , e outros 


Para iniciar o projeto:

precisa acessar a linha de comando dentro do projeto e digitar:

```
docker compose up -d
```

e depois acessar o postgres e digitar:

```
ALTER USER postgres WITH PASSWORD '12345';
```

E aproveite o registro da guilda  ⚔️
## Portas de acesso

| Portas | Acesso   |
| ------ | -------- |
| 8080   | API-REST |
| 9200   | Elastic  |
| 5601   | Kibana   |

# na pasta PostQuerys.Aventureiro 

Esta alumas querys que testei no api 


# Pasta Elastic esta os service e controller do elastic


E aqui esta as 3 partes explicando

--- 
## Navegação por versões

Cada etapa do projeto está separada por branches:

- [Parte 1 - Estrutura inicial](https://github.com/seu-usuario/seu-repo/tree/parte1)
- [Parte 2 - Integração com JPA](https://github.com/seu-usuario/seu-repo/tree/parte2)
- [Parte 3 - Elasticsearch](https://github.com/seu-usuario/seu-repo/tree/parte3)

---

# [Parte 1](#) 
eu criei uma API-REST no SpringBoot 
onde criei as dtos,service,controller 

### Dtos 

Utilizei para capturar os dados como (nome, especie,  etc.)

- Adicionar
- Remover
- Filtrar
- Atualizar

### Controller

O Controller utilizei para receber as requisições do cliente como HTTP e chamar a camada de serviço e retornar a resposta

### Service 

Utilizei o service para criar , atualizar, remover e filtrar os aventureiros e companheiros 

---
# [PARTE 2 ](#)

Aqui eu já utilizei o JPA(Banco de dados) integrando para API-REST  
onde criei as entidades, repositorios, testes 

### Entidades

Utilizei para mapear as tabelas do banco de dados para objetos da aplicação

### Repositórios 

Utilizei para interagir com o banco de dados, realizando operações de persistência como salvar, buscar, atualizar e deletar 

### Testes 

Utilizei para verificar se a camada de serviço esta funcionando corretamente e verificar se o Hibernate esta mapeando corretamente

---
# [PARTE 3] 
Aqui eu utilizei o ElasticSearch e criando filtro para materialized View

### Materialized View 

Aqui Mapiei o Materialized e fiz um filtro para verificar os ultimos 15 dias do rank missao para aventureiros 

### ElasticSearch 

utilizei para realizar buscas avançadas e eficientes nos dados da aplicação.


---
