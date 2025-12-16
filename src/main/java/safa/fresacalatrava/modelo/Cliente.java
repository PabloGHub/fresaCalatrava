package safa.fresacalatrava.modelo;

/*
create or replace table cliente (
        id int auto_increment primary key,
        nombre varchar(150),
        direccion varchar(150),
        telefono varchar(20)
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
        name = "cliente", catalog = "fresa_calatrava",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"id"}) }
)
public class Cliente implements IModelo //extends TipoCapturado<Cliente>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "telefono", length = 20)
    private String telefono;
}
