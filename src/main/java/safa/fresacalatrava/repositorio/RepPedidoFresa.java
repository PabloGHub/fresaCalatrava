package safa.fresacalatrava.repositorio;

import safa.fresacalatrava.modelo.PedidoFresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepPedidoFresa extends  JpaRepository<PedidoFresa, Integer>
{ }
