package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.fresacalatrava.IGetterSetter;
import safa.fresacalatrava.IMinimoGettersSetters;
import safa.fresacalatrava.IOperable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFinca implements IDTO, IMinimoGettersSetters
{
    private Integer id;
    private String nombre;
    private Float latitud;
    private Float longitud;
    private Integer supercie; // supercifie
}
