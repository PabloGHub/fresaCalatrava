package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.fresacalatrava.GetterSetter;
import safa.fresacalatrava.Operable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFecha implements IDTO, GetterSetter, Operable
{
    private Integer _dia;
    private Integer _mes;
    private Integer _anno;
}
