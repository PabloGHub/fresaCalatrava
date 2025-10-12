package safa.fresacalatrava.repositorio;

import safa.fresacalatrava.modelo.Finca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepFinca extends JpaRepository<Finca, Integer>
{ }
