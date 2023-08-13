# API-Management

## Collection Postman (Clique para acessar os endpoints)
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/23327087-d95d7560-ef0b-4e9c-84fa-9844a4016aab?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D23327087-d95d7560-ef0b-4e9c-84fa-9844a4016aab%26entityType%3Dcollection%26workspaceId%3D08fdd035-8650-414e-9d3c-7a4827205d4e)

## Entidades

- pessoa (id, nome, departamento e lista de tarefas)

- tarefa (id, título, descrição, prazo, departamento, duração, pessoa alocada e se já foi finalizado)

## Funcionalidades desejadas

- Adicionar um pessoa => (post/pessoas)

- Alterar um pessoa => (put/pessoas/{id})

- Remover pessoa => (delete/pessoas/{id})

- Adicionar um tarefa => (post/tarefas)

- Alocar uma pessoa na tarefa que tenha o mesmo departamento => (put/tarefas/alocar/{id})

- Finalizar a tarefa => (put/tarefas/finalizar/{id})

- Listar pessoas trazendo nome, departamento, total horas gastas nas tarefas => (get/pessoas)

- Buscar pessoas por nome e período, retorna média de horas gastas por tarefa => (get/pessoas/gastos)

- Listar 3 tarefas que estejam sem pessoa alocada com os prazos mais antigos => (get/tarefas/pendentes)

- Listar departamento e quantidade de pessoas e tarefas => (get/departamentos)

## Bancos de dados
- PostgreSQL
