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
public class DtoFecha implements IDTO
{
    private Integer _dia;
    private Integer _mes;
    private Integer _anno;
}
