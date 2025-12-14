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
public class DtoInvernadero_SinRela implements IDTO, GetterSetter, Operable
{
    private Integer _id;
    private Integer _codigo;
    private Integer _capacidad;
    private Integer _tipo;
}
