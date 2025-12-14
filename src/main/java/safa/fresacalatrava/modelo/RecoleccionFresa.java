package safa.fresacalatrava.modelo;

/*
create or replace table recoleccion_fresa(
        id int auto_increment primary key,
        id_recoleccion int not null,
        id_fresa int not null,
        cantidad int,
        constraint fk_recoleccion_fresa_recoleccion
	foreign key (id_recoleccion)
references recoleccion(id),
constraint fk_recoleccion_fresa_fresa
foreign key (id_fresa)
references fresa(id)
);
*/

import jakarta.persistence.*;
import lombok.*;
import safa.fresacalatrava.GetterSetter;
import safa.fresacalatrava.TipoCapturado;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table
(
        name = "recoleccion_fresa", catalog = "fresa_calatrava",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id"}) }
)
public class RecoleccionFresa implements GetterSetter //extends TipoCapturado<RecoleccionFresa>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_recoleccion", nullable = false)
    private Recoleccion recoleccion;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_fresa", nullable = false)
    private Fresa fresa;
}
