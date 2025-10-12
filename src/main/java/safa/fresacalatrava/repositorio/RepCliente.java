package safa.fresacalatrava.repositorio;

import safa.fresacalatrava.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepCliente extends JpaRepository<Cliente, Integer>
{ }
