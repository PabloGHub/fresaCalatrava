package safa.fresacalatrava.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFresa
{
    private Integer _id;
    private String _codigo;
    private String _nombre;
    private Integer _tipo;
    private Integer _temporada;
    private Float _rendimiento;
}
