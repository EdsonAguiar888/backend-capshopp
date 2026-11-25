package com.capshopp.backend_capshopp.dto;

import java.util.List;

public class PedidoRequest {

    private Long clienteId;
    private List<ItemCompraRequest> itens;

    public PedidoRequest() {}
    
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public List<ItemCompraRequest> getItens() { return itens; }
    public void setItens(List<ItemCompraRequest> itens) { this.itens = itens; }
}
