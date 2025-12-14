package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.fresacalatrava.GetterSetter;
import safa.fresacalatrava.Operable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFinca implements IDTO, GetterSetter, Operable
{
    private Integer id;
    private String nombre;
    private Float latitud;
    private Float longitud;
    private Integer supercie; // supercifie
}
