package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFinca
{
    private Integer _id;
    private String _nombre;
    private Float _latitud;
    private Float _longitud;
    private Integer _supercifie;
}
