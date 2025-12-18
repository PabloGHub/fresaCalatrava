package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.fresacalatrava.IGSClass;
import safa.fresacalatrava.IGetterSetter;
import safa.fresacalatrava.IOperable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoInvenadero implements IDTO
{
    private Integer _id;
    private String _codigo;
    private Integer _capacidad;
    private Integer _tipo;
    private DtoFinca _finca;
}
