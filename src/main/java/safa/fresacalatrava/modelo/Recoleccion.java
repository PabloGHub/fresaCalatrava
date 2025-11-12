package safa.fresacalatrava.modelo;

/*
create or replace table recoleccion(
        id int auto_increment primary key,
        fecha date,
        id_invernadero int not null,
        constraint fk_recoleccion_invernadero
	foreign key (id_invernadero)
references invernadero(id)
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
        name = "recoleccion", catalog = "fresa_calatrava",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id"}) }
)
public class Recoleccion
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fecha")
    private java.sql.Date fecha; // TODO: cambiar

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_invernadero", nullable = false)
    private Invernadero invernadero;
}
