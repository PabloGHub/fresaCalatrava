package safa.fresacalatrava.repositorio;

import safa.fresacalatrava.modelo.Recoleccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepRecoleccion extends JpaRepository<Recoleccion,Integer>
{ }
