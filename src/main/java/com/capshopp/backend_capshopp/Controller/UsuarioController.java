package com.capshopp.backend_capshopp.Controller;
import com.capshopp.backend_capshopp.Service.UsuarioService;
import com.capshopp.backend_capshopp.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service) { this.service=service; }
    @PostMapping("/auth/login")
    public ContaResponse login(@RequestBody LoginRequest dados) { return service.login(dados); }
    @GetMapping("/usuarios")
    public List<ContaResponse> listar() { return service.listar(); }
    @GetMapping("/usuarios/{id}")
    public ContaResponse buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping("/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ContaResponse criar(@RequestBody UsuarioRequest dados) { return service.criar(dados); }
    @PutMapping("/usuarios/{id}")
    public ContaResponse atualizar(@PathVariable Long id, @RequestBody UsuarioRequest dados) { return service.atualizar(id,dados); }
    @DeleteMapping("/usuarios/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) { service.excluir(id); }
    @GetMapping("/admin/dashboard")
    public Map<String,Object> dashboard() { return service.dashboard(); }
}
