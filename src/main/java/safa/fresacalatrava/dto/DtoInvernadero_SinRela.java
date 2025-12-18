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
public class DtoInvernadero_SinRela implements IDTO
{
    private Integer id;
    private Integer codigo;
    private Integer capacidad;
    private Integer tipo;
}
