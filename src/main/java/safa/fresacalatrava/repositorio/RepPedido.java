package safa.fresacalatrava.repositorio;

import safa.fresacalatrava.modelo.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepPedido extends JpaRepository<Pedido, Integer>
{ }
