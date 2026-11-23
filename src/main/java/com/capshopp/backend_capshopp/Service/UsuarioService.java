package com.capshopp.backend_capshopp.Service;
import com.capshopp.backend_capshopp.Entity.Usuario;
import com.capshopp.backend_capshopp.Enums.PerfilUsuario;
import com.capshopp.backend_capshopp.Repository.UsuarioRepository;
import com.capshopp.backend_capshopp.Repository.ProdutoRepository;
import com.capshopp.backend_capshopp.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import static org.springframework.http.HttpStatus.*;
@Service
public class UsuarioService {
    private final UsuarioRepository usuarios;
    private final ProdutoRepository produtos;
    public UsuarioService(UsuarioRepository usuarios, ProdutoRepository produtos) {
        this.usuarios = usuarios; this.produtos = produtos;
    }
    public ContaResponse login(LoginRequest dados) {
        if (dados == null || dados.getEmail() == null || dados.getSenha() == null) {
            throw new ResponseStatusException(BAD_REQUEST, "Informe email e senha");
        }
        Usuario usuario = usuarios.findByEmailIgnoreCase(dados.getEmail().trim()).orElse(null);
        if (usuario == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Email ou senha invalidos");
        } else if (!usuario.getSenha().equals(dados.getSenha())) {
            throw new ResponseStatusException(UNAUTHORIZED, "Email ou senha invalidos");
        } else {
            return resposta(usuario);
        }
    }
    public List<ContaResponse> listar() {
        return usuarios.findAll().stream().map(this::resposta).toList();
    }
    public ContaResponse buscar(Long id) { return resposta(encontrar(id)); }
    public ContaResponse criar(UsuarioRequest dados) {
        validar(dados);
        if (usuarios.findByEmailIgnoreCase(dados.getEmail().trim()).isPresent()) {
            throw new ResponseStatusException(CONFLICT, "Email ja cadastrado");
        }
        Usuario usuario = new Usuario(dados.getNome(), dados.getEmail().trim().toLowerCase(), dados.getSenha(), dados.getPerfil());
        return resposta(usuarios.save(usuario));
    }
    public ContaResponse atualizar(Long id, UsuarioRequest dados) {
        validar(dados);
        Usuario usuario = encontrar(id);
        usuarios.findByEmailIgnoreCase(dados.getEmail().trim()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) throw new ResponseStatusException(CONFLICT, "Email ja cadastrado");
        });
        usuario.setNome(dados.getNome()); usuario.setEmail(dados.getEmail().trim().toLowerCase());
        usuario.setSenha(dados.getSenha()); usuario.setPerfil(dados.getPerfil());
        return resposta(usuarios.save(usuario));
    }
    public void excluir(Long id) {
        Usuario usuario = encontrar(id);
        try { usuarios.delete(usuario); usuarios.flush(); }
        catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new ResponseStatusException(CONFLICT, "Conta possui produtos ou pedidos", e);
        }
    }
    public Map<String, Object> dashboard() {
        return Map.of("lojas", usuarios.findByPerfil(PerfilUsuario.LOJISTA).size(),
                "clientes", usuarios.findByPerfil(PerfilUsuario.CLIENTE).size(),
                "produtos", produtos.count(), "contas", usuarios.count(),
                "listaLojas", usuarios.findByPerfil(PerfilUsuario.LOJISTA).stream().map(this::resposta).toList(),
                "listaClientes", usuarios.findByPerfil(PerfilUsuario.CLIENTE).stream().map(this::resposta).toList());
    }
    private Usuario encontrar(Long id) {
        return usuarios.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuario nao encontrado"));
    }
    private void validar(UsuarioRequest dados) {
        if (dados == null || dados.getNome() == null || dados.getNome().isBlank() || dados.getEmail() == null ||
            dados.getEmail().isBlank() || dados.getSenha() == null || dados.getSenha().isBlank() || dados.getPerfil() == null)
            throw new ResponseStatusException(BAD_REQUEST, "Preencha nome, email, senha e perfil");
    }
    private ContaResponse resposta(Usuario usuario) {
        return new ContaResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getPerfil());
    }
}
