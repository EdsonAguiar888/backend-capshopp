package com.capshopp.backend_capshopp.Controller;
import com.capshopp.backend_capshopp.Service.ProdutoService;
import com.capshopp.backend_capshopp.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api")

public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) { this.service=service; }

    @GetMapping("/catalogo")
    public List<ProdutoResponse> catalogo(@RequestParam(defaultValue="") String nome) { return service.catalogo(nome); }

    @GetMapping("/produtos")
    public List<ProdutoResponse> daLoja(@RequestParam Long lojistaId) { return service.daLoja(lojistaId); }

    @GetMapping("/produtos/{id}")
    public ProdutoResponse buscar(@PathVariable Long id) { return service.buscar(id); }

    @PostMapping("/produtos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse criar(@RequestBody ProdutoRequest dados) { return service.criar(dados); }

    @PutMapping("/produtos/{id}")
    public ProdutoResponse atualizar(@PathVariable Long id,@RequestBody ProdutoRequest dados) { return service.atualizar(id,dados); }

    @PatchMapping("/produtos/{id}/desativar")
    public ProdutoResponse desativar(@PathVariable Long id) { return service.desativar(id); }
    
    @DeleteMapping("/produtos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) { service.excluir(id); }
}
