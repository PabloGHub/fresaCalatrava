package safa.fresacalatrava.modelo;

/*create or replace table valoracion (
	id int auto_increment primary key,
	fecha date,
	puntuacion int,
	comentario varchar(500),
	id_pedido int not null,
	constraint fk_valoracion_pedido
	foreign key (id_pedido)
	references pedido(id)
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
        name = "valoracion", catalog = "fresa_calatrava",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id"}) }
)
public class Valoracion implements GetterSetter //extends TipoCapturado<Valoracion>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fecha")
    private java.sql.Date fecha; // TODO: cambiar

    @Column(name = "puntuacion")
    private Integer puntuacion;

    @Column(name = "comentario", length = 500)
    private String comentario;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;
}
