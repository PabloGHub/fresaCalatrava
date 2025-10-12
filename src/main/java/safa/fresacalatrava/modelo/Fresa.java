package safa.fresacalatrava.modelo;

/*
create or replace table fresa(
	id int auto_increment primary key,
	codigo varchar(50) unique  not null,
	nombre varchar(150),
	tipo int(2),
	temporada int(2),
	rendimiento float
);
*/

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table
(
        name = "fresa", catalog = "fresa_calatrava",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id"}) }
)
public class Fresa
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "codigo", length = 50, nullable = false, unique = true)
    private String codigo;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "tipo", length = 2)
    private int tipo;

    @Column(name = "temporada", length = 2)
    private int temporada;

    @Column(name = "rendimiento")
    private float rendimiento;
}
