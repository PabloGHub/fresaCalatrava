package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.fresacalatrava.IGSClass;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFinca implements IDTO
{
    private Integer id;
    private String nombre;
    private Float latitud;
    private Float longitud;
    private Integer supercie; // supercifie
}
