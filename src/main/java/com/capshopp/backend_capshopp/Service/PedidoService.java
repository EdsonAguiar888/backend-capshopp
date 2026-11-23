package com.capshopp.backend_capshopp.Service;
import com.capshopp.backend_capshopp.Entity.*;
import com.capshopp.backend_capshopp.Enums.*;
import com.capshopp.backend_capshopp.Repository.*;
import com.capshopp.backend_capshopp.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import static org.springframework.http.HttpStatus.*;
@Service
public class PedidoService {
    private final PedidoRepository pedidos;
    private final UsuarioRepository usuarios;
    private final ProdutoRepository produtos;
    public PedidoService(PedidoRepository pedidos, UsuarioRepository usuarios, ProdutoRepository produtos) {
        this.pedidos=pedidos; this.usuarios=usuarios; this.produtos=produtos;
    }
    @Transactional
    public List<PedidoResponse> comprar(PedidoRequest dados) {
        if (dados == null || dados.getClienteId() == null || dados.getItens() == null || dados.getItens().isEmpty())
            throw new ResponseStatusException(BAD_REQUEST, "Informe clienteId e itens");
        Usuario cliente = usuarios.findById(dados.getClienteId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Cliente nao encontrado"));
        if (cliente.getPerfil() != PerfilUsuario.CLIENTE) throw new ResponseStatusException(BAD_REQUEST, "Usuario nao e cliente");
        // Cada item pertence a uma loja. Uma compra pode criar um pedido por loja.
        Map<Long, Pedido> porLoja = new LinkedHashMap<>();
        Set<Long> idsRecebidos = new HashSet<>();
        for (ItemCompraRequest item : dados.getItens()) {
            if (item == null || item.getProdutoId() == null || item.getQuantidade() <= 0)
                throw new ResponseStatusException(BAD_REQUEST, "Produto e quantidade obrigatorios");
            if (!idsRecebidos.add(item.getProdutoId())) throw new ResponseStatusException(BAD_REQUEST,"Nao repita o mesmo produto na compra");
            Produto p = produtos.findById(item.getProdutoId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Produto nao encontrado"));
            if (!p.isAtivo() || p.getEstoque() < item.getQuantidade())
                throw new ResponseStatusException(CONFLICT, "Produto inativo ou estoque insuficiente");
            p.setEstoque(p.getEstoque() - item.getQuantidade());
            Long lojaId = p.getLojista().getId();
            Pedido pedido = porLoja.get(lojaId);
            if (pedido == null) {
                pedido = new Pedido(cliente, p.getLojista(), Instant.now(), BigDecimal.ZERO, StatusPedido.PENDENTE);
                porLoja.put(lojaId, pedido);
            }
            ItemPedido novo = new ItemPedido(pedido, p, p.getNome(), item.getQuantidade(), p.getPreco());
            pedido.getItens().add(novo);
            pedido.setTotal(pedido.getTotal().add(p.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()))));
        }
        List<PedidoResponse> resultado = new ArrayList<>();
        for (Pedido pedido : porLoja.values()) {
            resultado.add(resposta(pedidos.save(pedido)));
        }
        return resultado;
    }
    @Transactional(readOnly = true)
    public List<PedidoResponse> listar(Long clienteId, Long lojistaId) {
        List<Pedido> encontrados;
        if (clienteId != null) encontrados = pedidos.findByClienteId(clienteId);
        else if (lojistaId != null) encontrados = pedidos.findByLojistaId(lojistaId);
        else encontrados = pedidos.findAll();
        return encontrados.stream().map(this::resposta).toList();
    }
    @Transactional(readOnly = true)
    public PedidoResponse buscar(Long id) { return resposta(encontrar(id)); }
    @Transactional
    public PedidoResponse concluir(Long id) {
        Pedido p = encontrar(id);
        if (p.getStatus() != StatusPedido.PENDENTE) throw new ResponseStatusException(CONFLICT,"Pedido nao esta pendente");
        p.setStatus(StatusPedido.CONCLUIDO);
        return resposta(p);
    }
    @Transactional
    public PedidoResponse cancelar(Long id) {
        Pedido p = encontrar(id);
        if (p.getStatus() != StatusPedido.PENDENTE) throw new ResponseStatusException(CONFLICT,"Pedido nao esta pendente");
        for (ItemPedido item : p.getItens()) {
            Produto produto = item.getProduto();
            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
        }
        p.setStatus(StatusPedido.CANCELADO);
        return resposta(p);
    }
    private Pedido encontrar(Long id) { return pedidos.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND,"Pedido nao encontrado")); }
    private PedidoResponse resposta(Pedido p) {
        List<ItemPedidoResponse> itens = new ArrayList<>();
        for (ItemPedido item : p.getItens()) {
            itens.add(new ItemPedidoResponse(item.getProduto().getId(), item.getNomeProduto(), item.getQuantidade(), item.getPrecoUnitario()));
        }
        return new PedidoResponse(p.getId(), p.getCliente().getId(), p.getLojista().getId(), p.getCriadoEm(), p.getTotal(), p.getStatus(), itens);
    }
}
