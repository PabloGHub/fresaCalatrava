package safa.fresacalatrava.modelo;

import jakarta.persistence.*;
import lombok.*;

/* -- Sql
create or replace table finca (
	id int auto_increment primary key,
	nombre varchar(150),
	latitud float ,
	longitud float,
	supercie int not null
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
    name = "finca", catalog = "fresa_calatrava",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})}
)
public class Finca
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "latitud")
    private Float latitud;

    @Column(name = "longitud")
    private Float longitud;

    @Column(name = "supercie", nullable = false)
    private Integer supercie;
}
