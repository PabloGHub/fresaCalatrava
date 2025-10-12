package safa.fresacalatrava.repositorio;

import safa.fresacalatrava.modelo.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepValoracion extends JpaRepository<Valoracion, Integer>
{ }
