package com.capshopp.backend_capshopp.dto;
import java.math.BigDecimal;
public class ProdutoResponse {
    private Long id;
    private Long lojistaId;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int estoque;
    private String imagem;
    private boolean ativo;

    public ProdutoResponse() {}

    public ProdutoResponse(Long id, Long lojistaId, String nome, String descricao, BigDecimal preco, int estoque, String imagem, boolean ativo) {
        this.id=id; this.lojistaId=lojistaId; this.nome=nome; this.descricao=descricao;
        this.preco=preco; this.estoque=estoque; this.imagem=imagem; this.ativo=ativo;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
