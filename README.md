# Projeto Colaboradores REST API

Este é um projeto que implementa uma REST API para gerenciar informações de colaborador.

## Configuração do Banco de Dados

Este projeto utiliza MySQL como banco de dados. Abaixo está o script SQL para criar a tabela `colaborador`:

```sql
CREATE TABLE colaborador (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cargo VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    forca_senha VARCHAR(50)
);


Como Executar o Projeto
Clone o repositório para o seu ambiente local.
Importe o projeto em sua IDE de preferência.
Certifique-se de ter o MySQL instalado e em execução.
Configure as informações de conexão do banco de dados no arquivo application.properties.
Execute o projeto.
Endpoints da API
A API oferece os seguintes endpoints:

GET /colaboradores: Retorna todos os colaboradores.
GET /colaborador/{id}: Retorna um colaborador específico pelo ID.
POST /colaborador: Adiciona um novo colaborador.
PUT /colaborador/{id}/senha: Atualiza a senha de um colaborador pelo ID.
