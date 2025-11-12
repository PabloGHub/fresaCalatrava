package safa.fresacalatrava.modelo;

import jakarta.persistence.*;
import lombok.*;

/*
create or replace table invernadero(
	id int auto_increment primary key,
	codigo varchar(50) unique  not null,
	capacidad int ,
	tipo int(2),
	id_finca int not null,
	constraint fk_invernadero_finca foreign key (id_finca) references finca(id)
);
*/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table
(
    name = "invernadero", catalog = "fresa_calatrava",
    uniqueConstraints = { @UniqueConstraint(columnNames = {"id"}) }
)
public class Invernadero
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "codigo", length = 50, nullable = false, unique = true)
    private String codigo;

    @Column(name = "capacidad")
    private Integer capacidad;

    @Column(name = "tipo", length = 2)
    private Integer tipo;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_finca", nullable = false)
    private Finca finca;
}
