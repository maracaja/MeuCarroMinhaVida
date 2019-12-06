CREATE DATABASE IF NOT EXISTS ecommerce CHARACTER SET latin1 COLLATE latin1_general_ci;

USE ecommerce;

CREATE TABLE IF NOT EXISTS Marca
(
	id 		INT UNSIGNED NOT NULL AUTO_INCREMENT,
    marca	VARCHAR(32) NOT NULL,
    urlLogo VARCHAR(256),
    CONSTRAINT pk_marca PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Produto
(
	id 			INT UNSIGNED NOT NULL AUTO_INCREMENT,
    ativo		BOOLEAN DEFAULT TRUE,
    marca 		INT UNSIGNED NOT NULL,
    modelo 		VARCHAR(32) NOT NULL DEFAULT '',
    ano 		INT UNSIGNED DEFAULT 2019,
    novo 		BOOLEAN DEFAULT TRUE,
    descricao 	VARCHAR(8192),
    preco 		DECIMAL(10, 2) NOT NULL DEFAULT 0.01,
    urlFoto1 	VARCHAR(256),
    urlFoto2 	VARCHAR(256),
    urlFoto3 	VARCHAR(256),
    CONSTRAINT pk_produto PRIMARY KEY (id),
    CONSTRAINT fk_marca_produto FOREIGN KEY (marca) REFERENCES Marca(id)
);

CREATE TABLE IF NOT EXISTS Cliente
(
	id 				INT UNSIGNED NOT NULL AUTO_INCREMENT,
    nome 			VARCHAR(64) NOT NULL,
    cpf 			CHAR(11) NOT NULL UNIQUE,
    dataNasc 		DATE NOT NULL DEFAULT 19000101,
    email 			VARCHAR(32) NOT NULL UNIQUE,
    senha 			VARCHAR(32) NOT NULL COLLATE latin1_general_cs,
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Endereco
(
	id 				INT UNSIGNED NOT NULL AUTO_INCREMENT,
    cep				VARCHAR(9) NOT NULL,
    logradouro 		VARCHAR(64) NOT NULL,
    numero 			VARCHAR(8) DEFAULT 'S/N', /* Pode ter letras ou mesmo s/n */
    complemento 	VARCHAR(32),
    bairro 			VARCHAR(32) DEFAULT '',
    cidade 			VARCHAR(32) NOT NULL,
    uf 				CHAR(2) NOT NULL,
    CONSTRAINT pk_endereco PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS EnderecosClientes
(
	id_cliente 	INT UNSIGNED NOT NULL,
    id_endereco INT UNSIGNED NOT NULL UNIQUE,
    marcador 	VARCHAR(32) DEFAULT '', /* Nome que o cliente pode customizar */
    CONSTRAINT pk_endcli PRIMARY KEY (id_cliente, id_endereco),
    CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_endereco FOREIGN KEY (id_endereco) REFERENCES Endereco(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Pedido
(
	id 			INT UNSIGNED NOT NULL AUTO_INCREMENT,
    dataHora 	DATETIME NOT NULL,
    id_cliente 	INT UNSIGNED NOT NULL,
    entrega		INT UNSIGNED NOT NULL,
    CONSTRAINT pk_pedido PRIMARY KEY (id),
    CONSTRAINT fk_cliente_pedido FOREIGN KEY (id_cliente) REFERENCES Cliente(id),
    CONSTRAINT fk_endereco_entrega FOREIGN KEY (entrega) REFERENCES Endereco(id)
);

CREATE TABLE IF NOT EXISTS ItemPedido
(
	id_pedido 	INT UNSIGNED NOT NULL,
    id_produto 	INT UNSIGNED NOT NULL,
    precoUnit 	FLOAT NOT NULL DEFAULT 0.01,
    quantidade  INT UNSIGNED DEFAULT 1,
    CONSTRAINT pk_itemPedido PRIMARY KEY (id_pedido, id_produto),
    CONSTRAINT fk_pedido_itemPedido FOREIGN KEY (id_pedido) REFERENCES Pedido(id) ON DELETE CASCADE,
    CONSTRAINT fk_produto_itemPedido FOREIGN KEY (id_produto) REFERENCES Produto(id)
);

CREATE TABLE IF NOT EXISTS SenhaAdmin (senha VARCHAR(32) NOT NULL DEFAULT 'admin');
INSERT INTO SenhaAdmin VALUES ('xEvCt!b}');

CREATE USER 'comercio'@'localhost' IDENTIFIED BY 'Projeto2019';
GRANT INSERT ON ecommerce.* TO 'comercio'@'localhost';
GRANT SELECT ON ecommerce.* TO 'comercio'@'localhost';
GRANT UPDATE ON ecommerce.* TO 'comercio'@'localhost';
GRANT DELETE ON ecommerce.Endereco TO 'comercio'@'localhost';
GRANT DELETE ON ecommerce.EnderecosClientes TO 'comercio'@'localhost';
FLUSH PRIVILEGES;