package safa.fresacalatrava.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import safa.fresacalatrava.GetterSetter;
import safa.fresacalatrava.Operable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFresa implements IDTO, GetterSetter, Operable
{
    private Integer _id;
    private String _codigo;
    private String _nombre;
    private Integer _tipo;
    private Integer _temporada;
    private Float _rendimiento;
}
