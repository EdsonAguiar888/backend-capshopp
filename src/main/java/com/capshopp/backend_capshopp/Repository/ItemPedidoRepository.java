package com.capshopp.backend_capshopp.Repository;
import com.capshopp.backend_capshopp.Entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    boolean existsByProdutoId(Long produtoId);
}
