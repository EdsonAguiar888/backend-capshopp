package com.capshopp.backend_capshopp.dto;
import com.capshopp.backend_capshopp.Enums.StatusPedido;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class PedidoResponse {

    private Long id;
    private Long clienteId;
    private Long lojistaId;
    private Instant criadoEm;
    private BigDecimal total;
    private StatusPedido status;
    private List<ItemPedidoResponse> itens;

    public PedidoResponse() {}

    public PedidoResponse(Long id, Long clienteId, Long lojistaId, Instant criadoEm, BigDecimal total, StatusPedido status, List<ItemPedidoResponse> itens) {
        this.id=id; this.clienteId=clienteId; this.lojistaId=lojistaId; this.criadoEm=criadoEm;
        this.total=total; this.status=status; this.itens=itens;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getLojistaId() { return lojistaId; }
    public void setLojistaId(Long lojistaId) { this.lojistaId = lojistaId; }
    public Instant getCriadoEm() { return criadoEm; }
    public void setCriadoEm(Instant criadoEm) { this.criadoEm = criadoEm; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public StatusPedido getStatus() { return status; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public List<ItemPedidoResponse> getItens() { return itens; }
    public void setItens(List<ItemPedidoResponse> itens) { this.itens = itens; }
}
