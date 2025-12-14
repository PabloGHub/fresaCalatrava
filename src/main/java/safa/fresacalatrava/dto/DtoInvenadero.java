package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import safa.fresacalatrava.GetterSetter;
import safa.fresacalatrava.Operable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoInvenadero implements IDTO, GetterSetter, Operable
{
    private Integer _id;
    private String _codigo;
    private Integer _capacidad;
    private Integer _tipo;
    private DtoFinca _finca;
}
