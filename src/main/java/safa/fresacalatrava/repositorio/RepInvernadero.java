package safa.fresacalatrava.repositorio;

import safa.fresacalatrava.modelo.Invernadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepInvernadero extends JpaRepository<Invernadero, Integer>
{ }
