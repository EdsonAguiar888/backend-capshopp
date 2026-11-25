INSERT INTO usuarios (nome, email, senha, perfil) VALUES
('Administrador CapShopp','admin@teste.com','12345','ADMIN'),
('Loja 1','loja1@teste.com','12345','LOJISTA'),
('Loja 2','loja2@teste.com','12345','LOJISTA'),
('Cliente 1','cliente1@teste.com','12345','CLIENTE'),
('Cliente 2','cliente2@teste.com','12345','CLIENTE');

INSERT INTO produtos (lojista_id, nome, descricao, preco, estoque, imagem, ativo)
VALUES
((SELECT id FROM usuarios WHERE email='loja1@teste.com'), 'Camiseta', 'Camiseta de exemplo', 59.90, 15, 'https://example.com/camiseta.jpg', TRUE),
((SELECT id FROM usuarios WHERE email='loja1@teste.com'), 'Mochila', 'Mochila de exemplo', 129.90, 8, 'https://example.com/mochila.jpg', TRUE),
((SELECT id FROM usuarios WHERE email='loja2@teste.com'), 'Mouse', 'Mouse de exemplo', 79.90, 10, 'https://example.com/mouse.jpg', TRUE),
((SELECT id FROM usuarios WHERE email='loja2@teste.com'), 'Teclado', 'Teclado de exemplo', 149.90, 5, 'https://example.com/teclado.jpg', TRUE);
