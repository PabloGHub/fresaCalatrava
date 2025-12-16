package safa.fresacalatrava.modelo;

/*
create or replace table pedido_fresa(
	id int auto_increment primary key,
	id_pedido int not null,
	id_fresa int not null,
	cantidad int,
	constraint fk_pedido_fresa_pedido
	foreign key (id_pedido)
	references pedido(id),
	constraint fk_pedido_fresa_fresa
	foreign key (id_fresa)
	references fresa(id)
);
*/

import jakarta.persistence.*;
import lombok.*;
import safa.fresacalatrava.IGetterSetter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table
(
        name = "pedido_fresa", catalog = "fresa_calatrava",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id"}) }
)
public class PedidoFresa implements IModelo //extends TipoCapturado<PedidoFresa>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_fresa", nullable = false)
    private Fresa fresa;

    @Column(name = "cantidad")
    private Integer cantidad;
}
