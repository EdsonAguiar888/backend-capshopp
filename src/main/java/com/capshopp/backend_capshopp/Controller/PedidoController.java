package com.capshopp.backend_capshopp.Controller;
import com.capshopp.backend_capshopp.Service.PedidoService;
import com.capshopp.backend_capshopp.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PedidoController {

    private final PedidoService service;
    public PedidoController(PedidoService service) { this.service=service; }

    @PostMapping("/compras")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PedidoResponse> comprar(@RequestBody PedidoRequest dados) { return service.comprar(dados); }

    @GetMapping("/pedidos")
    public List<PedidoResponse> listar(@RequestParam(required=false) Long clienteId, @RequestParam(required=false) Long lojistaId) {
        return service.listar(clienteId,lojistaId);
    }

    @GetMapping("/pedidos/{id}")
    public PedidoResponse buscar(@PathVariable Long id) { return service.buscar(id); }

    @PatchMapping("/pedidos/{id}/concluir")
    public PedidoResponse concluir(@PathVariable Long id) { return service.concluir(id); }
    
    @PatchMapping("/pedidos/{id}/cancelar")
    public PedidoResponse cancelar(@PathVariable Long id) { return service.cancelar(id); }
}
