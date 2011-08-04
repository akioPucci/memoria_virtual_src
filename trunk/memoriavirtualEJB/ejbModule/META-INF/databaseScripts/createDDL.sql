CREATE TABLE INSTITUICAO (NOME VARCHAR(255) NOT NULL, CEP VARCHAR(255), CIDADE VARCHAR(255), EMAIL VARCHAR(255) UNIQUE, ENDERECO VARCHAR(255), ESTADO VARCHAR(255), LOCALIZACAO VARCHAR(255), TELEFONE VARCHAR(255), PRIMARY KEY (NOME));
CREATE TABLE USUARIO (ID VARCHAR(255) NOT NULL, ADMINISTRADOR BOOLEAN, ATIVO BOOLEAN, EMAIL VARCHAR(255) UNIQUE, NOMECOMPLETO VARCHAR(255), SENHA VARCHAR(255), VALIDADE DATE, PRIMARY KEY (ID));
CREATE TABLE GRUPO (ID VARCHAR(255) NOT NULL, PRIMARY KEY (ID));
CREATE TABLE ACESSO (GRUPO VARCHAR(255) NOT NULL, INSTITUICAO VARCHAR(255) NOT NULL, USUARIO VARCHAR(255) NOT NULL, PRIMARY KEY (GRUPO, INSTITUICAO, USUARIO));
ALTER TABLE ACESSO ADD CONSTRAINT FK_ACESSO_USUARIO FOREIGN KEY (USUARIO) REFERENCES USUARIO (ID);
ALTER TABLE ACESSO ADD CONSTRAINT FK_ACESSO_GRUPO FOREIGN KEY (GRUPO) REFERENCES GRUPO (ID);
ALTER TABLE ACESSO ADD CONSTRAINT FK_ACESSO_INSTITUICAO FOREIGN KEY (INSTITUICAO) REFERENCES INSTITUICAO (NOME);

/* Usuario admin padr�o para o desenvolvimento do sistema:
ID:mvirtual
Email:mvirtual@usp.br
senha:mvirtual
*/
INSERT INTO USUARIO values( 'mvirtual', true, true, 'mvirtual@usp.br','Mem�ria Virtual',
						'8D1E3B49C3A725414FBED43AD7E0A480DEA6220A83DF3B10C4496270FC5A1E6328732550F4AC8C4F6ADE0EAE7F82DC9CF3219D724E6369AA044FD630B9C5E178',
						null);