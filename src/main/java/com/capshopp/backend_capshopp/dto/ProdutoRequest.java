package com.capshopp.backend_capshopp.dto;
import java.math.BigDecimal;
public class ProdutoRequest {

    private Long lojistaId;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int estoque;
    private String imagem;
    
    public ProdutoRequest() {}

    public Long getLojistaId() { return lojistaId; }
    public void setLojistaId(Long lojistaId) { this.lojistaId = lojistaId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
}
