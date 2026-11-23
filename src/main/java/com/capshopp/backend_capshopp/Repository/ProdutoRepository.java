package com.capshopp.backend_capshopp.Repository;
import com.capshopp.backend_capshopp.Entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivoTrueAndNomeContainingIgnoreCase(String nome);
    List<Produto> findByLojistaId(Long lojistaId);
}
