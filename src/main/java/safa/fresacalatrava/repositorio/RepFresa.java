package safa.fresacalatrava.repositorio;

import safa.fresacalatrava.modelo.Fresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepFresa extends JpaRepository<Fresa, Integer>
{ }
