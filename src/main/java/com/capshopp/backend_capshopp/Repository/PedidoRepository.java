package com.capshopp.backend_capshopp.Repository;
import com.capshopp.backend_capshopp.Entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByLojistaId(Long lojistaId);
}
