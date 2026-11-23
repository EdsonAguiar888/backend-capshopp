package com.capshopp.backend_capshopp.Service;
import com.capshopp.backend_capshopp.Entity.*;
import com.capshopp.backend_capshopp.Enums.PerfilUsuario;
import com.capshopp.backend_capshopp.Repository.*;
import com.capshopp.backend_capshopp.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@Service
@Transactional
public class ProdutoService {
    private final ProdutoRepository produtos;
    private final UsuarioRepository usuarios;
    private final ItemPedidoRepository itens;
    public ProdutoService(ProdutoRepository produtos, UsuarioRepository usuarios, ItemPedidoRepository itens) {
        this.produtos=produtos; this.usuarios=usuarios; this.itens=itens;
    }

    public List<ProdutoResponse> catalogo(String nome) {
        return produtos.findByAtivoTrueAndNomeContainingIgnoreCase(nome == null ? "" : nome).stream().map(this::resposta).toList();
    }

    public List<ProdutoResponse> daLoja(Long lojistaId) {
        return produtos.findByLojistaId(lojistaId).stream().map(this::resposta).toList();
    }

    public ProdutoResponse buscar(Long id) { return resposta(encontrar(id)); }

    public ProdutoResponse criar(ProdutoRequest dados) {
        validar(dados);
        Usuario lojista = usuarios.findById(dados.getLojistaId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Lojista nao encontrado"));
        if (lojista.getPerfil() != PerfilUsuario.LOJISTA) throw new ResponseStatusException(BAD_REQUEST, "Usuario nao e lojista");
        Produto p = new Produto(lojista, dados.getNome(), dados.getDescricao(), dados.getPreco(), dados.getEstoque(), dados.getImagem());
        return resposta(produtos.save(p));
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest dados) {
        validar(dados);
        Produto p = encontrar(id);
        if (!p.getLojista().getId().equals(dados.getLojistaId())) throw new ResponseStatusException(BAD_REQUEST, "Lojista diferente do produto");
        p.setNome(dados.getNome()); p.setDescricao(dados.getDescricao()); p.setPreco(dados.getPreco());
        p.setEstoque(dados.getEstoque()); p.setImagem(dados.getImagem());
        return resposta(produtos.save(p));
    }

    public ProdutoResponse desativar(Long id) {
        Produto p = encontrar(id); p.setAtivo(false); return resposta(produtos.save(p));
    }

    public void excluir(Long id) {
        Produto p = encontrar(id);
        if (itens.existsByProdutoId(id)) throw new ResponseStatusException(CONFLICT, "Produto usado em pedidos: desative em vez de excluir");
        produtos.delete(p);
    }

    private Produto encontrar(Long id) { return produtos.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Produto nao encontrado")); }
    private void validar(ProdutoRequest dados) {
        if (dados == null || dados.getLojistaId() == null || dados.getNome() == null || dados.getNome().isBlank() ||
            dados.getPreco() == null || dados.getPreco().compareTo(BigDecimal.ZERO) < 0 || dados.getEstoque() < 0)
            throw new ResponseStatusException(BAD_REQUEST, "Informe lojistaId, nome, preco e estoque validos");
    }
    
    private ProdutoResponse resposta(Produto p) {
        return new ProdutoResponse(p.getId(), p.getLojista().getId(), p.getNome(), p.getDescricao(),
                p.getPreco(), p.getEstoque(), p.getImagem(), p.isAtivo());
    }
}
