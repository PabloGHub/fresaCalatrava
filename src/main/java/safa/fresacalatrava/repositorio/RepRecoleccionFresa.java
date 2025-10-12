package safa.fresacalatrava.repositorio;

import safa.fresacalatrava.modelo.RecoleccionFresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepRecoleccionFresa extends  JpaRepository<RecoleccionFresa, Integer>
{ }
