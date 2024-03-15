```sql
CREATE TABLE colaboradores (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    cargo VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    forca_senha VARCHAR(50)
);