package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoInvernadero_SinRela implements IDTO
{
    private Integer _id;
    private Integer _codigo;
    private Integer _capacidad;
    private Integer _tipo;
}
