package safa.fresacalatrava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFecha implements IDTO
{
    private Integer _dia;
    private Integer _mes;
    private Integer _anno;
}
