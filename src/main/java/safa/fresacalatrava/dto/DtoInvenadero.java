package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoInvenadero
{
    private Integer _id;
    private String _codigo;
    private Integer _capacidad;
    private Integer _tipo;
    private DtoFinca _finca;
}
