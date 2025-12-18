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
public class DtoFresa implements IDTO
{
    private Integer id;
    private String codigo;
    private String nombre;
    private Integer tipo;
    private Integer temporada;
    private Float rendimiento;
}
