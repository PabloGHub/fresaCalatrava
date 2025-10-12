package safa.fresacalatrava.modelo;

/*create or replace table pedido (
	id int auto_increment primary key,
	codigo varchar(50),
	fecha date,
	estado int(2),
	importe_total float,
	id_cliente int not null,
	constraint fk_pedido_cliente
	foreign key (id_cliente)
	references cliente(id)
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
        name = "pedido", catalog = "fresa_calatrava",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id"}) }
)
public class Pedido
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "codigo", length = 50)
    private String codigo;

    @Column(name = "fecha")
    private java.sql.Date fecha;

    @Column(name = "estado", length = 2)
    private int estado;

    @Column(name = "importe_total")
    private float importeTotal;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
}
